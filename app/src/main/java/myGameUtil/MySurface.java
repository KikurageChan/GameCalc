package myGameUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by kikuragetyann on 16/01/10.
 */
public abstract class MySurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    /////このSurfaceViewに必要なアイテム/////
    private static int screenX;
    private static int screenXx;
    private static int screenY;
    private static int screenYy;
    private static int screenWidth;
    private static int screenHeight;
    private static int touchX;
    private static int touchY;
    private Thread thread;
    private Paint paint;
    private Canvas canvas;
    private int sleepTime = 30;
    //ゲームが起動した瞬間
    private static long startTime;
    private static long elapsedTime;
    //ゲームが動いている時true
    public static boolean gameIsRun = false;
    //ゲームオーバーになるとtrue
    public static boolean gameOver = false;

    public MySurface(Context context, AttributeSet attr) {
        super(context, attr);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public MySurface(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchX = (int) event.getX();
            touchY = (int) event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touchX = (int) event.getX();
            touchY = (int) event.getY();
        }
        return true;
    }

    @Override
    public final void surfaceCreated(SurfaceHolder holder) {
        /////Surfaceの初期処理/////
        screenX = 0;
        screenY = 0;
        screenXx = screenX + getWidth();
        screenYy = screenY + getHeight();
        screenWidth = getWidth();
        screenHeight = getHeight();
        touchX = MySurface.getScreenWidth() / 2;
        touchY = MySurface.getScreenHeight() / 2;
        gameOver = false;
        paint = new Paint();

        //スタート画面に描写する処理です。
        //ホルダーからキャンバスの取得
        canvas = getHolder().lockCanvas();
        drawFirst(paint,canvas);
        getHolder().unlockCanvasAndPost(canvas);
        /////スレッドの起動/////
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public final void surfaceDestroyed(SurfaceHolder holder) {
        gameIsRun = false;
        thread = null;
        gameOver = false;
    }

    @Override
    public final void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public final void run() {
        while (thread != null) {
            if(gameIsRun){
                elapsedTime = (System.currentTimeMillis() - startTime) / 1000L;
                gameBoard();
            }
            try {
                thread.sleep(this.sleepTime);//30
            } catch (Exception e) {
                System.out.println("エラー");
            }
        }
    }

    public final void gameBoard() {
        canvas = getHolder().lockCanvas();
        drawGame(paint,canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    public static void timerStart(){
        startTime = System.currentTimeMillis();
    }


    public abstract void drawFirst(Paint paint,Canvas canvas);
    public abstract void drawGame(Paint paint,Canvas canvas);


    /////getter/////
    public static int getScreenX() {
        return screenX;
    }

    public static int getScreenXx() {
        return screenXx;
    }

    public static int getScreenY() {
        return screenY;
    }

    public static int getScreenYy() {
        return screenYy;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getTouchX() {
        return touchX;
    }

    public static int getTouchY() {
        return touchY;
    }
    public static int getElapsedTime(){
        return (int)elapsedTime;
    }
    public void setSleepTime(int sleepTime){
        this.sleepTime = sleepTime;
    }
}
