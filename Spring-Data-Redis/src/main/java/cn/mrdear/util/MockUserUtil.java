package cn.mrdear.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import cn.mrdear.model.User;

/**
 * mock虚拟用户
 * @author Niu Li
 * @since 2017/3/30
 */
public class MockUserUtil {

  //mock数据来源
  private static String[] namePrefix = {"赵","钱","孙","李","周","吴","郑","王"};
  private static String[] nameSuffix = {"一","二","三","四","五","六","七","八"};

  private static Random random = new Random();

  /**
   * mock用户
   * @return 用户实体
   */
  public static User mockSignle(){
    User user = new User();
    int x = random.nextInt(namePrefix.length);
    String userName = namePrefix[x]+nameSuffix[x];
    user.setId((long) x);
    user.setUsername(userName);
    user.setPassword(UUID.randomUUID().toString());
    return user;
  }

  /**
   * mock集合
   * @param num 集合数量
   * @return  集合
   */
  public static List<User> mockList(int num){
    List<User> users = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      users.add(mockSignle());
    }
    return users;
  }
}
