package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ExitButton;
import ru.geekbrains.sprite.PlayButton;
import ru.geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {

    private final Game game;
    private Texture backgroundImage;
    private Background background;
    private TextureAtlas atlas;
    private ExitButton exitButton;
    private PlayButton playButton;
    private Star[] stars;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("textures/bg.png");
        background = new Background(backgroundImage);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        playButton = new PlayButton(atlas, game);
        exitButton = new ExitButton(atlas);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) stars[i] = new Star(atlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
        for (Star star : stars) star.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        playButton.touchDown(touch, pointer, button);
        exitButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        playButton.touchUp(touch, pointer, button);
        exitButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) star.update(delta);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        playButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }
}
