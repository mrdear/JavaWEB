package cn.mrdear.excel.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对bean一些转换方法
 *
 * @author Niu Li
 * @since 2017/2/23
 */
public class BeanUtils {

  /**
   * 转换bean为map
   *
   * @param source 要转换的bean
   * @param <T>    bean类型
   * @return 转换结果
   */
  public static <T> Map<String, Object> bean2Map(T source) throws IllegalAccessException {
    Map<String, Object> result = new HashMap<>();

    Class<?> sourceClass = source.getClass();
    //拿到所有的字段,不包括继承的字段
    Field[] sourceFiled = sourceClass.getDeclaredFields();
    for (Field field : sourceFiled) {
      field.setAccessible(true);//设置可访问,不然拿不到private
      //配置了注解的话则使用注解名称,作为header字段
      FieldName fieldName = field.getAnnotation(FieldName.class);
      if (fieldName == null) {
        result.put(field.getName(), field.get(source));
      } else {
        if (fieldName.Ignore()) continue;
        result.put(fieldName.value(), field.get(source));
      }
    }
    return result;
  }

  /**
   * map转bean
   * @param source   map属性
   * @param instance 要转换成的备案
   * @return 该bean
   */
  public static <T> T map2Bean(Map<String, Object> source, Class<T> instance) {
    try {
      T object = instance.newInstance();
      Field[] fields = object.getClass().getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);
        FieldName fieldName = field.getAnnotation(FieldName.class);
        if (fieldName != null){
          field.set(object,source.get(fieldName.value()));
        }else {
          field.set(object,source.get(field.getName()));
        }
      }
      return object;
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}

