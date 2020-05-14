package ru.geekbrains.sprite;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Starship extends Sprite {
    private Vector2 velocity;
    private Vector2 touchPosition;
    private Vector2 position;
    private Rect worldBounds;


    public Starship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 2, 1);
        velocity = new Vector2();
        touchPosition = new Vector2();
        position = new Vector2();
        worldBounds = new Rect();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.3f);
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

    public void keyDown(int keycode) {
        if(keycode != Keys.UP && keycode != Keys.DOWN && keycode != Keys.LEFT && keycode != Keys.RIGHT) return;
        switch(keycode) {
            case Keys.UP:
                touchPosition.set(pos.x, worldBounds.getTop() - getHalfHeight());
                break;
            case Keys.DOWN:
                touchPosition.set(pos.x, worldBounds.getBottom() + getHalfHeight());
                break;
            case Keys.LEFT:
                touchPosition.set(worldBounds.getLeft() + getHalfWidth(), pos.y);
                break;
            case Keys.RIGHT:
                touchPosition.set(worldBounds.getRight() - getHalfWidth(), pos.y);
                break;
        }
        velocity.set(touchPosition).sub(pos).setLength(0.005f);
    }

    public void keyUp(int keycode) {
        if(keycode == Keys.UP || keycode == Keys.DOWN || keycode == Keys.LEFT || keycode == Keys.RIGHT) velocity.set(0, 0);
    }
}
