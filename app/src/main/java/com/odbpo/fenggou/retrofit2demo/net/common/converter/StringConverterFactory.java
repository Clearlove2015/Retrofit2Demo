package com.odbpo.fenggou.retrofit2demo.net.common.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author: zc
 * @Time: 2018/5/31 10:53
 * @Desc:
 */
public class StringConverterFactory extends Converter.Factory {

    public static StringConverterFactory INSTANCE = new StringConverterFactory();

    public static StringConverterFactory create(){
        return INSTANCE;
    }

    // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if(type == String.class){
            return StringConverter.INSTANCE;
        }

        //其它类型我们不处理，返回null就行
        return null;
    }
}
