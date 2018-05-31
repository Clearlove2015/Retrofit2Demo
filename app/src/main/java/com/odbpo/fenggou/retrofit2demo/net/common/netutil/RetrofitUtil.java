package com.odbpo.fenggou.retrofit2demo.net.common.netutil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by acer on 2017/3/6.
 */

public class RetrofitUtil {

    public static RequestBody toRequestBody(String body) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
    }

    public static RequestBody toFormDataRequestBody(Map map) {
        return getStringRequestBody(map);
    }

    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            // MultipartBody.Part和后端约定好Key，这里的partName是用image
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 添加文本请求体参数
     */
    @SuppressWarnings("rawtypes")
    public static RequestBody getStringRequestBody(Map<?, ?> stringBodyMap) {
        FormBody.Builder multipartBodyBuilder = new FormBody.Builder();
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
        /*解析文本请求体*/
        if ((stringBodyMap != null) && (stringBodyMap.size() > 0)) {
            Iterator iterator = stringBodyMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                multipartBodyBuilder.add(entry.getKey() + "", entry.getValue() + "");
            }
        }
        return multipartBodyBuilder.build();
    }
}


