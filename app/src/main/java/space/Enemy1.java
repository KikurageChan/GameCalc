package space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

import myGameUtil.ActiveGameObject;
import myGameUtil.AnimationGameObject;

/**
 * Created by kikuragetyann on 16/04/01.
 */
public class Enemy1 extends AnimationGameObject {


    public Enemy1(float x, float y, Bitmap img, Bitmap[] imgs) {
        super(x, y, img, imgs);
        setSpeed(2);
    }

    public boolean isPlayerBulletHit(List<PlayerBullet> bullets) {
        for (int i = 0; i < bullets.size(); i++) {
            if (this.isHit(bullets.get(i)) && !this.getHit_Switch()) {
                this.setHit_Switch(true);
                bullets.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(this.img,getX(),getY(), null);
    }
}
