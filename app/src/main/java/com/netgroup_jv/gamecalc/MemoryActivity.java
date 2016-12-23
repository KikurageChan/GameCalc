package com.netgroup_jv.gamecalc;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import myGameUtil.KikurageSE;
import myGameUtil.KikurageUtil;

public class MemoryActivity extends AppCompatActivity {

    /////画面上のUI関係/////
    private TextView[] card_textViews;
    private TextView[] arrow_textViews;

    private LinearLayout scroll;

    /////プリファレンス/////
    private SharedPreferences preferences2;
    private SharedPreferences preferences3;

    /////効果音システム/////
    private KikurageSE kikurageSE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        card_textViews = new TextView[3];
        arrow_textViews = new TextView[3];
        //このアクティビティのレイアウトはactivity_memoryを設定します。
        setContentView(R.layout.activity_memory);

        //以下　必要なアイテムをレイアウトからfindしてきます。
//        card_textViews[0] = (TextView) findViewById(R.id.first);
//        card_textViews[1] = (TextView) findViewById(R.id.second);
//        card_textViews[2] = (TextView) findViewById(R.id.third);
        scroll = (LinearLayout) findViewById(R.id.scroll);

        //プリファレンス
        preferences2 = getSharedPreferences("GAMESCORE", MODE_PRIVATE);
        preferences3 = getSharedPreferences("FINDGAME", MODE_PRIVATE);
        //広告
        AdView adView = (AdView) this.findViewById(R.id.memoryAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //cardが見つかっている時の処理
        if (preferences3.getBoolean("card", false)) {
            addMemory(R.string.card, 1234, R.drawable.number_sample, true);
        }
        //drawが見つかっている時の処理
        if (preferences3.getBoolean("draw", false)) {
            addMemory(R.string.draw, 9999, R.drawable.draw_sample, false);
        }
        //arrowが見つかっている時の処理
        if (preferences3.getBoolean("arrow", false)) {
            addMemory(R.string.arrow, 5932, R.drawable.arrow_sample, true);
        }
        //blockが見つかっている時の処理
        if (preferences3.getBoolean("block", false)) {
            addMemory(R.string.block, 1753, R.drawable.block_sample, false);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        //スコアに応じてTextViewを変更
        if (preferences3.getBoolean("card", false)) {
            String[] cardScores = preferences2.getString("cardScore", "0,0,0").split(",");
            card_textViews[0].setText(cardScores[0]);
            card_textViews[1].setText(cardScores[1]);
            card_textViews[2].setText(cardScores[2]);
        }
        if (preferences3.getBoolean("arrow", false)) {
            //arrowのTextViewを変更
            String[] arrowScores = preferences2.getString("arrowScore", "0,0,0").split(",");
            arrow_textViews[0].setText(arrowScores[0]);
            arrow_textViews[1].setText(arrowScores[1]);
            arrow_textViews[2].setText(arrowScores[2]);
        }
    }

    public void addMemory(int stringID, int startingNumber, int imgID, boolean scoreDisplay) {
        String gameName = getString(stringID);
        Bitmap img = BitmapFactory.decodeResource(getResources(), imgID);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) KikurageUtil.getPxForInt(300), ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        params.bottomMargin = KikurageUtil.getPxForInt(50);

        final LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout2.setLayoutParams(params2);
        linearLayout.addView(linearLayout2);

        final LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(KikurageUtil.getPxForInt(0), ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.weight = 0.5f;
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.LEFT);
        textView.setText(gameName);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(25);
        linearLayout2.addView(textView, params3);
        final LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(KikurageUtil.getPxForInt(0), ViewGroup.LayoutParams.WRAP_CONTENT);
        params4.weight = 0.5f;
        TextView textView2 = new TextView(this);
        textView2.setGravity(Gravity.RIGHT);
        textView2.setText(String.valueOf(startingNumber));
        textView2.setTextColor(Color.WHITE);
        textView2.setTextSize(25);
        linearLayout2.addView(textView2, params4);

        final ViewGroup.LayoutParams params5 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, KikurageUtil.getPxForInt(1));
        View view = new View(this);
        view.setBackgroundColor(Color.rgb(63, 188, 235));
        linearLayout.addView(view, params5);

        final LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, KikurageUtil.getPxForInt(100), Gravity.CENTER);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(img);
        linearLayout.addView(imageView, params6);

        if (scoreDisplay) {
            final LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView3 = new TextView(this);
            textView3.setText(R.string.high_score);
            textView3.setTextColor(Color.WHITE);
            textView3.setTextSize(15);
            linearLayout.addView(textView3, params7);

            final LinearLayout.LayoutParams params8 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout3 = new LinearLayout(this);
            linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout3.setLayoutParams(params8);
            linearLayout.addView(linearLayout3);

            final LinearLayout.LayoutParams params9 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params9.weight = 0.1f;
            params9.gravity = Gravity.CENTER;
            ImageView imageView2 = new ImageView(this);
            imageView2.setImageResource(R.drawable.gold);
            linearLayout3.addView(imageView2, params9);

            final LinearLayout.LayoutParams params10 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            params10.weight = 0.2f;
            TextView textView4 = new TextView(this);
            textView4.setGravity(Gravity.CENTER_HORIZONTAL);
            textView4.setText("100");
            textView4.setTextColor(Color.WHITE);
            textView4.setTextSize(15);
            linearLayout3.addView(textView4, params10);

            final LinearLayout.LayoutParams params11 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params11.weight = 0.1f;
            params11.gravity = Gravity.CENTER;
            ImageView imageView3 = new ImageView(this);
            imageView3.setImageResource(R.drawable.silver);
            linearLayout3.addView(imageView3, params11);

            final LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            params12.weight = 0.2f;
            TextView textView5 = new TextView(this);
            textView5.setGravity(Gravity.CENTER_HORIZONTAL);
            textView5.setText("100");
            textView5.setTextColor(Color.WHITE);
            textView5.setTextSize(15);
            linearLayout3.addView(textView5, params12);

            final LinearLayout.LayoutParams params13 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params13.weight = 0.1f;
            params13.gravity = Gravity.CENTER;
            ImageView imageView4 = new ImageView(this);
            imageView4.setImageResource(R.drawable.bronze);
            linearLayout3.addView(imageView4, params13);

            final LinearLayout.LayoutParams params14 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            params14.weight = 0.2f;
            TextView textView6 = new TextView(this);
            textView6.setGravity(Gravity.CENTER_HORIZONTAL);
            textView6.setText("100");
            textView6.setTextColor(Color.WHITE);
            textView6.setTextSize(15);
            linearLayout3.addView(textView6, params14);

            //ゲームがArrowだった場合の処理
            if (gameName == getString(R.string.arrow)) {
                arrow_textViews[0] = textView4;
                arrow_textViews[1] = textView5;
                arrow_textViews[2] = textView6;
            } else if (gameName == getString(R.string.card)) {
                card_textViews[0] = textView4;
                card_textViews[1] = textView5;
                card_textViews[2] = textView6;

            }
        }
        scroll.addView(linearLayout, params);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memory, menu);
        return true;
    }

    ///////////////////////////////

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
