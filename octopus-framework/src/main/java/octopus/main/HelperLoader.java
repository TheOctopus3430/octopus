package octopus.main;

import com.sun.org.apache.bcel.internal.util.ClassSet;
import octopus.bean.BeanHelper;
import octopus.bean.ClassHelper;
import octopus.bean.IocHelper;
import octopus.util.ClassUtil;
import octopus.web.ControllerHelper;

/**
 * 加载相应的Helper类
 *
 * @author zhangyu
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classList) {
            ClassUtil.loadClass(aClass.getName(), false);
        }
    }

    public static void main(String[] args) {
        init();
    }
}
