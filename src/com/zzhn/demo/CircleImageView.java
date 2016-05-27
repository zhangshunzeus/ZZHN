package com.zzhn.demo;

import com.zzhn.zhongzhenhuaneng.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

public class CircleImageView extends ImageView {
	//缩放类型
		private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

	    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	    private static final int COLORDRAWABLE_DIMENSION = 2;
	    //边界宽度
	    private static final int DEFAULT_BORDER_WIDTH = 0;
	    //边界颜色
	    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
	    private static final boolean DEFAULT_BORDER_OVERLAY = false;

	    private final RectF mDrawableRect = new RectF();
	    private final RectF mBorderRect = new RectF();

	    private final Matrix mShaderMatrix = new Matrix();
	    private final Paint mBitmapPaint = new Paint();
	    private final Paint mBorderPaint = new Paint();

	    private int mBorderColor = DEFAULT_BORDER_COLOR;
	    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

	    private Bitmap mBitmap;
	    private BitmapShader mBitmapShader;//位图渲染
	    private int mBitmapWidth;//位图宽度
	    private int mBitmapHeight;//位图高度

	    private float mDrawableRadius;//位图半径
	    private float mBorderRadius;//带边框的图片半径

	    private ColorFilter mColorFilter;
	    
	    private boolean mReady;
	    private boolean mSetupPending;
	    private boolean mBorderOverlay;

	    public CircleImageView(Context context) {
	        super(context);

	        init();
	    }

	    public CircleImageView(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }

	    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        //通过obtainStyledAttributes获得值给TypedArray数组
	        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);
	        //获取边界宽度
	        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH);
	        //获得边界的颜色
	        mBorderColor = a.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR);
	        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_border_overlay, DEFAULT_BORDER_OVERLAY);
	        //调用recycle回收TypedArray，
	        a.recycle();

	        init();
	    }

	    private void init() {
	    	//setScaleType被设定为SCALE_TYPE，就是将图片水平居中，进行缩放
	        super.setScaleType(SCALE_TYPE);
	        mReady = true;

	        if (mSetupPending) {
	            setup();
	            mSetupPending = false;
	        }
	    }

	    @Override
	    public ScaleType getScaleType() {
	        return SCALE_TYPE;
	    }

	    @Override
	    public void setScaleType(ScaleType scaleType) {
	        if (scaleType != SCALE_TYPE) {
	            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
	        }
	    }

	    @Override
	    public void setAdjustViewBounds(boolean adjustViewBounds) {
	        if (adjustViewBounds) {
	            throw new IllegalArgumentException("adjustViewBounds not supported.");
	        }
	    }

	    @Override
	    protected void onDraw(Canvas canvas) {
	    	//如果图片不存在就不画
	        if (getDrawable() == null) {
	            return;
	        }
	        //绘制内圆型，参数内圆半径，图片画笔为mBitmapPaint
	        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius, mBitmapPaint);
	        //如果圆形边缘的宽度不为0，我们还要绘制带边界的外圆形，参数外圆半径画笔为mBorderPaint
	        if (mBorderWidth != 0) {
	            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius, mBorderPaint);
	        }
	    }

	    @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        super.onSizeChanged(w, h, oldw, oldh);
	        setup();
	    }

	    public int getBorderColor() {
	        return mBorderColor;
	    }

	    public void setBorderColor(int borderColor) {
	        if (borderColor == mBorderColor) {
	            return;
	        }

	        mBorderColor = borderColor;
	        mBorderPaint.setColor(mBorderColor);
	        invalidate();
	    }

	    public void setBorderColorResource(@ColorRes int borderColorRes) {
	        setBorderColor(getContext().getResources().getColor(borderColorRes));
	    }

	    public int getBorderWidth() {
	        return mBorderWidth;
	    }

	    public void setBorderWidth(int borderWidth) {
	        if (borderWidth == mBorderWidth) {
	            return;
	        }

	        mBorderWidth = borderWidth;
	        setup();
	    }

	    public boolean isBorderOverlay() {
	        return mBorderOverlay;
	    }

	    public void setBorderOverlay(boolean borderOverlay) {
	        if (borderOverlay == mBorderOverlay) {
	            return;
	        }

	        mBorderOverlay = borderOverlay;
	        setup();
	    }
	    //复写Imageview的setImage***（）方法
	    @Override
	    public void setImageBitmap(Bitmap bm) {
	        super.setImageBitmap(bm);
	        mBitmap = bm;
	        setup();
	    }

	    @Override
	    public void setImageDrawable(Drawable drawable) {
	        super.setImageDrawable(drawable);
	        mBitmap = getBitmapFromDrawable(drawable);
	        setup();
	    }

	    @Override
	    public void setImageResource(@DrawableRes int resId) {
	        super.setImageResource(resId);
	        mBitmap = getBitmapFromDrawable(getDrawable());
	        setup();
	    }

	    @Override
	    public void setImageURI(Uri uri) {
	        super.setImageURI(uri);
	        mBitmap = getBitmapFromDrawable(getDrawable());
	        setup();
	    }

	    @Override
	    public void setColorFilter(ColorFilter cf) {
	        if (cf == mColorFilter) {
	            return;
	        }

	        mColorFilter = cf;
	        mBitmapPaint.setColorFilter(mColorFilter);
	        invalidate();
	    }

	    private Bitmap getBitmapFromDrawable(Drawable drawable) {
	        if (drawable == null) {
	            return null;
	        }

	        if (drawable instanceof BitmapDrawable) {
	        	//返回bitmap
	            return ((BitmapDrawable) drawable).getBitmap();
	        }

	        try {
	            Bitmap bitmap;

	            if (drawable instanceof ColorDrawable) {
	                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
	            } else {
	                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
	            }

	            Canvas canvas = new Canvas(bitmap);
	            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	            drawable.draw(canvas);
	            return bitmap;
	        } catch (OutOfMemoryError e) {
	            return null;
	        }
	    }

	    private void setup() {
	        if (!mReady) {
	            mSetupPending = true;
	            return;
	        }
	        //防止空指针异常
	        if (mBitmap == null) {
	            return;
	        }

	        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
	        //设置画笔反锯齿
	        mBitmapPaint.setAntiAlias(true);
	      //设置画笔渲染器
	        mBitmapPaint.setShader(mBitmapShader);
	        //设置边界画笔样式
	        mBorderPaint.setStyle(Paint.Style.STROKE);
	        mBorderPaint.setAntiAlias(true);
	        mBorderPaint.setColor(mBorderColor);//画笔颜色
	        mBorderPaint.setStrokeWidth(mBorderWidth);//画笔边界宽度
	        //取原图片的宽度
	        mBitmapHeight = mBitmap.getHeight();
	        mBitmapWidth = mBitmap.getWidth();
	        //设置含边界显示区域，取得使circleImageview的布局实际大小，为方行
	        mBorderRect.set(0, 0, getWidth(), getHeight());
	        //计算圆形带边界部分的最小半径
	        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2, (mBorderRect.width() - mBorderWidth) / 2);

	        mDrawableRect.set(mBorderRect);
	        if (!mBorderOverlay) {
	            mDrawableRect.inset(mBorderWidth, mBorderWidth);
	        }
	        //计算的使内圆的最小半径，粗去边界宽的的半径
	        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);

	        updateShaderMatrix();
	        invalidate();
	    }

	    private void updateShaderMatrix() {
	    	//这里设置了最小缩放比例，平移参数，保证图片损失的最小和始终绘制图片中中央的部分
	        float scale;
	        float dx = 0;
	        float dy = 0;
	        
	        mShaderMatrix.set(null);
	        //取最小缩放比例
	        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
	        	//y轴缩放，x轴平移，使图片y轴方向的边的尺寸缩放到图片显示区域
	        	scale = mDrawableRect.height() / (float) mBitmapHeight;
	            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
	        } else {
	        	//x轴缩放，y轴平移，使图片x轴方向的边的尺寸缩放到图片显示区域
	            scale = mDrawableRect.width() / (float) mBitmapWidth;
	            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
	        }
	        //shaeder的变换矩阵，用于放大缩小
	        mShaderMatrix.setScale(scale, scale);
	        //平移
	        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);
	        //设置变换矩阵
	        mBitmapShader.setLocalMatrix(mShaderMatrix);
	    }
}
