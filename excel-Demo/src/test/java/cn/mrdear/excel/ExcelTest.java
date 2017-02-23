package cn.mrdear.excel;

import cn.mrdear.excel.core.Excel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Niu Li
 * @since 2017/2/23
 */
public class ExcelTest {
    LinkedHashMap<String,String> headers;

    @Before
    public void init() {
        headers = new LinkedHashMap<>();
        headers.put("username","用户名");
        headers.put("passWord", "密码");
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

        Excel.from(headers,content)
                .excelType(Excel.ExcelType.XLS)
                .build("niuli")
                .write("/tmp/niuli.xls");
    }
}
