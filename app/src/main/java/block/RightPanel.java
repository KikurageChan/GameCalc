package block;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/05.
 */
public class RightPanel extends View {
    private static int touchX;
    private static int touchY;

    public RightPanel(Context context){
        super(context);
        touchX = MySurface.getScreenWidth()/2;
        touchY = MySurface.getScreenHeight()/2;
    }

    public RightPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchX = MySurface.getScreenWidth()/2;
        touchY = MySurface.getScreenHeight()/2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = (int) event.getX();
            touchY = (int) event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touchX = (int) event.getX();
            touchY = (int) event.getY();
        }
        return true;
    }
    public static int getTouchX(){
        return touchX;
    }
    public static int getTouchY(){
        return touchY;
    }
}
