package cn.mrdear.excel.core;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.mrdear.excel.constant.ExcelType;
import cn.mrdear.excel.util.BeanUtils;

/**
 * 工具类入口
 * @author Niu Li
 * @since 2017/2/23
 */
public class ExcelFormat {

    private static Logger logger = LoggerFactory.getLogger(ExcelFormat.class);
    //该表格的工作本
    private Workbook workbook;

    /**
     * 控制表头,其中键为对应DTO的字段,值为表头显示内容
     */
    private LinkedHashMap<String,String> headers;
    /**
     * 具体表内容,只接受DTO
     */
    private List<?> contents;

    /**
     * 入口函数
     * @param headers 表单头部
     * @param content 表单内容DTO
     * @return this表单对象
     */
    public static ExcelFormat from(LinkedHashMap<String,String> headers,List<?> content){
        return new ExcelFormat(headers,content);
    }

    /**
     * 在此workbook中增加另一个sheet
     * @param headers 新sheet的表头
     * @param content 新sheet的内容
     * @return this
     */
    public ExcelFormat andForm(LinkedHashMap<String,String> headers,List<?> content){
        this.headers = headers;
        this.contents = content;
        return this;
    }


    /**
     * 端点方法,生成最终的表单
     * @return this
     */
    public ExcelFormat build(String sheetName){
        //创建字表
        Sheet sheet = sheetName == null ? workbook.createSheet() : workbook.createSheet(sheetName);
        //创建表头
        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        List<String> headers = new ArrayList<>(this.headers.keySet());//表头
        List<String> values = new ArrayList<>(this.headers.values());//对应值
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(values.get(i)==null?headers.get(i):values.get(i));
        }
        //构造表单内容
        try {
            for (Object content : contents) {
                Map<String,Object> contentMap = BeanUtils.bean2Map(content);
                Row current = sheet.createRow(rowNum++);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = current.createCell(i);
                    Object obj = contentMap.get(headers.get(i));
                    if (obj == null) {
                        obj = "";
                    }
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    } else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else {
                        throw new IllegalArgumentException("unsupported cell type");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("parse excel fail ",e);
        }
        //设置样式

        return this;
    }
    /**
     * 私有化构造函数
     */
    private ExcelFormat(LinkedHashMap<String, String> headers, List<?> contents) {
        this.headers = headers;
        this.contents = contents;
    }

    /**
     * 调用该方法后,此workbook则写入关闭
     * @param dirIncludedFileName 最终生成文件名称
     */
    public void write(String dirIncludedFileName){
        try {
            File file = new File(dirIncludedFileName);
            FileOutputStream os = new FileOutputStream(file);
            workbook.write(os);
            os.close();
        } catch (IOException e) {
            logger.error("write excel fail ",e);
        }finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                logger.error("write excel fail ",e);
            }
        }
    }

    /**
     * 结果写到一个输出流中
     * @param os 目标流
     */
    public void write(OutputStream os) {
        try {
            workbook.write(os);
            workbook.close();
        } catch (IOException e) {
            logger.error("write excel fail ",e);
        }
    }


    /**
     * 链式调用设置生成文档格式
     * @param type 指定格式
     * @return this
     */
    public ExcelFormat excelType(ExcelType type){
        workbook = type == ExcelType.XLS?new HSSFWorkbook():new XSSFWorkbook();
        return this;
    }

}
