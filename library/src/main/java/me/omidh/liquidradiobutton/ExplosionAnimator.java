package me.omidh.liquidradiobutton;

/*
 * Copyright (C) 2015 tyrantgit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Random;

class ExplosionAnimator extends ValueAnimator {

    static long DEFAULT_DURATION = 0x400;
//    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator(0.6f);
    private static final float END_VALUE = 1.0f;
    private static final float X = Utils.dp2Px(5);
    private static final float Y = Utils.dp2Px(20);
    private static final float V = Utils.dp2Px(20);
    //    private static final float V =0;
//    private static final float W = 0;
    private static final float W = Utils.dp2Px(10);
    private Paint mPaint;
    private Particle[] mParticles;
    private Rect mBound;
    private RadioButtonDrawable mContainer;
    private int mColor;

    public ExplosionAnimator(RadioButtonDrawable container, Rect bound , int color, int number) {
        mPaint = new Paint();
        mBound = new Rect(bound);
        mColor=color;
        mParticles = new Particle[5];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < number; i++) {
            mParticles[i] = generateParticle(random,i,number);
        }
        mContainer = container;
        setFloatValues(0f, END_VALUE);
//        setInterpolator(DEFAULT_INTERPOLATOR);
        setDuration(DEFAULT_DURATION);
    }

    private int GAP = 10 ;
    private Particle generateParticle(Random random,int index,int max) {
        Particle particle = new Particle();
//        particle.radius = V;
//        if (random.nextFloat() < 0.2f) {
//            particle.baseRadius = V + ((X - V) * random.nextFloat());
//        } else {
//            particle.baseRadius = W + ((V - W) * random.nextFloat());
//        }
//        float nextFloat = random.nextFloat();
        int nextDegree = Math.max(random.nextInt(360/max) - 10,0) +((index*(360/max))+GAP);
        //
//        switch (index){
//            case 0: nextDegree = 70 ; break;
//            case 1: nextDegree = 140 ; break;
//            case 2: nextDegree = 180 ; break;
//            case 3: nextDegree = 280 ; break;
//            case 4: nextDegree = 320 ; break;
//        }
        //
        float[] magnifiers = degreeToDirection(nextDegree);
        particle.xMag = magnifiers[0];
        particle.yMag = magnifiers[1];

//        particle.top = mBound.height() * ((0.18f * random.nextFloat()) + 0.2f);
//        particle.top = nextFloat < 0.2f ? particle.top : particle.top + ((particle.top * 0.2f) * random.nextFloat());
//        particle.top = mBound.height();
//        particle.bottom = (mBound.height() * (random.nextFloat() - 0.5f)) * 1.8f;
//        float f = nextFloat < 0.2f ? particle.bottom : nextFloat < 0.8f ? particle.bottom * 0.6f : particle.bottom * 0.3f;
//        particle.bottom = f;
//        particle.mag = 4.0f * particle.top / particle.bottom;
//        particle.neg = (-particle.mag) / particle.bottom;
//        f = mBound.centerX() + (Y * (random.nextFloat() - 0.5f));
        particle.baseCenterX = mBound.centerX();
//        particle.cx = f;
        particle.cx = mBound.centerX();
//        f = mBound.centerY() + (Y * (random.nextFloat() - 0.5f));
//        particle.baseCenterY = f;
        particle.baseCenterY = mBound.centerY();
//        particle.cy = f;
        particle.cy = mBound.centerY();
//        particle.life = END_VALUE / 10 * random.nextFloat();
//        particle.overflow = 0.4f * random.nextFloat();
        particle.baseRadius = particle.radius = Utils.dp2Px(8);

        particle.baseX = particle.xMag > 0 ? (float)( particle.baseCenterX + 0.5 * Utils.dp2Px(8)) : (float)( particle.baseCenterX - 0.5 * Utils.dp2Px(8));
        particle.baseY = particle.yMag > 0 ? (float)( particle.baseCenterY + 0.5 * Utils.dp2Px(8)) : (float)( particle.baseCenterY - 0.5 * Utils.dp2Px(8));

        return particle;
    }

    public boolean draw(Canvas canvas) {
        if (!isStarted()) {
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
        float baseX ;
        float baseY;
        //        float baseRadius;
//        float top;
//        float bottom;
//        float mag;
//        float neg;
//        float life;
//        float overflow;
        float yMag;
        float xMag;


        public void advance(float factor) {
            Log.i("EA" , "factor = "+factor);
//            float f = 0f;
//            float normalization = factor / END_VALUE;

//            normalization = (normalization - life) / (1f - life - overflow);
//            float f2 = normalization * END_VALUE;
//            if (normalization >= 0.7f) {
//                f = (normalization - 0.7f) / 0.3f;
//            }
//            f = bottom * f2;
//            cx = baseCenterX + f;


//            cx = baseCenterX + ((xMag*factor)*radius );
//            cy = baseCenterY + ((yMag*factor)*radius );



//            @keyframes circle--inner__out {
//                0% {
//                        transform: scale(1);
//  }
//
//                80% {
//                        transform: scale(0.19);
//  }
//
//                99% {
//                        transform: scale(0.21);
//  }
//
//                100% {
//                        transform: scale(0);
//  }
//            }
            if(factor<=0.80){
                radius =  baseRadius * (1 - factor*0.98765f) ;
            } else if (factor>0.80 && factor<=0.99){
                radius = baseRadius * (0.19f  + factor*0.20202020f)  ;
            } if (factor>0.99)
                radius = baseRadius * (0.21f-(0.21f*factor));


            cx = baseCenterX + ((xMag*factor)*Utils.dp2Px(12) );
            cy = baseCenterY + ((yMag*factor)*Utils.dp2Px(12) );

//            cy = (float) (baseCenterY - this.neg * Math.pow(f, 2.0)) - f * mag;
//            cy= (float) baseCenterY- f*mag;
//            radius = V + (baseRadius - V) * f2;
        }
    }

    private float[] degreeToDirection(int degree){
        // 0 1 0
        // 90 0 -1
        // 180 -1 0
        // 270 0 1
        return new float[]{(float)Math.cos(Math.toRadians(degree)),(float)-Math.sin(Math.toRadians(degree))};
    }

}
