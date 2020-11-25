package octopus.aop;

import octopus.bean.ClassHelper;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 *
 */
public final class AopHelper {

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
        //获取所有的的代理类
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> aClass : proxyClassSet) {
            if (aClass.isAnnotationPresent(ZAspect.class)) {
                ZAspect aspect = aClass.getAnnotation(ZAspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(aClass, targetClassSet);

            }
        }

        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>(16);
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> aClass : targetClassSet) {
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
