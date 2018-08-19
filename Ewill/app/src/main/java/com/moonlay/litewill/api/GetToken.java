package com.moonlay.litewill.api;

import android.util.Log;

import com.moonlay.litewill.config.Constants;
import com.moonlay.litewill.model.OAuthToken;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nandana.samudera on 5/11/2018.
 */

public class GetToken {
    public static String requestToken(){
        ApiInterface apiInterface = RestProvider.getToken().create(ApiInterface.class);
        String token = null;

        Call<OAuthToken> call = apiInterface.requestToken(Constants.HEADER_CLIENT_ID, Constants.HEADER_CLIENT_SECURITY,
                Constants.HEADER_GRANT_TYPE, Constants.HEADER_SCOPE);

        try {
            Response<OAuthToken> resp = call.execute();
            //Log.d("mydebug_token", "token resp code: " + resp.code());
            token = resp.body().getTokenType().toString() + " " + resp.body().getAccessToken().toString();
            //Log.d("mydebug_token", "token: " + token);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    public static String refreshToken(){
        ApiInterface apiInterface = RestProvider.getToken().create(ApiInterface.class);
        String token = null;

        Call<OAuthToken> call = apiInterface.refreshToken("access token yang disimpen", Constants.HEADER_CLIENT_ID, Constants.HEADER_CLIENT_SECURITY,
                Constants.HEADER_GRANT_TYPE_REFRESH, "refresh token yang disimpen");

        try {
            Response<OAuthToken> resp = call.execute();
            Log.d("mydebug_token", "token resp code: " + resp.code());
            token = resp.body().getTokenType().toString() + " " + resp.body().getAccessToken().toString();
            Log.d("mydebug_token", "token: " + token);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

}
