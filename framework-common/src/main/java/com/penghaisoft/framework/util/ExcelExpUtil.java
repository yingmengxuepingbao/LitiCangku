package com.penghaisoft.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author luoteng
 * @version 1.0
 * @Description EXCEl导出的公共方法
 * @date 2017年5月24日 下午4:20:27
 */
@Slf4j
public class ExcelExpUtil {
    private static final String EXCEL_LOCK = "732a138b-288d-49fd-8039-3f3673c64a66";
    /**
     * 转换为导出列的信息
     * @param columnComment 列头显示中文名
     * @param columnBeanName 列数据对象的属性字段名
     * @return
     */
    public static List<Map<String,String>> genColumnList(String[] columnComment,String[] columnBeanName){
//        两者必须等长
        if (columnComment.length!=columnBeanName.length){
            return null;
        }
        List<Map<String,String>> columnList = new ArrayList<>();
        for (int i = 0; i < columnBeanName.length; i++) {
            Map<String, String> columnMap = new HashMap<String, String>();
            columnMap.put("columnComment", columnComment[i]);
            columnMap.put("columnBeanName", columnBeanName[i]);
            columnList.add(columnMap);
        }

        return columnList;
    }

    /**
     * @param dataList   数据列表,可以为List<MdProduct>(适用于自己查询出的结果数据),
     *                   List<Map>(公共方法查询出的结果数据)
     * @param columnList EXCEL的标题、注释等 List<Map> 例如[{columnComment:'产品代码',
     *                   columnName:'product_code',
     *                   columnBeanName:'productCode'}，{...}]
     * @return SXSSFWorkbook
     * @Title generateWorkbook
     * @Description 生成excel
     * @author luoteng
     * @date 2017年5月24日 下午4:20:46
     */
    public static SXSSFWorkbook generateWorkbook(List dataList,
                                                 List<Map<String,String>> columnList) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        SXSSFWorkbook sw = null;
        sw = new SXSSFWorkbook(xssfWorkbook, 5000);
        Sheet sheet = sw.createSheet();

        CellStyle titleStyle = titleStyle(xssfWorkbook);
        //为当前titleStyle加锁
        titleStyle.setLocked(true);

        CellStyle contentStyle = contentStyle(xssfWorkbook);
        CellStyle contentStyleDate = contentStyleDate(xssfWorkbook);
        String cellFormatString = "@";
        Row titleRow = sheet.createRow(0);
//		Row commentsRow = sheet.createRow(1);
        int cellNum = 0;

        for (int i = 0; i < columnList.size(); i++) {
            Map map = columnList.get(i);
            Cell titleCell = titleRow.createCell(cellNum);
//			Cell commentsCell = commentsRow.createCell(cellNum);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellValue(map.get("columnComment").toString());
//			commentsCell.setCellStyle(titleStyle);
//			commentsCell.setCellValue(map.get("columnName").toString());
            cellNum++;
        }

        //int rowNum = 2;
        int rowNum = 1;

        for (int i = 0; i < dataList.size(); i++) {
            Row dataRow = sheet.createRow(rowNum++);
            Object object = dataList.get(i);
            int num = 0;

            if (object instanceof Map) {
                Map map = (Map) object;
                Iterator entries = map.entrySet().iterator();

                while (entries.hasNext()) {
                    Entry entry = (Entry) entries.next();
                    Cell cell = dataRow.createCell(num++);
                    cell.setCellStyle(contentStyle);
                    cell.setCellValue(entry.getValue().toString());
                }
            } else {
                Class cl = object.getClass();
                for (int j = 0; j < columnList.size(); j++) {
                    Map map = columnList.get(j);
                    try {
                        Cell cell = dataRow.createCell(num++);
                        String fieldName = map.get("columnBeanName").toString();
                        Field field = cl.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object val = field.get(object);
                        if (field.get(object) == null) {
                            val = "";
                            cell.setCellValue((String) val);
                        } else {
                            String fieldType = field.getGenericType().toString();
                            if (fieldType.equals("class java.util.Date")) {
//                                cellFormatString = "yyyy-MM-dd HH:mm";
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                val = df.format(field.get(object));
                                cell.setCellValue((String) val);
                            } else if (fieldType.equals("class java.math.BigDecimal")) {
                                Object o = field.get(object);
//                                if (val.indexOf(".") > 0) {
//                                    val = val.replaceAll("0+?$", "");
//                                    val = val.replaceAll("[.]$", "");
//                                }
                                cell.setCellValue(((BigDecimal) o).doubleValue());
                                cellFormatString = "#,##0.000000";
                            } else if (fieldType.equals("class java.lang.Integer")) {
                                cellFormatString = "#,##0";
                                cell.setCellValue((Integer) val);
                            } else if (fieldType.equals("class java.lang.Long")) {
                                cellFormatString = "#,##0";
                                cell.setCellValue((Long) val);
                            } else if (fieldType.equals("class java.lang.Double")) {
                                cellFormatString = "#,##0.000000";
                                cell.setCellValue((Double) val);
                            } else if (fieldType.equals("class java.lang.Float")) {
                                cell.setCellValue((Float) val);
                                cellFormatString = "#,##0.000000";
                            } else {
                                cellFormatString = "@";
                                val = field.get(object).toString();
                                cell.setCellValue((String) val);
                            }
                        }
                        // 为整个sheet加锁
                        sheet.protectSheet(EXCEL_LOCK);
                        contentStyle.setDataFormat(sw.createDataFormat().getFormat(cellFormatString));

                        cell.setCellStyle(contentStyle);
                    } catch (NoSuchFieldException e) {
                        log.info("columnList的参数在当前类内不存在，查询其父类！");
                        try {
                            num = num - 1;
                            Cell cell = dataRow.createCell(num++);
                            Class superCls = cl.getSuperclass();
                            Field field = superCls.getDeclaredField(map.get("columnBeanName").toString());
                            field.setAccessible(true);
                            Object val = field.get(object);
                            if (field.get(object) == null) {
                                val = "";
                                cell.setCellValue((String) val);
                            } else {
                                String fieldType = field.getGenericType().toString();
                                if (fieldType.equals("class java.util.Date")) {
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    val = df.format(field.get(object));
                                    cell.setCellValue((String) val);
                                } else if (fieldType.equals("class java.math.BigDecimal")) {
                                    Object o = field.get(object);
//                                if (val.indexOf(".") > 0) {
//                                    val = val.replaceAll("0+?$", "");
//                                    val = val.replaceAll("[.]$", "");
//                                }
                                    cell.setCellValue(((BigDecimal) o).doubleValue());
                                    cellFormatString = "#,##0.00";
                                } else if (fieldType.equals("class java.lang.Integer")) {
                                    cellFormatString = "#,##0";
                                    cell.setCellValue((Integer) val);
                                } else if (fieldType.equals("class java.lang.Long")) {
                                    cellFormatString = "#,##0";
                                    cell.setCellValue((Long) val);
                                } else if (fieldType.equals("class java.lang.Double")) {
                                    cellFormatString = "#,##0.00";
                                    cell.setCellValue((Double) val);
                                } else if (fieldType.equals("class java.lang.Float")) {
                                    cell.setCellValue((Float) val);
                                    cellFormatString = "#,##0.00";
                                } else {
                                    val = field.get(object).toString();
                                    cellFormatString = "@";
                                    cell.setCellValue((String) val);
                                }
                            }
                            cell.setCellStyle(contentStyle);
//                            cell.setCellValue(val);
                        } catch (NoSuchFieldException e1) {
                            log.info("columnList的参数在父类中不存在！");
                            e1.printStackTrace();
                        } catch (SecurityException e1) {
                            e1.printStackTrace();
                        } catch (IllegalArgumentException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        }
//						e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        log.info("columnList的参数在dataList内不存在！");
                        e.printStackTrace();
                    }
                }
            }
        }

        return sw;
    }

    /**
     * @param xssfWorkbook
     * @return CellStyle
     * @Title TitleStyle
     * @Description 设置excel标题样式
     * @author luoteng
     * @date 2017年5月24日 下午4:21:37
     */
    public static CellStyle titleStyle(XSSFWorkbook xssfWorkbook) {
        // 设置标题样式
        CellStyle styleTitle = xssfWorkbook.createCellStyle();
        styleTitle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置字体
        Font font = xssfWorkbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);// 字体大小
        styleTitle.setFont(font);
        // 边框
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
        styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        return styleTitle;
    }

    /**
     * @param xssfWorkbook
     * @return CellStyle
     * @Title ContentStyle
     * @Description 设置excel内容样式
     * @author luoteng
     * @date 2017年5月24日 下午4:21:45
     */
    public static CellStyle contentStyle(XSSFWorkbook xssfWorkbook) {
        // 设置标题样式
        CellStyle ContentStyle = xssfWorkbook.createCellStyle();
        // 设置字体
        Font font = xssfWorkbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);// 字体大小
        ContentStyle.setFont(font);
        // 边框
        ContentStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
        ContentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        ContentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        ContentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        ContentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        return ContentStyle;
    }

    public static CellStyle contentStyleDate(XSSFWorkbook xssfWorkbook) {
        // 设置标题样式
        CellStyle ContentStyle = xssfWorkbook.createCellStyle();
        XSSFDataFormat format = xssfWorkbook.createDataFormat();
        ContentStyle.setDataFormat(format.getFormat("yyyy年m月d日"));

        // 设置字体
        Font font = xssfWorkbook.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);// 字体大小
        ContentStyle.setFont(font);
        // 边框
        ContentStyle.setAlignment(HSSFCellStyle.ALIGN_GENERAL);
        ContentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        ContentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        ContentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        ContentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        return ContentStyle;
    }
}
