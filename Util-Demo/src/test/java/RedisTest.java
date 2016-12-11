import org.junit.Test;

import cn.mrdear.util.redis.service.JedisClientSingle;
import redis.clients.jedis.JedisPool;

/**
 * @author Niu Li
 * @date 2016/12/11
 */
public class RedisTest {

    @Test
    public void testGet() {
        //比较推荐依赖注入
        JedisPool jedisPool = new JedisPool("172.18.1.1",6636);
        JedisClientSingle clientSingle = new JedisClientSingle(jedisPool);
        String result = clientSingle.get("foo");
        System.out.println(result);
    }
}
