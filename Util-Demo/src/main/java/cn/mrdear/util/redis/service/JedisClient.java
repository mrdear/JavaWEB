package cn.mrdear.util.redis.service;


/**
 * 定义redis需要的接口
 * @author Niu Li
 * @date 2016/12/8
 */
public interface JedisClient {
    /**
     * 根据key值获取
     * @param key 指定key
     * @return 存在返回值,不存在返回null
     */
    String get(String key);

    /**
     * 设置值(String kei)
     * @param key key值
     * @param value 内容
     * @return 成功则 ok
     */
    String set(String key, String value);

    /**
     * 得到key的剩余时间
     * @param key 指定key
     * @return -2不存在 -1存在但无剩余时间 其他为剩余时间
     */
    Long ttl(String key);

    /**
     * 等价于 set + expire
     * @param key 设置的key
     * @param seconds 存活时间,到期自动删除
     * @param value 对应值
     * @return 结果
     */
    String setex(final String key, final String value, final int seconds);

    /**
     * 删除指定key
     * @param key 指定key
     * @return 不存在返回0 返回删除个数
     */
    Long del(String key);

    /**
     *给指定key设置时间
     * @param key 指定key
     * @param timeout 超时时间,单位秒
     * @return 1成功 0失败
     */
    Long expire(String key, int timeout);

    /**
     * 检测key是否存在
     * @param key 指定key
     * @return true存在
     */
    Boolean exists(String key);

    /**
     * 不存在则设置,并延时,存在则什么都不做
     * @param key key值
     * @param value 值
     * @param seconds 超时时间
     * @return true成功
     */
    Boolean setNxAndExpire(final String key, final String value, final int seconds);

}
