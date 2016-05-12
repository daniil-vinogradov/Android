package ru.vinogradov.translator.views;


import rx.Observable;

public interface IView {
    Observable<String> textChanged ();
    void showTranslation(String text);
}
