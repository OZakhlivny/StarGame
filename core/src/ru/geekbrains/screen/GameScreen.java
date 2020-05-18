package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.pool.EnemyShipsPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.Starship;

public class GameScreen extends BaseScreen {
    private static final float ENEMY_RISE_INTERVAL = 2.5f;

    private final Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    private Texture backgroundImage;
    private Background background;
    private TextureAtlas atlas;
    private Starship starship;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyShipsPool enemyShipsPool;

    private float enemyRiseTimer;

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("textures/bg.png");
        background = new Background(backgroundImage);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletPool = new BulletPool();
        starship = new Starship(atlas, bulletPool);
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) stars[i] = new Star(atlas);
        enemyShipsPool = new EnemyShipsPool();
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
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyShipsPool.freeAllDestroyed();
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipsPool.dispose();
        explosionSound.dispose();
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
        enemyRiseTimer += delta;
        if (enemyRiseTimer >= ENEMY_RISE_INTERVAL) {
            EnemyShip enemyShip = enemyShipsPool.obtain();
            enemyShip.set(atlas, getWorldBounds());
            enemyRiseTimer = 0f;
        }
        checkTargets();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        starship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipsPool.drawActiveSprites(batch);
        batch.end();
    }

    public void checkTargets(){
        List<EnemyShip> enemyShipList = enemyShipsPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for(EnemyShip enemyShip : enemyShipList){
            for(Bullet bullet : bulletList){
                if(enemyShip.isMe(bullet.pos)){
                    explosionSound.play();
                    bullet.destroy();
                    enemyShip.destroy();
                }
            }
        }
    }
}
