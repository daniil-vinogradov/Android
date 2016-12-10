package ru.vino.movies.base;


public interface PresenterProvider<T> {
    T getPresenter(String TAG);
}
