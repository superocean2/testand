package com.samsam.snake.Model;

import java.util.Random;

public class World {
    static final int WORLD_WIDTH = 16;
    static final int WORLD_HEIGHT = 19;
    static final int SCORE_INCREMENT = 10;
    static final int SCORE_INCREMENT_EXTRA = 30;
    static final float TICK_INITIAL = 0.2f;
    static final float TICK_DECREMENT = 0.05f;

    public Snake snake;
    public Stain stain;
    public boolean gameOver;
    public int score;
    public boolean newStain;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    float tick = TICK_INITIAL;

    public World() {
        gameOver=false;
        score=0;
        newStain=false;
        snake = new Snake();
        placeStain();
    }

    private void placeStain() {
        newStain=true;
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = snake.parts.size();
        for (int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }
        int type = random.nextInt(2);
        if(type==1)
        {
            type = random.nextInt(2);
        }
        int stainX = random.nextInt(type==0?WORLD_WIDTH:WORLD_WIDTH-1);
        int stainY = random.nextInt(type==0?WORLD_HEIGHT:WORLD_HEIGHT-1);

        while (true) {
            if (fields[stainX][stainY] == false)
                break;
            stainX += 1;
            if (type==0) {
                if (stainX >= WORLD_WIDTH) {
                    stainX = 0;
                    stainY += 1;
                    if (stainY >= WORLD_HEIGHT) {
                        stainY = 0;
                    }
                }
            }
            else
            {
                if (stainX >= WORLD_WIDTH-1) {
                    stainX = 0;
                    stainY += 1;
                    if (stainY >= WORLD_HEIGHT-1) {
                        stainY = 0;
                    }
                }
            }
        }
        stain = new Stain(stainX, stainY, type);
    }

    public void update(float deltaTime) {
        if (gameOver)
            return;
        tickTime += deltaTime;
        if (tickTime > tick) {
            tickTime = 0;
            gameOver = !snake.advance();
            if (gameOver) {
                snake.reverse();
            }
            if (snake.checkBitten()) {
                gameOver = true;
            }
        }
        SnakePart head = snake.parts.get(0);

        if (head.x == stain.x && head.y == stain.y) {
            if (stain.type==0) {
                score += SCORE_INCREMENT;
            }
            else
            {
                score+=SCORE_INCREMENT_EXTRA;
            }
            snake.eat();
            if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                gameOver = true;
            } else {
                placeStain();
            }
        }

    }
}
