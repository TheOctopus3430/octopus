package octopus.web;


import octopus.bean.BeanHelper;
import octopus.bean.IocHelper;
import octopus.config.ConfigHelper;
import octopus.main.HelperLoader;
import octopus.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 请求转发器
 *
 * @author zhangyu
 */
@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象，用户注册Servlet
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //获取请求方法和请求路径
        String method = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(method, requestPath);
        if (handler != null) {
            //获取Controller和Action
            Class<?> controllerClass = handler.getControllerClass();
            //获取IOC容器对应的Bean
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            //存储参数集合，这里包括俩个一个是在请求体一个在URL拼接的，俩个都需要放在这里
            Map<String, Object> paramMap = new HashMap<>(16);
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            //解码请求体
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                //解析URL后面的参数列表
                String[] params = StringUtils.split(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        //拆分K-V
                        String[] array = StringUtils.split(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramKey = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramKey, paramValue);
                        }
                    }
                }
            }
            //将前面的Map存储的参数封装起来
            Param param = new Param(paramMap);
            //通过反射调用Action方法
            Method actionMethod = handler.getActionMethod();
            //controllerBean、方法句柄、方法参数
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            //处理Action方法返回值
            if (result instanceof View) {
                //返回JSP页面
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    //判断重定向和请求转发
                    if (path.startsWith("/")) {
                        res.sendRedirect(req.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            //将Model中的对象都放入request域中
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, res);
                    }
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    PrintWriter writer = res.getWriter();
                    String json = JsonUtil.toJson(data);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
