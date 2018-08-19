package com.moonlay.litewill.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandana.samudera on 4/25/2018.
 */

public class OAuthToken {
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("expires_in")
    @Expose
    private int expiresIn;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
