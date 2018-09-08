package com.team.s.sapp.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.team.s.sapp.R;
import com.team.s.sapp.inf.CallBackAnimation;

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

    public static void SlideFadeInRight(final View[] view, Context context, int duration, final CallBackAnimation callBack) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_fade_right_in);
        animation.setDuration(duration);
        for (int i = 0; i < view.length; i++) {
            view[i].setVisibility(View.VISIBLE);
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
            view[i].startAnimation(animation);
        }
    }

    public static void SlideFadeOutRight(final View[] view, Context context, int duration, final CallBackAnimation callBack) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_fade_right_out);
        animation.setDuration(duration);
        for (int i = 0; i < view.length; i++) {
            int finalI = i;
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view[finalI].setVisibility(View.GONE);
                    if (callBack != null)
                        callBack.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            view[i].startAnimation(animation);
        }
    }

    public static void startMyAnim(View[] view, Context context, int duration, int reSourceID, final CallBackAnimation callBack) {

        for (int i = 0; i < view.length; i++) {
            Animation animation = AnimationUtils.loadAnimation(context, reSourceID);
            animation.setDuration(duration);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (callBack != null) {
                        callBack.onAnimationEnd();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view[i].startAnimation(animation);
        }
    }


    public static void goneGroupViewAnim(View[] views, View parent) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
            if (i == views.length - 1) {
                parent.setVisibility(View.GONE);
            }
        }
    }

    public static void visibleGroupViewAnim(View[] views, View parent) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
            if (i == views.length - 1) {
                parent.setVisibility(View.VISIBLE);
            }
        }
    }

//    public interface CallBackAnimation {
//
//        void onAnimationEnd();
//    }
}
