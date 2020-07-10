package debug.icu.hdminmvc.beans;

import java.lang.annotation.*;

@Documented
// 元注解
@Retention(RetentionPolicy.RUNTIME)
// 注解到类上
@Target(ElementType.TYPE)
public @interface Bean {
}
