package com.dn.APP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.dn.jerick.ExcelWriter;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class runAppCase {

	public AndroidDriver driver;
	public ExcelWriter excel;
	public int line = 0;

	public runAppCase(ExcelWriter excelw) {
		excel = excelw;
		// runCmd("cmd /c start ");
	}

	// 执行adb命令
	public void runAdb(String c) {
		String cmd = "cmd /c start " + c;
		runCmd(cmd);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 按键
	public void pressKey(int k) {
		String cmd = "cmd /c start adb shell input keyevent " + k;
		runCmd(cmd);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取url
	public void getUrl(String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("log::error：网址打开失败！");
			e.printStackTrace();
		}
	}

	// 获取文本
	public String getText(String xpath) {
		try {
			Thread.sleep(2000);
			driver.findElementByXPath(xpath);
			return driver.findElementByXPath(xpath).getText();
		} catch (Exception e) {
			System.out.println("log::error：元素获取文本失败！");
			e.printStackTrace();
		}
		return "";

	}

	// 等待
	public void wait(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 启动模拟器上面的app,并等待启动完成
	public void runApp(String device, String activity, String wait) {
		appDriver app = new appDriver(device, activity, "http://127.0.0.1:4723/wd/hub", wait);
		driver = app.getdriver();
	}

	// 通过路径，启动模拟器，并等待启动完成
	public void runAVD(String avd, int t) {
		runCmd(avd);
		try {
			Thread.sleep(t);
			// System.out.println("1");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 启动appium服务
	public void runAppium(String platform, String device, String apppkg, String time) {
		if (!isPortUsing(4723)) {
			String cmd = "cmd /c start appium -a 127.0.0.1 -p 4723 --platform-name " + platform
					+ "  --platform-version 5.1.1 --device-name " + device + " --app-pkg " + apppkg
					+ " --automation-name Appium --log-no-color";
			runCmd(cmd);
			try {
				Thread.sleep(Integer.parseInt(time));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("log::error：Appium 端口4723以被占用，请检查服务是否已经启动");
		}
		return;
	}

	// // 启动appium服务
	// public void runAppium(String platform, String device, String
	// apppkg,String activity,String time) {
	// if (!isPortUsing(4723)) {
	// String cmd = "cmd /c start appium -a 127.0.0.1 -p 4723 --platform-name "
	// + platform + " --platform-version 18 --device-name "
	// + device + " --app-activity "+ activity +" --app-pkg " + apppkg + "
	// --automation-name Appium --log-no-color";
	// runCmd(cmd);
	// try {
	// Thread.sleep(Integer.parseInt(time));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
	// System.out.println("log::error：Appium 端口4723以被占用，请检查服务是否已经启动");
	// }
	// return;
	// }

	// 启动adb，连接设备
	public int adbDevice(String device) {
		ArrayList<String> ret = null;
		try {
			ret = getCmd("adb connect " + device);
			System.out.println(ret);
			return 0;
		} catch (Exception e) {
			System.out.println("log::error：adb连接Device错误!");
			e.printStackTrace();
		}
		// if (ret.get(ret.size() - 1).contains("connected"))
		// return 0;
		// else {
		// System.out.println("log::error：adb连接Device失败!");
		// return -1;
		// }
		return -1;
	}

	// 执行并获取dos命令返回值。
	public ArrayList<String> getCmd(String str) {
		String cmd = str;
		Runtime runtime = Runtime.getRuntime();
		Process p = null;
		try {
			p = runtime.exec(cmd);
		} catch (Exception e) {
			System.out.println("log::error：执行cmd命令错误!");
		}
		if (p != null) {
			return convertStreamToString(p.getInputStream());
		} else {
			return null;
		}
	}

	// 脚本执行CMD命令的函数
	public void runCmd(String str) {
		String cmd = str;
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(cmd);
		} catch (Exception e) {
			System.out.println("log::error：执行cmd命令错误!");
		}
		return;
	}

	// 转为字符串列表
	public ArrayList<String> convertStreamToString(InputStream in) {
		BufferedReader br = null;
		ArrayList<String> res = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = br.readLine()) != null) {
				res.add(line);
			}
			// System.out.println(res);
			return res;
		} catch (Exception e) {
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 查看端口是否被占用
	public static boolean isPortUsing(int port) {
		boolean flag = false;
		try {
			InetAddress theAddress = InetAddress.getByName("127.0.0.1");
			Socket socket = new Socket(theAddress, port);
			flag = true;
			socket.close();
		} catch (IOException e) {

		}
		return flag;
	}

	// 退出
	public void quit() {
		// TODO Auto-generated method stub
		try {
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputByAcid(String id, String value) {
		try {
			// driver.findElementByAccessibilityId(id).clear();
			driver.findElementByAccessibilityId(id).sendKeys(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickByAcid(String id) {
		try {
			driver.findElementByAccessibilityId(id).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickByXpath(String xpath) {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			driver.findElementByXPath(xpath).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputByXpath(String xpath, String value) {
		try {
			driver.findElementByXPath(xpath).sendKeys(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 通过坐标点击
	public void clickbycoordinate(String coordinate) {
		try {
			String[] array = coordinate.split(",");
			runAdb("adb shell input tap " + array[0] + " " + array[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 点击坐标后输入
	public void input(String value) {
		try {
			runAdb("adb shell input text " + value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 向上翻页
	public void pageup() {
		try {
			runAdb("adb shell input keyevent 92");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 向下翻页
	public void pagedown() {
		try {
			runAdb("adb shell input keyevent 93");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 使用appium方法长按
	public void appiumHold(String x, String y, String time) {
		int xAxis = Integer.parseInt(x);
		int yAxis = Integer.parseInt(y);
		int t = Integer.parseInt(time);
		Duration last = Duration.ofMillis(t);
		TouchAction action = new TouchAction(driver);
		// action类分解动作，先长按，再松开
		action.longPress(xAxis, yAxis, last).release().perform();
	}
	// 退出
	public void exit() {
		try {
			runCmd("cmd /c start");
			runCmd("taskkill /f /t im node.exe");
			runCmd("taskkill /f /im cmd.exe");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 滑屏操作
	public void smoothplate(String coordinate1, String coordinate2, int time) {
		try {
			String[] array1 = coordinate1.split(",");
			String[] array2 = coordinate2.split(",");
			runCmd("adb shell input swipe " + array1[0] + " " + array1[1] + array2[0] + " " + array2[1] + " " + time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 管理端开锁
	public void openLock(String time, String coordinate1, String coordinate2, String coordinate3, String num) {
		int time1 = Integer.parseInt(time);
		for (int i = 0; i < Integer.parseInt(num); i++) {
			// 点击开锁按钮
			clickbycoordinate(coordinate1);
			// 等待开锁
			wait(time1);
			// 点击返回首页
			clickbycoordinate(coordinate2);
			// 等待1秒
			wait(1000);
			// 点击我知道了
			clickbycoordinate(coordinate3);
			// 等待1秒
			wait(1000);
		}
	}

	public void openLockXpath() {		
		// 点击开锁按钮	
		try {
			driver.findElementByXPath("//android.widget.ImageView[@resource-id='com.ruigao.developtemplateapplication:id/iv_main_scan_code_unlock']").click();
			excel.writeCell(line, 4, "点击开锁成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				excel.writeCell(line, 4, "点击开锁失败");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void openLockOk() {
		// 返回首页	
		try {
			//点击我知道了
			
			driver.findElementByXPath("//android.widget.ImageView[@resource-id='com.ruigao.developtemplateapplication:id/iv_scan_result_ble_unlcok_success']").click();
			excel.writeCell(line, 5, "蓝牙开锁成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				driver.findElementByXPath("//android.widget.ImageView[@resource-id='com.ruigao.developtemplateapplication:id/iv_scan_unlock_back_fail']").click();
				excel.writeCell(line,5, "进入远程开锁，返回首页成功");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				excel.writeCell(line, 5, "蓝牙开锁失败");
			}
		}
	
	}
	public void openLockAuto() {
		wait(2000);
		openLockXpath();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		excel.writeCell(line,3, df.format(new Date()));		
		wait(55000);
		openLockOk();
	}
	
	
	
	
}
