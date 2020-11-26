package octopus.threadlocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyThreadLocal<T> {


    private Map<Thread, T> threadTMap = new ConcurrentHashMap<>(16);

    /**
     * 将值放入线程局部变量中
     */
    public void set(T value) {
        threadTMap.put(Thread.currentThread(), value);
    }

    /**
     * 从线程局部变量获取值
     */
    public T get() {
        Thread thread = Thread.currentThread();
        T t = threadTMap.get(thread);
        if (t == null && !threadTMap.containsKey(thread)) {
            T value = initialValue();
            threadTMap.put(thread, value);
        }
        return t;
    }

    /**
     * 从线程局部变量移除值
     */
    public void remove() {
        threadTMap.remove(Thread.currentThread());
    }

    /**
     * 返回线程局部变量中的初始值
     */
    protected T initialValue() {
        return null;
    }

}
