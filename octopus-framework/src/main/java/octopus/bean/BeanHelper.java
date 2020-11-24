package octopus.bean;


import octopus.util.ReflectionUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理Bean
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>();

    static {
        //获取Bean集合（所有的Controller和Service）
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        //通过反射实例化，添加到Bean容器中
        for (Class<?> aClass : beanClassSet) {
            Object instance = ReflectionUtil.getInstance(aClass);
            BEAN_MAP.put(aClass, instance);
        }
    }

    /**
     * 获取Bean映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean
     *
     * @return
     */
    public static <T> T getBean(Class<?> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("Can not get bean by class:" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }


}
