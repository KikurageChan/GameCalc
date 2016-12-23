package com.netgroup_jv.gamecalc;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kikuragetyann on 16/03/04.
 */
public class AppData {
    private static AppData appData;
    private int ramInt;
    private String ramString;
    private ConcurrentHashMap<String, int[]> cardScoreMap;


    private AppData() {
        cardScoreMap = new ConcurrentHashMap<>();
    }

    //instance生成用メソッドです
    public static AppData getInstance() {
        if (appData == null) {
            appData = new AppData();
        }
        return appData;
    }

    /////getter/setter/////
    public synchronized int getRamInt() {
        return ramInt;
    }

    public synchronized void setRamInt(int ramInt) {
        this.ramInt = ramInt;
    }

    public synchronized String getRamString() {
        return ramString;
    }

    public synchronized void setRamString(String ramString) {
        this.ramString = ramString;
    }

    public ConcurrentHashMap getHashMap() {
        return this.cardScoreMap;
    }

    //スコア一覧と今回のスコアを取得して並び替えてスコア一覧に戻します。
    public String addScore(String scores, int score) {
        Integer[] integers = new Integer[3];
        String[] strings = scores.split(",");
        for (int i = 0; i < 3; i++) {
            integers[i] = Integer.valueOf(strings[i]);
        }
        Arrays.sort(integers, Collections.reverseOrder());
        if(integers[0] <= score){
            integers[2] = integers[1];
            integers[1] = integers[0];
            integers[0] = score;
        }else if(integers[1] <= score){
            integers[2] = integers[1];
            integers[1] = score;
        }else if(integers[2] <= score){
            integers[2] = score;
        }
        return String.valueOf(integers[0]) + "," + String.valueOf(integers[1]) + "," + String.valueOf(integers[2]);
    }
    //スコア一覧と今回のスコアを取得して上位3に入った場合trueを返します。
    public boolean isRankIn(String scores,int score){
        Integer[] integers = new Integer[3];
        String[] strings = scores.split(",");
        for (int i = 0; i < 3; i++) {
            integers[i] = Integer.valueOf(strings[i]);
        }
        Arrays.sort(integers, Collections.reverseOrder());
        if(integers[0] <= score || integers[1] <= score || integers[2] <= score){
            return true;
        }
        return false;
    }
}

