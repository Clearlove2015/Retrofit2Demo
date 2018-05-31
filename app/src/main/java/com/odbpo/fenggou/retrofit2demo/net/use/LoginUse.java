package com.odbpo.fenggou.retrofit2demo.net.use;

import com.odbpo.fenggou.retrofit2demo.net.common.Net;
import com.odbpo.fenggou.retrofit2demo.net.common.UrlRoot;
import com.odbpo.fenggou.retrofit2demo.net.common.base.UseCase;
import com.odbpo.fenggou.retrofit2demo.net.common.netutil.RetrofitUtil;

import rx.Observable;

/**
 * @author: zc
 * @Time: 2018/5/31 14:03
 * @Desc:
 */
public class LoginUse extends UseCase<String> {
    @Override
    protected Observable<String> buildUseCaseObservable() {
        return Net.getApiClient(UrlRoot.API_PATH).login(RetrofitUtil.toFormDataRequestBody(map));
    }
}
