package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BonusPool;
import ru.geekbrains.sprite.Bonus;

public class BonusEmitter {

    private static final float GENERATE_INTERVAL = 20f;

    private static final float FIRSTAIDKIT_BIG_HEIGHT = 0.05f;
    private static final int FIRSTAIDKIT_BIG_HP = 5;

    private static final float FIRSTAIDKIT_SMALL_HEIGHT = 0.05f;
    private static final int FIRSTAIDKIT_SMALL_HP = 1;

    private Rect worldBounds;
    private float generateTimer;

    private final TextureRegion firstAidKitSmallRegion;
    private final TextureRegion firstAidKitBigRegion;

    private final Vector2 firstAidKitSmallVelocity;
    private final Vector2 firstAidKitBigVelocity;

    private final BonusPool bonusPool;

    public BonusEmitter(TextureAtlas atlas, BonusPool bonusPool) {
        this.firstAidKitBigRegion = atlas.findRegion("first_aid_kit_big");
        this.firstAidKitSmallRegion = atlas.findRegion("first_aid_kit_small");
        this.firstAidKitBigVelocity = new Vector2(0, -0.15f);
        this.firstAidKitSmallVelocity = new Vector2(0, -0.25f);
        this.bonusPool = bonusPool;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void generate(float delta, int level) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            Bonus bonus = bonusPool.obtain();
            float type = (float) Math.random();
            if (type < 0.4f) {
                bonus.set(
                        firstAidKitBigRegion,
                        firstAidKitBigVelocity,
                        FIRSTAIDKIT_BIG_HEIGHT,
                        FIRSTAIDKIT_BIG_HP * level,
                        worldBounds
                );
            } else {
                bonus.set(
                        firstAidKitSmallRegion,
                        firstAidKitSmallVelocity,
                        FIRSTAIDKIT_SMALL_HEIGHT,
                        FIRSTAIDKIT_SMALL_HP * level,
                        worldBounds
                );
            }
            bonus.pos.x = Rnd.nextFloat(worldBounds.getLeft() + bonus.getHalfWidth(), worldBounds.getRight() - bonus.getHalfWidth());
            bonus.setBottom(worldBounds.getTop());
        }
    }
}
