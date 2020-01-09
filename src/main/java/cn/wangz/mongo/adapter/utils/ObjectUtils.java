package cn.wangz.mongo.adapter.utils;

/**
 * @author wang_zh
 * @date 2020/1/9
 */
public class ObjectUtils {

    public static boolean isBaseType(Object object) {
        Class clazz = object.getClass();
        return clazz == Integer.class || clazz == Integer.TYPE
                || clazz == Byte.class || clazz == Byte.TYPE
                || clazz == Long.class || clazz == Long.TYPE
                || clazz == Double.class || clazz == Double.TYPE
                || clazz == Float.class || clazz == Float.TYPE
                || clazz == Character.class || clazz == Character.TYPE
                || clazz == Short.class || clazz == Short.TYPE
                || clazz == Boolean.class || clazz == Boolean.TYPE;
    }

}
