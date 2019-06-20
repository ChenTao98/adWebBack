package com.adweb.adweb.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static String getTeacherId(HttpServletRequest httpServletRequest){
        return (String) httpServletRequest.getSession().getAttribute("openId");
//        return "1";
    }
}
