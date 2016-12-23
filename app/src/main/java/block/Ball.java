package block;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import com.netgroup_jv.gamecalc.MainActivity;

import java.util.Random;

import myGameUtil.CircleGameObject;
import myGameUtil.KikurageUtil;
import myGameUtil.MoveObject;

/**
 * Created by kikuragetyann on 16/02/05.
 */
public class Ball extends CircleGameObject{

    public static final int upper_Right = 100;
    public static final int lower_Right = 200;
    public static final int lower_Left = 300;
    public static final int upper_Left = 400;
    private int state;


    public Ball(float x, float y, float r) {
        super(x, y, r);
        paint.setColor(Color.rgb(50,50,50));
        paint.setAntiAlias(true);
        setSpeed(4);
        setState(randomDirection());
    }

    public void move(int state){
        switch(state){
            case upper_Right:moveUpperRight();
                break;
            case lower_Right:moveLowerRight();
                break;
            case lower_Left:moveLowerLeft();
                break;
            case upper_Left:moveUpperLeft();
                break;
        }
    }

    public int randomDirection() {
        int direction = 0;
        switch (KikurageUtil.random(4)) {
            case 0:
                direction = upper_Right;
                break;
            case 1:
                direction = lower_Right;
                break;
            case 2:
                direction = lower_Left;
                break;
            case 3:
                direction = upper_Left;
                break;
        }
        return direction;
    }


    public void moveUpperRight(){
        setX(getX() + getSpeed());
        setY(getY() - getSpeed());
    }
    public void moveLowerRight(){
        setX(getX() + getSpeed());
        setY(getY() + getSpeed());
    }
    public void moveLowerLeft(){
        setX(getX() - getSpeed());
        setY(getY() + getSpeed());
    }
    public void moveUpperLeft(){
        setX(getX() - getSpeed());
        setY(getY() - getSpeed());
    }

    public int getState(){
        return this.state;
    }
    public void setState(int direction){
        this.state = direction;
    }

    @Override
    public void draw(Canvas c) {
        c.drawCircle(getX(), getY(), getR(), paint);
    }

}
