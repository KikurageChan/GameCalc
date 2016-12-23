package space;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.netgroup_jv.gamecalc.ButtonBox;
import com.netgroup_jv.gamecalc.MainActivity;
import com.netgroup_jv.gamecalc.R;

import java.util.ArrayList;

import myGameUtil.GameEngine;
import myGameUtil.KikurageSE;
import myGameUtil.KikurageUtil;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/03/24.
 */
public class ShotGameEngine implements GameEngine {
    //プレイヤー
    private Player player;
    private Bitmap playerImg;
    private Bitmap lockImg;
    //プレイヤーの弾
    private ArrayList<PlayerBullet> playerBullets;

    //敵機1
    private ArrayList<Enemy1> enemy1s;
    private Bitmap enemyImg;
    //爆発エフェクト
    private Bitmap[] bombImgs;

    //Star
    private Star[] star;
    //Window
    private Window window;


    //その他のアイテム
    private ButtonBox buttonBox;
    private KikurageSE kikurageSE;
    private WaveControl waveControl;

    public ShotGameEngine(Resources resources, Context context) {
        /////フィールドの初期化/////
        buttonBox = ButtonBox.getInstance(MainActivity.activity);
        kikurageSE = KikurageSE.getInstance(context);
        waveControl = new WaveControl();
        playerBullets = new ArrayList<>();
        enemy1s = new ArrayList<>();
        bombImgs = new Bitmap[4];
        /////画像の読み込み/////
        playerImg = BitmapFactory.decodeResource(resources, R.drawable.player);
        lockImg = BitmapFactory.decodeResource(resources, R.drawable.lockon);
        enemyImg = BitmapFactory.decodeResource(resources, R.drawable.enemy1);
        bombImgs[0] = BitmapFactory.decodeResource(resources, R.drawable.bomb0);
        bombImgs[1] = BitmapFactory.decodeResource(resources, R.drawable.bomb1);
        bombImgs[2] = BitmapFactory.decodeResource(resources, R.drawable.bomb2);
        bombImgs[3] = BitmapFactory.decodeResource(resources, R.drawable.bomb3);
        /////オブジェクトの生成/////
        //Starを50個生成します。
        star = new Star[50];
        for (int i = 0; i < 50; i++) {
            star[i] = new Star();
        }
        //敵を10体作成します。
        for (int i = 0; i < 10; i++) {
            enemy1s.add(new Enemy1(KikurageUtil.random(MySurface.getScreenWidth()) + MySurface.getScreenWidth(), KikurageUtil.random(MySurface.getScreenHeight() - enemyImg.getHeight()), enemyImg, bombImgs));
        }
        //Windowを作成
        window = new Window();
        window.setSpeed(2);
        //playerを作成
        player = new Player(KikurageUtil.getPxForInt(10), MySurface.getScreenHeight() / 2 - (playerImg.getHeight() / 2), playerImg);
        player.setSpeed(1);
    }

    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        //背景を塗ります。
        canvas.drawColor(Color.BLACK);
        for (int i = 0; i < 50; i++) {
            star[i].draw(canvas);
        }
        player.draw(canvas);
        window.draw(canvas);
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        //背景を塗ります。
        canvas.drawColor(Color.BLACK);
        //星の描写と移動を行います。
        for (int i = 0; i < 50; i++) {
            star[i].draw(canvas);
            star[i].moveMX();
            if (star[i].getX() < MySurface.getScreenX()) {
                star[i].setX(KikurageUtil.random(MySurface.getScreenWidth()) + MySurface.getScreenWidth());
            }
        }
        //playerの描写です。
        player.draw(canvas);
        //windowの描写です。
        window.draw(canvas, enemyImg);
        //lock onの描写です。
        canvas.drawBitmap(lockImg, player.getCenterX() + KikurageUtil.getPxForInt(200), player.getCenterY() - lockImg.getHeight() / 2, paint);
        if (buttonBox.getButton(7).getButtonPressed() && player.getY() > MySurface.getScreenY()) {
            player.moveMY();
        } else if (buttonBox.getButton(2).getButtonPressed() && player.getYy() < MySurface.getScreenYy()) {
            player.moveY();
        }
        //enemy1の描写です
        for (int i = 0; i < enemy1s.size(); i++) {
            enemy1s.get(i).draw(canvas);
            //enemy1と弾が当たった処理です。
            if (enemy1s.get(i).isPlayerBulletHit(playerBullets)) {
                kikurageSE.play_Soft(KikurageSE.bomb);
                enemy1s.get(i).stop();
                new Thread(enemy1s.get(i)).start();
            }
            //enemy1がスクリーン端に到達した時の処理です。
            if (enemy1s.get(i).getXx() < MySurface.getScreenX()) {
                enemy1s.get(i).stop();
            }
        }
        //ボタンの5を押された時の処理です。
        if (buttonBox.getButton(5).getButtonPressed() && !buttonBox.getButton(5).getButtonOne()) {
            playerBullets.add(new PlayerBullet(player.getCenterX(), player.getCenterY()));
            buttonBox.getButton(5).setButtonOne(true);
            kikurageSE.play_Soft(KikurageSE.playerShot);
        }
        //playerの弾の描写です。
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).draw(canvas);
            playerBullets.get(i).moveX(10);
            //弾の消去
            if (playerBullets.get(i).getXx() >= MySurface.getScreenXx()) {
                playerBullets.remove(i);
            }
        }
        //このメソッドを置いておくだけで大丈夫です。
        waveControl.wave1(window);
        //wave1が開始された時の処理
        if (waveControl.wave1Start) {
            for (int i = 0; i < enemy1s.size(); i++) {
                enemy1s.get(i).moveMX();
            }
        }
    }
}
