package com.netgroup_jv.gamecalc;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.view.View.OnTouchListener;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import myGameUtil.KikurageSE;

public class SettingActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    /////効果音システム/////
    private KikurageSE kikurageSE;

    /////プリファレンス/////
    private SharedPreferences preferences;

    /////トースト/////
    private Toast t = null;

    /////画面上のUI関係/////
    //上部button系
    private LinearLayout settingOrColorFrame;
    private ImageView optionButton;
    private Bitmap optionImg;
    private Bitmap optionOnImg;
    private ImageView paintButton;
    private Bitmap paintImg;
    private Bitmap paintOnImg;
    private LinearLayout menuLayout;


    //MainColor
    private View mainColorSample;
    private SeekBar mainColorR;
    private SeekBar mainColorG;
    private SeekBar mainColorB;
    private int[] mainColors;

    //ScreenColor
    private View screenColorSample;
    private SeekBar screenColorR;
    private SeekBar screenColorG;
    private SeekBar screenColorB;
    private int[] screenColors;

    //CalculationTextColor
    private View calculationTextSample;
    private SeekBar calculationTextColorR;
    private SeekBar calculationTextColorG;
    private SeekBar calculationTextColorB;
    private int[] calculationTextColors;

    //SoundSwitch
    private Switch soundSwitch;
    private boolean soundValue = true;
    //AssistSwitch
    private Switch assistSwitch;
    private boolean assistValue = false;

    //ButtonColor
    private View buttonColorSample;
    private SeekBar buttonColorR;
    private SeekBar buttonColorG;
    private SeekBar buttonColorB;
    private int[] buttonColors;

    //ButtonTextColor
    private View buttonTextColorSample;
    private SeekBar buttonTextColorR;
    private SeekBar buttonTextColorG;
    private SeekBar buttonTextColorB;
    private int[] buttonTextColors;

    //その他フィールド
    private boolean optionIsOn;
    private boolean paintIsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //このアクティビティのレイアウトはactivity_settingを設定します。
        setContentView(R.layout.activity_setting);

        paintIsOn = true;
        optionIsOn = false;

        //必要なViewをそれぞれfindします。
        settingOrColorFrame = (LinearLayout) findViewById(R.id.settingOrColorFrame);
        optionButton = (ImageView) findViewById(R.id.optionButton);
        optionButton.setOnTouchListener(buttonTouch);
        optionImg = BitmapFactory.decodeResource(getResources(), R.drawable.option);
        optionOnImg = BitmapFactory.decodeResource(getResources(), R.drawable.optionon);
        paintButton = (ImageView) findViewById(R.id.paintButton);
        paintButton.setOnTouchListener(buttonTouch);
        paintImg = BitmapFactory.decodeResource(getResources(), R.drawable.paint);
        paintOnImg = BitmapFactory.decodeResource(getResources(), R.drawable.painton);

        menuLayout = (LinearLayout) findViewById(R.id.menuFrame);

        //このViewが起動する初期はpaintレイアウトを挿入します。
        getLayoutInflater().inflate(R.layout.paint, menuLayout);


        mainColors = new int[3];
        screenColors = new int[3];
        calculationTextColors = new int[3];
        buttonColors = new int[3];
        buttonTextColors = new int[3];

        preferences = getSharedPreferences("SETTING", MODE_PRIVATE);


        //広告
        AdView adView2 = (AdView) this.findViewById(R.id.settingAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView2.loadAd(adRequest);

    }

    protected void onResume() {
        super.onResume();
        //効果音システムの起動
        kikurageSE = KikurageSE.getInstance(getApplicationContext());
        //preferencesで保存されている値をシークバーなどに反映させる処理です。
        String[] mainColors = preferences.getString("mainColorRGB", "130,215,0").split(",");//みどりがデフォルト値
        for (int i = 0; i < mainColors.length; i++) {
            this.mainColors[i] = Integer.valueOf(mainColors[i]);
        }
        String[] screenColors = preferences.getString("screenColorRGB", "255,255,255").split(",");//白がデフォルト値
        for (int i = 0; i < screenColors.length; i++) {
            this.screenColors[i] = Integer.valueOf(screenColors[i]);
        }

        String[] calculationTextColors = preferences.getString("calculationTextColorRGB", "136,136,136").split(",");//灰色がデフォルト値
        for (int i = 0; i < calculationTextColors.length; i++) {
            this.calculationTextColors[i] = Integer.valueOf(calculationTextColors[i]);
        }
        assistValue = preferences.getBoolean("assist",false);
        soundValue = preferences.getBoolean("sound", true);
        String[] buttonColors = preferences.getString("buttonColorRGB", "223,223,223").split(",");//明灰色がデフォルト値
        for (int i = 0; i < buttonColors.length; i++) {
            this.buttonColors[i] = Integer.valueOf(buttonColors[i]);
        }
        String[] buttonTextColors = preferences.getString("buttonTextColorRGB", "0,0,0").split(",");//黒がデフォルト値
        for (int i = 0; i < buttonTextColors.length; i++) {
            this.buttonTextColors[i] = Integer.valueOf(buttonTextColors[i]);
        }
        optionButton.setBackgroundColor(getResources().getColor(R.color.darkGray));
        findPaint();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (t != null) {
            t.cancel();
        }
        t = Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT);
        t.show();
        //効果音システムを解放
        kikurageSE.unregister();
        //状態のセーブ
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mainColorRGB", mainColors[0] + "," + mainColors[1] + "," + mainColors[2]);
        editor.putString("screenColorRGB", screenColors[0] + "," + screenColors[1] + "," + screenColors[2]);
        editor.putString("calculationTextColorRGB", calculationTextColors[0] + "," + calculationTextColors[1] + "," + calculationTextColors[2]);
        editor.putBoolean("assist",assistValue);
        editor.putBoolean("sound", soundValue);
        editor.putString("buttonColorRGB", buttonColors[0] + "," + buttonColors[1] + "," + buttonColors[2]);
        editor.putString("buttonTextColorRGB", buttonTextColors[0] + "," + buttonTextColors[1] + "," + buttonTextColors[2]);
        editor.commit();
    }


    //ドラッグされると呼ばれます
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.mainColorR || seekBar.getId() == R.id.mainColorG || seekBar.getId() == R.id.mainColorB) {
            switch (seekBar.getId()) {
                case R.id.mainColorR:
                    mainColors[0] = progress;
                    break;
                case R.id.mainColorG:
                    mainColors[1] = progress;
                    break;
                case R.id.mainColorB:
                    mainColors[2] = progress;
                    break;
            }
            mainColorSample.setBackgroundColor(Color.argb(255, mainColors[0], mainColors[1], mainColors[2]));
        }
        if (seekBar.getId() == R.id.screenColorR || seekBar.getId() == R.id.screenColorG || seekBar.getId() == R.id.screenColorB) {
            switch (seekBar.getId()) {
                case R.id.screenColorR:
                    screenColors[0] = progress;
                    break;
                case R.id.screenColorG:
                    screenColors[1] = progress;
                    break;
                case R.id.screenColorB:
                    screenColors[2] = progress;
                    break;
            }
            screenColorSample.setBackgroundColor(Color.argb(255, screenColors[0], screenColors[1], screenColors[2]));
        }
        if (seekBar.getId() == R.id.calculationTextColorR || seekBar.getId() == R.id.calculationTextColorG || seekBar.getId() == R.id.calculationTextColorB) {
            switch (seekBar.getId()) {
                case R.id.calculationTextColorR:
                    calculationTextColors[0] = progress;
                    break;
                case R.id.calculationTextColorG:
                    calculationTextColors[1] = progress;
                    break;
                case R.id.calculationTextColorB:
                    calculationTextColors[2] = progress;
                    break;
            }
            calculationTextSample.setBackgroundColor(Color.argb(255, calculationTextColors[0], calculationTextColors[1], calculationTextColors[2]));
        }
        if (seekBar.getId() == R.id.buttonColorR || seekBar.getId() == R.id.buttonColorG || seekBar.getId() == R.id.buttonColorB) {
            switch (seekBar.getId()) {
                case R.id.buttonColorR:
                    buttonColors[0] = progress;
                    break;
                case R.id.buttonColorG:
                    buttonColors[1] = progress;
                    break;
                case R.id.buttonColorB:
                    buttonColors[2] = progress;
                    break;
            }
            buttonColorSample.setBackgroundColor(Color.argb(255, buttonColors[0], buttonColors[1], buttonColors[2]));
        }
        if (seekBar.getId() == R.id.buttonTextColorR || seekBar.getId() == R.id.buttonTextColorG || seekBar.getId() == R.id.buttonTextColorB) {
            switch (seekBar.getId()) {
                case R.id.buttonTextColorR:
                    buttonTextColors[0] = progress;
                    break;
                case R.id.buttonTextColorG:
                    buttonTextColors[1] = progress;
                    break;
                case R.id.buttonTextColorB:
                    buttonTextColors[2] = progress;
                    break;
            }
            buttonTextColorSample.setBackgroundColor(Color.argb(255, buttonTextColors[0], buttonTextColors[1], buttonTextColors[2]));
        }
    }

    //switchが変更されると呼ばれます。
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.soundSwitch) {
            if (isChecked) {
                soundValue = isChecked;
                kikurageSE.setSound_output(soundValue);
                kikurageSE.play_Normal(KikurageSE.cartridge);
            } else {
                soundValue = false;
                kikurageSE.setSound_output(soundValue);
            }
        }
        if(buttonView.getId() == R.id.assistSwitch){
            if (isChecked) {
                assistValue = isChecked;
            } else {
                assistValue = false;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) { // Menuボタンが押されたら
            finish();
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    public void findSetting(){
        //Assistの設定UI部品
        assistSwitch = (Switch)findViewById(R.id.assistSwitch);
        assistSwitch.setChecked(assistValue);
        assistSwitch.setOnCheckedChangeListener(this);

        //Soundの設定UI部品
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        soundSwitch.setChecked(soundValue);
        soundSwitch.setOnCheckedChangeListener(this);

    }

    public void findPaint() {
        //Main Colorの設定UI部品
        mainColorSample = findViewById(R.id.mainColorSample);
        mainColorR = (SeekBar) findViewById(R.id.mainColorR);
        mainColorR.setOnSeekBarChangeListener(this);
        mainColorG = (SeekBar) findViewById(R.id.mainColorG);
        mainColorG.setOnSeekBarChangeListener(this);
        mainColorB = (SeekBar) findViewById(R.id.mainColorB);
        mainColorB.setOnSeekBarChangeListener(this);

        //Screen Colorの設定UI部品
        screenColorSample = findViewById(R.id.screenColorSample);
        screenColorR = (SeekBar) findViewById(R.id.screenColorR);
        screenColorR.setOnSeekBarChangeListener(this);
        screenColorG = (SeekBar) findViewById(R.id.screenColorG);
        screenColorG.setOnSeekBarChangeListener(this);
        screenColorB = (SeekBar) findViewById(R.id.screenColorB);
        screenColorB.setOnSeekBarChangeListener(this);

        //Calculation Text Colorの設定UI部品
        calculationTextSample = findViewById(R.id.calculationTextColorSample);
        calculationTextColorR = (SeekBar) findViewById(R.id.calculationTextColorR);
        calculationTextColorR.setOnSeekBarChangeListener(this);
        calculationTextColorG = (SeekBar) findViewById(R.id.calculationTextColorG);
        calculationTextColorG.setOnSeekBarChangeListener(this);
        calculationTextColorB = (SeekBar) findViewById(R.id.calculationTextColorB);
        calculationTextColorB.setOnSeekBarChangeListener(this);

        //Button Colorの設定UI部品
        buttonColorSample = findViewById(R.id.buttonColorSample);
        buttonColorR = (SeekBar) findViewById(R.id.buttonColorR);
        buttonColorR.setOnSeekBarChangeListener(this);
        buttonColorG = (SeekBar) findViewById(R.id.buttonColorG);
        buttonColorG.setOnSeekBarChangeListener(this);
        buttonColorB = (SeekBar) findViewById(R.id.buttonColorB);
        buttonColorB.setOnSeekBarChangeListener(this);

        //Button Text Colorの設定UI部品
        buttonTextColorSample = findViewById(R.id.buttonTextColorSample);
        buttonTextColorR = (SeekBar) findViewById(R.id.buttonTextColorR);
        buttonTextColorR.setOnSeekBarChangeListener(this);
        buttonTextColorG = (SeekBar) findViewById(R.id.buttonTextColorG);
        buttonTextColorG.setOnSeekBarChangeListener(this);
        buttonTextColorB = (SeekBar) findViewById(R.id.buttonTextColorB);
        buttonTextColorB.setOnSeekBarChangeListener(this);

        //現在プリファレンスに登録されている情報で各アイテムをセットします。
        mainColorSample.setBackgroundColor(Color.argb(255, this.mainColors[0], this.mainColors[1], this.mainColors[2]));
        mainColorR.setProgress(this.mainColors[0]);
        mainColorG.setProgress(this.mainColors[1]);
        mainColorB.setProgress(this.mainColors[2]);
        screenColorSample.setBackgroundColor(Color.argb(255, this.screenColors[0], this.screenColors[1], this.screenColors[2]));
        screenColorR.setProgress(this.screenColors[0]);
        screenColorG.setProgress(this.screenColors[1]);
        screenColorB.setProgress(this.screenColors[2]);
        calculationTextSample.setBackgroundColor(Color.argb(255, this.calculationTextColors[0], this.calculationTextColors[1], this.calculationTextColors[2]));
        calculationTextColorR.setProgress(this.calculationTextColors[0]);
        calculationTextColorG.setProgress(this.calculationTextColors[1]);
        calculationTextColorB.setProgress(this.calculationTextColors[2]);
        buttonColorSample.setBackgroundColor(Color.argb(255, this.buttonColors[0], this.buttonColors[1], this.buttonColors[2]));
        buttonColorR.setProgress(this.buttonColors[0]);
        buttonColorG.setProgress(this.buttonColors[1]);
        buttonColorB.setProgress(this.buttonColors[2]);
        buttonTextColorSample.setBackgroundColor(Color.argb(255, this.buttonTextColors[0], this.buttonTextColors[1], this.buttonTextColors[2]));
        buttonTextColorR.setProgress(this.buttonTextColors[0]);
        buttonTextColorG.setProgress(this.buttonTextColors[1]);
        buttonTextColorB.setProgress(this.buttonTextColors[2]);
    }

    OnTouchListener buttonTouch = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.optionButton && event.getAction() == MotionEvent.ACTION_DOWN && paintIsOn) {
                menuLayout.removeAllViews();
                getLayoutInflater().inflate(R.layout.setting, menuLayout);
                optionButton.setBackgroundColor(getResources().getColor(R.color.black));
                optionButton.setImageBitmap(optionOnImg);
                paintButton.setBackgroundColor(getResources().getColor(R.color.darkGray));
                paintButton.setImageBitmap(paintImg);
                paintIsOn = false;
                optionIsOn = true;
                findSetting();
            }
            if (v.getId() == R.id.paintButton && event.getAction() == MotionEvent.ACTION_DOWN && optionIsOn) {
                menuLayout.removeAllViews();
                getLayoutInflater().inflate(R.layout.paint, menuLayout);
                optionButton.setBackgroundColor(getResources().getColor(R.color.darkGray));
                optionButton.setImageBitmap(optionImg);
                paintButton.setBackgroundColor(getResources().getColor(R.color.black));
                paintButton.setImageBitmap(paintOnImg);
                paintIsOn = true;
                optionIsOn = false;
                findPaint();
            }
            return true;
        }
    };

    ////////////////////////////////
    //タッチされた時に呼ばれます
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //離された時に呼ばれます
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
