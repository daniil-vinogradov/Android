package ru.vino.movies.base;


import java.io.Serializable;

import ru.vino.movies.viewstate.BaseViewState;

public abstract class BasePresenter<V, D extends Serializable> {

    private BaseViewState<V, D> viewState;

    public void attachView(V view) {
        viewState.attachView(view);
    }

    public void detachView() {
        viewState.detachView();
    }

    public void setViewState(BaseViewState<V, D> viewState) {
        this.viewState = viewState;
    }

}