package com.dn.jerick;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.dn.UI.ChromeDriver;
import com.dn.APP.runAppCase;

public class Test {
	private static WebDriver driver;
	public static void main(String[] args) {
//		System.setProperty("webdriver.chrome.driver", "E:\\jerick\\case\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
		
		ChromeDriver chrome = new ChromeDriver("webdriver.chrome.driver", "E:\\jerick\\case\\chromedriver.exe");
		driver = chrome.getdriver();
		driver.get("http://www.baidu.com");
		Test test=new Test();
		test.snapshot((TakesScreenshot) driver, "123.png");
	}
//	public static void main1(String[] args) {
//		ExcelWriter excelw = new ExcelWriter("D://login.xls", "D://login.xls");
//		runAppCase run = new runAppCase(excelw);
//		run.runAdb("adb devices");
//		System.setProperty("webdriver.chrome.driver", "E:\\jerick\\case\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		Test test=new Test();
//		test.snapshot((TakesScreenshot) driver, "123.png");
//		
//		
//	}

	// 截图关键字
	public void snapshot(TakesScreenshot drivername, String filename) {
		// this method will take screen shot ,require two parameters ,one is
		// driver name, another is file name
		//这个方法 屏幕截图，需要两个参数，一个是驱动程序名，另一个是文件名。

		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		//现在你可以做任何你需要做的事情，例如复制某处。
		try {
			System.out.println("save snapshot path is:E:/" + filename);
			FileUtils.copyFile(scrFile, new File("E:\\" + filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {
			System.out.println("screen shot finished");
		}
	}

}
