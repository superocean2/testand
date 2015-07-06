package samsam.colortest.com;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by NghiaTrinh on 7/6/2015.
 */
public class Helpers {
    public  static boolean isTouchedInRect(Rectangle r,float xInput,float yInput)
    {
        if (r.x<=xInput&&r.x+r.width>=xInput&&r.y<=yInput&&r.y+r.height>=yInput)
        {
            return true;
        }
        return  false;
    }
}
