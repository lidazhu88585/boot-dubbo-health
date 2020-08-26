package cn.rui;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
class BootHealthBackendApplicationTests {

    //使用POI读取Excel文件中的数据

    @Test
    void contextLoads() throws IOException {

        //加载指定文件
        XSSFWorkbook excel = new XSSFWorkbook (new FileInputStream (new File ("D:\\11.xlsx")));

        XSSFSheet sheetAt = excel.getSheetAt (0);
        //遍历每一行
        for (Row row : sheetAt) {
            //编辑行的列
            for (Cell cell : row) {
                System.out.println (cell.getStringCellValue ());
            }
        }

        excel.close ();
    }

    //使用POI写入数据到Excel表中,并且通过输入流将创建的Excel文件保存到本地磁盘
    @Test
    public void test() throws Exception{
        //创建一个Excel文件
        XSSFWorkbook excel = new XSSFWorkbook ();

        //创建一个工作表对象
        XSSFSheet sheet = excel.createSheet ("李大猪");

        //在工作表中创建行和列
        XSSFRow title = sheet.createRow (0);//第一行
        //在行中创建单元格对象
        title.createCell (0).setCellValue ("姓名");  //第一个单元格
        title.createCell (1).setCellValue ("地址");
        title.createCell (2).setCellValue ("年龄");

        //在工作表中创建行和列
        XSSFRow dateRow = sheet.createRow (1);//第二行
        dateRow.createCell (0).setCellValue ("李大猪");  //第一个单元格
        dateRow.createCell (1).setCellValue ("安徽合肥");
        dateRow.createCell (2).setCellValue ("20");

        //将文件保存到本地磁盘
        FileOutputStream fo = new FileOutputStream (new File ("D:\\hello.xlsx"));
        excel.write (fo);
        fo.flush ();
        excel.close ();
    }

}
