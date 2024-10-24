package com.ntd.challenge.record.v1.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class TimezoneUtils {
    private static final String TIMEZONE_HEADER = "X-Timezone";

    public static String getTimezone() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return "UTC";
        }

        HttpServletRequest request = requestAttributes
                .getRequest();

        String timeZone = request.getHeader(TIMEZONE_HEADER);
        return timeZone != null ? timeZone : "UTC";
    }
}
