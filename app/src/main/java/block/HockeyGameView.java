package block;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import myGameUtil.MySurface;


/**
 * Created by kikuragetyann on 16/02/09.
 */
public class HockeyGameView extends MySurface{
    private HockeyGameEngine hockeyGameEngine;

    public HockeyGameView(Context context) {
        super(context);
    }

    public HockeyGameView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        hockeyGameEngine = new HockeyGameEngine(getResources(),getContext());
        hockeyGameEngine.drawFirst(paint, canvas);
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        hockeyGameEngine.drawGame(paint, canvas);
    }



}
