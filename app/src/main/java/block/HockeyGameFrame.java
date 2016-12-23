package block;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import myGameUtil.KikurageUtil;


/**
 * Created by kikuragetyann on 16/02/10.
 */
public class HockeyGameFrame extends FrameLayout{

    private LinearLayout controlFrame;
    private RightPanel rightPanel;
    private LeftPanel leftPanel;
    private HockeyGameView hockeyGameView;

    public HockeyGameFrame(Context context) {
        super(context);
    }
    public HockeyGameFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void Create(Context context,ViewGroup layout){
        /////hockeyGame/////
        //android:id="@+id/hockeyGame"
        //android:layout_width="match_parent"
        //android:layout_height="100dp"
        //android:layout_gravity="center_horizontal"
        final LayoutParams params5 = new LayoutParams(LayoutParams.MATCH_PARENT, KikurageUtil.getPxForInt(100), Gravity.CENTER_HORIZONTAL);
        hockeyGameView = new HockeyGameView(context);
        hockeyGameView.setLayoutParams(params5);
        this.addView(hockeyGameView);


        //android:id="@+id/controlFrame"
        //android:layout_width="match_parent"
        //android:layout_height="wrap_content"
        final LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        controlFrame = new LinearLayout(context);
        controlFrame.setLayoutParams(params2);
        this.addView(controlFrame);


        //android:id="@+id/rightPanel"
        //android:layout_width="0dp"
        //android:layout_height="100dp"
        //android:layout_weight="1"
        //android:background="@color/cleanness"
        final LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(KikurageUtil.getPxForInt(0),KikurageUtil.getPxForInt(100));
        params4.weight = 1.0f;
        leftPanel = new LeftPanel(context);
        leftPanel.setLayoutParams(params4);
        leftPanel.setBackgroundColor(Color.argb(0, 0, 0, 0));
        controlFrame.addView(leftPanel);


        //android:id="@+id/leftPanel"
        //android:layout_width="0dp"
        //android:layout_height="100dp"
        //android:layout_weight="1"
        //android:background="@color/cleanness"
        final LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(KikurageUtil.getPxForInt(0),KikurageUtil.getPxForInt(100));
        params3.weight = 1.0f;
        rightPanel = new RightPanel(context);
        rightPanel.setLayoutParams(params3);
        rightPanel.setBackgroundColor(Color.argb(0, 0, 0, 0));
        controlFrame.addView(rightPanel);
    }
}
