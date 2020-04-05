package com.github.httplus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Configuration {

    private String url;
    private String protocol;
    private String version;
    private String method;
    private String body;
    private Map<String, String> headers;
    private Map<String, Object> params;

    public Configuration() {
        this.headers = new LinkedHashMap<>();
        this.params = new LinkedHashMap<>();
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addParam(String name, Object value) {
        params.put(name, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Object getParam(String key) {
        return params.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(String key, Class<T> clazz) {
        String param = getParam(key).toString();
        if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(param);
        } else if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(param);
        } else if (clazz.equals(Boolean.class)) {
            return (T) Boolean.valueOf(param);
        } else {
            return (T) param;
        }
    }

    public Set<String> paramKeys() {
        return params.keySet();
    }

    public Set<String> headerKeys() {
        return headers.keySet();
    }

    public String getUrl() {
        StringBuilder builder = new StringBuilder(url).append('?');
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
