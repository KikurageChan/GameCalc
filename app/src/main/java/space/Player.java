package space;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import myGameUtil.ActiveGameObject;

/**
 * Created by kikuragetyann on 16/03/24.
 */
public class Player extends ActiveGameObject {
    private int speedLevel;
    private int attackLevel;
    private int bulletLevel;

    public Player(float x, float y, Bitmap img) {
        super(x, y, img);
        this.speedLevel = 1;
        this.attackLevel = 1;
        this.bulletLevel = 1;
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(this.img, getX(), getY(), null);
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        this.speedLevel = speedLevel;
    }

    public int getAttackLevel() {
        return attackLevel;
    }

    public void setAttackLevel(int attackLevel) {
        this.attackLevel = attackLevel;
    }

    public int getBulletLevel() {
        return bulletLevel;
    }

    public void setBulletLevel(int bulletLevel) {
        this.bulletLevel = bulletLevel;
    }
}
