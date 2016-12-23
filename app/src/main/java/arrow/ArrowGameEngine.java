package arrow;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.netgroup_jv.gamecalc.AppData;
import com.netgroup_jv.gamecalc.MainActivity;
import com.netgroup_jv.gamecalc.R;

import java.util.ArrayList;

import myGameUtil.KikurageUtil;
import myGameUtil.GameEngine;
import myGameUtil.KikurageSE;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/02.
 */
public class ArrowGameEngine implements GameEngine {
    /////ゲーム上の部品/////
    private Apple apple;
    private Bitmap appleImg;
    private Bitmap collapseAppleImg;

    private ArrayList<Arrow> arrows;
    private Bitmap arrowImg;

    private AppleEffect appleEffect;
    private Bitmap[] appleEffectImg;

    private MoveArea moveArea;

    /////効果音システム/////
    private KikurageSE kikurageSE;

    /////その他フィールド/////
    //よけた本数
    public int throughCnt;
    private AppData appData;
    private String throughText;

    /////フラグ/////
    private boolean[] one;

    public ArrowGameEngine(Resources resources,Context context) {
        super();
        /////フィールドの初期化/////
        throughText = resources.getString(R.string.through_cnt);
        kikurageSE = KikurageSE.getInstance(context);
        throughCnt = 0;
        one = new boolean[10];
        appData = AppData.getInstance();
        /////画像の読み込み/////
        appleImg = BitmapFactory.decodeResource(resources,R.drawable.apple);
        collapseAppleImg = BitmapFactory.decodeResource(resources,R.drawable.applecollapse);
        arrowImg = BitmapFactory.decodeResource(resources,R.drawable.arrow);
        appleEffectImg = new Bitmap[6];
        appleEffectImg[0] = BitmapFactory.decodeResource(resources, R.drawable.appleeffect00);
        appleEffectImg[1] = BitmapFactory.decodeResource(resources, R.drawable.appleeffect01);
        appleEffectImg[2] = BitmapFactory.decodeResource(resources, R.drawable.appleeffect02);
        appleEffectImg[3] = BitmapFactory.decodeResource(resources, R.drawable.appleeffect03);
        appleEffectImg[4] = BitmapFactory.decodeResource(resources, R.drawable.appleeffect04);
        appleEffectImg[5] = BitmapFactory.decodeResource(resources, R.drawable.appleeffect05);
        /////オブジェクトの生成/////
        appleEffect = new AppleEffect(-100, -100,appleEffectImg[0],appleEffectImg);
        moveArea = new MoveArea(0, 0, MySurface.getScreenWidth() / 3, MySurface.getScreenHeight());
        arrows = new ArrayList<Arrow>();
        apple = new Apple(moveArea.getCenterX() - (appleImg.getWidth() / 2),moveArea.getCenterY() - (appleImg.getHeight() / 2),appleImg);
        for (int i = 0; i < 5; i++) {
            arrows.add(new Arrow((KikurageUtil.random(MySurface.getScreenWidth())) + MySurface.getScreenWidth(), KikurageUtil.random(MySurface.getScreenHeight() - arrowImg.getHeight()), arrowImg));
        }
    }

    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        //背景を塗ります。
        canvas.drawColor(Color.WHITE);
        //ピンク色のmoveAreaを描写します。
        moveArea.draw(canvas);
        //appleの描写します。
        apple.draw(canvas);
        //テキストを描写
        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(KikurageUtil.getPxForInt(14));//約21px
        paint.setAntiAlias(true);
        canvas.drawText(throughText + String.valueOf(throughCnt), MySurface.getScreenWidth() - KikurageUtil.getPxForInt(120), KikurageUtil.getPxForInt(20), paint);
        paint.reset();
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        //背景を塗ります。
        canvas.drawColor(Color.WHITE);
        //ピンク色のmoveAreaを描写します。
        moveArea.draw(canvas);
        //appleの描写です。
        apple.draw(canvas);
        //appleEffectの描写です。
        appleEffect.draw(canvas);
        //矢とりんごが当たり、りんごのアニメーションが終了するとゲームオーバーにします。。
        if (appleEffect.getAnimFinish()) {
            appData.setRamInt(throughCnt);
            MySurface.gameIsRun = false;
            MySurface.gameOver = true;
        }
        //矢に対する処理です。
        for (int i = 0; i < arrows.size(); i++) {
            //矢を個数分描写し、moveさせます。
            arrows.get(i).draw(canvas);
            arrows.get(i).moveX(-5);
            //appleとの当たり判定を書いています。
            if (apple.isHit(arrows.get(i)) && !apple.getHit_Switch()) {
                kikurageSE.play_Soft(KikurageSE.hit);
                kikurageSE.play_Soft(KikurageSE.fresh);
                appleEffect.setLocation(apple.getX(), apple.getY());
                apple.setHit_Switch(true);
                apple.changeImage(collapseAppleImg);
                new Thread(appleEffect).start();
            }
            //arrowが画面の端に到達するとcntを数えランダムに再配置、再描写します。
            if (arrows.get(i).getXx() < moveArea.getX()) {
                throughCnt++;
                arrows.get(i).setLocation((KikurageUtil.random(MySurface.getScreenWidth())) + MySurface.getScreenWidth(), KikurageUtil.random(MySurface.getScreenHeight() - Arrow.getImg_height()));
            }
        }
        //5秒に1本、arrowを増やします。
        for (int i = 0; i < 10; i++) {
            if (MySurface.getElapsedTime() == (i + 1) * 5 && !one[i]) {
                arrows.add(new Arrow((KikurageUtil.random(MySurface.getScreenWidth())) + MySurface.getScreenWidth(), KikurageUtil.random(MySurface.getScreenHeight() - arrowImg.getHeight()), arrowImg));
                one[i] = true;
            }
        }


        //テキストを描写
        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(KikurageUtil.getPxForInt(14));//21
        paint.setAntiAlias(true);
        canvas.drawText(throughText + String.valueOf(throughCnt), MySurface.getScreenWidth() - KikurageUtil.getPxForInt(120), KikurageUtil.getPxForInt(20), paint);



        //appleの移動範囲の指定
        if (!apple.getHit_Switch()) {
            if (moveArea.getX() <= apple.getX()) {
                if (moveArea.getY() <= apple.getY()) {
                    if (apple.getXx() <= moveArea.getXx()) {
                        if (apple.getYy() <= moveArea.getYy()) {
                            apple.moveX((int) (MainActivity.role * 1.5));
                            apple.moveY((int) (MainActivity.pitch * 1.5));
                        } else {
                            apple.setY(moveArea.getYy() - apple.getHeight());
                        }
                    } else {
                        apple.setX(moveArea.getXx() - apple.getWidth());
                    }
                } else {
                    apple.setY(moveArea.getY());
                }
            } else {
                apple.setX(moveArea.getX());
            }
        }
    }

}
