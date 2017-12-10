package com.example.samsung.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 2017-11-28.
 */

public class MultiTouchView extends View {
    private static final int SIZE = 60;

    final int MAX_POINTS = 10;
    float[] x = new float[MAX_POINTS];
    float[] y = new float[MAX_POINTS];
    boolean[] touching = new boolean[MAX_POINTS];

    List<Float> x2 = new ArrayList<Float>();
    List<Float> y2 = new ArrayList<Float>();
    List<Boolean> touching2 = new ArrayList<Boolean>();

    private Paint mPaint;

    public MultiTouchView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                x[id] = (int) event.getX(index);
                y[id] = (int) event.getY(index);
                touching[id] = true;

                x2.add(event.getX(index));
                y2.add(event.getY(index));
                touching2.add(true);

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touching[id] = false;
                /*x2.clear();
                y2.clear();
                touching2.clear();*/
                break;
        }
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*for( int i = 0; i < MAX_POINTS; i++) {
            if(touching[i]) {
                canvas.drawCircle(x[i],y[i], SIZE, mPaint);
            }
        }*/
        for( int i = 0; i < x2.size(); i++){
            if(touching2.get(i)){
                canvas.drawCircle(x2.get(i), y2.get(i), SIZE, mPaint);
            }
        }

    }
}
