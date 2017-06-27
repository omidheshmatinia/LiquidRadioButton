package me.omidh.liquidradiobutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Omid Heshmatinia on 6/18/2017.
 */

public class LiquidRadioButton extends android.support.v7.widget.AppCompatRadioButton {


    public LiquidRadioButton(Context context) {
        super(context);
        init(null);
    }

    public LiquidRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LiquidRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attr){

        RadioButtonDrawable drawable = new RadioButtonDrawable.Builder().build();
        drawable.setInEditMode(isInEditMode());
        drawable.setAnimEnable(false);
        setButtonDrawable(drawable);
        drawable.setAnimEnable(true);
    }

    @Override
    public void toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
        if (!isChecked()) {
            super.toggle();
        }
    }

    /**
     * Change the checked state of this button immediately without showing animation.
     * @param checked The checked state.
     */
    public void setCheckedImmediately(boolean checked){
//        if(getButtonDrawable() instanceof RadioButtonDrawable){
//            RadioButtonDrawable drawable = (RadioButtonDrawable)getButtonDrawable();
//            drawable.setAnimEnable(false);
//            setChecked(checked);
//            drawable.setAnimEnable(true);
//        }
//        else
            setChecked(checked);
    }

//    private int mCircleRadius = 10;
//    private int mStrokeWidth = 4;
//    private int mExplodeCounts = 5;
//    private boolean isChecked= false;
//    private Paint mCirclePaint = new Paint();
//    private Paint mStrokePaint = new Paint();
//    private boolean isExplodeAnimationRunning=false;
//    private ValueAnimator mEntranceAnimator;
//    public LiquidRadioButton(Context context) {
//        super(context);
//        init(null);
//    }
//
//    private void init(AttributeSet attrs) {
////        setButtonDrawable(null);
//        setLayerType(LAYER_TYPE_HARDWARE, null);
//        if (attrs != null) {
//            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LiquidRadioButton);
//            mCircleRadius = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_circleRadius, mCircleRadius);
//            mStrokeWidth = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_strokeWidth, mStrokeWidth);
////            mEditTextInputType = a.getInt(R.styleable.RippleValidatorEditText_android_inputType, EditorInfo.TYPE_NULL);
////            mNextFocusIds[0] = a.getResourceId(R.styleable.RippleValidatorEditText_android_nextFocusDown, 0);
////            mNextFocusIds[1] = a.getResourceId(R.styleable.RippleValidatorEditText_android_nextFocusLeft, 0);
////            mNextFocusIds[2] = a.getResourceId(R.styleable.RippleValidatorEditText_android_nextFocusUp, 0);
////            mNextFocusIds[3] = a.getResourceId(R.styleable.RippleValidatorEditText_android_nextFocusRight, 0);
////            mNextFocusIds[4] = a.getResourceId(R.styleable.RippleValidatorEditText_android_nextFocusForward, 0);
////            mImeOptions = a.getInt(R.styleable.RippleValidatorEditText_android_imeOptions, 0);
////            mHelperAnimation = a.getResourceId(R.styleable.RippleValidatorEditText_rve_helperAnimation,R.anim.fade_in_slide_right);
////            mHintColor = a.getColor(R.styleable.RippleValidatorEditText_android_textColorHint, mHintColor);
////            mBackgroundColor = a.getColor(R.styleable.RippleValidatorEditText_rve_backgroundColor, mBackgroundColor);
////            mAutoValidate = a.getBoolean(R.styleable.RippleValidatorEditText_rve_validateOnFocusLost,mAutoValidate);
////            mHintText = a.getString(R.styleable.RippleValidatorEditText_rve_hint);
////            mEditTextGravity = a.getInteger(R.styleable.RippleValidatorEditText_rve_editTextGravity,mEditTextGravity);
////            mHelperTextGravity = a.getInteger(R.styleable.RippleValidatorEditText_rve_helperTextGravity,mHelperTextGravity);
////            String typeface=a.getString(R.styleable.RippleValidatorEditText_rve_font);
////            if(typeface!=null){
////                mTypeFace = Typeface.createFromAsset(getContext().getAssets(),typeface);
////            }
////            mEditTextSize=a.getDimensionPixelSize(R.styleable.RippleValidatorEditText_rve_editTextSize,(int) convertDipToPixels(mEditTextSize));
////            mHelperTextSize=a.getDimensionPixelSize(R.styleable.RippleValidatorEditText_rve_helperTextSize,(int)convertDipToPixels(mHelperTextSize));
////            //corner radius
////            float topRight = a.getDimension(R.styleable.RippleValidatorEditText_rve_topRightCornerRadius,0);
////            float topLeft = a.getDimension(R.styleable.RippleValidatorEditText_rve_topLeftCornerRadius,0);
////            float BottomRight = a.getDimension(R.styleable.RippleValidatorEditText_rve_bottomRightCornerRadius,0);
////            float BottomLeft = a.getDimension(R.styleable.RippleValidatorEditText_rve_bottomLeftCornerRadius,0);
////            mCornerRadius = new float[]{topLeft,topLeft,topRight,topRight,BottomRight,BottomRight,BottomLeft,BottomLeft};
////            // colors
////            mNormalColor = a.getColor(R.styleable.RippleValidatorEditText_rve_normalColor,mNormalColor);
////            mErrorColor = a.getColor(R.styleable.RippleValidatorEditText_rve_errorColor,mErrorColor);
////            mTypingColor = a.getColor(R.styleable.RippleValidatorEditText_rve_typingColor,mTypingColor);
////            mEditTextColor = a.getColor(R.styleable.RippleValidatorEditText_rve_editTextColor,mEditTextColor);
////            mValidColor = a.getColor(R.styleable.RippleValidatorEditText_rve_validColor,mValidColor);
//
//            a.recycle();
//        }
//        mStrokePaint.setStyle(Paint.Style.STROKE);
//        mStrokePaint.setStrokeWidth(mStrokeWidth);
//        mCirclePaint.setStyle(Paint.Style.FILL);
//        setMinWidth(mCircleRadius*2);
//        setMinHeight(mCircleRadius*2);
//
//        //setup animators
//        mEntranceAnimator = ValueAnimator.ofFloat(0f,0.05f,0f,-0.05f,0f,0.02f,0f).setDuration(750);
//        mEntranceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mScaleFactor = (float)valueAnimator.getAnimatedValue();
//                invalidate();
//            }
//        });
//        mEntranceAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                mIsEnteringAnimationPlaying = true ;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                mIsEnteringAnimationPlaying = false ;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//                mIsEnteringAnimationPlaying = false ;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//    }
//
//    public LiquidRadioButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(attrs);
//    }
//
//    public LiquidRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(attrs);
//    }
//
//    @Override
//    public boolean isChecked() {
//        return isChecked;
//    }
//
//    @Override
//    public boolean performClick() {
//        setChecked(!isChecked);
//        return true;
//    }
//
//    private float mScaleFactor=0;
//    private Boolean mIsEnteringAnimationPlaying=false;
//    @Override
//    public void setChecked(boolean checked) {
//        isChecked=checked;
//        if(isChecked){
//            if(mEntranceAnimator.isRunning())
//                mEntranceAnimator.cancel();
//            mEntranceAnimator.start();
//        } else {
//            //todo
//        }
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if(!isChecked){
//            if(isExplodeAnimationRunning){
//
//            } else {
//                mStrokePaint.setColor(Color.RED);
//                mCirclePaint.setColor(Color.RED);
//                canvas.drawCircle(mCircleRadius, mCircleRadius, mCircleRadius, mStrokePaint);
//                canvas.drawCircle(mCircleRadius, mCircleRadius, mCircleRadius / 2, mCirclePaint);
//            }
//        } else {
//            if(mIsEnteringAnimationPlaying) {
//                canvas.scale(1-mScaleFactor,1+mScaleFactor,mCircleRadius/2,mCircleRadius/2);
//            } else {
//                canvas.drawCircle(mCircleRadius,mCircleRadius,mCircleRadius/2,mCirclePaint);
//            }
//            mStrokePaint.setColor(Color.GREEN);
//            mCirclePaint.setColor(Color.GREEN);
//            canvas.drawCircle(mCircleRadius,mCircleRadius,mCircleRadius,mStrokePaint);
//        }
//    }
}
