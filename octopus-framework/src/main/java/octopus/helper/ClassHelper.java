package octopus.helper;

import com.sun.org.apache.bcel.internal.util.ClassSet;
import octopus.annotation.ZController;
import octopus.annotation.ZService;
import octopus.helper.ConfigHelper;
import octopus.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下所有的Service类，即被@ZService注解的类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceClassSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            //判断是否是@ZService注解的类
            if (aClass.isAnnotationPresent(ZService.class)) {
                serviceClassSet.add(aClass);
            }
        }
        return serviceClassSet;
    }

    /**
     * 获取应用包下所有的controller类，即被@ZController注解的类
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerClassSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            //判断是否是@ZController注解的类
            if (aClass.isAnnotationPresent(ZController.class)) {
                controllerClassSet.add(aClass);
            }
        }
        return controllerClassSet;
    }

    /**
     * 获取应用下所有的Bean，包括service和controller
     */

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getServiceClassSet());
        return beanClassSet;
    }


}
