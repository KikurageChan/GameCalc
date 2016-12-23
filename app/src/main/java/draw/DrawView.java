package draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import myGameUtil.MySurface;

/**
 * Created by kikuragetyann on 16/02/11.
 */
public class DrawView extends View {
    private Paint paint;
    private Path path;
    private int penSize;
    private int penColor;
    private boolean isDot;
    private ArrayList<Path> paths = new ArrayList<>();
    private ArrayList<Paint> paints = new ArrayList<>();

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        penSize = 5;
        penColor = Color.BLACK;
        isDot = false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (!paths.isEmpty()) {
            for (int i = 0; i < paths.size(); i++) {
                canvas.drawPath(paths.get(i), paints.get(i));
            }
        }
        if (path != null && paint != null) {
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setAntiAlias(true);
                paint.setStrokeWidth(penSize);
                paint.setColor(penColor);
                if(isDot){
                    paint.setPathEffect(new DashPathEffect(new float[]{20.0f,20.0f},0));
                }
                path.moveTo(x - 1, y - 1);
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                path.lineTo(x, y);
                paths.add(path);
                paints.add(paint);
                break;
        }

        return true;
    }

    public void delete() {
        if(this.path != null) {
            this.path.reset();
            paths.clear();
            paints.clear();
        }
        invalidate();
    }

    public void setPenUp() {
        if (this.penSize < 20) {
            this.penSize++;
        }
    }

    public void setPenDown() {
        if (this.penSize > 1) {
            this.penSize--;
        }
    }

    public void setPenColor(int color) {
        this.penColor = color;
    }

    public int getPenSize(){
        return this.penSize;
    }
    public int getPenColor(){
        return this.penColor;
    }

    public void dotted(){
        if(this.isDot){
            this.isDot = false;
        }else{
            this.isDot = true;
        }
    }

    public void fill(){
        path = new Path();
        paint = new Paint();
        path.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(penColor);
        paths.add(path);
        paints.add(paint);
        invalidate();
    }

    public boolean getIsDot(){
        return this.isDot;
    }


    public void exit() {
        MySurface.gameIsRun = false;
        MySurface.gameOver = true;
    }
}