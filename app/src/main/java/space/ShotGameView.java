package space;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/03/24.
 */
public class ShotGameView extends MySurface {
    private ShotGameEngine shotGameEngine;


    public ShotGameView(Context context) {
        super(context);
    }
    public ShotGameView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        shotGameEngine = new ShotGameEngine(getResources(),getContext());
        shotGameEngine.drawFirst(paint, canvas);
    }
    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        shotGameEngine.drawGame(paint, canvas);
    }
}
