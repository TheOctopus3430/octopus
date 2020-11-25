package octopus.aop;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZAspect {


    Class<? extends Annotation> value();

}
