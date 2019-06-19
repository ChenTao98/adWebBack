package com.adweb.adweb.util;

import java.text.SimpleDateFormat;

public class StringUtil {
    public static boolean isEmpty(String string){
        return string==null||string.trim().equals("");
    }
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

}
