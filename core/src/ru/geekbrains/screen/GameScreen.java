package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.pool.EnemyShipsPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.NewGameButton;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.Starship;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private enum State {PLAYING, GAME_OVER}

    private Texture backgroundImage;
    private Background background;
    private TextureAtlas atlas;
    private Starship starship;
    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipsPool enemyShipsPool;
    private EnemyEmitter enemyEmitter;
    private State state;
    private GameOver gameOver;
    private NewGameButton newGameButton;

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("textures/bg.png");
        background = new Background(backgroundImage);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        starship = new Starship(atlas, bulletPool, explosionPool);
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) stars[i] = new Star(atlas);
        enemyShipsPool = new EnemyShipsPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlas, enemyShipsPool);
        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);
        state = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        free();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) star.resize(worldBounds);
        starship.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyShipsPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipsPool.dispose();
        explosionPool.dispose();
        starship.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) starship.touchDown(touch, pointer, button);
        else if(state == State.GAME_OVER) newGameButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) starship.touchUp(touch, pointer, button);
        else if(state == State.GAME_OVER) newGameButton.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING) starship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING) starship.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) star.update(delta);
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveSprites(delta);
            starship.update(delta);
            enemyShipsPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollision() {
        if (state != State.PLAYING) return;

        List<EnemyShip> enemyList = enemyShipsPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + starship.getHalfWidth();
            if (starship.pos.dst(enemy.pos) < minDist) {
                enemy.destroy();
                starship.damage(enemy.getDamage());
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != starship ||  bullet.isDestroyed()) continue;
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == starship || bullet.isDestroyed()) continue;
            if (starship.isBulletCollision(bullet)) {
                starship.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (starship.isDestroyed()) state = State.GAME_OVER;
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        if (state == State.PLAYING) {
            starship.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipsPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }

        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    public void newGame() {
        this.state = State.PLAYING;
        starship.flushDestroy();
        starship.setLeft(0 - starship.getHalfWidth());
        bulletPool.clearPool();
        enemyShipsPool.clearPool();
        explosionPool.clearPool();
    }
}
