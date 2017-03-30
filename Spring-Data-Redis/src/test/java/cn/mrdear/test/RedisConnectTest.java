package cn.mrdear.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import javax.annotation.Resource;

import cn.mrdear.Application;
import cn.mrdear.publish.Listen;

/**
 * @author Niu Li
 * @since 2017/3/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisConnectTest {
  @Resource
  private StringRedisTemplate stringRedisTemplate;
  @Resource
  private Listen listen;
  @Test
  public void testSetAndGet() {
    stringRedisTemplate.opsForValue().set("ping","pong");
    System.out.println(stringRedisTemplate.opsForValue().get("ping"));
  }

  @Test
  public void testPublish() throws InterruptedException {
    stringRedisTemplate.convertAndSend("java","hello world");
    listen.getLatch().await();
  }

  @Test
  public void testMulti() {
    boolean isThrow = false;
    List<Object> result = null;
    try {
      result = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
        @Override
        public List<Object> execute(RedisOperations operations) throws
            DataAccessException {
          operations.multi();
          ValueOperations<String,String> ops = operations.opsForValue();
          ops.set("ping1","pong1");
          ops.set("ping2","pong2");
          if (isThrow){
            throw new IllegalArgumentException("测试异常");
          }
          return operations.exec();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println(result);
  }

  @Test
  public void testPipe() {
//pop a specified number of items from a queue
    List<Object> results = stringRedisTemplate.executePipelined(
        new RedisCallback<Object>() {
          public Object doInRedis(RedisConnection connection) throws DataAccessException {
            StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
            for(int i=0; i< 10; i++) {
              stringRedisConn.rPush("myqueue",i+"");
            }
            return null;
          }
        });
  }
}
