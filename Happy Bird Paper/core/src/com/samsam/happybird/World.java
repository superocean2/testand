package com.samsam.happybird;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class World {

    Bird bird;
    Array<Fence> fences;
    Rectangle rectScreen = new Rectangle(5,14,786,392);
    private static final float BIRD_JUMP_IMPULSE = 350;
    private static final float GRAVITY = -20;
    private static final float BIRD_VELOCITY_X = 200;
    private static final float BIRD_START_X = 50;
    private static final float BIRD_START_Y = 150;
    private static final float FENCE_SPACE_X = 180;
    private static final float FENCE_SPACE_Y = 120;


    public World()
    {
        fences = new Array<Fence>();
        Array<Fence> temp = new Array<Fence>();
        bird = new Bird();
        bird.position.x=BIRD_START_X;
        bird.position.y=BIRD_START_Y;

        //fence bottom
        float xBefore=rectScreen.getX();
        for (int i=0;i<3;i++)
        {
            float x = xBefore+FENCE_SPACE_X;
            int h = MathUtils.random(60,180);
            Fence fence = new Fence(x,rectScreen.y,h);
            fences.add(fence);
            xBefore = x;
        }

       // fence top
        xBefore = rectScreen.getX();
        for (Fence fence:fences)
        {
            float x = xBefore+FENCE_SPACE_X;
            float y = fence.position.y + fence.height + FENCE_SPACE_Y +12;
            int h = (int)(rectScreen.getHeight()-fence.height-FENCE_SPACE_Y - 12 -12 +2);
            temp.add(new Fence(x,y,h));
            xBefore = x;
        }

        for (Fence fence: temp)
        {
            fences.add(fence);
        }
    }


}
