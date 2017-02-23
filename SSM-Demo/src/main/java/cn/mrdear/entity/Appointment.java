package cn.mrdear.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "appointment")
public class Appointment extends AppointmentKey {
    /**
     * 预约时间
     */
    @Column(name = "appoint_time")
    private Date appointTime;

    /**
     * 获取预约时间
     *
     * @return appoint_time - 预约时间
     */
    public Date getAppointTime() {
        return appointTime;
    }

    /**
     * 设置预约时间
     *
     * @param appointTime 预约时间
     */
    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }
}