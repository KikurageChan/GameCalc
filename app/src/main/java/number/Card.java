package number;

import com.netgroup_jv.gamecalc.MainActivity;

import myGameUtil.ActiveGameObject;
import myGameUtil.KikurageUtil;

/**
 * Created by kikuragetyann on 16/02/12.
 */
public abstract class Card extends ActiveGameObject {
    public static final int PASTELPINK = 0xffffadff;
    public static final int PASTELBLUE = 0xffadd7ff;
    public static final int PASTELGREEN = 0xffadffd7;
    public static final int PASTELYELLOW = 0xffffff9b;
    public static final int NORMAL = 1000;
    public static final int NORMAL_JAPAN = 2000;
    public static final int NORMAL_JAPAN_ROMA = 3000;
    private int colorCode;
    private String text;
    private int number;
    private int cardTextType;

    public Card(float x, float y) {
        //widthとheightは35dpに固定
        super(x, y, KikurageUtil.getPxForInt(35), KikurageUtil.getPxForInt(35));
        cardTextType = NORMAL;
    }

    private int getRandomNumber0_9() {
        return random(10);
    }

    private int getRandomNumber1_9() {
        return random(9) + 1;
    }

    private String getRandomNormal() {
        String text = "";
        switch (getRandomNumber0_9()) {
            case 0:
                text = "0";
                number = 0;
                break;
            case 1:
                text = "1";
                number = 1;
                break;
            case 2:
                text = "2";
                number = 2;
                break;
            case 3:
                text = "3";
                number = 3;
                break;
            case 4:
                text = "4";
                number = 4;
                break;
            case 5:
                text = "5";
                number = 5;
                break;
            case 6:
                text = "6";
                number = 6;
                break;
            case 7:
                text = "7";
                number = 7;
                break;
            case 8:
                text = "8";
                number = 8;
                break;
            case 9:
                text = "9";
                number = 9;
                break;
        }
        return text;
    }

    private String getRandomRoma() {
        String text = "";
        switch (getRandomNumber1_9()) {
            case 1:
                text = "I";
                number = 1;
                break;
            case 2:
                text = "II";
                number = 2;
                break;
            case 3:
                text = "III";
                number = 3;
                break;
            case 4:
                text = "IV";
                number = 4;
                break;
            case 5:
                text = "V";
                number = 5;
                break;
            case 6:
                text = "VI";
                number = 6;
                break;
            case 7:
                text = "VII";
                number = 7;
                break;
            case 8:
                text = "VIII";
                number = 8;
                break;
            case 9:
                text = "IX";
                number = 9;
                break;
        }
        return text;
    }

    private String getRandomJapan() {
        String text = "";
        switch (getRandomNumber0_9()) {
            case 0:
                text = "零";
                number = 0;
                break;
            case 1:
                text = "一";
                number = 1;
                break;
            case 2:
                text = "二";
                number = 2;
                break;
            case 3:
                text = "三";
                number = 3;
                break;
            case 4:
                text = "四";
                number = 4;
                break;
            case 5:
                text = "五";
                number = 5;
                break;
            case 6:
                text = "六";
                number = 6;
                break;
            case 7:
                text = "七";
                number = 7;
                break;
            case 8:
                text = "八";
                number = 8;
                break;
            case 9:
                text = "九";
                number = 9;
                break;
        }
        return text;
    }

    public String getRandomNormalJapan() {
        String text = "";
        switch (random(2)) {
            case 0:
                text = getRandomNormal();
                break;
            case 1:
                text = getRandomJapan();
                break;
        }
        return text;
    }

    public String getRandomAll() {
        String text = "";
        switch (random(3)) {
            case 0:
                text = getRandomNormal();
                break;
            case 1:
                text = getRandomJapan();
                break;
            case 2:
                text = getRandomRoma();
                break;
        }
        return text;
    }

    public int getRandomColor() {
        int color = 0;
        switch (random(4)) {
            case 0:
                color = PASTELPINK;
                break;
            case 1:
                color = PASTELBLUE;
                break;
            case 2:
                color = PASTELGREEN;
                break;
            case 3:
                color = PASTELYELLOW;
                break;
        }
        return color;
    }


    public void reset() {
        setColorCode(getRandomColor());
        setText(getCardTextType());
    }

    /////getter/setter/////
    public int getCardTextType() {
        return cardTextType;
    }

    public void setCardTextType(int cardTextType) {
        this.cardTextType = cardTextType;
    }


    public String getText() {
        return this.text;
    }

    protected void setText(int cardTextType) {
        switch (cardTextType) {
            case NORMAL:
                text = getRandomNormal();
                break;
            case NORMAL_JAPAN:
                text = getRandomNormalJapan();
                break;
            case NORMAL_JAPAN_ROMA:
                text = getRandomAll();
        }
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public int getNumber() {
        return this.number;
    }
}
