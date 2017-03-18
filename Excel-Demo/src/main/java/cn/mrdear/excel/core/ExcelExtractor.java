package cn.mrdear.excel.core;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mrdear.excel.constant.ExcelType;
import cn.mrdear.excel.util.BeanUtils;

/**
 * excel解析
 *
 * @author Niu Li
 * @since 2017/3/17
 */
public class ExcelExtractor {

  private static Logger logger = LoggerFactory.getLogger(ExcelFormat.class);

  /**
   * 解析excel,默认第一行为表头
   *
   * @param type         excel类型
   * @param excel        excel输入流
   * @param tClass       要生成的实体类
   * @param headerMapper 对应解析表格
   * @return 解析后集合
   * @throws IllegalArgumentException 解析错误
   */
  public static <T> List<T> extractFrom(ExcelType type, InputStream excel, Class<T> tClass,
      Map<String, String> headerMapper, Integer sheetIndex) throws IllegalArgumentException, IOException {
    Workbook workbook;
    try {
      workbook = type.equals(ExcelType.XLS) ? new HSSFWorkbook(excel) : new XSSFWorkbook(excel);
    } catch (IOException e) {
      throw new IllegalArgumentException("未知的表格类型");
    }
    //解析表头.默认第一行为表头
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    Iterator<Row> rowIterator = sheet.rowIterator();
    //第一行
    Row firstRow = rowIterator.next();
    Iterator<Cell> firstCell = firstRow.cellIterator();

    Set<String> existHeaderSet = new HashSet<>();
    List<String> existHeader = new ArrayList<>(headerMapper.size());
    firstCell.forEachRemaining(cell -> {
      if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
        throw new IllegalArgumentException("第一行必须全部为字符串,第" + cell.getColumnIndex() + 1 + "有问题");
      }
      existHeaderSet.add(cell.getStringCellValue());
      existHeader.add(cell.getStringCellValue());
    });
    //查询出不存在的表头
    for (String s : headerMapper.keySet()) {
      if (!existHeaderSet.contains(s)) {
        throw new IllegalArgumentException("不存在的表头:" + s);
      }
    }
    //开始解析表单
    Integer lineNumber = 0;
    List<T> result = new ArrayList<T>();
    try {
      while (rowIterator.hasNext()){
        Row x = rowIterator.next();
        lineNumber++;
        Map<String, Object> tempMap = new HashMap<>();
        Iterator<Cell> cellIterator = x.cellIterator();
        cellIterator.forEachRemaining(y -> {
          Object value = null;
          switch (y.getCellType()) {
            case Cell.CELL_TYPE_STRING:
              value = y.getStringCellValue();
              break;
            case Cell.CELL_TYPE_BOOLEAN:
              value = y.getBooleanCellValue();
              break;
            case Cell.CELL_TYPE_NUMERIC:
              value = y.getNumericCellValue();
              break;
            case Cell.CELL_TYPE_ERROR:
              value = "";
              break;
            case Cell.CELL_TYPE_BLANK:
              value = "";
              break;
            default:
              value = "";
          }
          String key = headerMapper.get(existHeader.get(y.getColumnIndex()));
          tempMap.put(key, value);
        });
        result.add(BeanUtils.map2Bean(tempMap, tClass));
      }
    } catch (Exception e) {
      logger.error("第{}行解析错误",lineNumber);
    }
    return result;
  }
}
