package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private Vector2 velocity;
    private Vector2 touchPosition;
    private Vector2 position;

    public Logo(Texture texture) {
        super(new TextureRegion(texture, 0, 0, 64, 32));
        velocity = new Vector2();
        touchPosition = new Vector2();
        position = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(position.set(touchPosition).sub(pos).len() < velocity.len()) velocity.set(0, 0);
        pos.add(velocity);

        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        touchPosition.set(touch);
        velocity.set(touchPosition).sub(pos).setLength(0.005f);
        return super.touchDown(touch, pointer, button);
    }
}
