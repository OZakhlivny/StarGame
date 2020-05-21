package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Explosion;

public class Ship extends Sprite {
    protected Rect worldBounds;

    protected final Vector2 velocityZero;
    protected final Vector2 velocity;

    protected ExplosionPool explosionPool;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;

    protected Sound bulletSound;
    protected float shootTimer;
    protected float shootInterval;

    protected int hp;

    protected boolean autoFire;
    protected boolean friendlyFire;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        velocity = new Vector2();
        velocityZero = new Vector2();
        autoFire = false;
        friendlyFire = false;
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = sound;
        velocity = new Vector2();
        velocityZero = new Vector2();
        bulletV = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(velocity, delta);
        if(autoFire) {
            shootTimer += delta;
            if (shootTimer >= shootInterval) {
                shoot();
                shootTimer = 0f;
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        bulletSound.play();
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }
}
