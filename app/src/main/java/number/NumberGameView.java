package number;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/12.
 */
public class NumberGameView extends MySurface {
    private NumberGameEngine numberGameEngine;
    public NumberGameView(Context context) {
        super(context);
    }
    public NumberGameView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        numberGameEngine = new NumberGameEngine(getResources(),getContext());
        numberGameEngine.drawFirst(paint, canvas);
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        numberGameEngine.drawGame(paint,canvas);
    }
}
