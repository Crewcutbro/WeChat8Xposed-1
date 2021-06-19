package com.huruwo.hposed.net;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by lw on 2018/1/23.
 */

public interface AppApiService {

    @GET()
    Observable<String> rx_get(
            @Url String url, @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Observable<String> rx_post(
            @Url String url,
            @HeaderMap Map<String, String> headers,
            @FieldMap Map<String, String> maps);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Call<String> post(@Url String url, @Body String data);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET()
    Call<String> get(@Url String url);

    @GET()
    Call<String> getWithHeader(@Url String url, @HeaderMap Map<String, String> headers);

    @POST()
    Call<String> postWithHeader(@Url String url, @Body String data,@HeaderMap Map<String, String> headers);

    @GET
    Call<ResponseBody> getCall(@Url String url);

}
