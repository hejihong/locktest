package com.dn.UI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

//Firefox浏览器驱动类
public class FFDriver {
	private WebDriver driver = null;

	public FFDriver(String propath, String driverpath) {
		// 设置 Firefox驱动的路径
		System.setProperty("webdriver.gecko.driver", driverpath);
		//设置Firefox的安装目录
		//System.setProperty("webdriver.firefox.bin", propath);
		DesiredCapabilities capabilities = new FirefoxOptions()
			       // For example purposes only
			      .setProfile(new FirefoxProfile())
			      .addTo(DesiredCapabilities.firefox());

		// 创建�?�? Firefox 的浏览器实例
		try {
			driver = new FirefoxDriver(capabilities);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("log--error：创建driver失败！！");
		}

	}

	public WebDriver getdriver() {
		return this.driver;
	}
}