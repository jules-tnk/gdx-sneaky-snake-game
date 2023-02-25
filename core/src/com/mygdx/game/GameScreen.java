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
    private Texture appleTexture;
    private Apple apple;
    private Snake snake;
    private static final float MOVE_TIME = 1F;
    private float timer = MOVE_TIME;



    @Override
    public void show() {
        batch = new SpriteBatch();
        appleTexture = new Texture(Gdx.files.internal("apple.png"));

        apple = new Apple(appleTexture);
        snake = new Snake();
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

        snake.drawFullSnake(batch);

        if (apple.isAvailable()) {
            apple.drawOnScreen(batch);
        }

        batch.end();
    }



    private void checkIfOutOfBounds() {
        if (snake.getHead().getX() >= Gdx.graphics.getWidth()) {
            snake.getHead().setX(0);
        }
        if (snake.getHead().getX() < 0) {
            snake.getHead().setX(Gdx.graphics.getWidth() - snake.getMOVEMENT());
        }
        if (snake.getHead().getY() >= Gdx.graphics.getHeight()) {
            snake.getHead().setY(0);
        }
        if (snake.getHead().getY() < 0) {
            snake.getHead().setY(Gdx.graphics.getHeight() - snake.getMOVEMENT());
        }
    }

    private void moveSnake() {
        snake.move();
    }

    private void queryKeyboardInput(){
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) {
            snake.setDirection(Direction.LEFT);
        }
        if (rightPressed) {
            snake.setDirection(Direction.RIGHT);
        }
        if (upPressed) {
            snake.setDirection(Direction.UP);
        }
        if (downPressed) {
            snake.setDirection(Direction.DOWN);
        }
    }

    private void checkAndPlaceApple() {
        if (!apple.isAvailable()) {
            do {
                int SNAKE_MOVEMENT = snake.getMOVEMENT();
                int appleX = MathUtils.random(0, (Gdx.graphics.getWidth() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT) * SNAKE_MOVEMENT;
                int appleY = MathUtils.random(0, (Gdx.graphics.getHeight() - SNAKE_MOVEMENT) / SNAKE_MOVEMENT) * SNAKE_MOVEMENT;
                apple.updatePosition(appleX, appleY);
            } while (apple.getX() == snake.getHead().getX() && apple.getY() == snake.getHead().getY());
            apple.setAvailable(true);
        }
    }

    private void checkAppleCollision() {
        boolean condition1 = apple.isAvailable();
        boolean condition2 = snake.getHead().getX() == apple.getX();
        boolean condition3 = snake.getHead().getY() == apple.getY();
        boolean isCollision = condition1 && condition2 && condition3;
        if (isCollision) {
            snake.addBodyPart();
            apple.setAvailable(false);
        }
    }

    private void updateBodyPartsPosition() {
        snake.updateBodyPartsPosition();
    }

}
