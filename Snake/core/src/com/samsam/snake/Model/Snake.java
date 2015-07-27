package com.samsam.snake.Model;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    
    public List<SnakePart> parts = new ArrayList<SnakePart>();
    public int direction;    

    public Snake() {        
        direction = UP;
        int x = (320/2)/20;
        parts.add(new SnakePart(x, 9));
        parts.add(new SnakePart(x, 8));
        parts.add(new SnakePart(x, 7));
        parts.add(new SnakePart(x, 6));
    }

    public void turnLeft() {
        if (direction==UP||direction==DOWN) {
            direction = LEFT;
        }
    }
    
    public void turnRight() {
        if (direction==UP||direction==DOWN) {
            direction = RIGHT;
        }
    }

    public void turnUp() {
        if (direction==LEFT||direction==RIGHT) {
            direction = UP;
        }
    }

    public void turnDown() {
        if (direction==LEFT||direction==RIGHT) {
            direction = DOWN;
        }
    }

    public void eat() {
        SnakePart end = parts.get(parts.size()-1); 
        parts.add(new SnakePart(end.x, end.y));
    }

    public void reverse()
    {
        for (int i=parts.size()-1;i>-1;i--)
        {
            SnakePart part = parts.get(i);
            if (direction==UP)
            {
                SnakePart after = new SnakePart(part.x,part.y-1);
                part.x = after.x;
                part.y = after.y;
            }
            if (direction==DOWN)
            {
                SnakePart after = new SnakePart(part.x,part.y+1);
                part.x = after.x;
                part.y = after.y;
            }
            if (direction==LEFT)
            {
                SnakePart after = new SnakePart(part.x+1,part.y);
                part.x = after.x;
                part.y = after.y;
            }
            if (direction==RIGHT)
            {
                SnakePart after = new SnakePart(part.x-1,part.y);
                part.x = after.x;
                part.y = after.y;
            }
        }
    }
    public boolean advance() {
        SnakePart head = parts.get(0);               
        
        int len = parts.size() - 1;
        for(int i = len; i > 0; i--) {
            SnakePart before = parts.get(i-1);
            SnakePart part = parts.get(i);
            part.x = before.x;
            part.y = before.y;
        }
        
        if(direction == UP)
            head.y += 1;
        if(direction == LEFT)
            head.x -= 1;
        if(direction == DOWN)
            head.y -= 1;
        if(direction == RIGHT)
            head.x += 1;
        
        if(head.x < 0)
            return false;
        if(head.x > 15)
            return false;
        if(head.y < 0)
            return false;
        if(head.y > 18)
            return false;

        return true;
    }

    public boolean checkBitten() {
        int len = parts.size();
        SnakePart head = parts.get(0);
        for(int i = 1; i < len; i++) {
            SnakePart part = parts.get(i);
            if(part.x == head.x && part.y == head.y)
                return true;
        }        
        return false;
    }      
}

