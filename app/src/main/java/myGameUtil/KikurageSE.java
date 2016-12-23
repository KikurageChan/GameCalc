package myGameUtil;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.netgroup_jv.gamecalc.R;


/**
 * Created by kikuragetyann on 16/02/18.
 */
public class KikurageSE {
    private static KikurageSE kikurageSE;
    private SoundPool soundPool;
    //音の ON - OFF
    public static boolean sound_output;

    public static int missSound;
    public static int gameOverSound;
    public static int typeSound;
    public static int cartridge;
    public static int bound;
    public static int paper;
    public static int fresh;
    public static int hit;
    public static int hiScore;
    public static int playerShot;
    public static int bomb;

    private KikurageSE(Context context) {
        sound_output = true;
        /////プールを作成します/////
        soundPool = new SoundPool(11, AudioManager.STREAM_MUSIC, 0);

        /////それぞれの音源を読み込みます/////
        //ミス
        missSound = soundPool.load(context, R.raw.miss, 1);
        //ゲームオーバー
        gameOverSound = soundPool.load(context, R.raw.gameover, 1);
        //タイプ音
        typeSound = soundPool.load(context, R.raw.type, 1);
        //薬莢
        cartridge = soundPool.load(context, R.raw.cartridge, 1);
        //跳ね返り音
        bound = soundPool.load(context, R.raw.bound, 1);
        //かみ
        paper = soundPool.load(context,R.raw.paper,1);
        //ぽちゃ
        fresh = soundPool.load(context,R.raw.fresh,1);
        //ヒット
        hit = soundPool.load(context,R.raw.fresh,1);
        //ハイスコア
        hiScore = soundPool.load(context,R.raw.hiscore,1);
        //プレイヤーショット
        playerShot = soundPool.load(context,R.raw.player_shot,1);
        //爆発
        bomb = soundPool.load(context,R.raw.bomb,1);

    }

    //instance生成用メソッドです
    public static KikurageSE getInstance(Context context) {
        if (kikurageSE == null) {
            kikurageSE = new KikurageSE(context);
        }
        return kikurageSE;
    }

    public void unregister() {
        kikurageSE = null;
        soundPool.release();
    }

    public void play_Big(int soundId) {
        if(sound_output) {
            soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0F);
        }
    }

    public void play_Normal(int soundId) {
        if(sound_output){
            soundPool.play(soundId, 0.5F, 0.5F, 0, 0, 1.0F);
        }
    }


    public void play_Soft(int soundId) {
        if(sound_output){
            soundPool.play(soundId, 0.1F, 0.1F, 0, 0, 1.0F);
        }
    }

    /////getter/setter/////
    public synchronized boolean getSound_output(){
        return sound_output;
    }
    public synchronized void setSound_output(boolean b){
        sound_output = b;
    }

}
