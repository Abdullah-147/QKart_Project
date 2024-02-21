package Qkart_TestNG.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Tests.QKART_Tests;

public class ListenerClass implements ITestListener {
	static int rowCount = 0;

	// static FileOutputStream out;
	public static void takeScreenshot(WebDriver driver, String screenshotType, String description) {
		try {
			File theDir = new File("./screenshots");
			if (!theDir.exists()) {
				theDir.mkdirs();
			}
			String timestamp = String.valueOf(System.currentTimeMillis());
			String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			//System.out.println(fileName);
			File DestFile = new File("./screenshots/" + fileName);
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeDataToExcel(String testCaseName, Object[] Parameters, String status) throws Exception {
		String para = Arrays.toString(Parameters);
		XSSFWorkbook workbook = new XSSFWorkbook();
		String path = "./RunResults.xlsx";
		if (rowCount == 0) {
			File file = new File(path);
			file.createNewFile();
			XSSFSheet sheet = workbook.createSheet("Results");
			XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			row.createCell(0).setCellValue("TestCase Name");
			row.createCell(1).setCellValue("Parameters");
			row.createCell(2).setCellValue("Status");
			FileOutputStream out = new FileOutputStream(path);
			workbook.write(out);
			rowCount++;
		}
		FileInputStream fs = new FileInputStream(path);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheet("Results");
		XSSFRow row = sheet.createRow(rowCount);
		rowCount++;
		row.createCell(0).setCellValue(testCaseName);
		row.createCell(1).setCellValue(para);
		row.createCell(2).setCellValue(status);
		FileOutputStream out = new FileOutputStream(path);
		wb.write(out);

	}

	public void onStart(ITestContext context) {
		System.out.println("onStart method started");
	}

	public void onFinish(ITestContext context) {
		System.out.println("onFinish method started");
	}

	public void onTestStart(ITestResult result) {
		System.out.println("New Test Started: " + result.getName());
		takeScreenshot(QKART_Tests.driver, "TestStart", result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("onTestSuccess Method: " + result.getName());
		takeScreenshot(QKART_Tests.driver, "TestSuccess", result.getName());
		try {
			if (!(result.getName().equals("Testcase09") || result.getName().equals("Testcase10"))) {
				writeDataToExcel(result.getName(), result.getParameters(), "SUCCESS");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.getParameters();
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("onTestFailure Method: " + result.getName());
		takeScreenshot(QKART_Tests.driver, "TestFailed", result.getName());
		try {
			if (!(result.getName().equals("Testcase09") || result.getName().equals("Testcase10"))) {
				writeDataToExcel(result.getName(), result.getParameters(), "FAIL");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("onTestSkipped Method: " + result.getName());
		try {
			if (!(result.getName().equals("Testcase09") || result.getName().equals("Testcase10"))) {
				writeDataToExcel(result.getName(), result.getParameters(), "SKIPPED");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("onTestFailedButWithinSuccessPercentage: " + result.getName());
	}

}
