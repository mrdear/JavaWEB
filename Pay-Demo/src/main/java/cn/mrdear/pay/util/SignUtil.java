package cn.mrdear.pay.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class SignUtil {

    /**
     * 连接Map键值对
     *
     * @param map
     *            Map
     * @param prefix
     *            前缀
     * @param suffix
     *            后缀
     * @param separator
     *            连接符
     * @param ignoreEmptyValue
     *            忽略空值
     * @param ignoreKeys
     *            忽略Key
     * @return 字符串
     */
    public static String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator,
                                  boolean ignoreEmptyValue, String... ignoreKeys) {
        List<String> list = new ArrayList<String>();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key)
                        && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
                    list.add(key + "=" + (value != null ? value : ""));
                }
            }
        }
        return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
    }

    /**
     * 把request请求参数转换为Map<String,String>
     * @param request 该请求
     * @return Map<String,String>格式的参数
     */
    public static Map<String,String> request2Map(HttpServletRequest request){
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> resData = new HashMap<String, String>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            resData.put(name, request.getParameter(name));
        }
        return resData;
    }

    /**
     * Bean转map
     * @param bean 要转的bean
     * @return 返回一个TreeMap
     */
    public static TreeMap<String, String> bean2TreeMap(Object bean) {
        TreeMap<String, String> requestMap = new TreeMap<String, String>();
        Class<?> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        try {
            for (int i = 0; i < fields.length; i++) {
                String key = fields[i].getName();
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(bean);
                    if ("sign".equals(key) || value == null || StringUtils.isEmpty(value.toString())) {
                        continue;
                    }
                    requestMap.put(key, value.toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return requestMap;
    }

}


