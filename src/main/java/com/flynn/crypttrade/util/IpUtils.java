package com.flynn.crypttrade.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtils {

    private IpUtils() {}

    public static String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}