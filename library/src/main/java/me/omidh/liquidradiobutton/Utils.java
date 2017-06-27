package me.omidh.liquidradiobutton;

import android.content.res.Resources;

/**
 * Created by Omid Heshmatinia on 6/20/2017.
 */

final class Utils {

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    private Utils() {
    }

    static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

//    public static int dpToPx(Context context, int dp){
//        return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
//    }
}
