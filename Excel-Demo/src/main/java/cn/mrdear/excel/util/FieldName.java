package cn.mrdear.excel.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义字段名
 * @author Niu Li
 * @since 2017/2/23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface FieldName {
    /**
     * 字段名
     */
    String value() default "";
    /**
     * 是否忽略
     */
    boolean Ignore() default false;
}
