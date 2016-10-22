package cn.mrdear.model;

import java.util.List;

/**
 * @author Niu Li
 * @date 2016/10/8
 */
public class SSModel {

    /**
     * configs : [{"remarks":"日本东","server":"t.com","server_port":111,"password":"111","method":"aes-256-cfb","obfs":"plain","obfsparam":"","remarks_base64":"5pel5pys5Lic5LqsIE5UVCDpq5jpgJ_oioLngrk=","tcp_over_udp":false,"udp_over_tcp":false,"protocol":"origin","obfs_udp":false,"enable":true,"id":"47-6A-A5-D4-AD-23-D4-68-0B-AA-83-B5-2F-CE-D6-B0"},{"remarks":"日本东京 KDDI 高速节点","server":"tky3.jp.v1.ss-fast.com","server_port":15951,"password":"B9PblQNy","method":"aes-256-cfb","obfs":"plain","obfsparam":"","remarks_base64":"5pel5pys5Lic5LqsIEtEREkg6auY6YCf6IqC54K5","tcp_over_udp":false,"udp_over_tcp":false,"protocol":"origin","obfs_udp":false,"enable":true,"id":"78-00-62-7C-78-95-25-0A-59-BB-13-2C-26-7D-5F-F6"},{"remarks":"日本 NTT 不计算流量节点","server":"unlimited.jp.v1.ss-fast.com","server_port":15951,"password":"B9PblQNy","method":"aes-256-cfb","obfs":"plain","obfsparam":"","remarks_base64":"5pel5pysIE5UVCDkuI3orqHnrpfmtYHph4_oioLngrk=","tcp_over_udp":false,"udp_over_tcp":false,"protocol":"origin","obfs_udp":false,"enable":true,"id":"F1-97-EB-56-D5-6B-8C-08-FF-ED-2D-97-E9-66-40-B7"}]
     * index : 8
     * random : false
     * global : false
     * enabled : true
     * shareOverLan : false
     * isDefault : false
     * localPort : 1080
     * pacUrl : null
     * useOnlinePac : false
     * reconnectTimes : 0
     * randomAlgorithm : 0
     * TTL : 0
     * proxyEnable : false
     * proxyType : 0
     * proxyHost : null
     * proxyPort : 0
     * proxyAuthUser : null
     * proxyAuthPass : null
     * authUser : null
     * authPass : null
     * autoban : false
     */

    private int index = 0;
    private boolean random = false;
    private boolean global = false;
    private boolean enabled = true;
    private boolean shareOverLan = false;
    private boolean isDefault = false;
    private int localPort = 1080;
    private String pacUrl;
    private boolean useOnlinePac = false;
    private int reconnectTimes = 0;
    private int randomAlgorithm = 0;
    private int TTL = 0;
    private boolean proxyEnable = false;
    private int proxyType = 0;
    private String proxyHost;
    private int proxyPort = 0;
    private String proxyAuthUser = "";
    private String proxyAuthPass = "";
    private String authUser = "";
    private String authPass = "";
    private boolean autoban = false;

    private List<ConfigsBean> configs;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isShareOverLan() {
        return shareOverLan;
    }

    public void setShareOverLan(boolean shareOverLan) {
        this.shareOverLan = shareOverLan;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public String getPacUrl() {
        return pacUrl;
    }

    public void setPacUrl(String pacUrl) {
        this.pacUrl = pacUrl;
    }

    public boolean isUseOnlinePac() {
        return useOnlinePac;
    }

    public void setUseOnlinePac(boolean useOnlinePac) {
        this.useOnlinePac = useOnlinePac;
    }

    public int getReconnectTimes() {
        return reconnectTimes;
    }

    public void setReconnectTimes(int reconnectTimes) {
        this.reconnectTimes = reconnectTimes;
    }

    public int getRandomAlgorithm() {
        return randomAlgorithm;
    }

    public void setRandomAlgorithm(int randomAlgorithm) {
        this.randomAlgorithm = randomAlgorithm;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    public boolean isProxyEnable() {
        return proxyEnable;
    }

    public void setProxyEnable(boolean proxyEnable) {
        this.proxyEnable = proxyEnable;
    }

    public int getProxyType() {
        return proxyType;
    }

    public void setProxyType(int proxyType) {
        this.proxyType = proxyType;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyAuthUser() {
        return proxyAuthUser;
    }

    public void setProxyAuthUser(String proxyAuthUser) {
        this.proxyAuthUser = proxyAuthUser;
    }

    public String getProxyAuthPass() {
        return proxyAuthPass;
    }

    public void setProxyAuthPass(String proxyAuthPass) {
        this.proxyAuthPass = proxyAuthPass;
    }

    public String getAuthUser() {
        return authUser;
    }

    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public String getAuthPass() {
        return authPass;
    }

    public void setAuthPass(String authPass) {
        this.authPass = authPass;
    }

    public boolean isAutoban() {
        return autoban;
    }

    public void setAutoban(boolean autoban) {
        this.autoban = autoban;
    }

    public List<ConfigsBean> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigsBean> configs) {
        this.configs = configs;
    }


}
