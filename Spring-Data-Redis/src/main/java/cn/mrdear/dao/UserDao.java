package cn.mrdear.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import cn.mrdear.model.User;

/**
 * redis操作示例
 *
 * @author Niu Li
 * @since 2017/3/30
 */
@Repository
public class UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDao.class);
  @Resource
  private StringRedisTemplate stringRedisTemplate;

  private static final String KEY_PREFIX = "users";

  private ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 保存一个用户
   */
  public void save(User user) {
    HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
    try {
      opsForHash.put(KEY_PREFIX, user.getId() + "", objectMapper.writeValueAsString(user));
    } catch (JsonProcessingException e) {
      logger.error("save user error,user id is ", user.getId());
    }
  }

  /**
   * 插入一个用户
   * @param id 用户id
   * @return 用户
   */
  public User find(Integer id) {
    try {
      HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
      String userJson = opsForHash.get(KEY_PREFIX, id.toString());
      if (Objects.isNull(userJson)){
        return null;
      }
      return objectMapper.readValue(userJson, User.class);
    } catch (IOException e) {
      logger.error("find user error id is {}",id);
    }
    return null;
  }

  /**
   * 查询全部用户
   */
  public List<User> query(){
    HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
    List<String> usersJson = opsForHash.values(KEY_PREFIX);
    List<User> users = new ArrayList<>(usersJson.size());
    usersJson.forEach(x -> {
      try {
        users.add(objectMapper.readValue(x,User.class));
      } catch (IOException e) {
        logger.error("read user error id is {}",x);
      }
    });
    return users;
  }
}
