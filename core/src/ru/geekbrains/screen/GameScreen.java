package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.Starship;

public class GameScreen extends BaseScreen {

    private Texture backgroundImage;
    private Background background;
    private TextureAtlas atlas;
    private Starship starship;
    private Star[] stars;
    private BulletPool bulletPool;



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
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        atlas.dispose();
        bulletPool.dispose();
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
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        starship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
