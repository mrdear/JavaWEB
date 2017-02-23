package com.haikong.dao;

import com.haikong.model.ReqDevice;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 具体设备的dao层
 */
@Repository("DeviceMapper")
public class DeviceMapper {
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据传送的设备信息,更新设备状态为关闭
     * @param reqDevice
     * @return
     */
    public boolean updateDeviceOff(ReqDevice reqDevice){
        int k = sqlSessionTemplate.update("deviceMapper.updateDeviceOff",reqDevice);
        return k>0;
    }

    /**
     * 更新设备的data和onoff字段
     * @param reqDevice
     * @return
     */
    public boolean updateDeviceDetail(ReqDevice reqDevice){
        int k = sqlSessionTemplate.update("deviceMapper.updateDeviceDetail",reqDevice);
        return k>0;
    }

    /**
     * 插入一个设备记录,然后再插入一条用户和设备关系记录
     * @param reqDevice
     * @return
     */
    public boolean insertDevice(ReqDevice reqDevice){
        if (findDeviceData(reqDevice.getDevice_id())==null){//如果设备记录不存在
            sqlSessionTemplate.insert("deviceMapper.insertDevice",reqDevice);
        }
        int m = sqlSessionTemplate.insert("deviceMapper.insertUserDevice",reqDevice);//插入设备和用户关系

        return m>0;
    }

    /**
     * 删除一条用户设备关系记录,但是不删除设备记录
     * @param reqDevice
     * @return
     */
    public boolean deleteUserDevice(ReqDevice reqDevice){
        int k = sqlSessionTemplate.delete("deviceMapper.deleteUserDevice",reqDevice);
        return k>0;
    }

    /**
     * 根据device_id查找粗其data字段
     * @param device_id
     * @return
     */
    public String findDeviceData(String device_id){
        return sqlSessionTemplate.selectOne("deviceMapper.findDeviceData",device_id);
    }

}
