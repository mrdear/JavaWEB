package cn.mrdear.core;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.mrdear.model.ConfigsBean;
import cn.mrdear.util.ModelUtil;
import cn.mrdear.util.WebClientUtil;

/**
 * @author Niu Li
 * @date 2016/10/8
 */
public class TutorialVpn {

    private static final java.lang.String HOME_PAGE = "https://www.ashadowsocks.com/tutorial/trial_port";

    public List<ConfigsBean> fetch(WebClient webClient) throws IOException, InterruptedException {
        webClient.waitForBackgroundJavaScriptStartingBefore(1000);
        //拿到整个页面
        final HtmlPage page = webClient.getPage(HOME_PAGE);
        //等待js执行
        Thread.sleep(500);
        //开始解析
        List<ConfigsBean> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ConfigsBean config = parsePage(page,i);
            lists.add(config);
        }
        //移除null元素
        lists.removeIf(configsBean -> configsBean==null);
        return lists;
    }

    private ConfigsBean parsePage(HtmlPage page,int id){
        DomNode font = page.getElementById("font"+id);
        if (font == null || !font.asText().equals("可用")){
            return null;
        }
        DomNode host = page.getElementById("host"+id);
        DomNode port = page.getElementById("port"+id);
        DomNode pass = page.getElementById("pass"+id);
        DomNode encrypt = page.getElementById("encrypt"+id);
        ConfigsBean configsBean = new ConfigsBean();
        configsBean.setRemarks(host.asText());
        configsBean.setServer(configsBean.getRemarks());
        configsBean.setServer_port(Integer.parseInt(port.asText()));
        configsBean.setMethod(encrypt.asText());
        configsBean.setPassword(pass.asText());
        configsBean.setObfs("plain");
        configsBean.setId(ModelUtil.generateId());
        return configsBean;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final WebClient webClient = WebClientUtil.INSTANCE.webClient;
        TutorialVpn tutorialVpn = new TutorialVpn();
        tutorialVpn.fetch(webClient);
    }
}
