package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ExitButton;
import ru.geekbrains.sprite.Logo;

public class GameScreen extends BaseScreen {

    private StarGame game;
    private MenuScreen menuScreen;
    private Texture logoImage, backgroundImage, exitButtonImage;
    private Background background;
    private Logo logo;
    private ExitButton exitButton;

    public GameScreen(MenuScreen menuScreen, StarGame game) {
        this.menuScreen = menuScreen;
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("game_back.jpg");
        logoImage = new Texture("ufo.png");
        exitButtonImage = new Texture("exit.png");
        background = new Background(backgroundImage);
        logo = new Logo(logoImage);
        exitButton = new ExitButton(exitButtonImage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
        exitButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        clearResources();
        super.dispose();
    }

    public void clearResources(){
        logoImage.dispose();
        backgroundImage.dispose();
        exitButtonImage.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        logo.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (exitButton.touchUp(touch, pointer, button)) {
            clearResources();
            game.setScreen(menuScreen);
        }
        return false;
    }
}
