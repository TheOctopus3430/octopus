package octopus.aop;

import octopus.bean.BeanHelper;
import octopus.bean.ClassHelper;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 *
 * @author zhangyu
 */
public final class AopHelper {


    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetMapEntry : targetMap.entrySet()) {
                //获取目标类类型
                Class<?> targetClass = targetMapEntry.getKey();
                //获取目标类对应的代理集合
                List<Proxy> proxyList = targetMapEntry.getValue();
                //创建代理对象集合
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                //覆盖Bean
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(ZAspect zAspect) {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> value = zAspect.value();
        if (!value.equals(ZAspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(value));
        }
        return targetClassSet;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>(16);
        //获取IOC容器所有的的代理类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> aClass : proxyClassSet) {
            if (aClass.isAnnotationPresent(ZAspect.class)) {
                //获取当前代理对象注解数据
                ZAspect aspect = aClass.getAnnotation(ZAspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(aClass, targetClassSet);

            }
        }
        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        //存储目标容器，KEY-目标类，Value-代理类集合
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>(16);
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            //获取代理类
            Class<?> proxyClass = proxyEntry.getKey();
            //获取目标对象集合
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> aClass : targetClassSet) {
                //获取代理类对象
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(aClass)) {
                    targetMap.get(aClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(aClass, proxyList);
                }
            }
        }
        return targetMap;
    }


}
