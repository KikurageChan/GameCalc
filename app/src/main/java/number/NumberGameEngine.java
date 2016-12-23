package number;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.netgroup_jv.gamecalc.AppData;
import com.netgroup_jv.gamecalc.ButtonBox;
import com.netgroup_jv.gamecalc.MainActivity;
import com.netgroup_jv.gamecalc.R;

import java.util.ArrayList;

import myGameUtil.GameEngine;
import myGameUtil.KikurageSE;
import myGameUtil.KikurageUtil;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/12.
 */
public class NumberGameEngine implements GameEngine {
    /////ゲーム上の部品/////
    private GameOverArea gameOverArea;
    ArrayList<NormalNumberCard> normalNumberCards;
    /////その他フィールド/////
    private NormalNumberCard headCard;
    private NormalNumberCard tailCard;
    private int a = MySurface.getScreenXx();
    private int deleteCardCount = 0;
    /////フラグ/////
    private boolean level1 = false;
    private boolean level2 = false;
    private boolean level3 = false;
    private boolean level4 = false;
    private boolean level5 = false;
    private boolean level6 = false;
    /////効果音システム/////
    private KikurageSE kikurageSE;
    /////UIのボタンを利用します/////
    private ButtonBox buttonBox;
    /////その他フィールド/////
    private AppData appData;
    private Resources resources;

    public NumberGameEngine(Resources resources,Context context){
        super();
        /////初期化処理/////
        this.resources = resources;
        appData = AppData.getInstance();
        /////効果音システム/////
        kikurageSE = KikurageSE.getInstance(context);
        /////ボタンを起動/////
        buttonBox = ButtonBox.getInstance(MainActivity.activity);
        /////オブジェクトの生成/////
        gameOverArea = new GameOverArea(0, 0, MySurface.getScreenWidth() / 6, MySurface.getScreenHeight());
        normalNumberCards = new ArrayList<NormalNumberCard>();
        //カードを7枚作成します。
        for (int i = 0; i < 8; i++) {
            //newしたタイミングではheightを取ってこれないため35dp指定しています。
            normalNumberCards.add(new NormalNumberCard(a, KikurageUtil.random(MySurface.getScreenHeight() - KikurageUtil.getPxForInt(35))));
            //重なり合わないようにx軸は次のwidthを足しています。そして10dpのマージンを右につけています。
            a = a + normalNumberCards.get(i).getWidth() + KikurageUtil.getPxForInt(10);
        }
        //Listの先頭のカードがhedaCardになります。
        headCard = normalNumberCards.get(0);
        //Listの末尾のカードがtailCardになります。
        tailCard = normalNumberCards.get(normalNumberCards.size() - 1);
    }

    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        //背景を白く塗りつぶします。
        canvas.drawColor(Color.WHITE);
        //gameOverAreaを描画します。
        gameOverArea.draw(canvas);
        //進入禁止を描写
        canvas.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.emergency),gameOverArea.getXx()-KikurageUtil.getPxForInt(10),0,null);
        //撃退したカード数を表示します
        paint.setColor(Color.BLACK);
        paint.setTextSize(KikurageUtil.getPxForInt(14));//21
        paint.setAntiAlias(true);
        canvas.drawText("SCORE:" + deleteCardCount, MySurface.getScreenXx() - paint.measureText("SCORE:0000"), MySurface.getScreenY() + KikurageUtil.getPxForInt(20), paint);
        paint.reset();
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        //背景を白く塗ります。
        canvas.drawColor(Color.WHITE);
        //gameOverAreaを描画します。
        gameOverArea.draw(canvas);
        //進入禁止を描写
        canvas.drawBitmap(BitmapFactory.decodeResource(resources, R.drawable.emergency), gameOverArea.getXx() - KikurageUtil.getPxForInt(10), 0, null);
        //cardsに入っているカード全てに対する処理です。
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).draw(canvas);
            normalNumberCards.get(i).moveX();
        }
        //撃退したカードの数を描写します。
//        paint.setColor(Color.BLACK);
        paint.setTextSize(KikurageUtil.getPxForInt(14));
        paint.setAntiAlias(true);
        canvas.drawText("SCORE:" + deleteCardCount, MySurface.getScreenXx()-paint.measureText("SCORE:0000"), MySurface.getScreenY()+KikurageUtil.getPxForInt(20), paint);
        //ゲームレベルチェック
        if (deleteCardCount < 20 && !level1) {
            level1();//20枚消すと次のレベルへ
        } else if (deleteCardCount >= 20 && !level2) {
            level2();//20枚消すと次のレベルへ
        } else if (deleteCardCount >= 40 && !level3) {
            level3();//30枚消すと次のレベルへ
        } else if (deleteCardCount >= 70 && !level4) {
            level4();//20枚消すと次のレベルへ
        } else if (deleteCardCount >= 90 && !level5) {
            level5();//40枚消すと次のレベルへ
        } else if (deleteCardCount >= 130 && !level6) {
            level6();
        }
        //x座標が1番小さいものがheadCard、1番大きいものがtailCardになります。
        for (int i = 0; i < normalNumberCards.size(); i++) {
            if (headCard.getX() > normalNumberCards.get(i).getX()) {
                headCard = normalNumberCards.get(i);
            }
            if (tailCard.getX() < normalNumberCards.get(i).getX()) {
                tailCard = normalNumberCards.get(i);
            }
        }
        for (int i = 0; i < 10; i++) {
            //押したボタンとheadCardの番号が一致した場合つまり正確に押した時
            if (buttonBox.getButton(i).getNumber() == headCard.getNumber() && buttonBox.getButton(i).getButtonPressed() && !buttonBox.getButtonOne()) {
                buttonBox.setButtonOne(true);
                deleteCardCount++;
                headCard.setX(tailCard.getXx() + KikurageUtil.getPxForInt(15));
                headCard.setY(KikurageUtil.random(MySurface.getScreenHeight() - KikurageUtil.getPxForInt(35)));
                headCard.reset();
                kikurageSE.play_Big(KikurageSE.typeSound);
                break;
                //押したボタンとheadCardの番号が一致していない場合つまりミスした場合
            } else if (buttonBox.getButton(i).getNumber() != headCard.getNumber() && buttonBox.getButton(i).getButtonPressed() && !buttonBox.getButtonOne()) {
                buttonBox.setButtonOne(true);
                //効果音missを鳴らします
                kikurageSE.play_Normal(KikurageSE.missSound);
                //全てのカードを少し移動させます(10dp)
                for (int j = 0; j < normalNumberCards.size(); j++) {
                    normalNumberCards.get(j).setX(normalNumberCards.get(j).getX() - KikurageUtil.getPxForInt(10));
                }
                break;
            }
        }
        //gameOverAreaに先頭のカードが触れるとゲームオーバーです。
        if (headCard.getXx() < gameOverArea.getXx()) {
            appData.setRamInt(deleteCardCount);
            MySurface.gameOver = true;
            MySurface.gameIsRun = false;
        }
    }

    //level1ではスピードが4　テキストはNormalのみ
    public void level1() {
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).setSpeed(-3);
        }
        level1 = true;
    }

    //level2ではスピードが5　テキストはNormalのみ
    public void level2() {
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).setSpeed(-3.5f);
        }
        level2 = true;
    }

    //level3ではスピードが5 テキストはNormalとJapanが混合します。
    public void level3() {
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).setCardTextType(Card.NORMAL_JAPAN);
        }
        level3 = true;
    }

    //level4ではスピードが6 テキストはNormalとJapanが混合します。
    public void level4() {
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).setSpeed(-4);
        }
        level4 = true;
    }

    //level5ではスピードが6、テキストはNormalとRomaとJapanが混合します。
    public void level5() {
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).setCardTextType(Card.NORMAL_JAPAN_ROMA);
        }
        level5 = true;
    }

    //level6ではスピードが7、テキストはNormalとRomaとJapanが混合します。
    public void level6() {
        for (int i = 0; i < normalNumberCards.size(); i++) {
            normalNumberCards.get(i).setSpeed(-5);
        }
        level6 = true;
    }
}

