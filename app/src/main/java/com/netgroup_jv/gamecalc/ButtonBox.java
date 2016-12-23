package com.netgroup_jv.gamecalc;

import android.app.Activity;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import myGameUtil.KikurageAndroidButton;

/**
 * Created by kikuragetyann on 16/02/03.
 */
public class ButtonBox implements View.OnTouchListener {

    private boolean buttonOne;
    private static ButtonBox buttonBox;
    //現在の色情報はここに一時保存されます。
    private int upColor;
    private int downColor = Color.argb(255, 68, 185, 200);
    //このbuttonsには0〜9までが入っています。
    private ArrayList<KikurageAndroidButton> buttons;
    //このoperatorButtonsには*/+-.=Cが入っています
    private ArrayList<KikurageAndroidButton> operatorButtons;
    private KikurageAndroidButton button0;
    private KikurageAndroidButton button1;
    private KikurageAndroidButton button2;
    private KikurageAndroidButton button3;
    private KikurageAndroidButton button4;
    private KikurageAndroidButton button5;
    private KikurageAndroidButton button6;
    private KikurageAndroidButton button7;
    private KikurageAndroidButton button8;
    private KikurageAndroidButton button9;

    private KikurageAndroidButton buttonGame;

    private KikurageAndroidButton buttonWaru;
    private KikurageAndroidButton buttonKakeru;
    private KikurageAndroidButton buttonTasu;
    private KikurageAndroidButton buttonDot;
    private KikurageAndroidButton buttonEqual;
    private KikurageAndroidButton buttonHiku;
    private KikurageAndroidButton buttonClear;

    private ButtonBox(Activity activity) {
        button0 = (KikurageAndroidButton) activity.findViewById(R.id.button0);
        button0.setOnTouchListener(this);
        button0.setNumber(0);
        button1 = (KikurageAndroidButton) activity.findViewById(R.id.button1);
        button1.setOnTouchListener(this);
        button1.setNumber(1);
        button2 = (KikurageAndroidButton) activity.findViewById(R.id.button2);
        button2.setOnTouchListener(this);
        button2.setNumber(2);
        button3 = (KikurageAndroidButton) activity.findViewById(R.id.button3);
        button3.setOnTouchListener(this);
        button3.setNumber(3);
        button4 = (KikurageAndroidButton) activity.findViewById(R.id.button4);
        button4.setOnTouchListener(this);
        button4.setNumber(4);
        button5 = (KikurageAndroidButton) activity.findViewById(R.id.button5);
        button5.setOnTouchListener(this);
        button5.setNumber(5);
        button6 = (KikurageAndroidButton) activity.findViewById(R.id.button6);
        button6.setOnTouchListener(this);
        button6.setNumber(6);
        button7 = (KikurageAndroidButton) activity.findViewById(R.id.button7);
        button7.setOnTouchListener(this);
        button7.setNumber(7);
        button8 = (KikurageAndroidButton) activity.findViewById(R.id.button8);
        button8.setOnTouchListener(this);
        button8.setNumber(8);
        button9 = (KikurageAndroidButton) activity.findViewById(R.id.button9);
        button9.setOnTouchListener(this);
        button9.setNumber(9);
        buttonKakeru = (KikurageAndroidButton) activity.findViewById(R.id.buttonKakeru);
        buttonKakeru.setOnTouchListener(this);
        buttonWaru = (KikurageAndroidButton) activity.findViewById(R.id.buttonWaru);
        buttonWaru.setOnTouchListener(this);
        buttonTasu = (KikurageAndroidButton) activity.findViewById(R.id.buttonTasu);
        buttonTasu.setOnTouchListener(this);
        buttonHiku = (KikurageAndroidButton) activity.findViewById(R.id.buttonHiku);
        buttonHiku.setOnTouchListener(this);
        buttonDot = (KikurageAndroidButton) activity.findViewById(R.id.buttonDot);
        buttonDot.setOnTouchListener(this);
        buttonEqual = (KikurageAndroidButton) activity.findViewById(R.id.buttonEqual);
        buttonEqual.setOnTouchListener(this);
        buttonClear = (KikurageAndroidButton) activity.findViewById(R.id.buttonClear);
        buttonClear.setOnTouchListener(this);
        buttons = new ArrayList<KikurageAndroidButton>();
        operatorButtons = new ArrayList<KikurageAndroidButton>();
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        operatorButtons.add(buttonKakeru);
        operatorButtons.add(buttonWaru);
        operatorButtons.add(buttonTasu);
        operatorButtons.add(buttonHiku);
        operatorButtons.add(buttonDot);
        operatorButtons.add(buttonEqual);
        operatorButtons.add(buttonClear);
    }

    private ButtonBox(Activity activity, int color, int textColor) {
        upColor = color;
        button0 = (KikurageAndroidButton) activity.findViewById(R.id.button0);
        button0.setOnTouchListener(this);
        button0.setNumber(0);
        button1 = (KikurageAndroidButton) activity.findViewById(R.id.button1);
        button1.setOnTouchListener(this);
        button1.setNumber(1);
        button2 = (KikurageAndroidButton) activity.findViewById(R.id.button2);
        button2.setOnTouchListener(this);
        button2.setNumber(2);
        button3 = (KikurageAndroidButton) activity.findViewById(R.id.button3);
        button3.setOnTouchListener(this);
        button3.setNumber(3);
        button4 = (KikurageAndroidButton) activity.findViewById(R.id.button4);
        button4.setOnTouchListener(this);
        button4.setNumber(4);
        button5 = (KikurageAndroidButton) activity.findViewById(R.id.button5);
        button5.setOnTouchListener(this);
        button5.setNumber(5);
        button6 = (KikurageAndroidButton) activity.findViewById(R.id.button6);
        button6.setOnTouchListener(this);
        button6.setNumber(6);
        button7 = (KikurageAndroidButton) activity.findViewById(R.id.button7);
        button7.setOnTouchListener(this);
        button7.setNumber(7);
        button8 = (KikurageAndroidButton) activity.findViewById(R.id.button8);
        button8.setOnTouchListener(this);
        button8.setNumber(8);
        button9 = (KikurageAndroidButton) activity.findViewById(R.id.button9);
        button9.setOnTouchListener(this);
        button9.setNumber(9);
        buttonGame = (KikurageAndroidButton) activity.findViewById(R.id.buttonGame);
        buttonKakeru = (KikurageAndroidButton) activity.findViewById(R.id.buttonKakeru);
        buttonKakeru.setOnTouchListener(this);
        buttonWaru = (KikurageAndroidButton) activity.findViewById(R.id.buttonWaru);
        buttonWaru.setOnTouchListener(this);
        buttonTasu = (KikurageAndroidButton) activity.findViewById(R.id.buttonTasu);
        buttonTasu.setOnTouchListener(this);
        buttonHiku = (KikurageAndroidButton) activity.findViewById(R.id.buttonHiku);
        buttonHiku.setOnTouchListener(this);
        buttonDot = (KikurageAndroidButton) activity.findViewById(R.id.buttonDot);
        buttonDot.setOnTouchListener(this);
        buttonEqual = (KikurageAndroidButton) activity.findViewById(R.id.buttonEqual);
        buttonEqual.setOnTouchListener(this);
        buttonClear = (KikurageAndroidButton) activity.findViewById(R.id.buttonClear);
        buttonClear.setOnTouchListener(this);
        buttons = new ArrayList<KikurageAndroidButton>();
        operatorButtons = new ArrayList<KikurageAndroidButton>();
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        operatorButtons.add(buttonKakeru);
        operatorButtons.add(buttonWaru);
        operatorButtons.add(buttonTasu);
        operatorButtons.add(buttonHiku);
        operatorButtons.add(buttonDot);
        operatorButtons.add(buttonEqual);
        operatorButtons.add(buttonClear);
        for (KikurageAndroidButton khButton : buttons) {
            khButton.setBackgroundColor(color);
            khButton.setTextColor(textColor);
        }
        for (KikurageAndroidButton khButton : operatorButtons) {
            khButton.setBackgroundColor(color);
            khButton.setTextColor(textColor);
        }
        buttonGame.setBackgroundColor(color);
        buttonGame.setTextColor(textColor);
    }

    public static ButtonBox getInstance(Activity activity) {
        if (buttonBox == null) {
            buttonBox = new ButtonBox(activity);
        }
        return buttonBox;
    }

    public static ButtonBox getInstance(Activity activity, int color, int textColor) {
        if (buttonBox == null) {
            buttonBox = new ButtonBox(activity, color, textColor);
        }
        return buttonBox;
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.button0:
                    button0.setButtonPressed(true);
                    button0.setBackgroundColor(downColor);
                    break;
                case R.id.button1:
                    button1.setButtonPressed(true);
                    button1.setBackgroundColor(downColor);
                    break;
                case R.id.button2:
                    button2.setButtonPressed(true);
                    button2.setBackgroundColor(downColor);
                    break;
                case R.id.button3:
                    button3.setButtonPressed(true);
                    button3.setBackgroundColor(downColor);
                    break;
                case R.id.button4:
                    button4.setButtonPressed(true);
                    button4.setBackgroundColor(downColor);
                    break;
                case R.id.button5:
                    button5.setButtonPressed(true);
                    button5.setBackgroundColor(downColor);
                    break;
                case R.id.button6:
                    button6.setButtonPressed(true);
                    button6.setBackgroundColor(downColor);
                    break;
                case R.id.button7:
                    button7.setButtonPressed(true);
                    button7.setBackgroundColor(downColor);
                    break;
                case R.id.button8:
                    button8.setButtonPressed(true);
                    button8.setBackgroundColor(downColor);
                    break;
                case R.id.button9:
                    button9.setButtonPressed(true);
                    button9.setBackgroundColor(downColor);
                    break;
                case R.id.buttonKakeru:
                    buttonKakeru.setButtonPressed(true);
                    buttonKakeru.setBackgroundColor(downColor);
                    break;
                case R.id.buttonWaru:
                    buttonWaru.setButtonPressed(true);
                    buttonWaru.setBackgroundColor(downColor);
                    break;
                case R.id.buttonTasu:
                    buttonTasu.setButtonPressed(true);
                    buttonTasu.setBackgroundColor(downColor);
                    break;
                case R.id.buttonHiku:
                    buttonHiku.setButtonPressed(true);
                    buttonHiku.setBackgroundColor(downColor);
                    break;
                case R.id.buttonDot:
                    buttonDot.setButtonPressed(true);
                    buttonDot.setBackgroundColor(downColor);
                    break;
                case R.id.buttonEqual:
                    buttonEqual.setButtonPressed(true);
                    buttonEqual.setBackgroundColor(downColor);
                    break;
                case R.id.buttonClear:
                    buttonClear.setButtonPressed(true);
                    buttonClear.setBackgroundColor(downColor);
                    break;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (view.getId()) {
                case R.id.button0:
                    button0.setButtonPressed(false);
                    setButtonOne(false);
                    button0.setButtonOne(false);
                    button0.setBackgroundColor(upColor);
                    break;
                case R.id.button1:
                    button1.setButtonPressed(false);
                    setButtonOne(false);
                    button1.setButtonOne(false);
                    button1.setBackgroundColor(upColor);
                    break;
                case R.id.button2:
                    button2.setButtonPressed(false);
                    setButtonOne(false);
                    button2.setButtonOne(false);
                    button2.setBackgroundColor(upColor);
                    break;
                case R.id.button3:
                    button3.setButtonPressed(false);
                    setButtonOne(false);
                    button3.setButtonOne(false);
                    button3.setBackgroundColor(upColor);
                    break;
                case R.id.button4:
                    button4.setButtonPressed(false);
                    setButtonOne(false);
                    button4.setButtonOne(false);
                    button4.setBackgroundColor(upColor);
                    break;
                case R.id.button5:
                    button5.setButtonPressed(false);
                    setButtonOne(false);
                    button5.setButtonOne(false);
                    button5.setBackgroundColor(upColor);
                    break;
                case R.id.button6:
                    button6.setButtonPressed(false);
                    setButtonOne(false);
                    button6.setButtonOne(false);
                    button6.setBackgroundColor(upColor);
                    break;
                case R.id.button7:
                    button7.setButtonPressed(false);
                    setButtonOne(false);
                    button7.setButtonOne(false);
                    button7.setBackgroundColor(upColor);
                    break;
                case R.id.button8:
                    button8.setButtonPressed(false);
                    setButtonOne(false);
                    button8.setButtonOne(false);
                    button8.setBackgroundColor(upColor);
                    break;
                case R.id.button9:
                    button9.setButtonPressed(false);
                    setButtonOne(false);
                    button9.setButtonOne(false);
                    button9.setBackgroundColor(upColor);
                    break;
                case R.id.buttonKakeru:
                    buttonKakeru.setButtonPressed(false);
                    setButtonOne(false);
                    buttonKakeru.setButtonOne(false);
                    buttonKakeru.setBackgroundColor(upColor);
                    break;
                case R.id.buttonWaru:
                    buttonWaru.setButtonPressed(false);
                    setButtonOne(false);
                    buttonWaru.setButtonOne(false);
                    buttonWaru.setBackgroundColor(upColor);
                    break;
                case R.id.buttonTasu:
                    buttonTasu.setButtonPressed(false);
                    setButtonOne(false);
                    buttonTasu.setButtonOne(false);
                    buttonTasu.setBackgroundColor(upColor);
                    break;
                case R.id.buttonHiku:
                    buttonHiku.setButtonPressed(false);
                    setButtonOne(false);
                    buttonHiku.setButtonOne(false);
                    buttonHiku.setBackgroundColor(upColor);
                    break;
                case R.id.buttonDot:
                    buttonDot.setButtonPressed(false);
                    setButtonOne(false);
                    buttonDot.setButtonOne(false);
                    buttonDot.setBackgroundColor(upColor);
                    break;
                case R.id.buttonEqual:
                    buttonEqual.setButtonPressed(false);
                    setButtonOne(false);
                    buttonEqual.setButtonOne(false);
                    buttonEqual.setBackgroundColor(upColor);
                    break;
                case R.id.buttonClear:
                    buttonClear.setButtonPressed(false);
                    setButtonOne(false);
                    buttonClear.setButtonOne(false);
                    buttonClear.setBackgroundColor(upColor);
                    break;
            }
        }
        return false;
    }

    public void unregister() {
        buttonBox = null;
    }

    /////getter/setter/////
    public KikurageAndroidButton getButton(int number) {
        return buttons.get(number);
    }

    public KikurageAndroidButton getOperatorButton(int number) {
        return operatorButtons.get(number);
    }

    public KikurageAndroidButton getButtonGame() {
        return this.buttonGame;
    }
    public int getButtonsSize(){
        return buttons.size();
    }

    public synchronized boolean getButtonOne() {
        return buttonOne;
    }

    public synchronized void setButtonOne(boolean b) {
        buttonOne = b;
    }
}
