package com.moonlay.litewill.api;

import android.util.Log;

import com.moonlay.litewill.config.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nandana.samudera on 3/20/2018.
 */

public class RestProvider {
    public static final String BASE_URL = "http://11.11.5.146:5541/api/";
    private static Retrofit tokenRest = null;
    private static Retrofit registerRest = null;
    private static Retrofit retrofit = null;

    public static Retrofit getToken() {
        if(tokenRest == null){
            tokenRest = new Retrofit.Builder()
                    .baseUrl(Constants.TOKEN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("mydebug_retrofit", "retrofit token");
        }
        return tokenRest;
    }

    public static Retrofit getClient() {
        if(registerRest == null){
            registerRest = new Retrofit.Builder()
                    .baseUrl(Constants.IDENTITY_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("mydebug_retrofit", "retrofit get rest");
        }
        return registerRest;
    }

    public static Retrofit getClient2() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRestLogin(){
        if(registerRest == null){
            registerRest = new Retrofit.Builder()
                    .baseUrl(Constants.LOGIN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("mydebug_retrofit", "Retrofit get rest");
        }
        return registerRest;
    }
}
