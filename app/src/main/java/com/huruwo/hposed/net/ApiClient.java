package com.huruwo.hposed.net;



import com.huruwo.hposed.BuildConfig;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by dxx on 2017/11/8.
 */

public class ApiClient {

    public static String getApiHost() {
        return "http://39.104.73.49:9090";
    }

    public static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    /**
     * 获取Service
     * 配置缓存
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = getRetrofitInstance();
        return retrofit.create(clazz);
    }

    /**
     * 单例retrofit
     */
    private static Retrofit retrofitInstance;

    private static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            synchronized (ApiClient.class) {
                if (retrofitInstance == null) {
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(getApiHost() )
                            .addConverterFactory(ScalarsConverterFactory.create()) //String
                            //.addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    /**
     * 单例OkHttpClient
     */
    private static OkHttpClient okHttpClientInstance;

    public static OkHttpClient getOkHttpClientInstance() {


        if (okHttpClientInstance == null) {
            synchronized (ApiClient.class) {
                if (okHttpClientInstance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

                    builder.proxy(Proxy.NO_PROXY).retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .cookieJar(new CookieJar() {
                                @Override
                                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                                    cookieStore.put(httpUrl.host(), list);
                                }

                                @Override
                                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                                    List<Cookie> cookies = cookieStore.get(httpUrl.host());
                                    return cookies != null ? cookies : new ArrayList<Cookie>();
                                }
                            });
                    //builder.addInterceptor(new TokenInterceptor());


                    //两个忽略证书操作
                    //builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
                    //builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());

                    //-------------------------------缓存相关------------------------------------------
//                    if (cacheConfig != null) {
//                        if(cacheConfig.cacheType==CacheConfig.PRE_CACHE_AFTER_NETWORK) {
//                            builder.addInterceptor(new CacheInterceptor(cacheConfig.maxAge)).addNetworkInterceptor(new CacheNetworkInterceptor()).cache(cache);
//                        }
//                    }

                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
                    }
                    okHttpClientInstance = builder.build();
                }
            }
        }
        return okHttpClientInstance;
    }

}
