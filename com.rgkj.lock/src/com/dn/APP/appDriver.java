package com.dn.APP;

import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;

public class appDriver { // Android驱动程序
	private AndroidDriver driver = null;
	//设备名称、app的main Activity类、appium服务器ip端口、等待启动时间
	public appDriver(String deviceName, String appActivity, String AppiumServerIP, String time) {
		// TODO Auto-generated method stub
		String platformVersion = "4.3";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("platformVersion", platformVersion);
		// capabilities.setCapability("app", apk);
		capabilities.setCapability("appActivity", appActivity);
		capabilities.setCapability("noSign", true);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("unicodeKeyboard", "True");
		capabilities.setCapability("resetKeyboard", "True");
		capabilities.setCapability("device", "Selendroid");
		//电脑连接了多个设备的时候，指定设备。
		capabilities.setCapability("udid", deviceName);
		try {
			driver = new AndroidDriver(new URL(AppiumServerIP), capabilities);
			System.out.println("App启动等待时间");
			Thread.sleep(Integer.parseInt(time));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public AndroidDriver getdriver() {
		return this.driver;
	
	}
}