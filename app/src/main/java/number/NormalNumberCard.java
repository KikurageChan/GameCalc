package number;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.netgroup_jv.gamecalc.MainActivity;

import myGameUtil.KikurageUtil;

/**
 * Created by kikuragetyann on 16/02/12.
 */
public class NormalNumberCard extends Card {
    private Path path;

    public NormalNumberCard(float x, float y) {
        super(x, y);
        path = new Path();
        setText(getCardTextType());
        setColorCode(getRandomColor());
    }


    @Override
    public void draw(Canvas canvas) {
        paint.reset();
        //四角形の背景を塗ります。
        path.addRect(getX(), getY(), getXx(), getYy(), Path.Direction.CW);
        paint.setColor(getColorCode());
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawPath(path, paint);
        paint.reset();
        //ボーダーを描きます。
        path.addRect(getX(), getY(), getXx(), getYy(), Path.Direction.CW);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        canvas.drawPath(path, paint);
        paint.reset();
        //テキストの処理
        paint.setColor(Color.BLACK);
        paint.setTextSize(KikurageUtil.getPxForInt(20));
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textWidth = paint.measureText(getText());
        // テキストの描画
        paint.setAntiAlias(true);
        canvas.drawText(getText(), getCenterX() - textWidth / 2, getCenterY() - (fontMetrics.ascent + fontMetrics.descent) / 2, paint);
        path.reset();
    }
}
