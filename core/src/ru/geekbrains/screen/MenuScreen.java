package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ExitButton;
import ru.geekbrains.sprite.PlayButton;

public class MenuScreen extends BaseScreen {

    private StarGame game;
    private GameScreen gameScreen;

    private Texture backgroundImage, exitButtonImage, playButtonImage;
    private Background background;
    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(StarGame game) {
        this.game = game;
        gameScreen = new GameScreen(this, game);
    }

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("space.jpg");
        playButtonImage = new Texture("play.png");
        exitButtonImage = new Texture("exit.png");
        background = new Background(backgroundImage);
        playButton = new PlayButton(playButtonImage);
        exitButton = new ExitButton(exitButtonImage);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playButton.resize(worldBounds);
        exitButton.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        playButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    public void clearResources(){
        backgroundImage.dispose();
        playButtonImage.dispose();
        exitButtonImage.dispose();
    }

    @Override
    public void dispose() {
        clearResources();
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
        if(playButton.touchUp(touch, pointer, button)){
            clearResources();
            game.setScreen(gameScreen);
        }
        if (exitButton.touchUp(touch, pointer, button)) Gdx.app.exit();
        return false;
    }
}
