package myGameUtil;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by kikuragetyann on 16/03/16.
 */
public class KikurageUtil {
    private static Random random;
    private static float density;
    private static KikurageUtil kikurageUtil;

    private KikurageUtil(Activity activity) {
        random = new Random();
        //dpの値を取得するためのメソッドです。
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        density = metrics.density;
    }

    //instance生成用メソッドです
    public synchronized static KikurageUtil getInstance(Activity activity) {
        if (kikurageUtil == null) {
            kikurageUtil = new KikurageUtil(activity);
        }
        return kikurageUtil;
    }

    //dpの値を入れるとそのデバイスに合わせたピクセルに返すメソッドです。戻り値float
    public synchronized static float getPxForFloat(float dp) {
        return dp * density;
    }
    //dpの値を入れるとそのデバイスに合わせたピクセルに返すメソッドです。戻り値int
    public synchronized static int getPxForInt(float dp) {
        return (int) (dp * density);
    }
    //ランダムな数値を返します。
    public static int random(int a) {
        return random.nextInt(a);
    }

}
