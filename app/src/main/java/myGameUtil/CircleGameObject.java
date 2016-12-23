package myGameUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by kikuragetyann on 16/02/05.
 */
public abstract class CircleGameObject implements MoveObject {
    protected Paint paint;
    protected Bitmap img;
    private boolean hit_Switch = false;
    private float speed;

    private float x;
    private float y;
    private float r;

    public CircleGameObject(float x, float y, Bitmap img) {
        this.x = x;
        this.y = y;
        this.r = img.getWidth() / 2;
        init();
    }


    public CircleGameObject(float x, float y, float r) {
        this.x = x;
        this.y = y;
        setR(r);
        init();
    }

    public void init() {
        paint = new Paint();
    }

    public void changeImage(Bitmap img) {
        this.img = img;
    }

    public abstract void draw(Canvas c);

    //引数の値は画面のサイズによって変更されるようにします。
    @Override
    public void move(float dpX, float dpY) {
        setX(getX() + KikurageUtil.getPxForFloat(dpX));
        setY(getY() + KikurageUtil.getPxForFloat(dpY));
    }

    //引数に値を指定しない場合は自分の持っているフィールドのspeedの値で移動を実行します。
    @Override
    public void moveX(float dpX) {
        setX(getX() + KikurageUtil.getPxForFloat(dpX));
    }

    @Override
    public void moveX() {
        setX(getX() + getSpeed());
    }

    @Override
    public void moveMX() {
        setX(getX() - getSpeed());
    }



    @Override
    public void moveY(float dpY) {
        setY(getY() + KikurageUtil.getPxForFloat(dpY));
    }


    @Override
    public void moveY() {
        setY(getY() + getSpeed());
    }

    @Override
    public void moveMY() {
        setY(getY() - getSpeed());
    }


    //////////getter/setter//////////
    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return this.r;
    }

    private void setR(float r) {
        this.r = KikurageUtil.getPxForFloat(r);
    }

    public void setHit_Switch(boolean b) {
        this.hit_Switch = b;
    }

    public boolean getHit_Switch() {
        return this.hit_Switch;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = KikurageUtil.getPxForFloat(speed);
    }


}
