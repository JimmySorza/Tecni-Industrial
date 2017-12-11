package com.tecnindustrial.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-tecniIndustrialApp-alert", message);
        headers.add("X-tecniIndustrialApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("" + entityName + ": creacion exitosa! " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("" + entityName + ": actualizacion exitosa! " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("" + entityName + ": eliminacion exitosa! " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Proceso de la entidad fallo, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-tecniIndustrialApp-error", defaultMessage);
        headers.add("X-tecniIndustrialApp-params", entityName);
        return headers;
    }
}
