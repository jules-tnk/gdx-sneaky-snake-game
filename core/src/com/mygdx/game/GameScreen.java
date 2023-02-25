package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture snakeHead;
    private Apple apple;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;
    private static final int SNAKE_MOVEMENT = 32;
    static int snakeX = 0, snakeY = 0;
    private int snakeDirection = Direction.RIGHT;
    private Array<SnakeBodyPart> snakeBody = new Array<>();

    private int snakeXBeforeUpdate = 0, snakeYBeforeUpdate = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("snakehead.png"));
        apple = new Apple(new Texture(Gdx.files.internal("apple.png")));
    }

    @Override
    public void render(float delta) {
        queryKeyboardInput();
        timer -= delta*4;
        if (timer <= 0) {
            timer = MOVE_TIME;
            moveSnake();
            checkIfOutOfBounds();
            updateBodyPartsPosition();
        }
        checkAppleCollision();
        checkAndPlaceApple();

        // set screen color
        clearScreen();

        // display sprites on screen
        drawOnScreen();
    }

    private void clearScreen() {
        // Set screen color to black
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawOnScreen() {
        batch.begin();

        batch.draw(snakeHead, snakeX, snakeY);

        for (SnakeBodyPart bodyPart : snakeBody) {
            if (!(bodyPart.getX() == snakeX && bodyPart.getY() == snakeY)){
                bodyPart.drawOnScreen(batch);
            }
        }

        if (apple.isAvailable()) {
            apple.drawOnScreen(batch);
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
        snakeXBeforeUpdate = snakeX;
        snakeYBeforeUpdate = snakeY;
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
        if (!apple.isAvailable()) {
            do {
                int appleX = MathUtils.random(0, (Gdx.graphics.getWidth() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT) * SNAKE_MOVEMENT;
                int appleY = MathUtils.random(0, (Gdx.graphics.getHeight() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT) * SNAKE_MOVEMENT;
                apple.updatePosition(appleX, appleY);
            } while (apple.getX() == snakeX && apple.getY() == snakeY);
            apple.setAvailable(true);
        }
    }

    private void checkAppleCollision() {
        boolean condition1 = apple.isAvailable();
        boolean condition2 = snakeX == apple.getX();
        boolean condition3 = snakeY == apple.getY();
        boolean isCollision = condition1 && condition2 && condition3;
        if (isCollision) {
            SnakeBodyPart bodyPart = new SnakeBodyPart(new Texture(Gdx.files.internal("snakebody.png")));
            bodyPart.updatePosition(snakeX, snakeY);
            snakeBody.insert(0, bodyPart);
            apple.setAvailable(false);
        }
    }

    private void updateBodyPartsPosition() {
        if (snakeBody.size > 0) {
            SnakeBodyPart bodyPart = snakeBody.removeIndex(0);
            bodyPart.updatePosition(snakeXBeforeUpdate, snakeYBeforeUpdate);
            snakeBody.add(bodyPart);
        }
    }

}
