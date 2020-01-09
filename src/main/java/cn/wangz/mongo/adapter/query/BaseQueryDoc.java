package cn.wangz.mongo.adapter.query;

import cn.wangz.mongo.adapter.annotation.QueryField;
import cn.wangz.mongo.adapter.utils.StringUtils;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * @author wang_zh
 * @date 2020/1/9
 */
public abstract class BaseQueryDoc implements QueryDoc {

    @Override
    public Document query() {
        return getQueryDoc(null, this);
    }

    private Document getQueryDoc(String rootName, Object root) {
        Document queryDoc = new Document();
        Field[] fields = root.getClass().getDeclaredFields();
        Stream.of(fields)
                .filter(field -> field.isAnnotationPresent(QueryField.class))
                .forEach(field -> {
                    QueryField queryField = field.getAnnotation(QueryField.class);
                    String fieldName = queryField.value();
                    if (StringUtils.isBlank(fieldName)) fieldName = field.getName();
                    fieldName =  StringUtils.isBlank(rootName)? fieldName: rootName + "." + fieldName;
                    Object value = null;
                    try {
                        field.setAccessible(true);
                        value = field.get(root);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("get value failed!");
                    }
                    if (value == null) return;
                    // field 是一个 Document，需要解析内部字段
                    if (field.getType().isAnnotationPresent(cn.wangz.mongo.adapter.annotation.Document.class)) {
                        Document filedQueryDoc = getQueryDoc(fieldName, value);
                        queryDoc.putAll(filedQueryDoc);
                    } else {
                        queryDoc.put(fieldName, value);
                    }
                });
        return queryDoc;
    }

}
