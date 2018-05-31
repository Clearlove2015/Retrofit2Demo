package com.odbpo.fenggou.retrofit2demo.net.common.netutil;

import com.odbpo.fenggou.retrofit2demo.util.ShareKey;
import com.odbpo.fenggou.retrofit2demo.util.SpUtil;

/**
 * @author: zc
 * @Time: 2018/3/6 14:38
 * @Desc:
 */
public class Auth {

    public static String getToken(){
        return SpUtil.getInstance().readStr(ShareKey.TOKEN);
    }
}
