package com.netgroup_jv.gamecalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import arrow.ArrowGameView;
import block.HockeyGameFrame;
import draw.DrawView;
import myGameUtil.KikurageUtil;
import myGameUtil.KikurageSE;
import myGameUtil.MySurface;
import number.NumberGameView;
import space.ShotGameView;


public class MainActivity extends AppCompatActivity implements SensorEventListener, Runnable, View.OnTouchListener {
    /////スレッド/////
    private Thread thread;
    private Handler handler;
    /////効果音システム/////
    KikurageSE kikurageSE;
    /////プリファレンス/////
    private SharedPreferences preferences;
    private SharedPreferences preferences2;
    private SharedPreferences preferences3;
    /////画面上のUI関係/////
    private Intent intent_to_memory;
    private Intent intent_to_setting;

    private LinearLayout imageButtonLayout;
    private ImageButton settingButton;
    private ImageButton memoryButton;
    private LinearLayout gameAssistLayout;
    private TableLayout buttonTableLayout;


    /////電卓に必要なアイテム/////
    private TextView textView;
    private TextView operatorTextView;
    private CalculatorSystem calculatorSystem;
    /////drawに必要なアイテム/////
    private TextView drawColorSample;
    private TextView drawThicknessSample;
    private ImageView drawLineSample;

    //このButtonBoxにはButton0からButton9まで入っています。
    private ButtonBox buttonBox;
    /////トースト/////
    private Toast t = null;//パスコードが違います
    private Toast t2 = null;//ゲーム中です
    private Toast t3;//太さ 10
    /////ゲームに必要なアイテム/////
    private FrameLayout gameFrame;
    private Button startButton;
    private Button overButton;

    private HockeyGameFrame hockeyGameFrame;
    private ArrowGameView arrowGameView;
    private NumberGameView numberGameView;
    private ShotGameView shotGameView;
    private DrawView drawView;
    /////センサー/////
    private SensorManager manager;
    private Sensor sensor;
    public static int pitch = 0;
    public static int role = 0;
    /////その他のフールド/////
    private AppData appData;
    private boolean isAssist;
    public static final int CALC = 10000;
    public static final int ARROW = 5932;
    public static final int BLOCK = 1753;
    public static final int NUMBER = 1234;
    public static final int DRAW = 9999;
    public static final int SPACE = 295;
    public static int gameType = 0;
    private final int GAMEOVER = 100;
    private boolean overButtonIsExistence;
    private boolean one = false;
    public static Activity activity;
    private KikurageUtil kikurageUtil;

    //アクティビティ生成のとき
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初期化処理
        overButtonIsExistence = false;
        kikurageUtil = KikurageUtil.getInstance(this);
        gameType = CALC;
        activity = this;
        intent_to_memory = new Intent(MainActivity.this, MemoryActivity.class);
        intent_to_setting = new Intent(MainActivity.this, SettingActivity.class);
        //プリファレンス//
        preferences = getSharedPreferences("SETTING", MODE_PRIVATE);
        preferences2 = getSharedPreferences("GAMESCORE", MODE_PRIVATE);
        preferences3 = getSharedPreferences("FINDGAME", MODE_PRIVATE);
        //appDataの準備
        appData = AppData.getInstance();
        //CalculationSystemの生成
        calculatorSystem = new CalculatorSystem();
        //画面タッチがなくても画面を暗くしないようにします。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //このアクティビティのレイアウトはactivity_mainを設定します。
        setContentView(R.layout.activity_main);
        //以下　必要なアイテムをレイアウトからfindしてきます。
        textView = (TextView) findViewById(R.id.text);
        operatorTextView = (TextView) findViewById(R.id.operatorText);
        gameFrame = (FrameLayout) findViewById(R.id.gameFrame);
        startButton = (Button) findViewById(R.id.startButton);
        overButton = (Button) findViewById(R.id.overButton);

        settingButton = (ImageButton) findViewById(R.id.settingButton);
        memoryButton = (ImageButton) findViewById(R.id.memoryButton);

        imageButtonLayout = (LinearLayout) findViewById(R.id.imageButtonLayout);
        gameAssistLayout = (LinearLayout) findViewById(R.id.gameAssistLayout);
        buttonTableLayout = (TableLayout) findViewById(R.id.buttonTableLayout);

        //textViewをトップ表示させます。
        gameFrame.removeAllViews();
        gameFrame.addView(textView);
        gameFrame.addView(operatorTextView);

        //センサーの起動
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(sensor.TYPE_ORIENTATION);

        handler = new Handler() {
            //メッセージ受信
            public void handleMessage(Message message) {
                if (message.what == GAMEOVER) {
                    overButtonIsExistence = true;
                    //高得点をとった場合
                    SharedPreferences.Editor editor2 = preferences2.edit();
                    if (gameType == NUMBER) {
                        if (appData.isRankIn(preferences2.getString("cardScore", "0,0,0"), appData.getRamInt())) {
                            editor2.putString("cardScore", appData.addScore(preferences2.getString("cardScore", "0,0,0"), appData.getRamInt()));
                            editor2.commit();
                            overButton.setText("High  score!");
                            overButton.setTextColor(Color.rgb(15, 188, 235));
                            //効果音ハイスコアを鳴らします。
                            kikurageSE = KikurageSE.getInstance(getApplicationContext());
                            kikurageSE.play_Normal(KikurageSE.hiScore);
                        }else{
                            //効果音ゲームオーバーを鳴らします。
                            kikurageSE = KikurageSE.getInstance(getApplicationContext());
                            kikurageSE.play_Normal(KikurageSE.gameOverSound);
                        }
                    } else if (gameType == ARROW) {
                        if (appData.isRankIn(preferences2.getString("arrowScore", "0,0,0"), appData.getRamInt())) {
                            editor2.putString("arrowScore", appData.addScore(preferences2.getString("arrowScore", "0,0,0"), appData.getRamInt()));
                            editor2.commit();
                            overButton.setText("High  score!");
                            overButton.setTextColor(Color.rgb(15, 188, 235));
                            //効果音ハイスコアを鳴らします。
                            kikurageSE = KikurageSE.getInstance(getApplicationContext());
                            kikurageSE.play_Normal(KikurageSE.hiScore);
                        }else{
                            //効果音ゲームオーバーを鳴らします。
                            kikurageSE = KikurageSE.getInstance(getApplicationContext());
                            kikurageSE.play_Normal(KikurageSE.gameOverSound);
                        }
                    } else if (gameType == DRAW) {
                        //効果音paperを鳴らします。
                        kikurageSE = KikurageSE.getInstance(getApplicationContext());
                        kikurageSE.play_Big(KikurageSE.paper);
                    }else{
                        //効果音ゲームオーバーを鳴らします。
                        kikurageSE = KikurageSE.getInstance(getApplicationContext());
                        kikurageSE.play_Normal(KikurageSE.gameOverSound);
                    }
                    gameFrame.addView(overButton);
                }
            }
        };
    }

    //アクティビティが表示状態になるとき
    @Override
    protected void onStart() {
        super.onStart();
    }


    //フォアグラウンドになるとき
    @Override
    protected void onResume() {
        super.onResume();
        //スレッド処理を開始
        thread = new Thread(this);
        if (thread != null) {
            thread.start();
        }
        /////効果音システムの起動/////
        kikurageSE = KikurageSE.getInstance(getApplicationContext());
        /////センサーマネージャーの起動/////
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        /////メインの色についての処理/////
        String[] mainColors = preferences.getString("mainColorRGB", "130,215,0").split(",");
        imageButtonLayout.setBackgroundColor(Color.argb(255, Integer.valueOf(mainColors[0]), Integer.valueOf(mainColors[1]), Integer.valueOf(mainColors[2])));
        buttonTableLayout.setBackgroundColor(Color.argb(255, Integer.valueOf(mainColors[0]), Integer.valueOf(mainColors[1]), Integer.valueOf(mainColors[2])));
        gameAssistLayout.setBackgroundColor(Color.argb(255, Integer.valueOf(mainColors[0]), Integer.valueOf(mainColors[1]), Integer.valueOf(mainColors[2])));
        /////スクリーンの色についての処理/////
        String[] screenColors = preferences.getString("screenColorRGB", "255,255,255").split(",");
        gameFrame.setBackgroundColor(Color.argb(255, Integer.valueOf(screenColors[0]), Integer.valueOf(screenColors[1]), Integer.valueOf(screenColors[2])));
        /////電卓のテキストの色についての処理/////
        String[] calculationTextColors = preferences.getString("calculationTextColorRGB", "136,136,136").split(",");
        textView.setTextColor(Color.argb(255, Integer.valueOf(calculationTextColors[0]), Integer.valueOf(calculationTextColors[1]), Integer.valueOf(calculationTextColors[2])));
        operatorTextView.setTextColor(Color.argb(255, Integer.valueOf(calculationTextColors[0]), Integer.valueOf(calculationTextColors[1]), Integer.valueOf(calculationTextColors[2])));
        /////ボタンの色についての処理/////
        String[] buttonColors = preferences.getString("buttonColorRGB", "223,223,223").split(",");
        int buttonColor = Color.argb(255, Integer.valueOf(buttonColors[0]), Integer.valueOf(buttonColors[1]), Integer.valueOf(buttonColors[2]));
        //ボタンのテキストの色についての処理です。
        String[] buttonTextColors = preferences.getString("buttonTextColorRGB", "0,0,0").split(",");
        int buttonTextColor = Color.argb(255, Integer.valueOf(buttonTextColors[0]), Integer.valueOf(buttonTextColors[1]), Integer.valueOf(buttonTextColors[2]));
        //buttonBoxに入っているButtonを生成、リスナーもすでに登録されています。
        buttonBox = ButtonBox.getInstance(this, buttonColor, buttonTextColor);
        buttonBox.getButtonGame().setOnTouchListener(this);
        /////サウンドについての処理/////
        boolean sound = preferences.getBoolean("sound", true);
        kikurageSE.setSound_output(sound);
        /////アシストについての処理/////
        isAssist = preferences.getBoolean("assist", false);
        if (isAssist) {
            operatorTextView.setVisibility(View.VISIBLE);
        } else {
            operatorTextView.setVisibility(View.INVISIBLE);
        }
        buttonBox.getOperatorButton(6).setOnClickListener(clear);
    }


    //様々なフラグを定期的に見るためにスレッドを回しています。1秒間に1回チェック
    @Override
    public void run() {
        while (thread != null) {
            try {
                thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("エラー");
            }
            if (MySurface.gameOver && !one) {
                Message msg = handler.obtainMessage(GAMEOVER);
                handler.sendMessage(msg);
                one = true;
            }
        }
    }

    //アクティビティがフォアグラウンドでなくなるとき
    @Override
    protected void onPause() {
        super.onPause();
        //ボタンを解放
        buttonBox.unregister();
        //効果音システムを解放
        kikurageSE.unregister();
        //センサーを解放
        manager.unregisterListener(this);
        //スレッドを消去
        thread = null;
        MySurface.gameIsRun = false;
    }

    //アクティビティが非表示になるとき
    @Override
    protected void onStop() {
        super.onStop();
    }


    public void numberOn(View view) {
        calculatorSystem.numberOn(textView, view, operatorTextView);
    }

    public void logic(View view) {
        calculatorSystem.logic(textView, view, operatorTextView);
    }

    public void clear(View view) {
        calculatorSystem.clear(textView, operatorTextView);
    }

    public void dot(View view) {
        calculatorSystem.dot(textView);
    }

    public void equal(View view) {
        calculatorSystem.equal(textView, operatorTextView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game(v);
            buttonBox.getButtonGame().setBackgroundColor(Color.argb(255, 68, 185, 200));
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            String[] buttonColors = preferences.getString("buttonColorRGB", "223,223,223").split(",");
            int buttonColor = Color.argb(255, Integer.valueOf(buttonColors[0]), Integer.valueOf(buttonColors[1]), Integer.valueOf(buttonColors[2]));
            buttonBox.getButtonGame().setBackgroundColor(buttonColor);
        }
        return false;
    }

    public void game(View view) {
        if (gameType == CALC) {
            if (textView.getText() == null) {
                if (t != null) {
                    t.cancel();
                }
                t = Toast.makeText(this, getString(R.string.pass_error), Toast.LENGTH_SHORT);
                t.show();
                return;
            } else if (textView.getText().equals("1753")) {
                gameType = BLOCK;
                gameFrame.removeView(textView);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
                hockeyGameFrame = new HockeyGameFrame(this);
                hockeyGameFrame.Create(this, gameFrame);
                hockeyGameFrame.setLayoutParams(params);
                gameFrame.addView(hockeyGameFrame);
                gameFrame.addView(startButton);
                if (!preferences3.getBoolean("block", false)) {
                    SharedPreferences.Editor e = preferences3.edit();
                    e.putBoolean("block", true);
                    e.apply();
                }
            } else if (textView.getText().equals("5932")) {
                gameType = ARROW;
                gameFrame.removeView(textView);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, kikurageUtil.getPxForInt(100), Gravity.CENTER_HORIZONTAL);
                arrowGameView = new ArrowGameView(this);
                arrowGameView.setLayoutParams(params);
                gameFrame.addView(arrowGameView);
                gameFrame.addView(startButton);
                if (!preferences3.getBoolean("arrow", false)) {
                    SharedPreferences.Editor e = preferences3.edit();
                    e.putBoolean("arrow", true);
                    e.apply();
                }
            } else if (textView.getText().equals("29.5")) {
                gameType = SPACE;
                gameFrame.removeView(textView);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, kikurageUtil.getPxForInt(100), Gravity.CENTER_HORIZONTAL);
                shotGameView = new ShotGameView(this);
                shotGameView.setSleepTime(25);
                shotGameView.setLayoutParams(params);
                gameFrame.addView(shotGameView);
                startButton.setTextColor(Color.WHITE);
                gameFrame.addView(startButton);
                getLayoutInflater().inflate(R.layout.space_assist, gameAssistLayout);
                if (!preferences3.getBoolean("shot", false)) {
                    SharedPreferences.Editor e = preferences3.edit();
                    e.putBoolean("shot", true);
                    e.apply();
                }
            } else if (textView.getText().equals("1234")) {
                gameType = NUMBER;
                gameFrame.removeView(textView);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, kikurageUtil.getPxForInt(100), Gravity.CENTER_HORIZONTAL);
                numberGameView = new NumberGameView(this);
                numberGameView.setLayoutParams(params);
                gameFrame.addView(numberGameView);
                gameFrame.addView(startButton);
                if (!preferences3.getBoolean("card", false)) {
                    SharedPreferences.Editor e = preferences3.edit();
                    e.putBoolean("card", true);
                    e.apply();
                }
            } else if (textView.getText().equals("9999")) {
                buttonBox.getOperatorButton(0).setOnClickListener(paintFill); //塗りつぶし
                buttonBox.getOperatorButton(1).setOnClickListener(penColorRandom); //ランダム色
                buttonBox.getOperatorButton(2).setOnClickListener(penUp); //ペンサイズ+
                buttonBox.getOperatorButton(3).setOnClickListener(penDown); //ペンサイズ-
                buttonBox.getOperatorButton(4).setOnClickListener(paintDot); //ドット
                buttonBox.getOperatorButton(5).setOnClickListener(eraser); //消しゴム
                buttonBox.getOperatorButton(6).setOnClickListener(drawClearPicture); //クリア
                buttonBox.getButton(0).setOnClickListener(drawExit); //終了
                buttonBox.getButton(1).setOnClickListener(penColorBlack); //黒色
                buttonBox.getButton(2).setOnClickListener(penColorDKGrey); //濃灰色
                buttonBox.getButton(3).setOnClickListener(penColorLTGrey); //薄灰色
                buttonBox.getButton(4).setOnClickListener(penColorYellow); //黄色
                buttonBox.getButton(5).setOnClickListener(penColorOrange); //オレンジ色
                buttonBox.getButton(6).setOnClickListener(penColorRed); //赤色
                buttonBox.getButton(7).setOnClickListener(penColorBlue); //青色
                buttonBox.getButton(8).setOnClickListener(penColorGreen); //緑色
                buttonBox.getButton(9).setOnClickListener(penColorPink); //ピンク色

                gameType = DRAW;
                gameFrame.removeView(textView);
                final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, kikurageUtil.getPxForInt(100), Gravity.CENTER_HORIZONTAL);
                drawView = new DrawView(this);
                drawView.setLayoutParams(params);
                gameFrame.addView(drawView);
                gameFrame.addView(startButton);
                getLayoutInflater().inflate(R.layout.draw_assist, gameAssistLayout);

                drawColorSample = (TextView) findViewById(R.id.drawColorSample);
                drawThicknessSample = (TextView) findViewById(R.id.drawThicknessSample);
                drawLineSample = (ImageView) findViewById(R.id.drawLineSample);

                drawColorSample.setTextColor(drawView.getPenColor());
                drawThicknessSample.setText(String.valueOf(drawView.getPenSize()));
                drawLineSample.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.line));


                if (!preferences3.getBoolean("draw", false)) {
                    SharedPreferences.Editor e = preferences3.edit();
                    e.putBoolean("draw", true);
                    e.apply();
                }
            } else {
                if (t != null) {
                    t.cancel();
                }
                t = Toast.makeText(this, getString(R.string.pass_error), Toast.LENGTH_SHORT);
                t.show();
                return;
            }
        }
    }


    public void startButton(View view) {
        gameFrame.removeView(startButton);
        MySurface.gameIsRun = true;
        MySurface.timerStart();
    }

    public void overButton(View view) {
        overButtonIsExistence = false;
        overButton.setText("GAME  OVER");
        overButton.setTextColor(Color.RED);
        gameFrame.removeAllViews();
        gameFrame.addView(textView);
        gameFrame.addView(operatorTextView);
        gameType = CALC;
        one = false;
        MySurface.gameIsRun = false;
        MySurface.gameOver = false;
        clear(view);
    }

    /////memoryButtonが押された時の処理です/////
    public void memory(View view) {
        if (gameType == CALC) {
            startActivity(intent_to_memory);
        } else {
            if (t2 != null) {
                t2.cancel();
            }
            t2 = Toast.makeText(this, getString(R.string.playing_game), Toast.LENGTH_SHORT);
            t2.show();
        }
    }

    /////settingButtonが押された時の処理です/////
    public void setting(View view) {
        if (gameType == CALC) {
            startActivity(intent_to_setting);
        } else {
            if (t2 != null) {
                t2.cancel();
            }
            t2 = Toast.makeText(this, getString(R.string.playing_game), Toast.LENGTH_SHORT);
            t2.show();
        }
    }


    //センサーの値が変わったとき
    @Override
    public void onSensorChanged(SensorEvent event) {
        pitch = (int) event.values[1];
        role = (int) event.values[2];
    }

    //プリファレンスの初期化(開発用)
    public void preferencesAllClear() {
        SharedPreferences.Editor e1 = preferences.edit();
        e1.clear();
        e1.commit();
        SharedPreferences.Editor e2 = preferences2.edit();
        e2.clear();
        e2.commit();
        SharedPreferences.Editor e3 = preferences3.edit();
        e3.clear();
        e3.commit();
    }

    /////リスナーを作成/////
    //電卓としてのclear機能
    View.OnClickListener clear = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (overButtonIsExistence) {
                overButton(view);
            } else {
                clear(textView);
            }
        }
    };
    //電卓としてのlogic機能
    View.OnClickListener logic = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            logic(view);
        }
    };
    //電卓としてのequal機能
    View.OnClickListener equal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            equal(view);
        }
    };
    //電卓としてのnumber機能
    View.OnClickListener numberOn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            numberOn(view);
        }
    };
    //電卓としてのdot機能
    View.OnClickListener dot = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dot(view);
        }
    };

    //Canvasを消す機能
    View.OnClickListener drawClearPicture = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.delete();
        }
    };
    //Drawを終了する機能
    View.OnClickListener drawExit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.exit), Toast.LENGTH_SHORT);
            t3.show();
            drawView.exit();
            buttonBox.getOperatorButton(0).setOnClickListener(logic); //×
            buttonBox.getOperatorButton(1).setOnClickListener(logic); //÷
            buttonBox.getOperatorButton(2).setOnClickListener(logic); //+
            buttonBox.getOperatorButton(3).setOnClickListener(logic); //-
            buttonBox.getOperatorButton(4).setOnClickListener(dot); //.
            buttonBox.getOperatorButton(5).setOnClickListener(equal); //=
            buttonBox.getOperatorButton(6).setOnClickListener(clear); //C
            for (int i = 0; i < buttonBox.getButtonsSize(); i++) {
                buttonBox.getButton(i).setOnClickListener(numberOn);
            }
            gameAssistLayout.removeAllViews();
        }
    };

    //ペンの太さを上げる機能
    View.OnClickListener penUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenUp();
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.thickness) + "  " + drawView.getPenSize(), Toast.LENGTH_SHORT);
            t3.show();
            drawThicknessSample.setText(String.valueOf(drawView.getPenSize()));
        }
    };
    //ペンの太さを下げる機能
    View.OnClickListener penDown = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenDown();
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.thickness) + "  " + drawView.getPenSize(), Toast.LENGTH_SHORT);
            t3.show();
            drawThicknessSample.setText(String.valueOf(drawView.getPenSize()));
        }
    };
    //消しゴム機能
    View.OnClickListener eraser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(255, 255, 255));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), " " + getString(R.string.eraser), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を黒にする機能
    View.OnClickListener penColorBlack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(0, 0, 0));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.black), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を濃灰色にする機能
    View.OnClickListener penColorDKGrey = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(131, 131, 131));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.dark_gray), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を薄灰色にする機能
    View.OnClickListener penColorLTGrey = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(204, 204, 204));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.light_gray), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を黄色にする機能
    View.OnClickListener penColorYellow = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(254, 242, 74));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.yellow), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色をオレンジにする機能
    View.OnClickListener penColorOrange = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(247, 182, 71));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.orange), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を赤にする機能
    View.OnClickListener penColorRed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(249, 41, 28));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.red), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を青にする機能
    View.OnClickListener penColorBlue = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(81, 200, 237));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.blue), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色を緑にする機能
    View.OnClickListener penColorGreen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(129, 212, 78));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.green), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色をピンクにする機能
    View.OnClickListener penColorPink = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(254, 173, 240));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.pink), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンの色をランダムにする機能
    View.OnClickListener penColorRandom = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.setPenColor(Color.rgb(kikurageUtil.random(256), kikurageUtil.random(256), kikurageUtil.random(256)));
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.color) + getString(R.string.random), Toast.LENGTH_SHORT);
            t3.show();
            drawColorSample.setTextColor(drawView.getPenColor());
        }
    };
    //ペンを点線にする機能
    View.OnClickListener paintDot = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.dotted();
            if (t3 != null) {
                t3.cancel();
            }
            if (drawView.getIsDot()) {
                t3 = Toast.makeText(getApplicationContext(), getString(R.string.dotted) + getString(R.string.dotOn), Toast.LENGTH_SHORT);
                t3.show();
                drawLineSample.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dot_line));
            } else {
                t3 = Toast.makeText(getApplicationContext(), getString(R.string.dotted) + getString(R.string.dotOff), Toast.LENGTH_SHORT);
                t3.show();
                drawLineSample.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.line));
            }
        }
    };
    //塗りつぶしをする機能
    View.OnClickListener paintFill = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawView.fill();
            if (t3 != null) {
                t3.cancel();
            }
            t3 = Toast.makeText(getApplicationContext(), getString(R.string.fill), Toast.LENGTH_SHORT);
            t3.show();
        }
    };


    ////////////////////////////////


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //今回は使いませんでした。
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //メニューボタンを押した時の処理です。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) { // Menuボタンが押されたら
            startActivity(intent_to_setting);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
