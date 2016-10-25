package ru.vino.weather.widgets;


import android.annotation.TargetApi;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class PlaceDetailTransition extends TransitionSet {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlaceDetailTransition() {
        setOrdering(ORDERING_TOGETHER);
        setDuration(200);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform());
    }
}