package me.omidh.liquidradiobutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;

class RadioButtonDrawable extends Drawable implements Animatable {

    private boolean mRunning = false;

    private Paint mPaint;
    private ValueAnimator mEntranceAnimator;
    private long mStartTime;
    private float mAnimProgress;
    private float mScaleFactor = 1f;
    private int mAnimDuration;
    private int mStrokeSize;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mInnerRadius;
    private int mPrevColor;
    private int mCurColor;
    private ColorStateList mStrokeColor;
    private boolean mChecked = false;

    private boolean mInEditMode = false;
    private boolean mAnimEnable = true;

    private RadioButtonDrawable(int width, int height, int strokeSize, ColorStateList strokeColor, int radius, int innerRadius, int animDuration){
        mAnimDuration = animDuration;
        mStrokeSize = strokeSize;
        mWidth = width;
        mHeight = height;
        mRadius = radius;
        mInnerRadius = innerRadius;
        mStrokeColor = strokeColor;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setInEditMode(boolean b){
        mInEditMode = b;
    }

    public void setAnimEnable(boolean b){
        mAnimEnable = b;
    }

    public boolean isAnimEnable(){
        return mAnimEnable;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mHeight;
    }

    @Override
    public int getMinimumWidth() {
        return mWidth;
    }

    @Override
    public int getMinimumHeight() {
        return mHeight;
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if(mChecked)
            drawChecked(canvas);
        else
            drawUnchecked(canvas);
    }

    private void drawChecked(Canvas canvas) {
        float cx = getBounds().exactCenterX();
        float cy = getBounds().exactCenterY();

        if(isRunning()){
            float halfStrokeSize = mStrokeSize / 2f;
            float inTime = (mRadius - halfStrokeSize) / (mRadius - halfStrokeSize + mRadius - mStrokeSize - mInnerRadius);

            float inProgress = mAnimProgress / inTime;
            float outerRadius = mRadius + halfStrokeSize * (1f - inProgress);
            float innerRadius = (mRadius - halfStrokeSize) * (1f - inProgress);

            canvas.scale(1-mScaleFactor,1+mScaleFactor,cx,cy);

//                mPaint.setColor(ColorUtil.getMiddleColor(mPrevColor, mCurColor, inProgress)); //todo
            mPaint.setColor(Color.GREEN);
//                mPaint.setStrokeWidth(outerRadius - innerRadius);
            mPaint.setStrokeWidth(mStrokeSize);
            mPaint.setStyle(Paint.Style.STROKE);
//                canvas.drawCircle(cx, cy, (outerRadius + innerRadius) / 2, mPaint);
            canvas.drawCircle(cx, cy, mRadius, mPaint);

            mPaint.setColor(Color.GREEN);
            mPaint.setStyle(Paint.Style.FILL);
//                canvas.drawCircle(cx, (cy-innerRadius)+cy*inProgress,innerRadius, mPaint);
            float centerY = Math.min( (cy- mRadius - mStrokeSize)+mRadius*inProgress,cy);
            float radius = Math.min( mInnerRadius-innerRadius,mInnerRadius);
            canvas.drawCircle(cx,centerY,radius , mPaint);
        }
        else{
            mPaint.setColor(mCurColor);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(mStrokeSize);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, mRadius, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, mInnerRadius, mPaint);
    }
    }

    ExplosionAnimator mExplosionsAnimator;
    private Boolean explosionAnimationIsRunning = false;
    private void drawUnchecked(Canvas canvas){
        float cx = getBounds().exactCenterX();
        float cy = getBounds().exactCenterY();

//        if(isRunning()){
//            float halfStrokeSize = mStrokeSize / 2f;
//            float inTime = (mRadius - mStrokeSize - mInnerRadius) / (mRadius - halfStrokeSize + mRadius - mStrokeSize - mInnerRadius);
//
////            if(mAnimProgress < inTime){
//                float inProgress = mAnimProgress / inTime;
//                float innerRadius = (mRadius - mStrokeSize) * inProgress + mInnerRadius * (1f - inProgress);
//
////                mPaint.setColor(ColorUtil.getMiddleColor(mPrevColor, mCurColor, inProgress)); //todo
//                mPaint.setColor(Color.RED);
//                mPaint.setStyle(Paint.Style.FILL);
//                canvas.drawCircle(cx, cy, innerRadius, mPaint);
//
//                float outerRadius = mRadius + halfStrokeSize * (1f - inProgress);
//                mPaint.setStrokeWidth(mStrokeSize);
//                mPaint.setStyle(Paint.Style.STROKE);
//                canvas.drawCircle(cx, cy, outerRadius - halfStrokeSize, mPaint);
////            }
////            else{
////                float outProgress = (mAnimProgress - inTime) / (1f - inTime);
////                float outerRadius = mRadius + halfStrokeSize * outProgress;
////                float innerRadius = (mRadius - halfStrokeSize) * outProgress;
////
////                mPaint.setColor(mCurColor);
////                mPaint.setStrokeWidth(outerRadius - innerRadius);
////                mPaint.setStyle(Paint.Style.STROKE);
////                canvas.drawCircle(cx, cy, (outerRadius + innerRadius) / 2, mPaint);
////            }
//        }
//        else{
            mPaint.setColor(mCurColor);
            mPaint.setStrokeWidth(mStrokeSize);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx, cy, mRadius, mPaint);
//        }
        if(!explosionAnimationIsRunning){
            explosionAnimationIsRunning = true ;
            ExplosionAnimator animator = new ExplosionAnimator(this,getBounds(),mCurColor,5);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mExplosionsAnimator = null;
                }
            });
            animator.setStartDelay(0);
            animator.setDuration(300);
            mExplosionsAnimator = animator;
            animator.start();
        }
        if(mExplosionsAnimator != null)
            mExplosionsAnimator.draw(canvas);
    }

    @Override
    protected boolean onStateChange(int[] state) {
        boolean checked = ViewUtil.hasState(state, android.R.attr.state_checked);
        int color = mStrokeColor.getColorForState(state, mCurColor);
        boolean needRedraw = false;

        if(mChecked != checked){
            mChecked = checked;
            explosionAnimationIsRunning = false;
            needRedraw =  true;
            if(!mInEditMode && mAnimEnable)
                start();

        }

        if(mCurColor != color){
            mPrevColor = isRunning() ? mCurColor : color;
            mCurColor = color;
            needRedraw = true;
        }
        else if(!isRunning())
            mPrevColor = color;

        return needRedraw;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    //Animation: based on http://cyrilmottier.com/2012/11/27/actionbar-on-the-move/

    private void resetAnimation(){
        mStartTime = SystemClock.uptimeMillis();
        mAnimProgress = 0f;
    }

    @Override
    public void start() {
        resetAnimation();
        startScaleAnimation();
        scheduleSelf(mUpdater, SystemClock.uptimeMillis() + ViewUtil.FRAME_DURATION);
        invalidateSelf();
    }

    @Override
    public void stop() {
        mRunning = false;
        unscheduleSelf(mUpdater);
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    private void startScaleAnimation(){
        mEntranceAnimator = ValueAnimator.ofFloat(0f,0.05f,0f,-0.05f,0f,0.02f,0f).setDuration(mAnimDuration);
        mEntranceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScaleFactor = (float)valueAnimator.getAnimatedValue();
            }
        });
//        mEntranceAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
        mEntranceAnimator.start();
    }
    @Override
    public void scheduleSelf(Runnable what, long when) {
        mRunning = true;
        super.scheduleSelf(what, when);
    }

    private final Runnable mUpdater = new Runnable() {

        @Override
        public void run() {
            update();
        }

    };

    private void update(){
        long curTime = SystemClock.uptimeMillis();
        mAnimProgress = Math.min(1f, (float)(curTime - mStartTime) / mAnimDuration);

        if(mAnimProgress == 1f)
            mRunning = false;

        if(isRunning())
            scheduleSelf(mUpdater, SystemClock.uptimeMillis() + ViewUtil.FRAME_DURATION);

        invalidateSelf();
    }

    public static class Builder{

        private int mAnimDuration = 400;
        private int mStrokeSize = 2;
        private int mWidth = 64;
        private int mHeight = 64;
        private int mRadius = 12;
        private int mInnerRadius = 8;
        private ColorStateList mStrokeColor;

//        public Builder(){}

        //        public Builder(Context context, int defStyleRes){
//            this(context, null, 0, defStyleRes);
//        }
        public Builder(){
//        public Builder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadioButtonDrawable, defStyleAttr, defStyleRes);
//
//            width(a.getDimensionPixelSize(R.styleable.RadioButtonDrawable_rbd_width, ThemeUtil.dpToPx(context, 32)));
//            height(a.getDimensionPixelSize(R.styleable.RadioButtonDrawable_rbd_height, ThemeUtil.dpToPx(context, 32)));
//            strokeSize(a.getDimensionPixelSize(R.styleable.RadioButtonDrawable_rbd_strokeSize, ThemeUtil.dpToPx(context, 2)));
//            radius(a.getDimensionPixelSize(R.styleable.RadioButtonDrawable_rbd_radius, ThemeUtil.dpToPx(context, 10)));
//            innerRadius(a.getDimensionPixelSize(R.styleable.RadioButtonDrawable_rbd_innerRadius, ThemeUtil.dpToPx(context, 5)));
//            strokeColor(a.getColorStateList(R.styleable.RadioButtonDrawable_rbd_strokeColor));
//            animDuration(a.getInt(R.styleable.RadioButtonDrawable_rbd_animDuration, context.getResources().getInteger(android.R.integer.config_mediumAnimTime)));
//
//            a.recycle();
            width(Utils.dp2Px(32));
            height(Utils.dp2Px(32));
            strokeSize(Utils.dp2Px(2));
            radius(Utils.dp2Px(12));
            innerRadius(Utils.dp2Px(8));
//            strokeColor(Color.GRAY);
            animDuration(750);
            if(mStrokeColor == null){
                int[][] states = new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked},
                };
                int[] colors = new int[]{
                        Color.GRAY,
                        Color.BLACK
//                        ThemeUtil.colorControlNormal(context, 0xFF000000),
//                        ThemeUtil.colorControlActivated(context, 0xFF000000),
                };
                strokeColor(new ColorStateList(states, colors));
            }
        }

        public RadioButtonDrawable build(){
            if(mStrokeColor == null)
                mStrokeColor = ColorStateList.valueOf(0xFF000000);

            return new RadioButtonDrawable(mWidth, mHeight, mStrokeSize, mStrokeColor, mRadius, mInnerRadius, mAnimDuration);
        }

        public Builder width(int width){
            mWidth = width;
            return this;
        }

        public Builder height(int height){
            mHeight = height;
            return this;
        }

        public Builder strokeSize(int size){
            mStrokeSize = size;
            return this;
        }

        public Builder strokeColor(int color){
            mStrokeColor = ColorStateList.valueOf(color);
            return this;
        }

        public Builder strokeColor(ColorStateList color){
            mStrokeColor = color;
            return this;
        }

        public Builder radius(int radius){
            mRadius = radius;
            return this;
        }

        public Builder innerRadius(int radius){
            mInnerRadius = radius;
            return this;
        }

        public Builder animDuration(int duration){
            mAnimDuration = duration;
            return this;
        }
    }
}
