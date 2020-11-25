package octopus.aop.proxy;

public class HelloImpl implements Hello {

    @Override
    public void sayHello(String name) {
        System.out.println("HelloImpl!\t" + name);
    }
}
