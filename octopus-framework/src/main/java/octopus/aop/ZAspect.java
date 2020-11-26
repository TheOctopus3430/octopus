package octopus.aop;

import java.lang.annotation.*;

/**
 * @author zhangyu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZAspect {


    Class<? extends Annotation> value();

}
