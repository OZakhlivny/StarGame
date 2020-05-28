package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class NewGameButton extends ScaledButton {

    private GameScreen gameScreen;
    private static final float ANIMATE_INTERVAL = 1f;
    private boolean scaleUp = true;
    private float animateTimer;

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getWidth() * 0.07f);
        setTop(-0.2f);
    }

    @Override
    public void action() {
        gameScreen.newGame();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUp = !scaleUp;
        }
        if (scaleUp) setScale(getScale() + 0.003f);
        else setScale(getScale() - 0.003f);
    }
}
