package octopus.aop.dynamic;

import octopus.aop.proxy.Hello;
import octopus.aop.proxy.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 测试动态代理
 *
 * @author zhangyu
 */
public class DynamicProxy implements InvocationHandler {

    private final Object target;

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }


    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before Method invoke");
        Object invoke = method.invoke(target, args);
        System.out.println("After Method invoke");
        return invoke;
    }

    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(hello);
        Hello proxy = dynamicProxy.getProxy();
        proxy.sayHello("Bob");
    }
}
