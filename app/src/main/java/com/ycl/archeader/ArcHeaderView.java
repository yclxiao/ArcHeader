package com.ycl.archeader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : yclxiao
 *     e-mail : yangchenglong@bm001.com
 *     time   : 2017/12/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ArcHeaderView extends View {

    private Paint paint;
    private PointF startPoint, endPoint, controlPoint;

    private int width;
    private int height;

    private Path path;

    private int startColor;
    private int endColor;

    private int arcHeight = 100;

    private LinearGradient linearGradient;


    public ArcHeaderView(Context context) {
        super(context);
        init();
    }

    public ArcHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArcHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        path = new Path();

        paint = new Paint();

        paint.setAntiAlias(true);//设置为true，则圆弧边缘的锯齿不明显
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);

        startPoint = new PointF(0, 0);
        endPoint = new PointF(0, 0);
        controlPoint = new PointF(0, 0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * view的尺寸改变时会调用
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        path.reset();

        path.moveTo(0, 0);
        path.addRect(0, 0, width, height - arcHeight, Path.Direction.CCW);//先画一个长方形，path添加矩形的轨迹

        startPoint.x = 0;
        startPoint.y = height - arcHeight;

        endPoint.x = width;
        endPoint.y = height - arcHeight;

        controlPoint.x = width / 2;
        controlPoint.y = height;

        //修改颜色线性渐变
        linearGradient = new LinearGradient(0, 0, 0, height, startColor, endColor, Shader.TileMode.CLAMP);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置颜色渐变
        paint.setShader(linearGradient);

        //以下是添加一个二次贝塞尔曲线轨迹
        path.moveTo(startPoint.x, startPoint.y);
        path.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);

        paint.setShadowLayer(30, 0, 0, getResources().getColor(R.color.start_color)); // 画阴影

        //开始画
        canvas.drawPath(path, paint);
    }

    public void setColor(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        linearGradient = new LinearGradient(0, height * 4/7, 0, height, this.startColor, this.endColor, Shader.TileMode.CLAMP);

        invalidate();
    }
}
