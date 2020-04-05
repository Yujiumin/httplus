package com.github.httplus.execute;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Yujiumin
 */
public class Response {

    private String body;
    private Integer code;
    private Map<String, String> headers;

    Response() {
        headers = new LinkedHashMap<>();
    }

    void setBody(String body) {
        this.body = body;
    }

    void setCode(Integer code) {
        this.code = code;
    }

    void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Integer getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    public Set<String> headerKeys() {
        return headers.keySet();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public boolean containsHeader(String name) {
        return headers.containsKey(name);
    }


}
