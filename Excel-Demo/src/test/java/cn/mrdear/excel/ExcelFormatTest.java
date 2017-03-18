package cn.mrdear.excel;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cn.mrdear.excel.constant.ExcelType;
import cn.mrdear.excel.core.ExcelExtractor;
import cn.mrdear.excel.core.ExcelFormat;

/**
 * @author Niu Li
 * @since 2017/2/23
 */
public class ExcelFormatTest {
    LinkedHashMap<String,String> headers;
    LinkedHashMap<String,String> extractorHeaders;

    @Before
    public void init() {
        headers = new LinkedHashMap<>();
        extractorHeaders = new LinkedHashMap<>();
        headers.put("username","用户名");
        headers.put("passWord", "密码");
        extractorHeaders.put("用户名","username");
        extractorHeaders.put("密码", "passWord");
    }

    @Test
    public void testMake() {
        List<Demo> content = new ArrayList<>();
        Demo demo1 = new Demo("1","1");
        Demo demo2 = new Demo("2","2");
        Demo demo3 = new Demo("3","3");
        Demo demo4 = new Demo("4","4");
        content.add(demo1);
        content.add(demo2);
        content.add(demo3);
        content.add(demo4);

        //写入
        ExcelFormat.from(headers,content)
                .excelType(ExcelType.XLS)
                .build("niuli")
                .write("/tmp/niuli.xls");
        System.out.println("写入成功");
        //读取
        try {
            InputStream in = new FileInputStream(new File("/tmp/niuli.xls"));
            List<Demo> result = ExcelExtractor.extractFrom(ExcelType.XLS,in,Demo.class,
                extractorHeaders,0);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("读取成功");
    }
}
