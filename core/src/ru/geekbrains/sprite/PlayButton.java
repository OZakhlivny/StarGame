package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class PlayButton extends Sprite {

    private boolean isPressed;

    public PlayButton(Texture texture) {
        super(new TextureRegion(texture, 0, 0, 104, 50));
        isPressed = false;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.083f);
        this.pos.set(-0.2f, -0.3f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(isMe(touch)){
            setScale(0.8f);
            isPressed = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(isPressed) {
            setScale(1f);
            isPressed = false;
            return true;
        }
        return false;
    }
}
