package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.utils.Regions;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame = 0;
    protected boolean destroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        regions = Regions.split(region, rows, cols, frames);
    }

    public Sprite(TextureRegion region, int xDivide, int yDivide){
        if(xDivide <= 0) xDivide = 1;
        if(yDivide <= 0) yDivide = 1;
        regions = new TextureRegion[xDivide * yDivide];
        TextureRegion[][] frames = region.split(region.getRegionWidth() / xDivide, region.getRegionHeight()/yDivide);
        for(int i = 0; i < yDivide; i++)
            for (int j = 0; j < xDivide; j++)
                regions[i * xDivide + j] = new TextureRegion(frames[i][j]);
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void resize(Rect worldBounds) {
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        destroyed = true;
    }

    public void flushDestroy() {
        destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
