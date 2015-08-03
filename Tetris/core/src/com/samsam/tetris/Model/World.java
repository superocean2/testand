package com.samsam.tetris.Model;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NghiaTrinh on 7/31/2015.
 */
public class World {
    public final int gridcols = 12;
    public final int gridrows = 20;
    public int score;
    public Figure main_figure;
    public Figure next_figure;
    public int[][] pool;
    private boolean action = false;
    private int new_figure;
    long down_speed = 300;
    public Timer timer = null;
    MoveTask task = new MoveTask(MoveTask.MOVE_DOWN);


    public World() {
        pool = new int[gridcols + 8][gridrows + 8];
        for (int i = 4; i < gridcols + 4; i++)
            for (int j = 4; j < gridrows + 4; j++)
                pool[i][j] = 0;
        for (int j = 0; j < gridcols; j++) {
            pool[4][j] = 1;
        }
        for (int i = 0; i < gridcols + 4; i++)
            pool[i][3] = 1;
        main_figure = new Figure(8, 23);
        next_figure = new Figure(8, 23);
        timer=new Timer();
    }

    public class MoveTask extends TimerTask {
        public int move;
        public static final int MOVE_LEFT = 0;
        public static final int MOVE_ROTATE = 1;
        public static final int MOVE_RIGHT = 2;
        public static final int MOVE_DOWN = 3;
        public static final int MOVE_DROP = 4;

        public MoveTask(int c) {
            move = c;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            switch (move) {
                case MOVE_LEFT:
                    main_figure.move_left(pool);
                    break;
                case MOVE_RIGHT:
                    main_figure.move_right(pool);
                    break;
                case MOVE_ROTATE:
                    main_figure.rotate(pool);
                    break;
                case MOVE_DROP:
                    main_figure.drop(pool);
                    break;
                case MOVE_DOWN: {
                    if (!main_figure.move_down(pool)) {
                        if (new_figure == 0)
                            new_figure = 2;
                        else new_figure--;
                    }
                    if (new_figure == 1) {
                        main_figure.print(pool);
                        delete();
                        main_figure = new Figure(next_figure);
                        next_figure = new Figure(11, 15);
                        new_figure = 0;
                    }

                }
            }
        }
    }

    public void update()
    {
        timer.schedule(task, 300, down_speed);
    }

    private void delete()
    {
        action = true;
        int x = 0;
        boolean combo;
        for (int j=4;j< gridrows +4;j++)
        {
            combo = true;
            for (int i=4;i< gridcols +4;i++)
                if (pool[i][j] == 0) combo = false;
            if (combo)
            {
                for (int k=j;k< gridrows +4;k++)
                    for (int l=4;l< gridcols +4;l++)
                        pool[l][k] = pool[l][k+1];
                x++;
                j--;
            }
        }
        switch (x)
        {
            case 1:
                score +=100; break;
            case 2:
                score +=300; break;
            case 3:
                score +=700; break;
            case 4:
                score +=1500; break;
        }

        action = false;
    }
}
