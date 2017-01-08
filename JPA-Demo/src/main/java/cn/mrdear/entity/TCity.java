package cn.mrdear.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Niu Li
 * @date 2017/1/7
 */
@Entity
@Table(name = "t_city", schema = "test", catalog = "")
public class TCity {
    private int id;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Basic
    @Column(name = "name", nullable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String state;

    @Basic
    @Column(name = "state", nullable = true, length = 30)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String country;

    @Basic
    @Column(name = "country", nullable = true, length = 30)
    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }    private String map;

    @Basic
    @Column(name = "map", nullable = true, length = 30)
    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCity tCity = (TCity) o;

        if (id != tCity.id) return false;
        if (name != null ? !name.equals(tCity.name) : tCity.name != null) return false;
        if (state != null ? !state.equals(tCity.state) : tCity.state != null) return false;
        if (country != null ? !country.equals(tCity.country) : tCity.country != null) return false;
        if (map != null ? !map.equals(tCity.map) : tCity.map != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }
}
