package com.odbpo.fenggou.retrofit2demo.net.common.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author: zc
 * @Time: 2018/5/31 10:49
 * @Desc:
 */
public class StringConverter implements Converter<ResponseBody, String> {

    public static StringConverter INSTANCE = new StringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }

    }
}
