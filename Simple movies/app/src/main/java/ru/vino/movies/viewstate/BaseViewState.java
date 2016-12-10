package ru.vino.movies.viewstate;


import java.io.Serializable;
import java.lang.ref.WeakReference;

public class BaseViewState<V, D extends Serializable> {

    public static final int NO_STATE = 0;
    public static final int STATE_SHOW_LOADING = 1;
    public static final int STATE_SHOW_PAGINATION_LOADING = 2;
    public static final int STATE_SHOW_CONTENT = 3;
    public static final int STATE_ERROR = 4;

    private WeakReference<V> view;
    private int currentViewState = NO_STATE;
    private D data;

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

    public int getCurrentViewState() {
        return currentViewState;
    }

    public void setCurrentViewState(int currentViewState) {
        this.currentViewState = currentViewState;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
