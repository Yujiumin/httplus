package com.github.httplus.annotation;

import com.alibaba.fastjson.JSON;
import com.github.httplus.Configuration;
import com.github.httplus.XML;
import com.github.httplus.execute.Executable;

import java.lang.reflect.Field;

import static com.github.httplus.common.Constants.REQUEST_BODY_TYPE_JSON;
import static com.github.httplus.common.Constants.REQUEST_BODY_TYPE_XML;

/**
 * 注解解析器
 *
 * @author Yujiumin
 */
public class AnnotationParser {

    // 解析好的HTTP请求配置
    private Configuration configuration;

    public AnnotationParser(Executable executable) throws ClassNotFoundException {
        try {
            configuration = new Configuration();
            Class<? extends Executable> executableClass = executable.getClass();
            Request executableClassAnnotation = executableClass.getAnnotation(Request.class);
            if (executableClassAnnotation == null) {
                throw new ClassNotFoundException(String.format("没有在请求类上找到注解[%s]", Request.class.getName()));
            }
            configuration.setUrl(executableClassAnnotation.value());
            configuration.setMethod(executableClassAnnotation.method().toUpperCase());

            Field[] fields = executableClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getAnnotation(RequestBody.class) != null) {
                    RequestBody annotation = field.getAnnotation(RequestBody.class);
                    String type = annotation.type().toUpperCase();
                    Object value = field.get(executable);
                    if (REQUEST_BODY_TYPE_JSON.equals(type)) {
                        configuration.setBody(JSON.toJSONString(value));
                    } else if (REQUEST_BODY_TYPE_XML.equals(type)) {
                        configuration.setBody(XML.toXmlString(value, annotation.root()));
                    } else {
                        throw new UnsupportedOperationException("不支持该类型的数据");
                    }
                } else {
                    if (field.getAnnotation(RequestHeader.class) != null) {
                        RequestHeader annotation = field.getAnnotation(RequestHeader.class);
                        String name = annotation.value();
                        String value = String.valueOf(field.get(executable));
                        if (name.isEmpty()) {
                            name = field.getName();
                        }
                        configuration.addHeader(name, value);
                    } else if (field.getAnnotation(RequestParam.class) != null) {
                        RequestParam annotation = field.getAnnotation(RequestParam.class);
                        String name = annotation.value();
                        String value = String.valueOf(field.get(executable));
                        if (name.isEmpty()) {
                            name = field.getName();
                        }
                        configuration.addParam(name, value);
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

    }

    public Configuration parse() {
        return configuration;
    }

}
