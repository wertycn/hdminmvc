package debug.icu.hdminmvc.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描器
 */
public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        //存储扫描到的类
        List<Class<?>> classList = new ArrayList<>();
        // 包名转换为文件路径
        String path = packageName.replace(".", "/");
        // 使用默认类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取个可遍历的url资源
        Enumeration<URL> resources = classLoader.getResources(path);
        //遍历资源
        while (resources.hasMoreElements()) {
            URL resouce = resources.nextElement();
            //判断是否为jar包
            if (resouce.getProtocol().contains("jar")) {
                //获取jar包绝对路径 通过资源打开的连接需要强制转换
                JarURLConnection jarUrlConnection = (JarURLConnection) resouce.openConnection();
                String jarFilePath = jarUrlConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath, path));
            } else {
                //TODO : other
            }

        }
        return classList;
    }

    /**
     * 获取jar包中所有类
     *
     * @param jarFilePath jar包路径
     * @param path        指定需要的类的相对路径
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        // 每个entries 实体都是jar包中的一个文件
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            // 获取一个实体的名称 一个.class文件的路径
            String entryName = jarEntry.getName();

            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                // 分隔符替换为. 去除掉.class后缀
                String classFullName = entryName.replace("/", ".").substring(0, entryName.length() - 6);

                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
