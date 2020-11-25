package octopus.aop;

import java.lang.reflect.Method;

/**
 * 切面代理
 *
 * @author zhangyu
 */
public abstract class AspectProxy implements Proxy {


    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            end();
        }
        return result;
    }

    protected abstract void after(Class<?> targetClass, Method targetMethod, Object[] methodParams);

    protected abstract void before(Class<?> targetClass, Method targetMethod, Object[] methodParams);

    protected abstract boolean intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams);

    protected abstract void end();

    protected abstract void begin();


}
