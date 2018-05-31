package com.odbpo.fenggou.retrofit2demo.net.common;

import com.odbpo.fenggou.retrofit2demo.util.AppToast;

import rx.Subscriber;

public abstract class AbsAPICallback<T> extends Subscriber<T> {

    protected AbsAPICallback() {

    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        //打印异常信息（上线关闭）
        AppToast.show("接口异常：" + e.getMessage());
        System.out.println("zc_exception:" + e.getMessage());

        //token过期
        if (e.getMessage().equals("HTTP 401 Unauthorized")) {
            AppToast.show("token过期");
        }

        onCompleted();
    }

    protected abstract void onDone(T t);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        onDone(t);
    }
}
