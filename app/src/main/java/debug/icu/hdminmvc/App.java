package debug.icu.hdminmvc;

import debug.icu.hdminmvc.starter.FrameWork;


public class App {
    public static void main(String[] args) {
        System.out.println("Hello , My mini mvc framework app");
        FrameWork.run(App.class, args);
    }
}
