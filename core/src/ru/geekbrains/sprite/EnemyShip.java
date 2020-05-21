package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class EnemyShip extends Ship {
    private static final float ENTERING_SPEED = -0.7f;
    private boolean inFight;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(bulletPool, explosionPool, worldBounds, sound);
        friendlyFire = true;
        inFight = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(!inFight &&(getTop() <= worldBounds.getTop())) {
            velocity.set(velocityZero);
            autoFire = inFight = true;
            shootTimer = shootInterval;
        }

        if (getBottom() <= worldBounds.getBottom()) destroy();
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float shootInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.velocityZero.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.shootInterval = shootInterval;
        this.shootTimer = 0;
        this.hp = hp;
        setHeightProportion(height);
        this.velocity.set(0, ENTERING_SPEED);
    }

    @Override
    public void destroy() {
        super.destroy();
        inFight = false;
    }
}
