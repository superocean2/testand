package com.samsam.tetris.Model;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NghiaTrinh on 7/31/2015.
 */
public class World {
    public final int gridcols = 10;
    public final int gridrows = 19;
    public int score;
    public Figure main_figure;
    public Figure next_figure;
    public int[][] pool;
    private int new_figure;
    long down_speed = 200;
    public Timer timer = null;
    public boolean gameOver=false;
    private final int NEWFIGUREX=4;
    private final int NEWFIGUREY=19;
    MoveTask task = new MoveTask(MoveTask.MOVE_DOWN);


    public World() {
        pool = new int[gridcols + 8][gridrows + 8];
        for (int i = 4; i < gridcols + 4; i++)
            for (int j = 4; j < gridrows + 4; j++)
                pool[i][j] = 0;
        for (int j = 0; j < gridcols+1; j++) {
            pool[j][0] = 1;
        }
        for (int i=0;i<gridrows;i++)
        {
            pool[0][i]=1;
            pool[gridcols+1][i]=1;
        }

        main_figure = new Figure(NEWFIGUREX, NEWFIGUREY);
        next_figure = new Figure(NEWFIGUREX, NEWFIGUREY);
        timer=new Timer();
        score=0;
    }

    public class MoveTask extends TimerTask {
        public int move;
        public static final int MOVE_DOWN = 3;

        public MoveTask(int c) {
            move = c;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            switch (move) {
                case MOVE_DOWN: {
                    if (!main_figure.move_down(pool)) {
                            if (new_figure == 0)
                                new_figure = 1;
                            else new_figure--;
                    }
                    if (new_figure == 1) {
                        main_figure.print(pool);
                        delete();
                        main_figure = new Figure(next_figure);
                        next_figure = new Figure(NEWFIGUREX, NEWFIGUREY);
                        new_figure = 0;
                        if (main_figure.crossing(pool))
                        {
                            game_Over();
                        }
                    }

                }
            }
        }
    }

    public void update()
    {
        timer.schedule(task, 300, down_speed);
    }
    public void restart()
    {
        timer.cancel();
    }
    public void game_Over()
    {
        timer.cancel();
        gameOver = true;
    }
    public void pause()
    {
       timer.cancel();
    }
    public void resume() {
        timer.cancel();
        timer = new Timer();
        MoveTask task = new MoveTask(MoveTask.MOVE_DOWN);
        timer.schedule(task, 0, down_speed);
    }
    private void delete()
    {
        int x = 0;
        boolean combo;
        for (int j=1;j< gridrows +1;j++)
        {
            combo = true;
            for (int i=1;i< gridcols+1;i++)
                if (pool[i][j] == 0) combo = false;
            if (combo)
            {
                for (int k=j;k< gridrows +1;k++)
                    for (int l=1;l< gridcols+1;l++)
                        pool[l][k] = pool[l][k+1];
                x++;
                j--;
            }
        }
        switch (x)
        {
            case 1:
                score +=10; break;
            case 2:
                score +=30; break;
            case 3:
                score +=60; break;
            case 4:
                score +=120; break;
        }

    }
}
