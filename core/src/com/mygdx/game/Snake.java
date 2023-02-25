package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

public class Snake {
    private SnakeHead head;
    private Array<SnakeBodyPart> bodyParts = new Array<>();
    private int MOVEMENT = 32;
    private int direction;
    private int snakeXBeforeUpdate = 0, snakeYBeforeUpdate = 0;

    public Snake() {
        head = new SnakeHead(new Texture(Gdx.files.internal("snakehead.png")));
        head.updatePosition(0, 0);
    }

    public void drawFullSnake(Batch batch) {
        head.drawOnScreen(batch);

        for (SnakeBodyPart bodyPart : bodyParts) {
            if (!(bodyPart.getX() == head.getX() && bodyPart.getY() == head.getY())){
                bodyPart.drawOnScreen(batch);
            }
        }

    }

    public void move(){
        snakeXBeforeUpdate = head.getX();
        snakeYBeforeUpdate = head.getY();
        switch (direction) {
            case Direction.RIGHT:
                head.setX(head.getX() + MOVEMENT);
                break;
            case Direction.LEFT:
                head.setX(head.getX() - MOVEMENT);
                break;
            case Direction.UP:
                head.setY(head.getY() + MOVEMENT);
                break;
            case Direction.DOWN:
                head.setY(head.getY() - MOVEMENT);
                break;
        }
    }

    public void addBodyPart() {
        SnakeBodyPart bodyPart = new SnakeBodyPart(new Texture(Gdx.files.internal("snakebody.png")));
        bodyPart.updatePosition(head.getX(), head.getY());
        bodyParts.insert(0, bodyPart);
    }

    public void updateBodyPartsPosition() {
        if (bodyParts.size > 0) {
            SnakeBodyPart bodyPart = bodyParts.removeIndex(0);
            bodyPart.updatePosition(snakeXBeforeUpdate, snakeYBeforeUpdate);
            bodyParts.add(bodyPart);
        }
    }

    public SnakeHead getHead() {
        return head;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getMOVEMENT() {
        return MOVEMENT;
    }
}
