package com.volokh.danylo.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by danylo.volokh on 11/2/2015.
 */
public class Capsule extends LinearLayout {

    private static final String TAG = Capsule.class.getSimpleName();
    private final Paint mPaint = new Paint();

    {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
    }

    public Capsule(Context context) {
        super(context);
    }

    public Capsule(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Capsule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Capsule(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // draw a cross
        canvas.drawLine(1, 1, getMeasuredWidth(), 1, mPaint);
        canvas.drawLine(1, getHeight()-1, getMeasuredWidth(), getHeight()-1, mPaint);
        canvas.drawLine(1, 1, 1, getHeight()-1, mPaint);
        canvas.drawLine(getMeasuredWidth()-1, 1, getMeasuredWidth()-1, getHeight()-1, mPaint);


        canvas.drawLine(getMeasuredWidth()/2, 0, getMeasuredWidth()/2, getMeasuredHeight(), mPaint);
        canvas.drawLine(0, getMeasuredHeight()/2, getMeasuredWidth(), getMeasuredHeight()/2, mPaint);
    }
}
