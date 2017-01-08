package cn.mrdear.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Niu Li
 * @date 2017/1/7
 */
@Entity
@javax.persistence.Table(name = "t_hotel", schema = "test", catalog = "")
public class THotel {
    private int id;

    @Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Basic
    @javax.persistence.Column(name = "name", nullable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String address;

    @Basic
    @javax.persistence.Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private Integer city;

    @Basic
    @javax.persistence.Column(name = "city", nullable = true)
    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        THotel tHotel = (THotel) o;

        if (id != tHotel.id) return false;
        if (name != null ? !name.equals(tHotel.name) : tHotel.name != null) return false;
        if (address != null ? !address.equals(tHotel.address) : tHotel.address != null)
            return false;
        if (city != null ? !city.equals(tHotel.city) : tHotel.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
