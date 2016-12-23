package space;

import android.graphics.Canvas;
import android.graphics.Color;

import myGameUtil.ActiveGameObject;
import myGameUtil.KikurageUtil;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/03/30.
 */
public class Star extends ActiveGameObject{

    //コンストラクタ
    public Star(){
        super(KikurageUtil.random(MySurface.getScreenWidth()*2), KikurageUtil.random(MySurface.getScreenHeight()), KikurageUtil.random(KikurageUtil.getPxForInt(2))+1, KikurageUtil.getPxForInt(2));
        setSpeed(KikurageUtil.random(4)+1);
    }


    //描写メソッドだね☆
    @Override
    public void draw(Canvas c) {
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        c.drawRect(getX(),getY(),getXx(),getYy(),paint);
    }

}
