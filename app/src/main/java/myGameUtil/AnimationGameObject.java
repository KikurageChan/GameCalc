package myGameUtil;

import android.graphics.Bitmap;

/**
 * Created by kikuragetyann on 16/01/24.
 */
public abstract class AnimationGameObject extends ActiveGameObject implements Runnable {

    protected Bitmap[] anim;
    private boolean animFinish = false;
    private int animTurn = 1;   //1 正順 //0 負順 //1<n 繰り返し
    private int animSpeed;


    public AnimationGameObject(float x, float y,Bitmap img,Bitmap[] imgs) {
        super(x, y, img);
        animSpeed = 100;
        this.anim = imgs;
    }


    @Override
    public void run() {
        if (animTurn == 1) {
            for (int i = 0; i < anim.length; i++) {
                changeImage(anim[i]);
                try {
                    Thread.sleep(animSpeed);
                    if (i == anim.length - 1) {
                        animFinish = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else if(animTurn == 0){
            for (int i = anim.length - 1; i >= 0; i--) {
                changeImage(anim[i]);
                try {
                    Thread.sleep(animSpeed);
                    if (i == 0) {
                        animFinish = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }else{
            int i = 0;
            while(i < anim.length){
                changeImage(anim[i]);
                i++;
                try {
                    Thread.sleep(animSpeed);
                } catch (Exception e) {
                    System.out.println(e);
                }
                if(i == anim.length - 1){
                    i = 0;
                }
            }
        }
    }
    public void reset(){
        this.img = anim[0];
        animFinish = false;
    }

    public boolean getAnimFinish() {
        return this.animFinish;
    }


    public void setAnimSpeed(int speed) {
        this.animSpeed = speed;
    }

    public void setAnimTurn(int turn) {
        this.animTurn = turn;
    }
}
