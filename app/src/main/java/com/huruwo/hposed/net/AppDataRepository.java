package com.huruwo.hposed.net;


import com.blankj.utilcode.util.ToastUtils;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.Response;


/**
 * Created by dxx on 2017/11/8.
 */

public class AppDataRepository {


    AppApiService service;

    public AppDataRepository() {
        service = ApiClient.create(AppApiService.class);
    }

    public static Response<String> getData(String url) {
        try {
            return ApiClient.create(AppApiService.class).get(url).execute();
        } catch (IOException e) {
            ToastUtils.showLong("访问错误:"+e.getMessage());
            return null;
        }
    }

    public static Response<String> getWithHeader(String url,Map<String,String> headMap){
        try {
            return ApiClient.create(AppApiService.class).getWithHeader(url,headMap).execute();
        } catch (IOException e) {
            ToastUtils.showLong("访问错误:"+e.getMessage());
            return null;
        }
    }

    public static Observable<String> getRxData(String url, Map<String, String> headers, Map<String, String> maps, Observer s) {
        SubscriberManager<String> subscriberManager = new SubscriberManager();
        Observable<String> o = ApiClient.create(AppApiService.class).rx_get(url, headers, maps);
        subscriberManager.toSubscribe(o, s);
        return o;
    }


    public static Observable<String> postRxData(String url, Map<String, String> headers, Map<String, String> maps, Observer s) {
        SubscriberManager<String> subscriberManager = new SubscriberManager();
        Observable<String> o = ApiClient.create(AppApiService.class).rx_post(url, headers, maps);
        subscriberManager.toSubscribe(o, s);
        return o;
    }

    public static Response<String> postBodyData(String url, String json) {
        try {
            return ApiClient.create(AppApiService.class).post(url, json).execute();
        } catch (IOException e) {
            ToastUtils.showLong("访问错误:"+e.getMessage());
            return null;
        }
    }

    public static Response<String> postWithHeader(String url,String json,Map<String,String> headMap){
        try {
            return ApiClient.create(AppApiService.class).postWithHeader(url,json,headMap).execute();
        } catch (IOException e) {
            ToastUtils.showLong("访问错误:"+e.getMessage());
            return null;
        }
    }
}
