package number;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import myGameUtil.PassiveGameObject;

/**
 * Created by kikuragetyann on 16/02/12.
 */
public class GameOverArea extends PassiveGameObject {

    public GameOverArea(float x, float y, int width, int height) {
        super(x, y, width, height);
        paint.setARGB(255, 255, 100, 140);
    }

    @Override
    public void draw(Canvas canvas) {
        //エリアを描写
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
    }
}
