package octopus.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import octopus.aop.proxy.HelloImpl;

import java.lang.reflect.Method;

/**
 * @author zhangyu
 */
public class CGLibProxy implements MethodInterceptor {

    private static final CGLibProxy cgLibProxy = new CGLibProxy();

    private CGLibProxy() {
    }

    public static CGLibProxy getInstance() {
        return cgLibProxy;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("After");
        return result;
    }

    public static void main(String[] args) {

        HelloImpl proxy = CGLibProxy.getInstance().getProxy(HelloImpl.class);
        proxy.sayHello("HHl");
    }


}
