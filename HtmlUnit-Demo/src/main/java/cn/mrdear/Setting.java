package cn.mrdear;

import java.util.List;

import cn.mrdear.model.ConfigsBean;

/**
 * @author Niu Li
 * @date 2016/10/11
 */
public class Setting
{

    /**
     * sspath : D:\tools\翻墙\gui-config.json
     */

    private String sspath;
    /**
     * 本地节点配置
     */
    private List<ConfigsBean> local;


    public String getSspath() {
        return sspath;
    }

    public void setSspath(String sspath) {
        this.sspath = sspath;
    }

    public List<ConfigsBean> getLocal() {
        return local;
    }

    public void setLocal(List<ConfigsBean> local) {
        this.local = local;
    }

}
