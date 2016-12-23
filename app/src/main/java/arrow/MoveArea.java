package arrow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import myGameUtil.PassiveGameObject;

/**
 * Created by kikuragetyann on 16/01/10.
 */
public class MoveArea extends PassiveGameObject {


    MoveArea(float x, float y, int width, int height) {
        super(x, y, width, height);
        paint.setColor(Color.rgb(255, 220, 220));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
    }
}
