package octopus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 获取实例
     *
     * @param cls
     * @return
     */
    public static Object getInstance(Class<?> cls) {
        Object instance = null;
        try {
            instance = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 反射调用方法
     */

    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result = null;
        method.setAccessible(true);
        try {
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 反射设置字段
     */
    public static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure ",e);
            throw new RuntimeException(e);
        }

    }


}
