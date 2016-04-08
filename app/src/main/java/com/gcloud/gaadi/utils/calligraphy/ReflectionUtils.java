package com.gcloud.gaadi.utils.calligraphy;

import com.gcloud.gaadi.utils.GCLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chris on 17/12/14.
 * For Calligraphy.
 */
class ReflectionUtils {

    static Field getField(Class clazz, String fieldName) {
        try {
            final Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException ignored) {
        }
        return null;
    }

    static Object getValue(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException ignored) {
        }
        return null;
    }

    static void setValue(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
        }
    }

    static Method getMethod(Class clazz, String methodName) {
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    static void invokeMethod(Object object, Method method, Object... args) {
        try {
            if (method == null) return;
            method.invoke(object, args);
        } catch (IllegalAccessException ignored) {
            GCLog.e(ignored.getMessage());
        } catch (InvocationTargetException ignored) {
            GCLog.e(ignored.getMessage());
        }
    }
}
