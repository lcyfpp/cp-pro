package com.linkedaim.admin.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @desc：对象转MAP工具类
 * @title：BeanConvertMapUtil
 * @author: zhaoyangyang
 * @date: 2018/5/30 0030 20:44
 * @version: v1.0
 */
public class ConvertUtil {

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }
        return obj;
    }


    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, Object> objectToSortMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

    public static LinkedHashMap<String, Object> objectToSortMapDesc(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            linkedHashMap.put(key, value);
        }
        return linkedHashMap;
    }


    public static String objectToUrlPair(Object o) throws Exception {
        Class<? extends Object> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(o);
            if (value != null) {
                if (value instanceof BigDecimal) {
                    value = new BigDecimal(value.toString()).stripTrailingZeros().toPlainString();
                }
                sb.append(name).append("=").append(value).append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
