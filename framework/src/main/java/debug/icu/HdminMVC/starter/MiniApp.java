package debug.icu.HdminMVC.starter;

import debug.icu.HdminMVC.beans.BeanFactory;
import debug.icu.HdminMVC.core.ClassScanner;
import debug.icu.HdminMVC.web.handler.HandlerManger;
import debug.icu.HdminMVC.web.service.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class MiniApp {
    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello , My mini mvc framework mini app");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            //System.out.println(classList);
            classList.forEach(it -> System.out.println(it.getName()));
            // 初始化MappingHandler
            HandlerManger.resolveMappingHandler(classList);

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
