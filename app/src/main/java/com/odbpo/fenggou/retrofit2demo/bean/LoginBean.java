package com.odbpo.fenggou.retrofit2demo.bean;

import java.io.Serializable;

/**
 * @author: zc
 * @Time: 2018/5/31 14:26
 * @Desc:
 */
public class LoginBean implements Serializable{
    /**
     * code : K-000000
     * message : 操作成功
     * data : {"token":"eyJhbGciOiJIUzI1NiIsImNhbGciOiJERUYifQ.eNqqViouTVKyUjI0NzY1NDEzMzM1U9JRykwsUbIyNDUyNzcxtzS3BAqkKFkB5XWU8hJzUzGUp1YUgJVbGIO0WNYCAAAA__8.HOH2WNmUnR7VF3eL8kvGXOE1ykCvFmipAFHnF7z-2dg"}
     */

    private String code;
    private String message;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiIsImNhbGciOiJERUYifQ.eNqqViouTVKyUjI0NzY1NDEzMzM1U9JRykwsUbIyNDUyNzcxtzS3BAqkKFkB5XWU8hJzUzGUp1YUgJVbGIO0WNYCAAAA__8.HOH2WNmUnR7VF3eL8kvGXOE1ykCvFmipAFHnF7z-2dg
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
