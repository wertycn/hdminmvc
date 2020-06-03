package debug.icu.HdminMVC.web.handler;

import debug.icu.HdminMVC.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//请求映射器
public class MappingHandler {
    // 需要处理的uri
    private String uri;
    // 对应的controller 方法
    private Method method;
    // 对应的controller类
    private Class<?> controller;
    // 调用方法所需要的参数
    private String[] args;

    MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }


    public boolean handler(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        // 判断当前handler能否处理改请求
        String requestUri = ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(requestUri)) {
            return false;
        }
        Object[] paramters = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            paramters[i] = req.getParameter(args[i]);
        }
        Object ctl = BeanFactory.getBean(controller);
        //Object ctl = controller.newInstance();
        Object response = method.invoke(ctl, paramters);
        res.getWriter().println(response.toString());
        return true;
    }
}
