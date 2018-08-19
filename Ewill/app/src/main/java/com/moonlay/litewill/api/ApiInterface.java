package com.moonlay.litewill.api;

import com.moonlay.litewill.model.AddShared;
import com.moonlay.litewill.model.ChangeEmail;
import com.moonlay.litewill.model.ChangeEmailResponse;
import com.moonlay.litewill.model.ChangePassword;
import com.moonlay.litewill.model.ChangePasswordResponse;
import com.moonlay.litewill.model.ChangePhone;
import com.moonlay.litewill.model.ChangePin;
import com.moonlay.litewill.model.ChangePinResponse;
import com.moonlay.litewill.model.DeleteShared;
import com.moonlay.litewill.model.DeleteWill;
import com.moonlay.litewill.model.Document;
import com.moonlay.litewill.model.MemberInfoResponse;
import com.moonlay.litewill.model.MyWill;
import com.moonlay.litewill.model.ForgotPassword;
import com.moonlay.litewill.model.ForgotPasswordResponse;
import com.moonlay.litewill.model.ForgotUsername;
import com.moonlay.litewill.model.ForgotUsernameResponse;
import com.moonlay.litewill.model.LoginUser;
import com.moonlay.litewill.model.LoginUserResponse;
import com.moonlay.litewill.model.Logout;
import com.moonlay.litewill.model.LogoutResponse;
import com.moonlay.litewill.model.MyWillDetailResponse;
import com.moonlay.litewill.model.MyWillResponse;
import com.moonlay.litewill.model.OAuthToken;
import com.moonlay.litewill.model.ProductListResponse;
import com.moonlay.litewill.model.Questions;
import com.moonlay.litewill.model.RegisterUserResponse;
import com.moonlay.litewill.model.RegisterUser;
import com.moonlay.litewill.model.ResetPassword;
import com.moonlay.litewill.model.ResetPasswordResponse;
import com.moonlay.litewill.model.SecurityQuestionResponse;
import com.moonlay.litewill.model.StandardResponse;
import com.moonlay.litewill.model.UpgradeSearcher;
import com.moonlay.litewill.model.UpdateShared;
import com.moonlay.litewill.model.UpdateWill;
import com.moonlay.litewill.model.UpdateWillAddress;
import com.moonlay.litewill.model.UpgradeMaker;
import com.moonlay.litewill.model.UpgradeMakerResponse;
import com.moonlay.litewill.model.ValidatePin;
import com.moonlay.litewill.model.ValidateResponse;
import com.moonlay.litewill.model.VerifyChangePhone;
import com.moonlay.litewill.model.VerifyEmail;
import com.moonlay.litewill.model.VerifyEmailChange;
import com.moonlay.litewill.model.VerifyEmailResponse;
import com.moonlay.litewill.model.VerifyPhone;
import com.moonlay.litewill.model.WillNotify;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nandana.samudera on 3/16/2018.
 */

public interface ApiInterface {
    /*@POST("login_register/")
    Call<ServerResponse> operation(@Body ServerRequest request);*/

    /*@Headers({
            "x-requestid: 3d28d40d-f7c7-45fe-8894-a3a523272a73",
            "x-agent: mobile",
            "x-version: 1.0",
            "Content-Type: application/json"
    })
    @FormUrlEncoded
    @POST("api/account/register")
    Call<ServerResponse> postUser(@Field("pin") String pin,
                                  @Field("userName") String userName,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("mobileNumber") String mobileNumber,
                                  @Field("securityQuestion") String securityQuestion,
                                  @Field("securityAnswer") String securityAnswer,
                                  @Field("countryCode") String countryCode);*/

    @FormUrlEncoded
    @POST("connect/token")
    Call<OAuthToken> requestToken(@Field("client_id") String clientId,
                                  @Field("client_secret") String clientSec,
                                  @Field("grant_type") String grantType,
                                  @Field("scope") String scope);

    @FormUrlEncoded
    @POST("connect/token")
    Observable<OAuthToken> requestTokenRx(@Field("client_id") String clientId,
                                          @Field("client_secret") String clientSec,
                                          @Field("grant_type") String grantType,
                                          @Field("scope") String scope);

    @FormUrlEncoded
    @POST("connect/token")
    Call<OAuthToken> refreshToken(@Header("Authorization") String authorization,
                                  @Field("client_id") String clientId,
                                  @Field("client_secret") String clientSec,
                                  @Field("grant_type") String grantType,
                                  @Field("refresh_token") String refreshToken); //refresh token dari login

    @POST("account/register")
    Call<RegisterUserResponse> registerUser(@Header("x-requestid") String reqId,
                                            @Header("x-agent") String agent,
                                            @Header("x-version") String version,
                                            @Header("Authorization") String authToken,
                                            @Body RegisterUser user);

    @POST("account/login")
    Call<LoginUserResponse> loginUser(@Header("x-requestid") String reqId,
                                      @Header("x-agent") String agent,
                                      @Header("x-version") String version,
                                      @Header("Authorization") String authToken,
                                      @Body LoginUser loginUser);

    @POST("account/login")
    Observable<LoginUserResponse> loginUserRx(@Header("x-requestid") String reqId,
                                              @Header("x-agent") String agent,
                                              @Header("x-version") String version,
                                              @Header("Authorization") String authToken,
                                              @Body LoginUser loginUser);

    @POST("account/confirm-email")
    Call<VerifyEmailResponse> verifyEmail(@Header("x-requestid") String reqId,
                                          @Header("x-agent") String agent,
                                          @Header("x-version") String version,
                                          @Header("Authorization") String authToken,
                                          @Body VerifyEmail verify);

    @POST("account/confirm-email")
    Call<VerifyEmailResponse> verifyEmailChange(@Header("x-requestid") String reqId,
                                                @Header("x-agent") String agent,
                                                @Header("x-version") String version,
                                                @Header("Authorization") String authToken,
                                                @Body VerifyEmailChange verify);

    @POST("account/confirm-phone-number")
    Call<StandardResponse> verifyPhone(@Header("x-requestid") String reqId,
                                       @Header("x-agent") String agent,
                                       @Header("x-version") String version,
                                       @Header("Authorization") String authToken,
                                       @Body VerifyPhone verifyPhone);

    @POST("account/confirm-change-phone-number")
    Call<StandardResponse> verifyChangePhone(@Header("x-requestid") String reqId,
                                             @Header("x-agent") String agent,
                                             @Header("x-version") String version,
                                             @Header("Authorization") String authToken,
                                             @Body VerifyChangePhone verifyChangePhone);

    @POST("account/forgot-username")
    Call<ForgotUsernameResponse> forgotUsername(@Header("x-requestid") String reqId,
                                                @Header("x-agent") String agent,
                                                @Header("x-version") String version,
                                                @Header("Authorization") String authToken,
                                                @Body ForgotUsername forgotUsername);

    @POST("account/forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(@Header("x-requestid") String reqId,
                                                @Header("x-agent") String agent,
                                                @Header("x-version") String version,
                                                @Header("Authorization") String authToken,
                                                @Body ForgotPassword forgotPassword);

    @POST("account/reset-password")
    Call<ResetPasswordResponse> resetPassword(@Header("x-requestid") String reqId,
                                              @Header("x-agent") String agent,
                                              @Header("x-version") String version,
                                              @Header("Authorization") String authToken,
                                              @Body ResetPassword resetPassword);

    @POST("account/change-password")
    Call<ChangePasswordResponse> changePassword(@Header("x-requestid") String reqId,
                                                @Header("x-agent") String agent,
                                                @Header("x-version") String version,
                                                @Header("Authorization") String authToken,
                                                @Body ChangePassword changePassword);

    @POST("account/change-pin")
    Call<ChangePinResponse> changePin(@Header("x-requestid") String reqId,
                                      @Header("x-agent") String agent,
                                      @Header("x-version") String version,
                                      @Header("Authorization") String authToken,
                                      @Body ChangePin changePin);


    @POST("account/change-email")
    Call<ChangeEmailResponse> changeEmail(@Header("x-requestid") String reqId,
                                          @Header("x-agent") String agent,
                                          @Header("x-version") String version,
                                          @Header("Authorization") String authToken,
                                          @Body ChangeEmail changeEmail);

    @POST("account/change-phone")
    Call<StandardResponse> changePhone(@Header("x-requestid") String reqId,
                                       @Header("x-agent") String agent,
                                       @Header("x-version") String version,
                                       @Header("Authorization") String authToken,
                                       @Body ChangePhone changePhone);

    @GET("account/security-question")
    Call<SecurityQuestionResponse> securityQuestion(@Header("x-requestid") String reqId,
                                                    @Header("x-agent") String agent,
                                                    @Header("x-version") String version,
                                                    @Header("Authorization") String authToken,
                                                    @Query("userName") String userName);

    @POST("account/validate")
    Call<ValidateResponse> question(@Header("x-requestid") String reqId,
                                    @Header("x-agent") String agent,
                                    @Header("x-version") String version,
                                    @Header("Authorization") String authToken,
                                    @Body Questions questions);

    @POST("account/validate")
    Call<ValidateResponse> validatePin(@Header("x-requestid") String reqId,
                                       @Header("x-agent") String agent,
                                       @Header("x-version") String version,
                                       @Header("Authorization") String authToken,
                                       @Body ValidatePin validatePin);

    @POST("account/logout")
    Call<LogoutResponse> logout(@Header("x-requestid") String reqId,
                                @Header("x-agent") String agent,
                                @Header("x-version") String version,
                                @Header("Authorization") String authToken,
                                @Body Logout logout);

    @GET("mobi/members/info")
    Call<MemberInfoResponse> memberInfo(@Header("x-requestid") String reqId,
                                        @Header("x-agent") String agent,
                                        @Header("x-version") String version,
                                        @Header("Authorization") String authToken);

    @POST("mobi/members/upgrade-maker")
    Call<UpgradeMakerResponse> upgradeMaker(@Header("x-requestid") String reqId,
                                            @Header("x-agent") String agent,
                                            @Header("x-version") String version,
                                            @Header("Authorization") String authToken,
                                            @Body UpgradeMaker upgradeMaker);

    @POST("mobi/members/upgrade-searcher")
    Call<StandardResponse> upgradeSearcher(@Header("x-requestid") String reqId,
                                           @Header("x-agent") String agent,
                                           @Header("x-version") String version,
                                           @Header("Authorization") String authToken,
                                           @Body UpgradeSearcher searcher);

    @POST("wills/create")
    Call<StandardResponse> createWill(@Header("x-requestid") String reqId,
                                      @Header("x-agent") String agent,
                                      @Header("x-version") String version,
                                      @Header("Authorization") String authToken,
                                      @Body MyWill myWill);

    @GET("wills")
    Call<MyWillResponse> myWillList(@Header("x-requestid") String reqId,
                                    @Header("x-agent") String agent,
                                    @Header("x-version") String version,
                                    @Header("Authorization") String authToken);

    @GET("wills/{id}")
    Call<MyWillDetailResponse> myWillDetail(@Header("x-requestid") String reqId,
                                            @Header("x-agent") String agent,
                                            @Header("x-version") String version,
                                            @Header("Authorization") String authToken,
                                            @Path("id") int id);

    @POST("wills/delete-will")
    Call<StandardResponse> deleteWill(@Header("x-requestid") String reqId,
                                      @Header("x-agent") String agent,
                                      @Header("x-version") String version,
                                      @Header("Authorization") String authToken,
                                      @Body DeleteWill deleteWill);

    @POST("wills/update-will")
    Call<StandardResponse> updateWill(@Header("x-requestid") String reqId,
                                      @Header("x-agent") String agent,
                                      @Header("x-version") String version,
                                      @Header("Authorization") String authToken,
                                      @Body UpdateWill updateWill);

    @POST("wills/update-address")
    Call<StandardResponse> updateWillAddress(@Header("x-requestid") String reqId,
                                             @Header("x-agent") String agent,
                                             @Header("x-version") String version,
                                             @Header("Authorization") String authToken,
                                             @Body UpdateWillAddress updateWillAddress);

    @POST("wills/update-shared")
    Call<StandardResponse> updateWillShared(@Header("x-requestid") String reqId,
                                            @Header("x-agent") String agent,
                                            @Header("x-version") String version,
                                            @Header("Authorization") String authToken,
                                            @Body UpdateShared shared);

    @POST("wills/update-document")
    Call<StandardResponse> updateWillDocument(@Header("x-requestid") String reqId,
                                              @Header("x-agent") String agent,
                                              @Header("x-version") String version,
                                              @Header("Authorization") String authToken,
                                              @Body List<Document> documents);

    @GET("wills/search")
    Call<MyWillResponse> searchWill(@Header("x-requestid") String reqId,
                                    @Header("x-agent") String agent,
                                    @Header("x-version") String version,
                                    @Header("Authorization") String authToken,
                                    @Query("name") String name,
                                    @Query("memberTypeId") int memberTypeId);

    @GET("wills/shared-will")
    Call<MyWillResponse> sharedWill(@Header("x-requestid") String reqId,
                                    @Header("x-agent") String agent,
                                    @Header("x-version") String version,
                                    @Header("Authorization") String authToken,
                                    @Query("email") String email);

    @POST("wills/add-shared")
    Call<StandardResponse> addShared(@Header("x-requestid") String reqId,
                                     @Header("x-agent") String agent,
                                     @Header("x-version") String version,
                                     @Header("Authorization") String authToken,
                                     @Body AddShared addShareds);

    @POST("wills/delete-shared")
    Call<StandardResponse> deleteShared(@Header("x-requestid") String reqId,
                                        @Header("x-agent") String agent,
                                        @Header("x-version") String version,
                                        @Header("Authorization") String authToken,
                                        @Body DeleteShared deleteShared);

    @POST("wills/will-notify")
    Call<StandardResponse> willNotify(@Header("x-requestid") String reqId,
                                      @Header("x-agent") String agent,
                                      @Header("x-version") String version,
                                      @Header("Authorization") String authToken,
                                      @Body WillNotify willNotify);

    @GET("products/product-list")
    Call<ProductListResponse> productList(@Header("x-requestid") String reqId,
                                          @Header("x-agent") String agent,
                                          @Header("x-version") String version,
                                          @Header("Authorization") String authToken);
}