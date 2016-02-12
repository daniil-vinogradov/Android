package com.vinogradov.myweather.rest.response;

import android.content.Context;

import com.vinogradov.myweather.database.City;

import io.realm.Realm;

/**
 * @author d.vinogradov
 */
public class NewCityResponse extends BaseResponse {

    public NewCityResponse(Context context) {
        super(context);
    }

    @Override
    public void save(Context context) {

        City city = getTypedAnswer();
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(city);
        realm.commitTransaction();

    }
}
