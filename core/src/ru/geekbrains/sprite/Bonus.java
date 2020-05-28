package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Bonus extends Sprite {
    private Rect worldBounds;
    private Vector2 velocity;
    private int hp;
    private boolean scaleUp = true;
    private static final float ANIMATE_INTERVAL = 1f;
    private float animateTimer;

    public Bonus() {
        regions = new TextureRegion[1];
        velocity = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(velocity, delta);
        if (getBottom() <= worldBounds.getBottom()) destroy();
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUp = !scaleUp;
        }
        if (scaleUp) setScale(getScale() + 0.005f);
        else setScale(getScale() - 0.005f);
    }

    public void set(
            TextureRegion region,
            Vector2 v0,
            float height,
            int hp,
            Rect worldBounds
    ) {
        this.regions[0] = region;
        this.velocity.set(v0);
        setHeightProportion(height);
        this.hp = hp;
        this.worldBounds = worldBounds;
    }

    public int getHP() {
        return hp;
    }
}
