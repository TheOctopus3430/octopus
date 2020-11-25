package octopus.aop.proxy;

public class HelloProxy implements Hello {

    private Hello hello;

    public HelloProxy(Hello hello) {
        this.hello = hello;
    }

    @Override
    public void sayHello(String name) {
        System.out.println("before method invoke");
        hello.sayHello(name);
        System.out.println("after method invoke");
    }
}
