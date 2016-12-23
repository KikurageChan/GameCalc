package block;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import myGameUtil.GameObject;

/**
 * Created by kikuragetyann on 16/02/05.
 */
public class Block_2P extends GameObject {

    public Block_2P(float x, float y, Bitmap img) {
        super(x, y, img);
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(this.img, getX(), getY(), null);
    }
}
