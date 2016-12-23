package arrow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import myGameUtil.ActiveGameObject;

/**
 * Created by kikuragetyann on 16/01/12.
 */
public class Arrow extends ActiveGameObject {


    public Arrow(float x, float y, Bitmap img) {
        super(x, y, img);
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(img, getX(),getY(), null);
    }


}
