package com.samsam.newblock;

import java.util.Random;

/**
 * Created by NghiaTrinh on 7/31/2015.
 */
public class Figure {
    public int data[][];
    public int x,y;
    public int color;

    public Figure(int x,int y)
    {
        Random r = new Random();
        this.x=x;
        this.y=y;
        color = r.nextInt(8);

        int type = r.nextInt(5);
        switch (type)
        {
            case 0: data = new int[][] {{0,0},{0,1},{1,1},{1,0}}; break;
            case 1: data = new int[][] {{0,0},{0,1},{1,0}}; break;
            case 2: data = new int[][] {{1,1},{0,1},{1,0}}; break;
            case 3: data = new int[][] {{0,1},{1,1},{0,0}}; break;
            case 4: data = new int[][] {{1,0},{1,1},{0,0}}; break;
        }
    }
    public Figure(Figure copy)
    {
        data = new int[copy.data.length][2];
        for (int i=0;i<copy.data.length;i++)
        {
            data[i][0] = copy.data[i][0];
            data[i][1] = copy.data[i][1];
        }
        x = copy.x;
        y = copy.y;
        color = copy.color;
    }
    public boolean rotate(int[][] pool) {
        int buf;
        Figure figure = new Figure(this);

        for (int i = 0;i<4;i++)
        {
            buf = figure.data[i][0];
            figure.data[i][0] = figure.data[i][1];
            figure.data[i][1] = 3-buf;
        }
        if (!figure.crossing(pool))
            for (int i = 0;i<4;i++)
            {
                buf = data[i][0];
                data[i][0] = data[i][1];
                data[i][1] = 3-buf;
            }
        else return false;
        return true;
    }
    public boolean move_left(int[][]pool)
    {
        x--;
        if (crossing(pool))
            x++;
        else return true;
        return false;
    }
    public boolean move_right(int[][] pool)
    {
        x++;
        if (crossing(pool))
            x--;
        else return true;
        return false;
    }
    public boolean move_down(int[][] pool)
    {
        y--;
        if (this.crossing(pool))
        {
            y++;
            return false;
        }
        return true;
    }
    public void drop(int[][] pool)
    {
        do
            y--;
        while (!crossing(pool));
        y++;
    }
    public boolean crossing(int[][] pool)
    {
        for (int i=0;i<data.length;i++) {
            if (pool[data[i][0] + x+1][data[i][1] + y+1] != 0) return true;
        }
        return false;
    }

    public void print(int[][] pool,int[][] poolColor)
    {
        for (int i=0;i<data.length;i++) {
            pool[data[i][0] + x + 1][data[i][1] + y + 1] = 1;
            poolColor[data[i][0] + x + 1][data[i][1] + y + 1] = color;
        }
    }
}