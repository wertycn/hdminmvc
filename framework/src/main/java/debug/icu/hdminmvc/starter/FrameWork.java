package debug.icu.hdminmvc.starter;

import debug.icu.hdminmvc.beans.BeanFactory;
import debug.icu.hdminmvc.core.ClassScanner;
import debug.icu.hdminmvc.web.handler.HandlerManger;
import debug.icu.hdminmvc.web.service.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class FrameWork {
    public static void run(Class<?> cls, String[] args) {
        System.out.println("欢迎使用 HdminMVC ~");
        // 创建
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer(2021);
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            System.out.println("扫描到以下class:");
            classList.forEach(it -> System.out.println(it.getName()));
            // 初始化MappingHandler
            System.out.println("开始初始化MappingHandler:");
            HandlerManger.resolveMappingHandler(classList);
            System.out.println("初始化MappingHandler完成");
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
