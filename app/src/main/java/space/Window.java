package space;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import myGameUtil.ActiveGameObject;
import myGameUtil.GameObject;
import myGameUtil.KikurageUtil;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/04/01.
 */
public class Window extends ActiveGameObject {

    private Paint border;
    private Paint waveText;
    private Paint.FontMetrics waveMetrics;
    private int waveTextHeight;
    private long newTime;
    private long timer;

    public Window() {
        super(MySurface.getScreenXx() - KikurageUtil.getPxForInt(80)-3, MySurface.getScreenY() - KikurageUtil.getPxForInt(40)-3, KikurageUtil.getPxForInt(80), KikurageUtil.getPxForInt(40));
        border = new Paint();
        border.setAntiAlias(true);
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeCap(Paint.Cap.ROUND);
        border.setStrokeWidth(3);
        border.setColor(Color.CYAN);
        paint.setAntiAlias(true);
        paint.setColor(Color.DKGRAY);
        waveText = new Paint();
        waveText.setAntiAlias(true);
        waveText.setColor(Color.WHITE);
        waveText.setTextSize(KikurageUtil.getPxForInt(8));
        waveMetrics = waveText.getFontMetrics();
        waveTextHeight = (int)(waveMetrics.descent - waveMetrics.ascent);
    }

    @Override
    public void draw(Canvas c){
        c.drawLine(getX(), getY(), getXx(), getY(), border);
        c.drawLine(getXx(), getY(), getXx(), getYy(), border);
        c.drawLine(getXx(), getYy(), getX(), getYy(), border);
        c.drawLine(getX(), getYy(), getX(), getY(), border);
        c.drawRect(getX(), getY(), getXx(), getYy(), paint);
    }
    public void draw(Canvas c, Bitmap... imgs){
        c.drawLine(getX(), getY(), getXx(), getY(), border);
        c.drawLine(getXx(), getY(), getXx(), getYy(), border);
        c.drawLine(getXx(), getYy(), getX(), getYy(), border);
        c.drawLine(getX(), getYy(), getX(), getY(), border);
        c.drawRect(getX(), getY(), getXx(), getYy(), paint);
        c.drawText("WAVE 1",getX()+getWidth()/3,getY()+waveTextHeight,waveText);
        for(int i=0;i<imgs.length;i++){
            c.drawBitmap(imgs[i],getCenterX()-imgs[i].getWidth()/2,getCenterY()-imgs[i].getHeight()/2,null);
        }
    }

    @Override
    public void moveY() {
        if(getYy() < (MySurface.getScreenY()+getHeight())+3){
            super.moveY();
        }
    }

    public void settingTimer(){
        this.newTime = System.currentTimeMillis();
    }

    public void startTimer() {
        timer = (System.currentTimeMillis() - newTime) / 1000L;
    }

    public int getTimer() {
        return (int)timer;
    }
}
