package debug.icu.HdminMVC.web.handler;

import debug.icu.HdminMVC.web.mvc.Controller;
import debug.icu.HdminMVC.web.mvc.RequestParam;
import debug.icu.HdminMVC.web.mvc.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler  管理器
 */
public class HandlerManger {
    // 保存多个mappingHandler
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    // 跳出带有controller注解的类
    public static void resolveMappingHandler(List<Class<?>> classList) {
        for (Class<?> cls : classList) {
            // 判断controller注解是否存在
            if (cls.isAnnotationPresent(Controller.class)) {
                //  解析controller类
                parseHandlerFormController(cls);
            }
        }

    }

    private static void parseHandlerFormController(Class<?> cls) {
        //  通过反射获取到类的所有方法
        Method[] methods = cls.getDeclaredMethods();
        // 找到被RequestMapping注解的方法
        for (Method method : methods) {
            // 判断RequestMapping注解是否存在
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            // 从注解属性中获取uri
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            // 所需要的参数
            List<String> paramNameList = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
            HandlerManger.mappingHandlerList.add(mappingHandler);
        }

    }
}
