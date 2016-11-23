package cn.mrdear.model;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author Niu Li
 * @date 2016/10/8
 */
public class ConfigsBean {

        /**
        * remarks : 加利福尼亚，洛杉矶，美国
        * server : 23.105.203.111
        * server_port : 11111
        * password : 1111111
        * method : aes-256-cfb
        * obfs : plain
        * obfsparam :
        * remarks_base64 :
        * tcp_over_udp : false
        * udp_over_tcp : false
        * protocol : origin
        * obfs_udp : false
        * enable : true
        * id : 6B-7C-2E-E6-75-BD-0B-75-B6-12-30-06-39-61-69-37
        */
        private String remarks;
        private String server;
        private int server_port;
        private String password;
        private String method;
        private String obfs;
        private String obfsparam = "";
        private String remarks_base64 = "";
        private boolean tcp_over_udp = false;
        private boolean udp_over_tcp = false;
        private String protocol = "origin";
        private boolean obfs_udp = false;
        private boolean enable = true;
        private String id;

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
            try {
                if (remarks != null && !"".equals(remarks)){
                    remarks_base64 = Base64.getEncoder().encodeToString(remarks.getBytes("UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                remarks_base64 = "节点...";
            }
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public int getServer_port() {
            return server_port;
        }

        public void setServer_port(int server_port) {
            this.server_port = server_port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getObfs() {
            return obfs;
        }

        public void setObfs(String obfs) {
            this.obfs = obfs;
        }

        public String getObfsparam() {
            return obfsparam;
        }

        public void setObfsparam(String obfsparam) {
            this.obfsparam = obfsparam;
        }

        public String getRemarks_base64() {
            return remarks_base64;
        }

        public void setRemarks_base64(String remarks_base64) {
            this.remarks_base64 = remarks_base64;
        }

        public boolean isTcp_over_udp() {
            return tcp_over_udp;
        }

        public void setTcp_over_udp(boolean tcp_over_udp) {
            this.tcp_over_udp = tcp_over_udp;
        }

        public boolean isUdp_over_tcp() {
            return udp_over_tcp;
        }

        public void setUdp_over_tcp(boolean udp_over_tcp) {
            this.udp_over_tcp = udp_over_tcp;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public boolean isObfs_udp() {
            return obfs_udp;
        }

        public void setObfs_udp(boolean obfs_udp) {
            this.obfs_udp = obfs_udp;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    @Override
    public String toString() {
        return "ConfigsBean{" +
                "remarks='" + remarks + '\'' +
                ", server='" + server + '\'' +
                ", server_port=" + server_port +
                ", password='" + password + '\'' +
                ", method='" + method + '\'' +
                ", obfs='" + obfs + '\'' +
                ", obfsparam='" + obfsparam + '\'' +
                ", remarks_base64='" + remarks_base64 + '\'' +
                ", tcp_over_udp=" + tcp_over_udp +
                ", udp_over_tcp=" + udp_over_tcp +
                ", protocol='" + protocol + '\'' +
                ", obfs_udp=" + obfs_udp +
                ", enable=" + enable +
                ", id='" + id + '\'' +
                '}';
    }
}
