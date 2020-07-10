package debug.icu.hdminmvc.beans;

import debug.icu.hdminmvc.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanjinxiang
 */
public class BeanFactory {
    private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls) {
        return classToBean.get(cls);
    }

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> waitCreate = new ArrayList<>(classList);
        while (waitCreate.size() != 0) {
            // 保存当前容器大小
            int remainSize = waitCreate.size();
            // 遍历
            for (int i = 0; i < waitCreate.size(); i++) {
                // 如果完成创建  则从待创建列表中删除
                if (beanCreate(waitCreate.get(i))) {
                    waitCreate.remove(i);
                }
            }
            // 循环引用的bean 暂不支持
            if (waitCreate.size() == remainSize) {
                throw new Exception("创建Bean时进入死循环");
            }
        }

    }

    /**
     * @param cls
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean beanCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        // 判断是否是一个bean  通过判断@Bean @Controller注解
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
            return true;
        }
        // 如果是Bean 通过反射实例化
        Object bean = cls.newInstance();

        // 遍历类中所有声明的字段 包括private 和 protected的
        for (Field field : cls.getDeclaredFields()) {
            // 判断是否有AutoWired 注解  如果有则将声明的Bean注入进去
            if (field.isAnnotationPresent(AutoWired.class)) {
                // 获取声明的CLASS对象
                Class<?> fieldType = field.getType();
                // 从bean工厂获取声明的bean
                Object reliantBean = BeanFactory.getBean(fieldType);
                // 如果bean还未创建，先退出
                if (reliantBean == null) {
                    return false;
                }
                field.setAccessible(true);
                //依赖注入 DI
                field.set(bean, reliantBean);
            }
        }
        // 将创建的Bean加入Bean工厂
        classToBean.put(cls, bean);
        return true;
    }
}
