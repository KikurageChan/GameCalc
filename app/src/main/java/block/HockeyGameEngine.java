package block;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.netgroup_jv.gamecalc.R;

import myGameUtil.GameEngine;
import myGameUtil.KikurageSE;
import myGameUtil.KikurageUtil;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/02.
 */
public class HockeyGameEngine implements GameEngine {
    /////ゲーム上の部品/////
    private Paint paint;
    private Path path;

    private Block_1P block_1P;
    private Bitmap block_1PImg;

    private Block_2P block_2P;
    private Bitmap block_2PImg;

    private Ball ball;

    private Obstacle obstacle1;
    private Obstacle obstacle2;
    private Obstacle obstacle3;

    private Bitmap[] obstacleImg;
    /////効果音システムを利用します/////
    private KikurageSE kikurageSE;
    /////その他フィールド/////
    /////フラグ/////
    private boolean one10min = false;
    private boolean one20min = false;
    private boolean one30min = false;


    public HockeyGameEngine(Resources resources, Context context) {
        super();
        paint = new Paint();
        path = new Path();
        path.moveTo(0, 0);
        path.lineTo(MySurface.getScreenXx(), 0);
        path.moveTo(0, MySurface.getScreenYy());
        path.lineTo(MySurface.getScreenXx(), MySurface.getScreenYy());
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(80, 80, 80));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        kikurageSE = KikurageSE.getInstance(context);
        /////フィールドの初期化/////
        /////画像の読み込み/////
        block_1PImg = BitmapFactory.decodeResource(resources, R.drawable.block);
        block_2PImg = BitmapFactory.decodeResource(resources, R.drawable.block);
        obstacleImg = new Bitmap[5];
        obstacleImg[0] = BitmapFactory.decodeResource(resources, R.drawable.obstacle_100);
        obstacleImg[1] = BitmapFactory.decodeResource(resources, R.drawable.obstacle_80);
        obstacleImg[2] = BitmapFactory.decodeResource(resources, R.drawable.obstacle_60);
        obstacleImg[3] = BitmapFactory.decodeResource(resources, R.drawable.obstacle_40);
        obstacleImg[4] = BitmapFactory.decodeResource(resources, R.drawable.obstacle_20);
        /////オブジェクトの生成/////
        block_1P = new Block_1P(MySurface.getScreenXx() / 8, ((MySurface.getScreenHeight() / 2) - (block_1PImg.getHeight() / 2)), block_1PImg);
        block_2P = new Block_2P((MySurface.getScreenXx() - MySurface.getScreenXx() / 8) - block_2PImg.getWidth(), ((MySurface.getScreenHeight() / 2) - (block_1PImg.getHeight() / 2)), block_1PImg);
        ball = new Ball(MySurface.getScreenWidth() / 2, MySurface.getScreenHeight() / 2, 6);
        obstacle1 = new Obstacle(KikurageUtil.getPxForInt(-100), KikurageUtil.getPxForInt(-100), obstacleImg[0], obstacleImg);
        obstacle2 = new Obstacle(KikurageUtil.getPxForInt(-100), KikurageUtil.getPxForInt(-100), obstacleImg[0], obstacleImg);
        obstacle3 = new Obstacle(KikurageUtil.getPxForInt(-100), KikurageUtil.getPxForInt(-100), obstacleImg[0], obstacleImg);
        obstacle1.setAnimTurn(0);//反対からアニメを再生します。
        obstacle2.setAnimTurn(0);//反対からアニメを再生します。
        obstacle3.setAnimTurn(0);//反対からアニメを再生します。
    }

    @Override
    public void drawFirst(Paint paint, Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(this.path, this.paint);
        block_1P.draw(canvas);
        block_2P.draw(canvas);
    }

    @Override
    public void drawGame(Paint paint, Canvas canvas) {
        //背景を塗ります。
        canvas.drawColor(Color.WHITE);
        //枠を塗ります。
        canvas.drawPath(this.path, this.paint);
        //block_1Pの描写です。
        block_1P.draw(canvas);
        //block_2Pの描写です。
        block_2P.draw(canvas);
        //ballの描写です。
        ball.draw(canvas);
        //obstacleの描写です。
        obstacle1.draw(canvas);
        obstacle2.draw(canvas);
        obstacle3.draw(canvas);
        //ballは方向に応じて移動します。
        ball.move(ball.getState());

        //ballが画面外に到達するとゲームオーバーです。
        if (MySurface.getScreenXx() <= ball.getX() - ball.getR() * 2) {
            MySurface.gameIsRun = false;
            MySurface.gameOver = true;
        } else if (MySurface.getScreenX() >= ball.getX() + ball.getR() * 2) {
            MySurface.gameIsRun = false;
            MySurface.gameOver = true;
        }
        //スクリーンの上下に当たった時の処理です。
        if (MySurface.getScreenY() >= ball.getY() - ball.getR()) {
            if (ball.getState() == Ball.upper_Right) {
                ball.setState(Ball.lower_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.upper_Left) {
                ball.setState(Ball.lower_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            }
        }
        if (MySurface.getScreenYy() <= ball.getY() + ball.getR()) {
            if (ball.getState() == Ball.lower_Right) {
                ball.setState(Ball.upper_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Left) {
                ball.setState(Ball.upper_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            }
        }
        //block_1Pがボールに当たった時の処理です。
        if (block_1P.isHit(ball) && ball.getState() == Ball.upper_Left) {
            ball.setState(Ball.upper_Right);
            kikurageSE.play_Soft(KikurageSE.bound);
        } else if (block_1P.isHit(ball) && ball.getState() == Ball.lower_Left) {
            ball.setState(Ball.lower_Right);
            kikurageSE.play_Soft(KikurageSE.bound);
        }
        //block_2Pがボールに当たった時の処理です。
        if (block_2P.isHit(ball) && ball.getState() == Ball.upper_Right) {
            ball.setState(Ball.upper_Left);
            kikurageSE.play_Soft(KikurageSE.bound);
        } else if (block_2P.isHit(ball) && ball.getState() == Ball.lower_Right) {
            ball.setState(Ball.lower_Left);
            kikurageSE.play_Soft(KikurageSE.bound);
        }

        //block_1Pの移動についての処理です。
        if (block.LeftPanel.getTouchY() <= 0) {
            block_1P.setY(0);
        }
        if (LeftPanel.getTouchY() - block_1P.getHeight() / 2 >= 0 && LeftPanel.getTouchY() + block_1P.getHeight() / 2 <= MySurface.getScreenYy()) {
            block_1P.setY(LeftPanel.getTouchY() - block_1P.getHeight() / 2);
        }
        if (LeftPanel.getTouchY() >= MySurface.getScreenHeight()) {
            block_1P.setYy(MySurface.getScreenYy());
        }
        //block_2Pの移動についての処理です。
        if (RightPanel.getTouchY() <= 0) {
            block_2P.setY(0);
        }
        if (RightPanel.getTouchY() - block_2P.getHeight() / 2 >= 0 && RightPanel.getTouchY() + block_2P.getHeight() / 2 <= MySurface.getScreenYy()) {
            block_2P.setY(RightPanel.getTouchY() - block_2P.getHeight() / 2);
        }
        if (RightPanel.getTouchY() >= MySurface.getScreenHeight()) {
            block_2P.setYy(MySurface.getScreenYy());
        }
        //obstacleについての処理です。
        //10秒経過後obstacle1が発生するようになります。
        if (MySurface.getElapsedTime() >= 10 && !one10min) {
            obstacle1.setLocation(KikurageUtil.random(MySurface.getScreenX() + MySurface.getScreenWidth() / 3) + MySurface.getScreenWidth() / 3, KikurageUtil.random(MySurface.getScreenYy() - obstacleImg[0].getHeight()) + MySurface.getScreenY());
            new Thread(obstacle1).start();
            ball.setSpeed(5);
            one10min = true;
        }
        //20秒経過後obstacle2が発生するようになります。
        if (MySurface.getElapsedTime() >= 20 && !one20min) {
            obstacle2.setLocation(KikurageUtil.random(MySurface.getScreenX() + MySurface.getScreenWidth() / 3) + MySurface.getScreenWidth() / 3, KikurageUtil.random(MySurface.getScreenYy() - obstacleImg[0].getHeight()) + MySurface.getScreenY());
            new Thread(obstacle2).start();
            ball.setSpeed(6);
            one20min = true;
        }
        //30秒経過後obstacle3が発生するようになります。
        if (MySurface.getElapsedTime() >= 30 && !one30min) {
            obstacle3.setLocation(KikurageUtil.random(MySurface.getScreenX() + MySurface.getScreenWidth() / 3) + MySurface.getScreenWidth() / 3, KikurageUtil.random(MySurface.getScreenYy() - obstacleImg[0].getHeight()) + MySurface.getScreenY());
            new Thread(obstacle3).start();
            one30min = true;
            ball.setSpeed(7);
        }
        //obstacle1とballが当たった時の処理です。
        if (obstacle1.isHit(ball)) {
            if (ball.getState() == Ball.upper_Right) {
                ball.setState(Ball.upper_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Right) {
                ball.setState(Ball.lower_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.upper_Left) {
                ball.setState(Ball.upper_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Left) {
                ball.setState(Ball.lower_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            }
            obstacle1.setLocation(KikurageUtil.random(MySurface.getScreenX() + MySurface.getScreenWidth() / 3) + MySurface.getScreenWidth() / 3, KikurageUtil.random(MySurface.getScreenYy() - obstacleImg[0].getHeight()) + MySurface.getScreenY());
            new Thread(obstacle1).start();
        }
        //obstacle2とballが当たった時の処理です。
        if (obstacle2.isHit(ball)) {
            if (ball.getState() == Ball.upper_Right) {
                ball.setState(Ball.upper_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Right) {
                ball.setState(Ball.lower_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.upper_Left) {
                ball.setState(Ball.upper_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Left) {
                ball.setState(Ball.lower_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            }
            obstacle2.setLocation(KikurageUtil.random(MySurface.getScreenX() + MySurface.getScreenWidth() / 3) + MySurface.getScreenWidth() / 3, KikurageUtil.random(MySurface.getScreenYy() - obstacleImg[0].getHeight()) + MySurface.getScreenY());
            new Thread(obstacle2).start();
        }
        //obstacle3とballが当たった時の処理です。
        if (obstacle3.isHit(ball)) {
            if (ball.getState() == Ball.upper_Right) {
                ball.setState(Ball.upper_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Right) {
                ball.setState(Ball.lower_Left);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.upper_Left) {
                ball.setState(Ball.upper_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            } else if (ball.getState() == Ball.lower_Left) {
                ball.setState(Ball.lower_Right);
                kikurageSE.play_Soft(KikurageSE.bound);
            }
            obstacle3.setLocation(KikurageUtil.random(MySurface.getScreenX() + MySurface.getScreenWidth() / 3) + MySurface.getScreenWidth() / 3, KikurageUtil.random(MySurface.getScreenYy() - obstacleImg[0].getHeight()) + MySurface.getScreenY());
            new Thread(obstacle3).start();
        }
    }

}