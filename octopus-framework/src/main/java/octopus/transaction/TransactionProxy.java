package octopus.transaction;

import octopus.annotation.Transaction;
import octopus.aop.Proxy;
import octopus.aop.ProxyChain;

import java.lang.reflect.Method;

/**
 * @author Administrator
 */
public class TransactionProxy implements Proxy {


    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };


    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();


            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
            } finally {
                FLAG_HOLDER.remove();
            }


        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
