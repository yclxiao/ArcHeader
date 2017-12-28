package com.ycl.archeader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : yclxiao
 *     e-mail : yangchenglong@bm001.com
 *     time   : 2017/12/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CircleProgressView extends View {

    private int mMaxProgress = 100;

    private int mProgress = 30;

    private int mCircleLineStrokeWidth = 8;

    private int mTxtStrokeWidth = 2;

    // 画圆所在的距形区域
    private RectF mRectF;

    private Paint mPaint;

    private Context mContext;


    public CircleProgressView(Context context) {
        super(context);
        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.rgb(0xe9, 0xe9, 0xe9));
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        // 位置
        mRectF.left = mCircleLineStrokeWidth ; // 左上角x
        mRectF.top = mCircleLineStrokeWidth ; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth ; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth ; // 右下角y

        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, 135, 270, false, mPaint);


        // 绘制已有进度条
        mPaint.setColor(Color.rgb(0xf8, 0x60, 0x30));
        mPaint.setStrokeWidth(mCircleLineStrokeWidth * 2);
        canvas.drawArc(mRectF, 135, ((float) mProgress / mMaxProgress) * 270, false, mPaint);

    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

}
