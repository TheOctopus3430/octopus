package octopus.threadlocal;

public class MyThread extends Thread {

    public MyThread(String name, MyCounter myCounter) {
        super(name);
        this.myCounter = myCounter;
    }

    private MyCounter myCounter;

    @Override
    public void run() {
        System.out.println("当前线程" + Thread.currentThread().getName() + "\t" + myCounter.getCounter());
    }

    public static void main(String[] args) {
        MyCounter counter = new MyCounter();
        MyThread myThreadA = new MyThread("线程A", counter);
        MyThread myThreadB = new MyThread("线程B", counter);
        MyThread myThreadC = new MyThread("线程C", counter);
        myThreadA.start();
        myThreadB.start();
        myThreadC.start();
    }
}

class MyCounter {


    private static ThreadLocal<Integer> counter = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };


    public Integer getCounter() {
        counter.set(counter.get() + 1);
        return counter.get();
    }

    @Override
    public String toString() {
        return counter + "";
    }
}