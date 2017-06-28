package me.omidh.liquidradiobutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

class RadioButtonDrawable extends Drawable implements Animatable {

    private boolean mRunning = false;

    private Paint mPaint;
    private ValueAnimator mEntranceAnimator;
    private float mAnimProgress;
    private float mScaleFactor = 1f;
    private int mInAnimDuration;
    private int mOutAnimDuration;
    private int mExplodeCounts;
    private int mStrokeSize;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mInnerRadius;
    private int mCurColor;
    private ColorStateList mColorState;
    private boolean mChecked = false;

    private boolean mInEditMode = false;
    private boolean mAnimEnable = true;

    private ExplosionAnimator mExplosionsAnimator;
    private Boolean explosionAnimationIsRunning = true;

    private RadioButtonDrawable(Builder builder){
        mInAnimDuration = builder.mInAnimDuration;
        mOutAnimDuration = builder.mOutAnimDuration;
        mExplodeCounts = builder.mExplodeCounts;
        mStrokeSize = builder.mStrokeSize;
        mHeight = mWidth = (builder.mRadius+builder.mOuterPadding) * 2;
        mRadius = builder.mRadius;
        mInnerRadius = builder.mInnerRadius;
        mColorState = builder.mColorStateList;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    void setInEditMode(boolean b){
        mInEditMode = b;
    }

    void setAnimEnable(boolean b){
        mAnimEnable = b;
    }

//    boolean isAnimEnable(){
//        return mAnimEnable;
//    }

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
    public void draw(@NonNull Canvas canvas) {
        if(mChecked)
            drawChecked(canvas);
        else
            drawUnchecked(canvas);
    }

    private void drawChecked(Canvas canvas) {
        float cx = getBounds().exactCenterX();
        float cy = getBounds().exactCenterY();

        if(isRunning()){
            canvas.scale(1-mScaleFactor,1+mScaleFactor,cx,cy);
            mPaint.setColor(mCurColor);
            mPaint.setStrokeWidth(mStrokeSize);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx, cy, mRadius, mPaint);

            mPaint.setColor(mCurColor);
            mPaint.setStyle(Paint.Style.FILL);
            float centerY = (cy - ( mRadius + mStrokeSize) * (1 - mAnimProgress));
            float radius = mInnerRadius*mAnimProgress;
            canvas.drawCircle(cx,centerY,radius , mPaint);
        }
        else{
            mPaint.setColor(mCurColor);
            mPaint.setStrokeWidth(mStrokeSize);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx, cy, mRadius, mPaint);

            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, mInnerRadius, mPaint);
        }
    }

    private void drawUnchecked(Canvas canvas){
        float cx = getBounds().exactCenterX();
        float cy = getBounds().exactCenterY();

        mPaint.setColor(mCurColor);
        mPaint.setStrokeWidth(mStrokeSize);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        if(!explosionAnimationIsRunning){
            explosionAnimationIsRunning = true ;
            ExplosionAnimator animator = new ExplosionAnimator(this,getBounds(),mCurColor,mExplodeCounts
            ,mRadius,mInnerRadius);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mExplosionsAnimator = null;
                }
            });
            animator.setDuration(mOutAnimDuration);
            mExplosionsAnimator = animator;
            animator.start();
        }
        if(mExplosionsAnimator != null)
            mExplosionsAnimator.draw(canvas);
    }

    @Override
    protected boolean onStateChange(int[] state) {
        boolean checked = Utils.hasState(state, android.R.attr.state_checked);
        int color = mColorState.getColorForState(state, mCurColor);
        boolean needRedraw = false;

        if(mChecked != checked){
            mChecked = checked;
            explosionAnimationIsRunning = false;
            needRedraw =  true;
            if(!mInEditMode && mAnimEnable)
                start();

        }

        if(mCurColor != color){
            mCurColor = color;
            needRedraw = true;
        }

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

    private void resetAnimation(){
        mAnimProgress = 0f;
    }

    @Override
    public void start() {
        resetAnimation();
        startScaleAnimation();
        update();
        invalidateSelf();
    }

    @Override
    public void stop() {
        mRunning = false;
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    private void startScaleAnimation(){
        mEntranceAnimator = ValueAnimator.ofFloat(0f,0.1f,0f,-0.1f,0f,0.04f,0f).setDuration(mInAnimDuration);
        mEntranceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScaleFactor = (float)valueAnimator.getAnimatedValue();
                invalidateSelf();
            }
        });
        mEntranceAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mEntranceAnimator.start();
    }

    private void update(){
        mRunning = true;
        ValueAnimator animator = ValueAnimator.ofFloat(0,1f);
        animator.setDuration(mInAnimDuration/2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimProgress = (float) valueAnimator.getAnimatedValue();
                invalidateSelf();
            }
        });
        animator.start();
    }

    static class Builder{

        private int mInAnimDuration;
        private int mOutAnimDuration;
        private int mExplodeCounts;
        private int mStrokeSize;
        private int mRadius;
        private int mInnerRadius;
        private int mCheckedColor;
        private int mUnCheckedColor;
        private int mOuterPadding;
        private ColorStateList mColorStateList;


        RadioButtonDrawable build(){
            int[][] states = new int[][]{
                    new int[]{-android.R.attr.state_checked},
                    new int[]{android.R.attr.state_checked},
            };
            int[] colors = new int[]{
                    mUnCheckedColor,
                    mCheckedColor
            };
            mColorStateList = new ColorStateList(states,colors);

            return new RadioButtonDrawable(this);
        }


        Builder strokeSize(int size){
            mStrokeSize = size;
            return this;
        }
        Builder explodeCount(int count){
            mExplodeCounts = count;
            return this;
        }
        Builder checkedColor(int color){
            mCheckedColor = color;
            return this;
        }
        Builder unCheckedColor(int color){
            mUnCheckedColor = color;
            return this;
        }
        Builder radius(int radius){
            mRadius = radius;
            return this;
        }

        Builder innerRadius(int radius){
            mInnerRadius = radius;
            return this;
        }

        Builder outAnimDuration(int duration){
            mOutAnimDuration = duration;
            return this;
        }

        Builder inAnimDuration(int duration){
            mInAnimDuration = duration;
            return this;
        }

        public Builder outerPadding(int padding) {
            mOuterPadding = padding;
            return this;
        }
    }
}
