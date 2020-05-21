package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Ship;
import ru.geekbrains.pool.EnemyShipsPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.Starship;
import ru.geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private final Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    private Texture backgroundImage;
    private Background background;
    private TextureAtlas atlas;
    private Starship starship;
    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipsPool enemyShipsPool;
    private EnemyEmitter enemyEmitter;

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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) star.resize(worldBounds);
        starship.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
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
        explosionSound.dispose();
        explosionPool.dispose();
        starship.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starship.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        starship.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        starship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        starship.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) star.update(delta);
        bulletPool.updateActiveSprites(delta);
        starship.update(delta);
        enemyShipsPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
        checkTargets();
        checkCollisions();
    }


    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        starship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipsPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    public void checkTargets(){
        List<EnemyShip> enemyShipList = enemyShipsPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for(Bullet bullet : bulletList){
            for(EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isMe(bullet.pos) && !((Ship) (bullet.getOwner())).isFriendlyFire()) {
                    bullet.destroy();
                    enemyShip.destroy();
                }
            }
            if((bullet.getOwner() != starship) && starship.isMe(bullet.pos)){
                bullet.destroy();
                starship.destroy();
            }
        }
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShipList = enemyShipsPool.getActiveObjects();
        for(EnemyShip enemyShip : enemyShipList) {
            if(!enemyShip.isOutside(starship)) enemyShip.destroy();
        }
    }
}
