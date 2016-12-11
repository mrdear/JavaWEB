package cn.mrdear.util.redis.service;


import cn.mrdear.util.redis.calback.WorkCallback;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单机版jedis
 * 参考博文:http://www.jianshu.com/p/da69edda2a43
 *         http://www.jianshu.com/p/c3b8180af944
 * @author Niu Li
 * @date 2016/12/8
 */
public class JedisClientSingle implements JedisClient{
    /**
     * 连接池,建议使用其他工具注入进来
     * 如果不能注入的话,可以使用构造方法初始化
     */
    private final JedisPool jedisPool;

    public JedisClientSingle(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String get(final String key) {
        return excute(new WorkCallback<String, Jedis>() {
            @Override
            public String doWorkCallback(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public String set(final String key, final String value) {
        return excute(new WorkCallback<String, Jedis>() {
            @Override
            public String doWorkCallback(Jedis jedis) {
                return jedis.set(key,value);
            }
        });
    }

    public Long ttl(final String key) {
        return excute(new WorkCallback<Long, Jedis>() {
            @Override
            public Long doWorkCallback(Jedis jedis) {
                return jedis.ttl(key);
            }
        });
    }

    public String setex(final String key, final String value, final int seconds) {
        return excute(new WorkCallback<String, Jedis>() {
            @Override
            public String doWorkCallback(Jedis jedis) {
                return jedis.setex(key,seconds,value);
            }
        });
    }
    public Long del(final String key) {
        return excute(new WorkCallback<Long, Jedis>() {
            @Override
            public Long doWorkCallback(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    public Long expire(final String key, final int timeout) {
        return excute(new WorkCallback<Long, Jedis>() {
            @Override
            public Long doWorkCallback(Jedis jedis) {
                return jedis.expire(key,timeout);
            }
        });
    }

    public Boolean exists(final String key) {
        return excute(new WorkCallback<Boolean, Jedis>() {
            @Override
            public Boolean doWorkCallback(Jedis jedis) {
                return excute(new WorkCallback<Boolean, Jedis>() {
                    public Boolean doWorkCallback(Jedis jedis) {
                        return jedis.exists(key);
                    }
                });
            }
        });
    }

    public Boolean setNxAndExpire(final String key, final String value, final int seconds) {
        return excute(new WorkCallback<Boolean, Jedis>() {
            @Override
            public Boolean doWorkCallback(Jedis jedis) {
                Long result = jedis.setnx(key,value);
                return result == 1L && jedis.expire(key, seconds) > 0;
            }
        });
    }

    /**
     * 通用模板方法,很适合提取公共操作
     * @param workCallback 处理函数
     * @param <T> 返回类型
     * @return 结果
     */
    private <T> T excute(WorkCallback<T, Jedis> workCallback) {
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            return workCallback.doWorkCallback(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null !=jedis){
                jedis.close();
            }
        }
        return null;
    }


}
