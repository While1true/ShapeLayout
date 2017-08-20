package com.example.ck.shapelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;


/**
 * Created by S0005 on 2017/1/18.
 */

public class RadiusRelativelayout extends RelativeLayout {

    private float cornerRadius;
    private Path path;

    public RadiusRelativelayout(Context context) {
        this(context, null);
    }

    public RadiusRelativelayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadiusRelativelayout);
        ;
        try {
            cornerRadius = typedArray.getDimension(R.styleable.RadiusRelativelayout_corner_radius, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    public RadiusRelativelayout setPath(Path path) {
        this.path = path;
        invalidate();
        return this;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //无设置空
        if (path == null && cornerRadius == 0) {
            super.dispatchDraw(canvas);
            return;
        }

        /**
         * @path 要显示的形状
         * 未设置path 设置了园角度
         */
        if (path == null) {
            path = new Path();
            path.addRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), cornerRadius, cornerRadius, Path.Direction.CCW);
        }

        /**
         * 抗锯齿
         */
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        int save = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(path, paint);
        canvas.restoreToCount(save);
        paint.setXfermode(null);
    }


}
