package Qkart_TestNG.resources;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;


public class DP {
    @DataProvider (name = "data-provider")
    public Object[][] dpMethod (Method m) throws Exception{
        switch (m.getName()) {
            case "TestCase01": 
            return getExcelData("TestCase01");
            case "TestCase02": 
            return getExcelData("TestCase02");
            case "TestCase03": 
            return getExcelData("TestCase03");
            case "TestCase04": 
            return getExcelData("TestCase04");
            case "TestCase05": 
            return getExcelData("TestCase05");
            case "TestCase06": 
            return getExcelData("TestCase06");
            case "TestCase07": 
            return getExcelData("TestCase07");
            case "TestCase08": 
            return getExcelData("TestCase08");
            case "TestCase11": 
            return getExcelData("TestCase11");
            case "TestCase12": 
            return getExcelData("TestCase12");
    }
            return null;
    }

    public Object[][] getExcelData(String sheetName) throws Exception{
        FileInputStream fis = new FileInputStream(
				"src/main/resources/Resources/Dataset.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(fis);
		DataFormatter df = new DataFormatter();
		XSSFSheet sheet = book.getSheet(sheetName);
		int rowCount=4;
		XSSFRow row = sheet.getRow(0);
		int cellCount=row.getLastCellNum();
		//System.out.println(rowCount+"---"+cellCount);
		//XSSFCell cell = row.getCell(0);
		Object obj[][]=new Object[rowCount-1][cellCount-1];
		for(int i=0;i<rowCount-1;i++) {
			for(int j=0;j<cellCount-1;j++) {
				XSSFCell cell1=sheet.getRow(i+1).getCell(j+1);
				String cellTxt=df.formatCellValue(cell1);
				obj[i][j]=cellTxt;
				//System.out.println(obj[i][j]);
			}
    }
    return obj;
}
}