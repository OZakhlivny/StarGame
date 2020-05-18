package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.utils.Regions;

public class EnemyShip extends Sprite {
    private static final float SIZE = 0.15f;
    private static final float SPEED = -0.1f;

    private Vector2 velocity;
    private Rect worldBounds;

    public EnemyShip() {
        velocity = new Vector2(0, SPEED);
    }


    @Override
    public void update(float delta) {
        pos.mulAdd(velocity, delta);
        if (isOutside(worldBounds)) destroy();
    }


    public void set(TextureAtlas atlas, Rect worldBounds) {
        this.worldBounds = worldBounds;
        regions = Regions.split(atlas.findRegion(String.format("enemy%d", (int)(Math.random()*3))), 1, 2, 2);
        setHeightProportion(SIZE);
        setLeft(Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight() - getWidth()));
        setTop(worldBounds.getTop());
    }


}
