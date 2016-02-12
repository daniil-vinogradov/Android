package com.vinogradov.myweather.rest.response;

import android.content.Context;

import io.realm.Realm;

/**
 * @author d.vinogradov
 */
public class BaseResponse {

    private Object answer;

    private RequestResult requestResult;

    private Realm realm;

    public BaseResponse(Context context) {
        requestResult = RequestResult.ERROR;
        realm = Realm.getInstance(context);
    }

    public RequestResult getRequestResult() {
        return requestResult;
    }

    public Realm getRealm() {
        return realm;
    }

    public BaseResponse setRequestResult(RequestResult requestResult) {
        this.requestResult = requestResult;
        return this;
    }

    public <T> T getTypedAnswer() {
        if (answer == null) {
            return null;
        }
        //noinspection unchecked
        return (T) answer;
    }

    public BaseResponse setAnswer(Object answer) {
        this.answer = answer;
        return this;
    }

    public void save(Context context) {
    }
}
