package cn.mrdear.service;

import com.alibaba.fastjson.JSONObject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Niu Li
 * @date 2016/9/23
 */
@Service
public class CacheTestService {

    @Cacheable(value = "springCache")
    public JSONObject getjson(int id){
        JSONObject object = new JSONObject();
        object.put("hahah","123");
        object.put("id",id);
        return object;
    }
}
