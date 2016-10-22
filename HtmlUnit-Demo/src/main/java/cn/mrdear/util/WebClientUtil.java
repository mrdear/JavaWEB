package cn.mrdear.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author Niu Li
 * @date 2016/10/8
 */
public enum  WebClientUtil {

    INSTANCE;

    public WebClient webClient;

    WebClientUtil() {
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);//支持https
        webClient.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false); // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        webClient.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        webClient.getOptions().setDoNotTrackEnabled(false);
        webClient.setJavaScriptTimeout(8000);//设置js运行超时时间
        webClient.waitForBackgroundJavaScript(500);//设置页面等待js响应时间,
    }
}
