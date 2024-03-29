package com.n16.qltv.utils;

import com.n16.qltv.model.Publisher;

public class StrProcessor {
    public static String getStrCategoryResult(Publisher publisher) {
        return String.format("Có phải bạn đang tìm: %s - Tại: %s",
                publisher.getPublisherName().trim(), publisher.getPublisherAddress());
    }
    public static String formatWebsite(String web) {
        int _1stBlockLen = 0; final int MAX_SPLIT_LEN = 3;
        StringBuffer sb = new StringBuffer(web);
        String[] testPattern = web.split("://");
        _1stBlockLen = testPattern[0].length();
        if(_1stBlockLen != 0) {
            sb.replace(0, MAX_SPLIT_LEN + _1stBlockLen, "");

            return sb.toString();
        }

        return web;
    }
}
