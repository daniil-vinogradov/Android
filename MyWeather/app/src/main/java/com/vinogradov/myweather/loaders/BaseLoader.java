package com.vinogradov.myweather.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.vinogradov.myweather.rest.response.RequestResult;
import com.vinogradov.myweather.rest.response.BaseResponse;

import java.io.IOException;

/**
 * @author d.vinogradov
 */
public abstract class BaseLoader extends AsyncTaskLoader<BaseResponse> {

    private Context context;

    public BaseLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public BaseResponse loadInBackground() {
        try {
            BaseResponse response = null;
            try {
                response = apiCall();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (response.getRequestResult() == RequestResult.SUCCESS) {
                response.save(getContext());
                response.getRealm().close();
                onSuccess();
            } else {
                onError();
            }
            return response;
        } catch (IOException e) {
            onError();
            return new BaseResponse(context);
        }
    }

    public Context getContext(){
        return context;
    }

    protected void onSuccess() {
    }

    protected void onError() {
    }

    protected abstract BaseResponse apiCall() throws IOException, ClassNotFoundException;
}