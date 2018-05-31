package com.odbpo.fenggou.retrofit2demo.net.common.base;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author: zc
 * @Time: 2018/3/6 13:44
 * @Desc:
 */
public abstract class UseCase<T> {

    protected String[] params;

    protected Map map;

    protected List<MultipartBody.Part> parts = new ArrayList<>();

    protected abstract Observable<T> buildUseCaseObservable();

    public Observable<T> execute(RxAppCompatActivity activity) {
        return this.buildUseCaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.<T>bindToLifecycle());
    }

    public void setParams(String... params) {
        this.params = params;
    }

    public void setFormParams(Map map) {
        this.map = map;
    }

    public void setMulitParams(List<MultipartBody.Part> parts){
        this.parts = parts;
    }

}
