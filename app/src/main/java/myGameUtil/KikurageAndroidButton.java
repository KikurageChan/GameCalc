package myGameUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by kikuragetyann on 16/02/14.
 */
public class KikurageAndroidButton extends Button{
    //int型の変数を持ちます。
    private int number;
    //ボタンが押されている時にtrueになるフラグを持ちます。
    private boolean buttonPressed = false;
    //ボタンをループの中で離すまで連続入力を防ぐ時に使用します。
    private boolean buttonOne;
    public KikurageAndroidButton(Context context) {
        super(context);
    }
    public KikurageAndroidButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number){
        this.number = number;
    }
    public boolean getButtonPressed(){
        return this.buttonPressed;
    }
    public void setButtonPressed(boolean b){
        this.buttonPressed = b;
    }

    public synchronized boolean getButtonOne() {
        return buttonOne;
    }

    public synchronized void setButtonOne(boolean b) {
        buttonOne = b;
    }
}
