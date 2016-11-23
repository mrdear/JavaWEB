package cn.mrdear.core;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.WebClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import cn.mrdear.Setting;
import cn.mrdear.model.ConfigsBean;
import cn.mrdear.model.SSModel;
import cn.mrdear.util.SystemUtil;
import cn.mrdear.util.WebClientUtil;

/**
 * 使用步骤
 * 1.配置setting.json里面的ss配置文件地址,如果有的话,会读取,然后值修改里面的节点配置,没有的话则创建新文件,按照默认配置来
 * 2.启动main代码测试
 * 3.打包为jar运行
 * 4.配合doc里面的bat脚本,可以很大的简化你的运行.
 *
 * 代码逻辑很简单,直接看这个类的调用就好了
 * @author Niu Li
 * @date 2016/10/8
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //读取配置路径
        Setting setting = SystemUtil.getSetting();
        File ssfile = new File(setting.getSspath());
        if (!ssfile.exists()){
            ssfile.createNewFile();
        }

        try (final WebClient webClient = WebClientUtil.INSTANCE.webClient;
             InputStream inputStream = new FileInputStream(ssfile);
             OutputStream outputStream = new FileOutputStream(ssfile);
        ) {
            //1通道
            MianVpn mianVpn = new MianVpn();
            List<ConfigsBean> mianVpns = mianVpn.fetch(webClient);
            System.out.println("通道一");
            mianVpns.forEach(System.out::println);
            //2通道
            TutorialVpn tutorialVpn = new TutorialVpn();
            List<ConfigsBean> tutorials = tutorialVpn.fetch(webClient);
            System.out.println("通道二");
            tutorials.forEach(System.out::println);
            //本地的配置
            mianVpns.addAll(setting.getLocal());
            System.out.println("本地配置");
            setting.getLocal().forEach(System.out::println);
            //集合
            mianVpns.addAll(tutorials);


            SSModel model = JSON.parseObject(inputStream, null, SSModel.class);
            if (model == null) {
                model = new SSModel();
                model.setConfigs(mianVpns);
            }
            JSON.writeJSONString(outputStream, model);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
