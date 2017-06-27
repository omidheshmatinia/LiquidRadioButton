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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.Random;

class ExplosionAnimator extends ValueAnimator {

    static long DEFAULT_DURATION = 0x400;
    private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateInterpolator(0.6f);
    private static final float END_VALUE = 1.4f;
    private static final float X = Utils.dp2Px(5);
    private static final float Y = Utils.dp2Px(20);
    private static final float V = Utils.dp2Px(2);
    private static final float W = Utils.dp2Px(1);
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
           mParticles[i] = generateParticle(random);
        }
        mContainer = container;
        setFloatValues(0f, END_VALUE);
        setInterpolator(DEFAULT_INTERPOLATOR);
        setDuration(DEFAULT_DURATION);
    }

    private Particle generateParticle(Random random) {
        Particle particle = new Particle();
        particle.radius = V;
        if (random.nextFloat() < 0.2f) {
            particle.baseRadius = V + ((X - V) * random.nextFloat());
        } else {
            particle.baseRadius = W + ((V - W) * random.nextFloat());
        }
        float nextFloat = random.nextFloat();
        particle.top = mBound.height() * ((0.18f * random.nextFloat()) + 0.2f);
        particle.top = nextFloat < 0.2f ? particle.top : particle.top + ((particle.top * 0.2f) * random.nextFloat());
        particle.bottom = (mBound.height() * (random.nextFloat() - 0.5f)) * 1.8f;
        float f = nextFloat < 0.2f ? particle.bottom : nextFloat < 0.8f ? particle.bottom * 0.6f : particle.bottom * 0.3f;
        particle.bottom = f;
        particle.mag = 4.0f * particle.top / particle.bottom;
        particle.neg = (-particle.mag) / particle.bottom;
        f = mBound.centerX() + (Y * (random.nextFloat() - 0.5f));
        particle.baseCx = f;
        particle.cx = f;
        f = mBound.centerY() + (Y * (random.nextFloat() - 0.5f));
        particle.baseCy = f;
        particle.cy = f;
        particle.life = END_VALUE / 10 * random.nextFloat();
        particle.overflow = 0.4f * random.nextFloat();
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
//        mContainer.invalidate();
        mContainer.invalidateSelf();
        return true;
    }

    @Override
    public void start() {
        super.start();
//        mContainer.invalidate(mBound);
        mContainer.invalidateSelf();
    }

    private class Particle {
        float cx;
        float cy;
        float radius;
        float baseCx;
        float baseCy;
        float baseRadius;
        float top;
        float bottom;
        float mag;
        float neg;
        float life;
        float overflow;


        public void advance(float factor) {
            float f = 0f;
            float normalization = factor / END_VALUE;

            normalization = (normalization - life) / (1f - life - overflow);
            float f2 = normalization * END_VALUE;
//            if (normalization >= 0.7f) {
//                f = (normalization - 0.7f) / 0.3f;
//            }
            f = bottom * f2;
            cx = baseCx + f;
            cy = (float) (baseCy - this.neg * Math.pow(f, 2.0)) - f * mag;
            radius = V + (baseRadius - V) * f2;
        }
    }
}
