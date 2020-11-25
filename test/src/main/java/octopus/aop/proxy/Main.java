package octopus.aop.proxy;

public class Main {

    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        HelloProxy helloProxy = new HelloProxy(hello);
        helloProxy.sayHello("Alice");
    }
}
