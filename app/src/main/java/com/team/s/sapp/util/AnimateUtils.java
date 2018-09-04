package com.team.s.sapp.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.team.s.sapp.R;

public class AnimateUtils {

    public static final int ANIM_DURATION_1 = 100;//0,1s
    public static final int ANIM_DURATION_2 = 200;//0,2s
    public static final int ANIM_DURATION_3 = 300;//0,3s
    public static final int ANIM_DURATION_4 = 400;//0,4s
    public static final int ANIM_DURATION_5 = 500;//0,5s

    public static void SlideInRight(View view, Context context, int duration, final CallBackAnimation callBack) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.setVisibility(View.VISIBLE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callBack != null)
                    callBack.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    public static void FadeOut(final View view, Context context, int duration, final CallBackAnimation callBack) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        animation.setDuration(duration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                if (callBack != null)
                    callBack.onAnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    public interface CallBackAnimation {

        void onAnimationEnd();
    }
}
