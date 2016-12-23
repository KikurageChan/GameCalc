package space;

import android.graphics.Canvas;
import android.graphics.Color;

import myGameUtil.ActiveGameObject;
import myGameUtil.KikurageUtil;

/**
 * Created by kikuragetyann on 16/03/31.
 */
public class PlayerBullet extends ActiveGameObject{

    public PlayerBullet(float x,float y){
        super(x,y,KikurageUtil.getPxForInt(5),KikurageUtil.getPxForInt(2));
    }

    @Override
    public void draw(Canvas c) {
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        c.drawRect(getX(),getY(),getXx(),getYy(),paint);
    }
}
