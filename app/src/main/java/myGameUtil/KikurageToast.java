package myGameUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kikuragetyann on 16/03/14.
 */
public class KikurageToast {
    private Toast toast;
    private String message;
    private Context context;

    public KikurageToast(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    public void show() {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
