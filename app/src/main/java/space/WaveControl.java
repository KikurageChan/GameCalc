package space;

import myGameUtil.GameObject;
import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/04/01.
 */
public class WaveControl {
    private boolean wave1;
    public boolean wave1Start;
    public boolean wave1Finish;

    WaveControl() {

    }

    //wave1の中身です。
    public void wave1(Window window){
        //ここは1度だけ
        if(MySurface.getElapsedTime() == 3 && !wave1){
            window.settingTimer();
            wave1 = true;
        }
        //ここは複数回
        if(wave1) {
            window.startTimer();
            if(window.getTimer() < 3){
                window.moveY();
            }
            //wave1が開始されてから3秒後
            if(window.getTimer() > 3){
                window.moveMY();
                wave1Start=true;
            }
        }

    }
}
