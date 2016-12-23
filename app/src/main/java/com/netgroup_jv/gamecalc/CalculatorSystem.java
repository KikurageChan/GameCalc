package com.netgroup_jv.gamecalc;

import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by kikuragetyann on 16/02/10.
 */
public class CalculatorSystem {
    private String text;
    private String number;
    private String number2;
    private String logic;
    private String ans;
    private int cnt = 0;

    public CalculatorSystem() {
        text = "0";
        number = "0";
        number2 = null;
        logic = null;
        ans = null;
    }

    public void numberOn(TextView textView, View view, TextView operatorText) {
        if (MainActivity.gameType == MainActivity.CALC) {
            int button = 0;
            switch (view.getId()) {
                case R.id.button0:
                    button = 0;
                    break;
                case R.id.button1:
                    button = 1;
                    break;
                case R.id.button2:
                    button = 2;
                    break;
                case R.id.button3:
                    button = 3;
                    break;
                case R.id.button4:
                    button = 4;
                    break;
                case R.id.button5:
                    button = 5;
                    break;
                case R.id.button6:
                    button = 6;
                    break;
                case R.id.button7:
                    button = 7;
                    break;
                case R.id.button8:
                    button = 8;
                    break;
                case R.id.button9:
                    button = 9;
                    break;
            }
            if (logic == null) {
                if (text.length() < 8) {
                    if (text.equals("0")) {
                        //0のときはボタンと同じテキストにします。
                        text = String.valueOf(button);
                    } else {
                        //0ではないときは右に数を追加していきます。
                        text = text + String.valueOf(button);
                    }
                }
                number = text;
            } else {
                if (cnt == 0) {
                    cnt++;
                    text = "";
                    operatorText.setText(String.valueOf(number) + getLogicText(Integer.parseInt(logic)));
                }
                if (text.length() < 8) {
                    if (text.equals("0")) {
                        //0のときはボタンと同じテキストにします。
                        text = String.valueOf(button);
                    } else {
                        //0ではないときは右に数を追加していきます。
                        text = text + String.valueOf(button);
                    }
                }
                number2 = text;
            }
            textView.setText(text);
        }
    }

    public void logic(TextView textView, View view, TextView operatorText) {
        if (MainActivity.gameType == MainActivity.CALC) {
            if (logic != null && number != null && number2 != null) {
                equal(textView, operatorText);
                operatorText.setText(String.valueOf(ans));
                switch (view.getId()) {
                    case R.id.buttonTasu:
                        logic = "1";
                        operatorText.setText(operatorText.getText() + "＋");
                        break;
                    case R.id.buttonHiku:
                        logic = "2";
                        operatorText.setText(operatorText.getText() + "−");
                        break;
                    case R.id.buttonKakeru:
                        logic = "3";
                        operatorText.setText(operatorText.getText() + "×");
                        break;
                    case R.id.buttonWaru:
                        logic = "4";
                        operatorText.setText(operatorText.getText() + "÷");
                        break;
                }
            } else {
                switch (view.getId()) {
                    case R.id.buttonTasu:
                        logic = "1";
                        if (containOperator(operatorText.getText().toString())) {
                            if (operatorText.getText().toString().contains("＋")) {
                                operatorText.setText(operatorText.getText());
                            } else if (operatorText.getText().toString().contains("−")) {
                                operatorText.setText(operatorText.getText().toString().replace('−', '＋'));
                            } else if (operatorText.getText().toString().contains("×")) {
                                operatorText.setText(operatorText.getText().toString().replace('×', '＋'));
                            } else if (operatorText.getText().toString().contains("÷")) {
                                operatorText.setText(operatorText.getText().toString().replace('÷', '＋'));
                            }
                        } else {
                            operatorText.setText("＋");
                        }
                        break;
                    case R.id.buttonHiku:
                        logic = "2";
                        if (containOperator(operatorText.getText().toString())) {
                            if (operatorText.getText().toString().contains("＋")) {
                                operatorText.setText(operatorText.getText().toString().replace('＋', '−'));
                            } else if (operatorText.getText().toString().contains("−")) {
                                operatorText.setText(operatorText.getText());
                            } else if (operatorText.getText().toString().contains("×")) {
                                operatorText.setText(operatorText.getText().toString().replace('×', '−'));
                            } else if (operatorText.getText().toString().contains("÷")) {
                                operatorText.setText(operatorText.getText().toString().replace('÷', '−'));
                            }
                        } else {
                            operatorText.setText("−");
                        }
                        break;
                    case R.id.buttonKakeru:
                        logic = "3";
                        if (containOperator(operatorText.getText().toString())) {
                            if (operatorText.getText().toString().contains("＋")) {
                                operatorText.setText(operatorText.getText().toString().replace('＋', '×'));
                            } else if (operatorText.getText().toString().contains("−")) {
                                operatorText.setText(operatorText.getText().toString().replace('−', '×'));
                            } else if (operatorText.getText().toString().contains("×")) {
                                operatorText.setText(operatorText.getText());
                            } else if (operatorText.getText().toString().contains("÷")) {
                                operatorText.setText(operatorText.getText().toString().replace('÷', '×'));
                            }
                        } else {
                            operatorText.setText("×");
                        }
                        break;
                    case R.id.buttonWaru:
                        logic = "4";
                        if (containOperator(operatorText.getText().toString())) {
                            if (operatorText.getText().toString().contains("＋")) {
                                operatorText.setText(operatorText.getText().toString().replace('＋', '÷'));
                            } else if (operatorText.getText().toString().contains("−")) {
                                operatorText.setText(operatorText.getText().toString().replace('−', '÷'));
                            } else if (operatorText.getText().toString().contains("×")) {
                                operatorText.setText(operatorText.getText().toString().replace('×', '÷'));
                            } else if (operatorText.getText().toString().contains("÷")) {
                                operatorText.setText(operatorText.getText());
                            }
                        } else {
                            operatorText.setText("÷");
                        }
                        break;
                }
            }
        }
    }

    public void clear(TextView textView, TextView operatorText) {
        if (MainActivity.gameType == MainActivity.CALC) {
            text = "0";
            operatorText.setText("");
            number = "0";
            textView.setText(text);
            logic = null;
            number2 = null;
            ans = null;
            cnt = 0;
        }
    }

    public void dot(TextView textView) {
        if (MainActivity.gameType == MainActivity.CALC) {
            if (!text.contains(".")) {
                text = text + ".";
                textView.setText(text);
            }
        }
    }

    public void equal(TextView textView, TextView operatorText) {
        if (MainActivity.gameType == MainActivity.CALC) {
            if (number != null && number2 != null) {
                ans = calculation(Double.parseDouble(number), Double.parseDouble(number2), Integer.parseInt(logic));
                if (ans.equals("エラー")) {
                    clear(textView, operatorText);
                } else {
                    textView.setText(ans);
                    operatorText.setText("");
                    logic = null;
                    number = String.valueOf(ans);
                    number2 = null;
                    text = ans;
                    cnt = 0;
                }
            }
        }
    }

    public String calculation(double a, double b, int logic) {
        if (MainActivity.gameType == MainActivity.CALC) {
            double ans = 0;
            String s;
            switch (logic) {
                case 1:
                    ans = a + b;
                    break;
                case 2:
                    ans = a - b;
                    break;
                case 3:
                    ans = a * b;
                    break;
                case 4:
                    ans = a / b;
                    break;
            }
            if (ans == Double.POSITIVE_INFINITY || ans == Double.NEGATIVE_INFINITY) {
                return "エラー";
            }
            if (ans < 1000) {
                DecimalFormat df2 = new DecimalFormat("0.######");
                s = df2.format(ans);
            } else {
                DecimalFormat df2 = new DecimalFormat("0.###");
                s = df2.format(ans);
            }

            if (s.endsWith(".0")) {
                s = s.replace(".0", "");
            }
            if (s.length() > 9) {
                s = "エラー";
            } else if (s.contains("NaN")) {
                s = "エラー";
            }
            return s;
        }
        return "";
    }
    private String getLogicText(int logic) {
        String text = "";
        switch (logic) {
            case 1:
                text = "＋";
                break;
            case 2:
                text = "−";
                break;
            case 3:
                text = "×";
                break;
            case 4:
                text = "÷";
                break;
        }
        return text;
    }
    private boolean containOperator(String text){
        if(text.contains("＋") || text.contains("−") || text.contains("×") || text.contains("÷")){
            return true;
        }
        return false;
    }
}
