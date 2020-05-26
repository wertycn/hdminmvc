package debug.icu.HdminMVC.web.service;

import debug.icu.HdminMVC.web.servlet.DispatchServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;

    public TomcatServer(String[] args) {
        this.args = args;
    }

    public void startServer() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(2020);
        tomcat.start();
        Context context = new StandardContext();
        context.setPath("");
//         注册默认监听器
        context.addLifecycleListener(new Tomcat.FixContextListener());
//        注册servlet
        DispatchServlet servlet = new DispatchServlet();
        Tomcat.addServlet(context, "dispatchServlet", servlet);

//        设置uri 映射
        context.addServletMappingDecoded("/", "dispatchServlet");
//         建立context 和 servlet 的练习
        tomcat.getHost().addChild(context);

        Thread awaitThread = new Thread("tomcat_await_thread") {
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);
        awaitThread.start();

    }
}

