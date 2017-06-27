package me.omidh.liquidradiobutton;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

class ExplosionAnimator extends ValueAnimator {

    private Paint mPaint;
    private Particle[] mParticles;
    private Rect mBound;
    private RadioButtonDrawable mContainer;
    private int mColor;
    private int GAP = 10 ;
    private int mOuterRadius = 10 ;
    private int mInnerRadius = 10 ;

    ExplosionAnimator(RadioButtonDrawable container, Rect bound , int color, int number,int outerRadius
    ,int innerRadius) {
        mInnerRadius= innerRadius;
        mOuterRadius = outerRadius;
        mPaint = new Paint();
        mBound = new Rect(bound);
        mColor=color;
        mParticles = new Particle[number];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < number; i++) {
            mParticles[i] = generateParticle(random,i,number);
        }
        mContainer = container;
        setFloatValues(0f, 1f);
        GAP = 50 / number ;
    }

    private Particle generateParticle(Random random,int index,int max) {
        Particle particle = new Particle();

        int nextDegree = Math.max(random.nextInt(360/max) - GAP,0) +((index*(360/max))+GAP);

        float[] magnifiers = degreeToDirection(nextDegree);
        particle.xMag = magnifiers[0];
        particle.yMag = magnifiers[1];

        particle.baseCenterX = mBound.centerX();
        particle.cx = mBound.centerX();
        particle.baseCenterY = mBound.centerY();
        particle.cy = mBound.centerY();
        particle.baseRadius = particle.radius = mInnerRadius;

        return particle;
    }

    boolean draw(Canvas canvas) {
        if (!isRunning()) {
            return false;
        }
        for (Particle particle : mParticles) {
            particle.advance((float) getAnimatedValue());
            mPaint.setColor(mColor);
            canvas.drawCircle(particle.cx, particle.cy, particle.radius, mPaint);
        }
        mContainer.invalidateSelf();
        return true;
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidateSelf();
    }

    private class Particle {
        float cx;
        float cy;
        float radius;
        float baseRadius;
        float baseCenterX;
        float baseCenterY;
        float yMag;
        float xMag;


        void advance(float factor) {
            if(factor<=0.80){
                radius =  baseRadius * (1 - factor*0.98765f) ;
            } else if (factor>0.80 && factor<=0.99){
                radius = baseRadius * (0.19f  + factor*0.20202020f)  ;
            } if (factor>0.99)
                radius = baseRadius * (0.21f-(0.21f*factor));

            cx = baseCenterX + ((xMag*factor)* mOuterRadius);
            cy = baseCenterY + ((yMag*factor)* mOuterRadius);
        }
    }

    private float[] degreeToDirection(int degree){
        return new float[]{(float)Math.cos(Math.toRadians(degree)),(float)-Math.sin(Math.toRadians(degree))};
    }

}
