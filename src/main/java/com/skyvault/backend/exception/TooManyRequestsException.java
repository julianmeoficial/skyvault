package com.skyvault.backend.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Duration;

public class TooManyRequestsException extends BaseException {

    private static final String TYPE = "https://api.skyvault.com/problems/too-many-requests";
    private static final HttpStatus STATUS = HttpStatus.TOO_MANY_REQUESTS;
    private static final String TITLE = "Too Many Requests";

    public TooManyRequestsException(String detail) {
        super(TYPE, STATUS, TITLE, detail);
    }

    public TooManyRequestsException(String detail, URI instance) {
        super(TYPE, STATUS, TITLE, detail, instance);
    }

    public TooManyRequestsException(String detail, Duration retryAfter) {
        super(TYPE, STATUS, TITLE, detail);
        addProperty("retryAfterSeconds", retryAfter.getSeconds());
    }

    // ✅ FACTORY METHODS CORREGIDOS - RETORNAN TooManyRequestsException
    public static TooManyRequestsException rateLimitExceeded(Integer maxRequests, Duration period) {
        TooManyRequestsException exception = new TooManyRequestsException(
                String.format("Rate limit exceeded. Maximum %d requests allowed per %d seconds",
                        maxRequests, period.getSeconds())
        );
        exception.addProperty("maxRequests", maxRequests);
        exception.addProperty("periodSeconds", period.getSeconds());
        return exception;
    }

    public static TooManyRequestsException rateLimitExceeded(Integer maxRequests, Duration period, Duration retryAfter) {
        TooManyRequestsException exception = new TooManyRequestsException(
                String.format("Rate limit exceeded. Maximum %d requests allowed per %d seconds. Retry after %d seconds",
                        maxRequests, period.getSeconds(), retryAfter.getSeconds()),
                retryAfter
        );
        exception.addProperty("maxRequests", maxRequests);
        exception.addProperty("periodSeconds", period.getSeconds());
        return exception;
    }
}
