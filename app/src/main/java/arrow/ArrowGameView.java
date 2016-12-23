package arrow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/09.
 */
public class ArrowGameView extends MySurface{
    private ArrowGameEngine arrowGameEngine;

    public ArrowGameView(Context context) {
        super(context);
    }

    public ArrowGameView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        arrowGameEngine = new ArrowGameEngine(getResources(),getContext());
        arrowGameEngine.drawFirst(paint, canvas);
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        arrowGameEngine.drawGame(paint, canvas);
    }
}
