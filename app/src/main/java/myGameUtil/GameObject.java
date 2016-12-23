package myGameUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by kikuragetyann on 16/01/10.
 */
public abstract class GameObject {
    protected Paint paint;
    private Random random;
    protected Bitmap img;
    private boolean hit_Switch = false;
    protected float x;
    protected float y;
    protected float xx;
    protected float yy;
    protected int width;
    protected int height;
    protected static int img_width;
    protected static int img_height;


    //画像を持たずにdrawのみの場合はこのコンストラクタを使用します。
    public GameObject(float x, float y, int width,int height) {
        this.x = x;
        this.y = y;
        this.xx = this.x + width;
        this.yy = this.y + height;
        this.width = width;
        this.height = height;
        init();
    }

    //画像をこのインスタンス生成時に渡しても良いです。
    public GameObject(float x, float y, Bitmap img) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.xx = this.x + img.getWidth();
        this.yy = this.y + img.getHeight();
        this.width = img.getWidth();
        this.height = img.getHeight();
        img_width = img.getWidth();
        img_height = img.getHeight();
        init();
    }



    public void init() {
        random = new Random();
        paint = new Paint();
    }

    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }

    public float getCenterX() {
        return (getX() + getXx()) / 2;
    }

    public float getCenterY() {
        return (getY() + getYy()) / 2;
    }


    public void changeImage(Bitmap img) {
        this.img = img;
    }


    public boolean isHit(GameObject obj) {
        if (this.getXx() >= obj.getX() && this.getX() <= obj.getXx()) {
            if (this.getY() <= obj.getYy() && this.getYy() >= obj.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean isHit(CircleGameObject obj) {
        if (A(obj) || B(obj) || C(obj) || D(obj) || E(obj) || F(obj)) {
            return true;
        }
        return false;
    }


    public abstract void draw(Canvas c);

    public int random(int a) {
        return random.nextInt(a);
    }

    ////////getter/setter///////////
    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
        this.xx = this.x + this.width;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
        this.yy = this.y + this.height;
    }

    public float getXx() {
        return this.xx;
    }

    public void setXx(float xx) {
        this.xx = xx;
        this.x = this.xx - this.width;
    }

    public float getYy() {
        return this.yy;
    }

    public void setYy(float yy) {
        this.yy = yy;
        this.y = this.yy - this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHit_Switch(boolean b) {
        this.hit_Switch = b;
    }

    public boolean getHit_Switch() {
        return this.hit_Switch;
    }

    public Bitmap getImg() {
        return this.img;
    }

    public static int getImg_width(){
        return img_width;
    }
    public static int getImg_height(){
        return img_height;
    }


    //////////当たり判定//////////
    public final boolean A(CircleGameObject obj) {
        if ((obj.getX() >= getX()) && (obj.getX() <= getXx()) && (obj.getY() >= getY() - obj.getR()) && (obj.getY() <= getYy() + obj.getR())) {
            return true;
        }
        return false;
    }

    public final boolean B(CircleGameObject obj) {
        if ((obj.getX() >= getX() - obj.getR()) && (obj.getX() <= getXx() + obj.getR()) && (obj.getY() >= getY()) && (obj.getY() <= getYy())) {
            return true;
        }
        return false;
    }

    public final boolean C(CircleGameObject obj) {
        if (Math.pow(getX() - obj.getX(), 2) + Math.pow(getY() - obj.getY(), 2) <= Math.pow(obj.getR(), 2)) {
            return true;
        }
        return false;
    }

    public final boolean D(CircleGameObject obj) {
        if (Math.pow(getXx() - obj.getX(), 2) + Math.pow(getY() - obj.getY(), 2) <= Math.pow(obj.getR(), 2)) {
            return true;
        }
        return false;
    }

    public final boolean E(CircleGameObject obj) {
        if (Math.pow(getXx() - obj.getX(), 2) + Math.pow(getYy() - obj.getY(), 2) <= Math.pow(obj.getR(), 2)) {
            return true;
        }
        return false;
    }

    public final boolean F(CircleGameObject obj) {
        if (Math.pow(getX() - obj.getX(), 2) + Math.pow(getYy() - obj.getY(), 2) <= Math.pow(obj.getR(), 2)) {
            return true;
        }
        return false;
    }
}

