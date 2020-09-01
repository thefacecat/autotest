package com.datacvg.cases;

import com.datacvg.config.Config;
import com.datacvg.utils.FileUtils;
import com.datacvg.utils.RequestUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class TestExcelWithDataProvider {

    public static Object[][] readData(String dataFile, String sheetName, int beginRowNum, int rowOffset,
                                      int beginColumnNum, int columnOffset) {
        return read(dataFile, sheetName, beginRowNum, rowOffset, beginColumnNum, columnOffset);
    }


    /**
     * @param dataFile 文件名
     * @param sheetName excel中的sheet
     * @param beginRowNum 开始行
     * @param rowOffset 行偏移量
     * @param beginColumnNum 开始列
     * @param columnOffset 列偏移量
     * @return
     */
    private static Object[][] read(String dataFile, String sheetName, int beginRowNum, int rowOffset,
                                   int beginColumnNum, int columnOffset) {
        File excelFile = new File(dataFile);
        Workbook wb = null;
        Object[][] data = null;
        try {
            wb = Workbook.getWorkbook(excelFile);
            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null)
                return null;
            int rows = sheet.getRows();

            int cols = sheet.getColumns();
            if (rowOffset == 0) {
                rowOffset = rows - beginRowNum;
            } else if (rows < (beginRowNum + rowOffset)) {
                rowOffset = rows - beginRowNum;
            }
            if (columnOffset == 0) {
                columnOffset = cols - beginColumnNum;
            } else if (cols < (beginColumnNum + columnOffset)) {
                columnOffset = cols - beginColumnNum;
            }
            data = new Object[rowOffset][columnOffset];
            //打印列长度
//            System.out.println(data[0].length);

            for (int i = beginRowNum; i < beginRowNum + rowOffset; i++) {
                for (int j = beginColumnNum; j < beginColumnNum + columnOffset; j++) {
                    // 获取单元格数据 getCell(col,row);
                    Cell cell = sheet.getCell(j, i);
                    if (cell != null) {
                        String celldata = cell.getContents().trim();
                        data[i - beginRowNum][j - beginColumnNum] = celldata;
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    // 定义数据驱动
    @DataProvider(name = "getData")
    public static Object[][] getData() throws IOException {
        FileUtils.getFilePath();
        //读取dataFile文件内容，从第二行开始读取 "f:\\ex\\ExcelCasesDemo.xls" Config.filepath
        return readData(Config.filepath, "Sheet1", 1, 0, 0, 0);
    }

    // 测试案例绑定DataProvider后自动循环执行
    @Test(dataProvider = "getData")
    public void testSearch(String no,String titleName,String type,String url,String path,
                           String contentType,String request,String expect,String isLoggin) {
        //调用请求类方法
        RequestUtils.httpRequest(type, url, path, contentType, request, expect, isLoggin);
    }
}