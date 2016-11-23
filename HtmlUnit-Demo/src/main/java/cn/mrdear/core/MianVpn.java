package cn.mrdear.core;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import cn.mrdear.model.ConfigsBean;
import cn.mrdear.util.ModelUtil;

/**
 * @author Niu Li
 * @date 2016/10/8
 */
public class MianVpn {

    private static final java.lang.String HOME_PAGE = "https://www.mianvpn.com";

    public List<ConfigsBean> fetch(WebClient webClient) throws IOException {
        //拿到整个页面
        final HtmlPage page = webClient.getPage(HOME_PAGE);
        //拿到全部a标签
        DomNodeList<DomElement> domNodeList = page.getElementsByTagName("a");

        return domNodeList.stream()
                          //找到内容为Surge的a标签
                          .filter(domElement -> {
                    if (domElement.getTextContent().equals("Surge")) {
                        System.out.println(domElement.getTextContent());
                        return true;
                    }
                    return false;
                })
                          //模拟点击,并取出结果
                          .map(domElement -> {
                    HtmlPage tempPage = null;
                    try {
                        webClient.waitForBackgroundJavaScript(500);
                        tempPage = domElement.click();
                        Thread.sleep(500);
                        DomElement surge_url = tempPage.getElementById("surge_url");
                        if (surge_url != null) {
                            String href = surge_url.getAttribute("href");
                            System.out.println(href);
                            //转换为想要的结果
                            return parseUrl(href);
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                          //过滤掉为null的结果
                          .filter(configsBean -> configsBean != null)
                          //转换为list
                          .collect(Collectors.toList());
    }

    /**
     * https://user.mianvpn.com/api/ss/surge/?host=47.88.188.62&port=10001&method=rc4-md5&pw=9575
     * 解析得到的结果
     */
    private ConfigsBean parseUrl(String url) {
        String paramStr = url.substring(url.indexOf('?')+1);
        String[] paramArr = paramStr.split("&");

        String host = paramArr[0].substring(paramArr[0].indexOf('=')+1);
        Integer port = Integer.parseInt(paramArr[1].substring(paramArr[1].indexOf('=')+1));
        String method = paramArr[2].substring(paramArr[2].indexOf('=')+1);
        String pwd = paramArr[3].substring(paramArr[3].indexOf('=')+1);

        ConfigsBean configsBean = new ConfigsBean();
        configsBean.setRemarks(host);
        configsBean.setServer(host);
        configsBean.setServer_port(port);
        configsBean.setMethod(method);
        configsBean.setPassword(pwd);
        configsBean.setObfs("http_simple");
        configsBean.setId(ModelUtil.generateId());
        return configsBean;
    }
}
