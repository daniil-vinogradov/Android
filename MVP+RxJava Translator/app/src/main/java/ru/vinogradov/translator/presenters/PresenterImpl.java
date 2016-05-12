package ru.vinogradov.translator.presenters;


import android.util.Log;

import ru.vinogradov.translator.models.IModel;
import ru.vinogradov.translator.models.ModelImpl;
import ru.vinogradov.translator.views.IView;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterImpl implements IPresenter {

    private IView view;
    private IModel model;

    String lang = "ru-en";

    private Subscription subscription;

    public PresenterImpl(IView view) {
        this.view = view;
        this.model = new ModelImpl();
    }

    @Override
    public void initialize() {
        subscription = view
                .textChanged()
                .filter(s -> !s.isEmpty() && s.charAt(s.length() - 1) == ' ')
                .observeOn(Schedulers.io())
                .flatMap(text -> model.getResponse(text, lang))
                .map(translatorResponse -> translatorResponse.getText().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> view.showTranslation(text),
                        throwable -> Log.e("ERROR", throwable.toString()));
    }

    @Override
    public void destroy() {
        subscription.unsubscribe();
    }
}
