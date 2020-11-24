package octopus.helper;

import octopus.annotation.ZInject;
import octopus.util.ArrayUtil;
import octopus.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.FileHandler;

/**
 * 依赖注入辅助类
 *
 * @author zhangyu
 */
public final class IocHelper {


    static {
        //加载所有的Bean对象
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //遍历每一个Bean,获取对应的Field
        for (Map.Entry<Class<?>, Object> bean : beanMap.entrySet()) {
            Class<?> key = bean.getKey();
            Object value = bean.getValue();
            //遍历Field
            Field[] declaredFields = key.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(declaredFields)) {
                for (Field declaredField : declaredFields) {
                    //判断是否有@Inject注解，如果有则进行注入操作
                    if (declaredField.isAnnotationPresent(ZInject.class)) {
                        //获取属性类型Class
                        Class<?> aClassType = declaredField.getType();
                        //再次从IOC容器中获取Bean注入
                        Object fieldInstance = beanMap.get(aClassType);
                        if (fieldInstance != null) {
                            //注入
                            ReflectionUtil.setField(value, declaredField, fieldInstance);
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {

    }
}
