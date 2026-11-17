package com.crammr.studyflashcard.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class FlipCardAnimationHelper {

    public static void flipCard(View frontView, View backView, boolean showBack) {
        float distance = 8000;
        float scale = frontView.getResources().getDisplayMetrics().density * distance;
        frontView.setCameraDistance(scale);
        backView.setCameraDistance(scale);

        final View visibleView = showBack ? frontView : backView;
        final View hiddenView = showBack ? backView : frontView;

        ObjectAnimator flipOut = ObjectAnimator.ofFloat(visibleView, "rotationY", 0f, 90f);
        flipOut.setDuration(180);
        flipOut.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator flipIn = ObjectAnimator.ofFloat(hiddenView, "rotationY", -90f, 0f);
        flipIn.setDuration(180);
        flipIn.setInterpolator(new AccelerateDecelerateInterpolator());

        flipOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                visibleView.setVisibility(View.GONE);
                hiddenView.setVisibility(View.VISIBLE);
                flipIn.start();
            }
        });

        flipOut.start();
    }
}
