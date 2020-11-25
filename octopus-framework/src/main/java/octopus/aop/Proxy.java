package octopus.aop;


/**
 * @author zhangyu
 */
public interface Proxy {


    /**
     * @Description: 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;


}
