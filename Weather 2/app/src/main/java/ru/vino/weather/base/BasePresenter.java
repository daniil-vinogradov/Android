package ru.vino.weather.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> {

    private WeakReference<V> view;

    public void attachView(V view) {
        this.view = new WeakReference<V>(view);
    }

    public V getView() {
        return view == null ? null : view.get();
    }

    public boolean isViewAttached() {
        return view != null && view.get() != null;
    }

    public void detachView() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }

}
