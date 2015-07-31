package com.samsam.tetris.Model;

import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;

/**
 * Created by NghiaTrinh on 7/31/2015.
 */
public class World
{
    private Rectangle bounds;
    public final int rows = 12;
    public final int columns = 20;
    private int score;
    private Timer timer = null;
    private Figure main_figure;
    private Figure next_figure;
    private int[][] pool;

    public World()
    {
        pool = new int[rows+8][columns+8];
        main_figure=new Figure(11,15);
        next_figure=new Figure(11,15);
    }
}
