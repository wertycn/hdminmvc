package debug.icu.HdminMVC.starter;

import debug.icu.HdminMVC.web.service.TomcatServer;
import org.apache.catalina.LifecycleException;

public class MiniApp {
    public static void run(Class<?> cls, String[] args) {
        System.out.println("Hello , My mini mvc framework mini app");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }


    }
}
