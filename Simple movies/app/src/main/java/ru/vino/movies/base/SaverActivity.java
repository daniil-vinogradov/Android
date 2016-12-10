package ru.vino.movies.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class SaverActivity<C> extends AppCompatActivity {

    C component;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Object saver = getLastCustomNonConfigurationInstance();
        if (saver != null) {
            component = (C) saver;
        }

        if (component == null)
            component = setComponent();
        onInject(component);

        super.onCreate(savedInstanceState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return component;
    }

    public abstract C setComponent();

    public abstract void onInject(C component);

}
