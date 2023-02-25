package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import static com.mygdx.game.GameScreen.snakeX;
import static com.mygdx.game.GameScreen.snakeY;

public class SnakeBodyPart {
    private int x, y;
    private static final Texture texture = new Texture(Gdx.files.internal("snakebody.png"));

    public SnakeBodyPart() {
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawOnScreen(Batch batch) {
        if (!(x == snakeX && y == snakeY)){
            batch.draw(texture, x, y);
        }
    }

}
