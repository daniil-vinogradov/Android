package ru.vinogradov.translator.models;


import rx.Observable;

public interface IModel {
    Observable<TranslatorResponse> getResponse(String text, String lang);
}
