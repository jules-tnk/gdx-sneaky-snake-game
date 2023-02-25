package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Apple {
    private int x, y;
    private boolean isAvailable = false;
    private static final Texture texture = new Texture(Gdx.files.internal("apple.png"));

    public Apple() {}

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawOnScreen(Batch batch) {
        batch.draw(texture, x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
