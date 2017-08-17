package com.sy.searchengine.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by yanshi on 2017/7/6.
 */
public class Util {

    /**
     * 将UTF-8的字符串转换为UTF-32的字符串
     * @param body
     * @return
     */
    public String utf8_to_utf32(String body){
        String bodyUTF32=null;
        if(body!=null){
            try {
                bodyUTF32=new String(body.getBytes(),"UTF-32");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return bodyUTF32;
    }
}
