package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture img, backgroundImage;
    private Background background;


    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("space.jpg");
        img = new Texture("ufo.png");
        background = new Background(backgroundImage);

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        backgroundImage.dispose();
        super.dispose();
    }
}
