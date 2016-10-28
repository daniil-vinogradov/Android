package ru.vino.weather.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

public class GuiUtils {

    public interface RevealAnimationListener {
        void onAnimationStart();

        void onAnimationEnd();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(final View view, int cx, int cy,
                                         float startRadius, int duration) {
        float finalRadius = (float) Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        anim.setDuration(duration);
        anim.setInterpolator(new FastOutLinearInInterpolator());
        view.setVisibility(View.VISIBLE);
        anim.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(final View view, int cx, int cy,
                                         float startRadius, int duration, RevealAnimationListener listener) {
        float finalRadius = (float) Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        anim.setDuration(duration);
        anim.setInterpolator(new FastOutLinearInInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onAnimationEnd();
            }
        });
        view.setVisibility(View.VISIBLE);
        anim.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealHide(final View view, int cx, int cy,
                                         float startRadius, float finalRadius,
                                         int duration, RevealAnimationListener listener) {
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius);
        anim.setInterpolator(new FastOutLinearInInterpolator());
        anim.setDuration(duration);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                listener.onAnimationEnd();
            }
        });
        anim.start();
    }

    public static void animateSlide(ViewGroup viewGroup, int startDelay, int duration,
                                    @Nullable RevealAnimationListener listener) {
        TransitionManager.beginDelayedTransition(viewGroup, new TransitionSet()
                .addTransition(new Fade().setStartDelay(startDelay))
                .addTransition(new Slide())
                .setDuration(duration)
                .setStartDelay(startDelay)
                .addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        if (listener != null)
                            listener.onAnimationEnd();
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                }));
    }

    public static Drawable getDrawable(int icon, Context context) {
        // AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        String name = "ic_" + String.valueOf(icon);
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return AppCompatResources.getDrawable(context, resourceId);
    }


}
