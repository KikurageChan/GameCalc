package myGameUtil;

import android.graphics.Bitmap;

/**
 * Created by kikuragetyann on 16/03/10.
 */
public abstract class PassiveGameObject extends GameObject {

    public PassiveGameObject(float x, float y, Bitmap img) {
        super(x, y, img);
    }
    public PassiveGameObject(float x, float y, int width, int height) {
        super(x, y, width, height);
    }
}
