package me.omidh.liquidradiobutton;

import android.content.res.Resources;

final class Utils {

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    private Utils() {
    }

    static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    static boolean hasState(int[] states, int state){
        if(states == null)
            return false;

        for (int state1 : states)
            if (state1 == state)
                return true;

        return false;
    }
}
