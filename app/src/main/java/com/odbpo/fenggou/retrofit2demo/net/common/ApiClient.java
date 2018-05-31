package com.odbpo.fenggou.retrofit2demo.net.common;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * @author: zc
 * @Time: 2018/3/6 10:45
 * @Desc:
 */
public interface ApiClient {
    @POST(UriMethod.LOGIN)
    Observable<String> login(@Body RequestBody requestBody);

    @GET(UriMethod.CUSTOMERS)
    Observable<String> getCustomer();

    @Multipart
    @POST(UriMethod.UPLOAD_IMG)
    Observable<String> upLoadImgsRemote(@Part List<MultipartBody.Part> parts);

    @GET(UriMethod.GET_BANNER)
    Observable<String> getBanner();

}
