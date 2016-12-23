package block;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import myGameUtil.AnimationGameObject;

/**
 * Created by kikuragetyann on 16/03/10.
 */
public class Obstacle extends AnimationGameObject{


    public Obstacle(float x, float y, Bitmap img, Bitmap[] imgs) {
        super(x, y, img, imgs);
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(this.img, getX(), getY(), null);
    }
}
