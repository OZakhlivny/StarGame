package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;

public class ExitButton extends ScaledButton {
    private static final float MARGIN = 0.05f;

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getWidth() * 0.25f);
        setBottom(worldBounds.getBottom() + MARGIN);
        setRight(worldBounds.getRight() - MARGIN);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
