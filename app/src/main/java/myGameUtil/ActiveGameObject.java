package myGameUtil;

import android.graphics.Bitmap;

/**
 * Created by kikuragetyann on 16/03/10.
 */
public abstract class ActiveGameObject extends GameObject implements MoveObject {
    private float speed;
    private boolean isMove = true;

    public ActiveGameObject(float x, float y, Bitmap img) {
        super(x, y, img);
    }

    public ActiveGameObject(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    //引数の値はピクセルで考えているのでそれを内部でdpにします
    public void move(float dpX, float dpY) {
        if (isMove) {
            setX(getX() + KikurageUtil.getPxForFloat(dpX));
            setY(getY() + KikurageUtil.getPxForFloat(dpY));
        }
    }

    @Override
    //引数に値を指定しない場合は自分の持っているフィールドのspeedの値で移動を実行します。
    public void moveX(float dpX) {
        if (isMove) {
            setSpeed(dpX);
            setX(getX() + KikurageUtil.getPxForFloat(dpX));
        }
    }

    @Override
    public void moveX() {
        if (isMove) {
            setX(getX() + getSpeed());
        }
    }

    @Override
    public void moveMX() {
        if (isMove) {
            setX(getX() - getSpeed());
        }
    }

    @Override
    public void moveY(float dpY) {
        if (isMove) {
            setSpeed(dpY);
            setY(getY() + KikurageUtil.getPxForFloat(dpY));
        }
    }

    @Override
    public void moveY() {
        if (isMove) {
            setY(getY() + getSpeed());
        }
    }

    @Override
    public void moveMY() {
        if (isMove) {
            setY(getY() - getSpeed());
        }
    }

    public void stop() {
        isMove = false;
    }

    /////getter/setter/////
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = KikurageUtil.getPxForFloat(speed);
    }

    public boolean getIsMove() {
        return this.isMove;
    }
}
