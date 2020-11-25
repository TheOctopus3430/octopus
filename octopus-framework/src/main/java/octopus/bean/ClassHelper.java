package octopus.bean;

import octopus.annotation.ZController;
import octopus.annotation.ZService;
import octopus.config.ConfigHelper;
import octopus.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            //该方法通过标识转换或扩展参考转换来测试由指定的Class 类表示的类型是否可以转换为由此类对象表示的类型
            //判断aClass是否是superClass相同类型或者是子类
            if (superClass.isAssignableFrom(aClass) && !superClass.equals(aClass)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if (aClass.isAnnotationPresent(annotationClass)) {
                classSet.add(aClass);
            }
        }
        return classSet;
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
