package cn.mrdear.util.dateutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日期工具类
 * 参考博文:http://www.jianshu.com/p/5675690b351e
 * @author Niu Li
 * @date 2016/12/11
 */
public abstract class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static Map<String,ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    public final static String MDHMSS = "MMddHHmmssSSS";
    public final static String YMD = "yyyyMMdd";
    public final static String YMD_ = "yyyy-MM-dd";
    public final static String HMS = "HHmmss";
    public final static String YMDHMS = "yyyyMMddHHmmss";
    public final static String YM = "yyMM";
    public final static String YMDHMS_ = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
     *
     * @param pattern SimpleDateFormat规则
     * @return 该实例
     */
    private static SimpleDateFormat getSdf(final String pattern){
        ThreadLocal<SimpleDateFormat> t = sdfMap.get(pattern);
        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (t == null){
            synchronized (DateUtil.class){
                // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                logger.debug("put new sdf of pattern " + pattern + " to map");
                // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                t = sdfMap.get(pattern);
                if (t == null){
                    t = new ThreadLocal<SimpleDateFormat>(){
                        @Override
                        protected SimpleDateFormat initialValue() {
                            logger.debug("thread: " + Thread.currentThread() + " init pattern: " + pattern);
                            return new SimpleDateFormat(pattern);
                        }
                    };
                }
                sdfMap.put(pattern,t);
            }
        }
        return t.get();
    }

    /**
     * 为指定时间按照相应日历字段增加时间
     * @param date 初始时间
     * @param time 要增加的时间
     * @param filed 日历字段 参考Calendar的静态字段
     * @return 修改后的时间
     */
    public static Date addDate(Date date, int time, int filed) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        calendar.add(filed, time);
        return calendar.getTime();
    }
    /**
     * 格局给定的SDF格式化时间
     * @param date 时间
     * @param sdf 指定转换格式
     * @return 转换后的串
     */
    public static String format(Date date, String sdf) {
        return getSdf(sdf).format(date);
    }

    /**
     * 把字符串按照指定格式转换
     * @param str 时间串
     * @param sdf 给定转换格式
     * @return 转换后的时间
     */
    public static Date parse(String str, String sdf) {

        Date date = null;
        try {
            date = parseCanThrow(str, sdf);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 把字符串按照指定格式转换,需要主动抛异常情况下使用
     * @param str 时间串
     * @param sdf 给定转换格式
     * @return 转换后的时间
     */
    public static Date parseCanThrow(String str, String sdf) throws ParseException {
        return getSdf(sdf).parse(str);
    }

}
