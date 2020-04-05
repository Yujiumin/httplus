package com.github.httplus;

import com.alibaba.fastjson.JSON;
import org.dom4j.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Yujiumin
 */
public class XML {

    //  标签正则表达式
    private static final String REGEX_TAG = "^<\\w+>.+</\\w+>$";

    /**
     * 将对象转为xml字符串
     *
     * @param object 对象
     * @param root   根节点
     * @return 字符串
     */
    @SuppressWarnings("unchecked")
    public static String toXmlString(Object object, String root) {
        if (object instanceof String) {
            return object.toString();
        } else if (object instanceof Map) {
            return toXmlString((Map<String, Object>) object, root);
        } else {
            return toXmlString(JSON.parseObject(JSON.toJSONString(object), Map.class), root);
        }
    }

    /**
     * 将map转为xml数据
     *
     * @param map  集合
     * @param root 根节点
     * @return 字符串
     */
    private static String toXmlString(Map<String, Object> map, String root) {
        StringBuilder builder = new StringBuilder(String.format("<%s>\n", root));
        for (String key : map.keySet()) {
            Object value = map.get(key);
            builder.append(String.format("\t<%s><![CDATA[%s]]></%s>\n", key, value, key));
        }
        builder.append(String.format("</%s>\n", root));
        return builder.toString();
    }

    /**
     * 将字符串转为对象
     *
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            Document document = DocumentHelper.parseText(text);
            Element rootElement = document.getRootElement();
            Map<String, Object> map = parseObject(rootElement);
            String jsonString = JSON.toJSONString(map);
            return JSON.parseObject(jsonString, clazz);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将此节点以及其子节点封装成map
     *
     * @param element 起始节点
     * @return 节点map
     */
    private static Map<String, Object> parseObject(Element element) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < element.nodeCount(); i++) {
            Node node = element.node(i);
            String key = node.getName();
            String value = node.getText();
            if (value.matches(REGEX_TAG)) {
                map.put(key, parseObject((Element) node));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}
