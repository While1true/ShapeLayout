# ShapeLayout
各种形状的path就可绘制出各种形状
### 自定义ViewGroup形状

- 重写ViewGroup的 dispatchDraw方法
- 利用 Paint 的setXfermode方法，根据path的形状绘制出不同形状图案的效果
- 只替换各种形状的path就可绘制出各种形状
> 说明：参考了一些网上的各种实现方式，这个是比较好的实现方式
- 
![device-2017-08-20-125810.png](http://upload-images.jianshu.io/upload_images/6456519-9dec1e2a5c533332.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/200)


[github 地址](https://github.com/While1true/ShapeLayout)
xml

```
 <com.example.ck.shapelayout.RadiusRelativelayout
        android:layout_width="250dp"
        android:layout_height="250dp"
        android:layout_centerInParent="true"
        app:corner_radius="5dp">

        <ImageView
            android:layout_width="250dp"
            android:layout_height="250dp"
            android:background="#666"
            android:scaleType="fitXY"
            android:src="@mipmap/ic_launcher" />

    </com.example.ck.shapelayout.RadiusRelativelayout>
```



```
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
            float v = dp2px(cornerRadius);
            path.addRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), v, v, Path.Direction.CCW);
        }

        /**
         * 抗锯齿
         */
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
       //保存图层
        int save = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        super.dispatchDraw(canvas);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(path, paint);
        canvas.restoreToCount(save);
        paint.setXfermode(null);
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
```
