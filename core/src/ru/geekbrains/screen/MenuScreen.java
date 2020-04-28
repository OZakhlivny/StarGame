package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img, backgroundImage;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 touchPosition;

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("space.jpg");
        img = new Texture("ufo.png");
        position = new Vector2();
        velocity = new Vector2();
        touchPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(position.epsilonEquals(touchPosition, 1)) velocity.set(0, 0);
        position.add(velocity);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.draw(img, position.x, position.y);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        backgroundImage.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.printf("touchDown touchPosition.x = %.0f, touchPosition.y = %.0f\n", touchPosition.x, touchPosition.y);
        velocity.set(touchPosition);
        velocity.sub(position);
        velocity.nor();
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
