package com.example.tuionf.paypsdview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by tuion on 2017/5/15.
 */

public class PayPsdInputView extends android.support.v7.widget.AppCompatEditText {

    private Context mContext;

    /**
     * view的高度
     */
    private int height;
    private int width;

    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;

    //最大可输入的数字
    private int maxCount = 6;
    //圆圈点的颜色 默认是黑色
    private int circleColor = Color.BLACK;

    //实心圆的半径
    private int radius = 10;

    //分割线的宽度  默认2
    private int divideLineWidth = 2;

    //竖直分割线的颜色
    private int divideLineColor = Color.GRAY;

    //矩形边框的圆角
    private int rectAngle = 0;

    private int borderColor = Color.GRAY;

    //竖直分割线的画笔
//    private Paint divideLinePaint;

    //
    private Paint borderPaint;

    //圆的画笔
    private Paint circlePaint;
    private int bottomLineLength;
    private int text_length;
    private Canvas mCanvas;

    private RectF rectF = new RectF();

    private static final String TAG = "PsdPayInputView";

    public PayPsdInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        Log.e(TAG, "PsdPayInputView: ");
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        //TODO
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.PsdPayInputView);
        maxCount = typedArray.getInt(R.styleable.PsdPayInputView_maxCount,maxCount);
        circleColor = typedArray.getColor(R.styleable.PsdPayInputView_circleColor,circleColor);
        radius = typedArray.getInt(R.styleable.PsdPayInputView_radius,radius);
        divideLineWidth = typedArray.getInt(R.styleable.PsdPayInputView_divideLineWidth,divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.PsdPayInputView_divideLineColor,divideLineColor);
//        rectAngle = typedArray.getInt(R.styleable.PsdPayInputView_rectAngle,rectAngle);

        typedArray.recycle();

    }

    private void initPaint() {
        borderPaint = getPaint(3, Paint.Style.STROKE, borderColor);
        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);
        //TODO  可添加  暂不添加
//        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);
    }

    private Paint getPaint(int StrokeWidth, Paint.Style style,int color) {
        Paint p = new Paint();
        p.setStrokeWidth(StrokeWidth);
        //去锯齿
        p.setAntiAlias(true);
        //设置   画笔样式分三种：
//        1.Paint.Style.STROKE：描边
//        2.Paint.Style.FILL_AND_STROKE：描边并填充
//        3.Paint.Style.FILL：填充
        p.setStyle(style);
        p.setColor(color);
        return p;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2; //第一个圆心的x坐标
        startY = h / 2; //第一个圆心的y坐标

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawRect((320 - 80), 20, (320 - 80) / 2 + 80, 20 + 40, borderPaint);
        this.mCanvas = canvas;
        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

//        canvas.drawRect(0, 0, width, height,borderPaint);
        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    borderPaint);
        }

        drawRectCurcle(canvas);
    }

    private void drawRectCurcle(Canvas canvas) {
        for (int i = 0; i < text_length; i++) {
//            canvas.drawCircle(startX+i*2*startX,startY,radius,circlePaint);
            canvas.drawCircle(startX+i*divideLineWStartX,startY,radius,circlePaint);
        }
    }

    //原有的文本s中，从start开始的count个字符被一个新的长度为after的文本替换
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        text_length = text.toString().length();

        invalidate();
    }




}
