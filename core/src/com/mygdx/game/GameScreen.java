package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture snakeHead;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;
    private static final int SNAKE_MOVEMENT = 32;
    private static int snakeX = 0, snakeY = 0;
    private int snakeDirection = Direction.RIGHT;

    @Override
    public void show() {
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
    }

    @Override
    public void render(float delta) {
        queryKeyboardInput();
        timer -= delta;
        if (timer <= 0) {
            timer = MOVE_TIME;
            moveSnake();
            checkIfOutOfBounds();
        }

        // Set screen color to black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        doBatchRender();

    }

    private void doBatchRender() {
        batch.begin();
        // begin rendering code
        batch.draw(snakeHead, snakeX, snakeY);
        // en rendering code
        batch.end();
    }

    private void checkIfOutOfBounds() {
        if (snakeX >= Gdx.graphics.getWidth()) {
            snakeX = 0;
        }
        if (snakeX < 0) {
            snakeX = Gdx.graphics.getWidth() - SNAKE_MOVEMENT;
        }
        if (snakeY >= Gdx.graphics.getHeight()) {
            snakeY = 0;
        }
        if (snakeY < 0) {
            snakeY = Gdx.graphics.getHeight() - SNAKE_MOVEMENT;
        }
    }

    private void moveSnake() {
        switch (snakeDirection) {
            case Direction.RIGHT:
                snakeX += SNAKE_MOVEMENT;
                break;
            case Direction.LEFT:
                snakeX -= SNAKE_MOVEMENT;
                break;
            case Direction.UP:
                snakeY += SNAKE_MOVEMENT;
                break;
            case Direction.DOWN:
                snakeY -= SNAKE_MOVEMENT;
                break;
        }
    }

    private void queryKeyboardInput(){
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            snakeDirection = Direction.LEFT;
        }
        if (rightPressed) {
            snakeDirection = Direction.RIGHT;
        }
        if (upPressed) {
            snakeDirection = Direction.UP;
        }
        if (downPressed) {
            snakeDirection = Direction.DOWN;
        }

    }

}
