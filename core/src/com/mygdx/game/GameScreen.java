package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture snakeHead;
    private Texture apple;
    private boolean isAppleAvailable = false;
    private static int appleX = 0, appleY = 0;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;
    private static final int SNAKE_MOVEMENT = 32;
    private static int snakeX = 0, snakeY = 0;
    private int snakeDirection = Direction.RIGHT;

    @Override
    public void show() {
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
        apple = new Texture(Gdx.files.internal("apple.png"));
    }

    @Override
    public void render(float delta) {
        queryKeyboardInput();
        timer -= delta*4;
        if (timer <= 0) {
            timer = MOVE_TIME;
            moveSnake();
            checkIfOutOfBounds();
        }
        checkAppleCollision();
        checkAndPlaceApple();

        // set screen color
        clearScreen();

        // display sprites on screen
        doBatchRender();
    }

    private void clearScreen() {
        // Set screen color to black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void doBatchRender() {
        batch.begin();

        batch.draw(snakeHead, snakeX, snakeY);


        if (isAppleAvailable) {
            batch.draw(apple, appleX, appleY);
        }

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

    private void checkAndPlaceApple() {
        if (!isAppleAvailable) {
            do {
                appleX = MathUtils.random(0, (Gdx.graphics.getWidth() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT) * SNAKE_MOVEMENT;
                appleY = MathUtils.random(0, (Gdx.graphics.getHeight() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT) * SNAKE_MOVEMENT;
            } while (appleX == snakeX && appleY == snakeY);
            isAppleAvailable = true;
        }
    }

    private void checkAppleCollision() {
        boolean condition1 = isAppleAvailable;
        boolean condition2 = snakeX == appleX;
        boolean condition3 = snakeY == appleY;
        if (condition1 && condition2 && condition3) {
            isAppleAvailable = false;
        }
    }

}
