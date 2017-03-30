package cn.mrdear.publish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author Niu Li
 * @since 2017/3/29
 */
@Configuration
public class PublishConfig {
  /**
   * 注入消息容器
   * @param jedisConnectionFactory jedis连接池
   * @param listenerAdapter 监听适配器
   * @return bean
   */
  @Bean
  public RedisMessageListenerContainer container(RedisConnectionFactory jedisConnectionFactory,
      MessageListenerAdapter listenerAdapter){
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(jedisConnectionFactory);
    //绑定监听者与信道的管理
    container.addMessageListener(listenerAdapter,new PatternTopic("java"));
    return container;
  }

  @Bean
  public MessageListenerAdapter adapter(Listen listen){
    //指定监听者和监听方法
    return new MessageListenerAdapter(listen,"handleMsg");
  }
}
