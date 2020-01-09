package cn.wangz.mongo.adapter.update;

import cn.wangz.mongo.adapter.annotation.UpdateField;
import cn.wangz.mongo.adapter.utils.StringUtils;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author wang_zh
 * @date 2020/1/9
 */
public abstract class BaseUpdateDoc implements UpdateDoc {

    @Override
    public Document update() {
        Map<String, Document> updateMap = getUpdateMap(null, this);
        Document update = new Document();
        if (!updateMap.isEmpty()) {
            update.putAll(updateMap);
        }
        return update;
    }


    public Map<String, Document> getUpdateMap(String rootName, Object root) {
        Map<String, Document> updateMap = new HashMap<>();
        Field[] fields = root.getClass().getDeclaredFields();
        Stream.of(fields)
                .filter(field -> field.isAnnotationPresent(UpdateField.class))
                .forEach(field -> {
                    UpdateField updateField = field.getAnnotation(UpdateField.class);
                    String fieldName = updateField.value();
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
                        Map<String, Document> filedUpdateMap = getUpdateMap(fieldName, value);
                        filedUpdateMap.entrySet().stream().forEach(entry -> {
                            if (!updateMap.containsKey(entry.getKey())) {
                                updateMap.put(entry.getKey(), entry.getValue());
                            } else {
                                updateMap.get(entry.getKey()).putAll(entry.getValue());
                            }
                        });
                    } else {
                        String operator = updateField.operator();
                        if (!OPERATOR_SET.contains(operator)) {
                            throw new IllegalArgumentException("operator is illegal, field: " + fieldName);
                        }
                        if (!updateMap.containsKey(operator)) {
                            updateMap.put(operator, new Document());
                        }
                        updateMap.get(operator).put(fieldName, value);
                    }
                });
        return updateMap;
    }

    private static final List<String> OPERATOR_SET = Arrays.asList("$currentDate", "$inc", "$min", "$max", "$mul", "$rename", "$set", "$setOnInsert", "$unset");
}
