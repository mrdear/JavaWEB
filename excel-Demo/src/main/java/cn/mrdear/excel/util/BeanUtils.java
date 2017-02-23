package cn.mrdear.excel.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对bean一些转换方法
 * @author Niu Li
 * @since 2017/2/23
 */
public class BeanUtils {

    /**
     * 转换bean为map
     * @param source 要转换的bean
     * @param <T> bean类型
     * @return 转换结果
     */
    public static <T> Map<String,Object> bean2Map(T source) throws IllegalAccessException {
        Map<String,Object> result = new HashMap<>();

        Class<?> sourceClass = source.getClass();
        //拿到所有的字段,不包括继承的字段
        Field[] sourceFiled = sourceClass.getDeclaredFields();
        for (Field field : sourceFiled) {
            field.setAccessible(true);//设置可访问,不然拿不到private
            //配置了注解的话则使用注解名称,作为header字段
            FiledName filedName = field.getAnnotation(FiledName.class);
            if (filedName == null){
                result.put(field.getName(),field.get(source));
            }else {
                if (filedName.Ignore()) continue;
                result.put(filedName.value(),field.get(source));
            }
        }
        return result;
    }

}

