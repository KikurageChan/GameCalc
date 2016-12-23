package arrow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import myGameUtil.ActiveGameObject;

/**
 * Created by kikuragetyann on 16/01/10.
 */
public class Apple extends ActiveGameObject {

    public Apple(float x, float y, Bitmap img) {
        super(x, y, img);
    }

    //移動にセンサーを利用するのでこのAppleだけ特殊です。オーバーロードしています。
    @Override
    public void moveX(float x) {
        setX(getX() + (-(x) / 2));
    }
    //移動にセンサーを利用するのでこのAppleだけ特殊です。オーバーロードしています。
    @Override
    public void moveY(float y) {
        setY(getY() + (-(y) / 2));
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(this.img,getX(),getY(), null);
    }

}
