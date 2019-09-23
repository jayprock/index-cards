package com.bitbus.indexcards.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public final class UrlUtil {

    private UrlUtil() {
        // Not intended for instantiation
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }

    public static String appendToUrl(String currentUrl, String... pathParams) {
        String pathParam = pathParams[0];
        String url = currentUrl.endsWith("/") //
                ? currentUrl + pathParam //
                : currentUrl + "/" + pathParam;
        if (pathParams.length == 1) {
            return url;
        }
        return appendToUrl(url, Arrays.copyOfRange(pathParams, 1, pathParams.length));
    }

}
