package com.sdmx.support.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public final class URLUtils {

    public final static String requestParamBuild(Map<String, Object> param) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Object> item : param.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }

            builder.append(item.getKey() + "=" + item.getValue());
        }

        return builder.toString();
    }

    public final static String getFileUrl(String filepath) {
        return "/file?p="+ filepath.replace(IOUtils.STORAGE_LOCATION, "");
    }

    public final static String getRawFileUrl(String filepath) {
        return "/raw?p="+ filepath.replace(IOUtils.STORAGE_LOCATION, "");
    }
}
