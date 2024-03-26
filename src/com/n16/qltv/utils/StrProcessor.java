package com.n16.qltv.utils;

import com.n16.qltv.model.Publisher;

public class StrProcessor {
    public static String getStrCategoryResult(Publisher publisher) {
        return String.format("Có phải bạn đang tìm: %s - Tại: %s",
                publisher.getPublisherName().trim(), publisher.getPublisherAddress());
    }
}
