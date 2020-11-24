package octopus.web;

import com.sun.org.apache.regexp.internal.RE;
import octopus.annotation.ZAction;
import octopus.bean.ClassHelper;
import octopus.util.ArrayUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理器辅助类
 *
 * @author zhangyu
 */
public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new ConcurrentHashMap<>(16);

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            //遍历Controller
            for (Class<?> controllerClass : controllerClassSet) {
                //读取Controller中的处理方法
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(declaredMethods)) {
                    //针对每一个被@Action注解标识的构建映射关系
                    for (Method method : declaredMethods) {
                        if (method.isAnnotationPresent(ZAction.class)) {
                            //获取注解上的value
                            ZAction annotation = method.getAnnotation(ZAction.class);
                            //自定义的格式：  get:/login
                            String value = annotation.value();
                            //验证URL映射规则
                            if (value.matches("\\w+:/\\w*")) {
                                String[] array = value.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法和请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    //封装Handler
                                    Handler handler = new Handler(controllerClass, method);
                                    //添加对应映射关系对
                                    ACTION_MAP.put(request, handler);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
