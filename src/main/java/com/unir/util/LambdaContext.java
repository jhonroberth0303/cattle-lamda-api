package com.unir.util;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.LambdaRuntime;

public class LambdaContext {
    private final LambdaLogger logger = LambdaRuntime.getLogger();

    public void logException(String type, String message) {
        if (logger != null) {
            logger.log(type + message);
        }
    }

    public void logException(String type, String message, Exception ex) {
        if (logger != null) {
            logger.log(type + message + getTrace(ex));

        }
    }

    private String getTrace(Exception ex) {
        StringBuilder traceBuilder = new StringBuilder("\n");
        if (ex != null && ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
            for (StackTraceElement element : ex.getStackTrace()) {
                traceBuilder.append(element.toString()).append("\n");
            }
        }
        return traceBuilder.toString();
    }
}
