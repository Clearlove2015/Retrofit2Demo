package com.odbpo.fenggou.retrofit2demo.net.common;

import com.odbpo.fenggou.retrofit2demo.net.common.converter.StringConverterFactory;
import com.odbpo.fenggou.retrofit2demo.net.common.netutil.Auth;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: zc
 * @Time: 2018/3/2 15:40
 * @Desc:
 */
public class Net {

    private static int DEFAULT_TIMEOUT = 60;

    //普通网络请求头
    static class HeadInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + Auth.getToken());
            Request request = builder.build();
            Response response = chain.proceed(request);
            String json = response.body().string();//获取返回json数据
            System.out.println("Interceptor-Head：" + json);
            return chain.proceed(request);//注意此处不能用response代替，否则报closed
        }
    }

    //文件上传请求头
    static class MultiHeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "multipart/form-data;charset=utf-8")
                    .addHeader("Authorization", "Bearer " + Auth.getToken())
                    .build();
            return chain.proceed(request);
        }
    }

    //门店请求头
    static class StoreHeadInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json");
            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    public static ApiClient getApiClient(String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HeadInterceptor())//常用请求头
                .addInterceptor(new MultiHeaderInterceptor())//文件上传请求头
                .addInterceptor(new StoreHeadInterceptor())//门店请求头
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(StringConverterFactory.create())//自定义解析String器
                .addConverterFactory(GsonConverterFactory.create())//使用Gson转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .client(okHttpClient)
                .build();
        ApiClient apiClient = retrofit.create(ApiClient.class);
        return apiClient;
    }

}
