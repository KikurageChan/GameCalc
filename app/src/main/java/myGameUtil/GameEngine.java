package myGameUtil;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by kikuragetyann on 16/02/02.
 */
public interface GameEngine {
    public abstract void drawFirst(Paint paint,Canvas canvas);
    public abstract void drawGame(Paint paint,Canvas canvas);
}
