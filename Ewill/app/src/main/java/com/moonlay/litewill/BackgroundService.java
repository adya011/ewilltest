package com.moonlay.litewill;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.moonlay.litewill.api.ApiInterface;
import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.model.OAuthToken;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nandana.samudera on 4/30/2018.
 */

public class BackgroundService extends IntentService {
    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.TOKEN_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<OAuthToken> call = client.requestToken("mobile.droid", "Xv5LpK8K15", "client_credentials", "ewill");

        try{
            Response<OAuthToken> response = call.execute();
            Log.d("mydebug", "Sukses");
        }catch (IOException e){
            e.printStackTrace();
            Log.d("mydebug", "Failed");
        }
    }
}
