package me.omidh.liquidradiobutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

public class LiquidRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private int mCircleRadius = Utils.dp2Px(6);
    private int mStrokeWidth = Utils.dp2Px(2);
    private int mExplodeCounts = 5;
    private int mStrokeRadius = Utils.dp2Px(10);
    private int mOuterPadding =  Utils.dp2Px(4);
    private int mInAnimDuration = 400;
    private int mOutAnimDuration = 300;
    private int mCheckedColor = Color.GRAY ;
    private int mUnCheckedColor = Color.GRAY ;

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

    protected void init(AttributeSet attrs){
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LiquidRadioButton);
            mCircleRadius = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_innerCircleRadius, mCircleRadius);
            mStrokeRadius = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_strokeRadius, mStrokeRadius);
            mStrokeWidth = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_strokeWidth, mStrokeWidth);
            mOuterPadding = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_outterPadding, mOuterPadding);
            mExplodeCounts = a.getInt(R.styleable.LiquidRadioButton_lrb_explodeCount, mExplodeCounts);
            mInAnimDuration = a.getInt(R.styleable.LiquidRadioButton_lrb_inAnimDuration, mInAnimDuration);
            mOutAnimDuration = a.getInt(R.styleable.LiquidRadioButton_lrb_outAnimDuration, mOutAnimDuration);
            mCheckedColor = a.getColor(R.styleable.LiquidRadioButton_lrb_checkedColor,mCheckedColor);
            mUnCheckedColor = a.getColor(R.styleable.LiquidRadioButton_lrb_unCheckedColor,mUnCheckedColor);
//            Boolean isChecked = a.getBoolean(R.styleable.androidc,false);
//            setChecked(isChecked);
        }
        if(mCircleRadius>mStrokeRadius)
            throw new IllegalArgumentException("Outer radius can't be less than Inner Radius");
        RadioButtonDrawable drawable = new RadioButtonDrawable.Builder()
                .inAnimDuration(mInAnimDuration)
                .outAnimDuration(mOutAnimDuration)
                .radius(mStrokeRadius)
                .innerRadius(mCircleRadius)
                .strokeSize(mStrokeWidth)
                .explodeCount(mExplodeCounts)
                .checkedColor(mCheckedColor)
                .unCheckedColor(mUnCheckedColor)
                .outerPadding(mOuterPadding)
                .build();
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

    @Override public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putBoolean("isChecked",isChecked());
        return bundle;
    }

    @Override public void onRestoreInstanceState(Parcelable state) {
        if ( state!=null && state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            setChecked(bundle.getBoolean("isChecked",false));
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }
}
