package com.dn.UI;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dn.UI.ChromeDriver;
import com.dn.UI.FFDriver;
import com.dn.jerick.ExcelWriter;

public class U_KeyWords {
	private WebDriver driver;
	public String text = "";
	public ExcelWriter excel;
	public int line = 0;

	public U_KeyWords(ExcelWriter excelw) {
		excel = excelw;
	}

	// 打开浏览器
	public void openBrowser(String B, String dpath) {
		// 打开Chrome
		if (B.equals("cc") || B.equals("chrome")) {
			ChromeDriver chrome = new ChromeDriver("", dpath + "/case/chromedriver.exe");
			driver = chrome.getdriver();

		}

		// 打开IE
		if (B.equals("ie") || B.equals("IE")) {
			IEDriver ie = new IEDriver("", dpath + "\\case\\IEdriver.exe");
			driver = ie.getdriver();
		}

		if (B.equals("ff") || B.equals("FF")) {
			// 调driver的方法,设置driver路径 \\转译
			System.setProperty("webdriver.gecko.driver", dpath + "\\case\\geckodriver.exe");
			// new driver对象,调用浏览器driver
			driver = new FirefoxDriver();
		}

	}

	// 打开网址
	public void getUrl(String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("打开浏览器错误!");
			e.printStackTrace();
		}
	}

	// 关闭浏览器
	public void closeBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("关闭浏览器出错！");
		}
	}

	// 浏览器最大化
	public void bigwin() {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("浏览器最大化失败!");
		}
	}

	// 等待ms
	public void sleep(String time) {
		try {
			Thread.sleep(Integer.parseInt(time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 元素点击
	public void click(String xpath) {
		try {
			explicityWate(xpath);
			driver.findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 通过className点击
	public void clickClassName(String className) {
		try {
			driver.findElement(By.className(className)).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 输入字符串
	public void input(String xpath, String value) {
		try {
			explicityWate(xpath);
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("输入元素失败!");
			e.printStackTrace();
		}
	}

	// 关闭旧窗口,切换到新窗口操作
	public void closeOldWin() {
		List<String> handles = new ArrayList<String>();
		Set<String> s = driver.getWindowHandles();
		// 循环获取集合里的句柄,保存到List数组handles里面
		for (Iterator<String> it = s.iterator(); it.hasNext();) {
			handles.add(it.next().toString());
		}
		driver.close();
		driver.switchTo().window(handles.get(1));
	}

	// 关闭新窗口,切换旧窗口操作
	public void closeNewWin() {
		List<String> handles = new ArrayList<String>();
		Set<String> s = driver.getWindowHandles();
		// 循环获取集合里的句柄,保存到List数组handles里面
		for (Iterator<String> it = s.iterator(); it.hasNext();) {
			handles.add(it.next().toString());
		}
		driver.switchTo().window(handles.get(1));
		driver.close();
		driver.switchTo().window(handles.get(0));
	}

	// 获取元素文本值
	public String getText(String xpath) {
		try {
			explicityWate(xpath);
			return driver.findElement(By.xpath(xpath)).getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "null";
		}
	}

	// 进入iframe子页面
	public void intoIframe(String xpath) {
		try {
			explicityWate(xpath);
			driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("进入iframe失败!");
			e.printStackTrace();
		}
	}

	// 退出iframe子页面
	public void outIframe() {
		driver.switchTo().defaultContent();
	}

	// 操作弹出窗口
	public void testMultipleWindowsTitle(WebDriver driver) throws Exception {
		// 获取当前窗口的句柄
		String parentWindowId = driver.getWindowHandle();
		System.out.println("driver.getTitle(): " + driver.getTitle());

		WebElement button = driver.findElement(By.xpath("//input[@value='打开窗口']"));
		button.click();

		Set<String> allWindowsId = driver.getWindowHandles();

		// 获取所有的打开窗口的句柄
		for (String windowId : allWindowsId) {
			if (driver.switchTo().window(windowId).getTitle().contains("微信登录")) {
				driver.switchTo().window(windowId);
				break;
			}
		}

		System.out.println("driver.getTitle(): " + driver.getTitle());

		// 再次切换回原来的父窗口
		driver.switchTo().window(parentWindowId);
		System.out.println("parentWindowId: " + driver.getTitle());
	}

	// 鼠标悬停
	public void hover(String xPath) {
		try {
			explicityWate(xPath);
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath(xPath))).build().perform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("log--error:元素" + xPath + "hover失败");
		}
	}

	// 获取跳转链接
	public String getLink(String xpath) {
		String link = "about:blank";
		try {
			explicityWate(xpath);
			link = driver.findElement(By.xpath(xpath)).getAttribute("href");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取链接失败!");
		}
		return link;
	}

	// submit表单
	public void submit(String xpath) {
		try {
			explicityWate(xpath);
			driver.findElement(By.xpath(xpath)).submit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("submit错误!");
		}
	}

	// JS操作元素
	public void exeJS(String text) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("JS操作错误!");
		}
	}

	// select下拉菜单选项 choose:第几个选项
	public void select(String xpath, String choose) {
		try {
			explicityWate(xpath);
			Select selectAge = new Select(driver.findElement(By.xpath(xpath)));
			selectAge.selectByIndex(Integer.parseInt(choose));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("select错误!");
		}
	}

	// 上传图片 name默认file filePath图片所在地址
	public void inputpicture(String name, String filePath) {
		try {
			driver.findElement(By.name(name)).sendKeys(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("上传图片失败!");
		}
	}

	// 刷新浏览器
	public void refresh() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("刷新失败!");
		}
	}

	// 浏览器后退
	public void back() {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 浏览器前进
	public void forward() {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 确认alert弹出框
	public void alert() {
		try {
			Alert alert = driver.switchTo().alert();
			// update is executed
			alert.accept();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("点击alert错误!");
		}
	}

	// 获取title
	public void getTitle() {
		try {
			text = driver.getTitle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			text = "";
			e.printStackTrace();
		}
	}

	// 校验包含
	public void assertcontains(String value) {
		if (text.contains(value)) {
			excel.writeCell(line, 4, "Pass");
		} else {
			excel.writeCell(line, 4, "Fail");
		}
	}

	public void snapshotcc(String filename, String dpath) {
		snapshot((TakesScreenshot) driver, filename, dpath);
	}

	// 截图关键字
	public void snapshot(TakesScreenshot drivername, String filename, String dpath) {
		// this method will take screen shot ,require two parameters ,one is
		// driver name, another is file name
		// 这个方法 屏幕截图，需要两个参数，一个是驱动程序名，另一个是文件名。

		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		// 现在你可以做任何你需要做的事情，例如复制某处。
		try {
			System.out.println("save snapshot path is:" + dpath + "\\" + filename + ".png");
			FileUtils.copyFile(scrFile, new File(dpath + "\\" + filename + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {
			System.out.println("screen shot finished");
		}
	}

	// 报错时截图操作
	public void saveScrShot(String method) {

		// 获取当前的执行时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
		// 当前时间的字符串
		String createdate = sdf.format(date);

		// 拼接文件名，形式为：工作目录路径+方法名+执行时间.png
		String scrName = "SCRshot/" + method + createdate + ".png";
		// 以当前文件名创建文件
		File scrShot = new File(scrName);
		// 将截图保存到临时文件
		File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(tmp, scrShot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 显式等待,等待可点击元素出现即可执行下一指令
	public void explicityWate(String xpath) {
		// 设置等待的最大时长，10秒
		WebDriverWait wait = new WebDriverWait(driver, 10);
		// 等待条件为指定的元素出现
		wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath(xpath));
			}
		});
	}

	// 移动到页面最底部
	public void pageDown() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("移动到页面底部失败");
			e.printStackTrace();
		}
	}
	
	//移动页面至指定坐标
	public void pageNext(String num) {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, "+num+")");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("移动到指定坐标失败");
			e.printStackTrace();
		}
	}


	// 添加设备号,默认电信，批量添加，测试用
	public void addnum(String xpath1, String xpath2, String xpath3, String xpath4, String num1, String num2,
			String num3, String num) {

		for (int i = 0; i < Integer.parseInt(num); i++) {
			// 点击添加门锁按钮
			click(xpath1);
			sleep(num1);
			// 填写门锁编号
			input(xpath2, num2 + i);
			// 填写IMEI号
			input(xpath3, num2 + i);
			// 点击确定
			click(xpath4);
			sleep(num1);
			// 点击确定弹窗
			alert();
		}
	}

	// 添加单个设备号，默认电信
	public void addnumb(String xpath1, String xpath2, String xpath3, String xpath4, String num1, String num2,
			String num3,String num4) {

		// 点击添加门锁按钮
		click(xpath1);
		sleep(num1);
		// 填写门锁编号
		input(xpath2, num2);
		// 填写IMEI号
		input(xpath3, num3);
		input("//*[@id=\"imsi\"]",num4);
		sleep(num1);
		click("//*[@id=\"formid\"]/div/div[4]/div[2]/div/button/div/div/div");
		sleep(num1);
		click("//*[@id=\"formid\"]/div/div[4]/div[2]/div/div/div/ul/li[2]/a");
		sleep(num1);
		// 点击确定
		click(xpath4);
		sleep(num1);
		// 点击确定弹窗
		alert();
		sleep(num1);

	}

	// 添加单个房间，已写好xpath简易版，含添加设备号
	public void addr(String num, String num1, String num2) {
		// 点击添加房源管理
		click("//*[@id=\"house_manage_sp\"]/a/span");
		// 搜索栏填入相关房源
		sleep("2000");
		input("//*[@id=\"search_name\"]", num);
		// 点击搜索
		click("//*[@id=\"search_house\"]");
		// 点击添加房间按钮
		sleep("2000");
		click("//*[@id=\"add_house\"]");
		// 输入房间号
		input("//*[@id=\"roomNum\"]", num1);
		// 点击门锁选项卡
		click("/html/body/div[3]/div/div/div[2]/div[2]/div[2]/div/button/span[1]");
		// 输入门锁编号
		sleep("1000");
		input("/html/body/div[3]/div/div/div[2]/div[2]/div[2]/div/div/div/input", num2);
		click("/html/body/div[3]/div/div/div[2]/div[2]/div[2]/div/div/div[2]/ul/li/a/span[2]");
		click("/html/body/div[3]/div/div/div[2]");
		// 点击确定
		sleep("1000");
		click("/html/body/div[3]/div/div/div[3]/button[1]");
		sleep("2000");
		// 点击确定弹窗
		alert();
	}

	// 添加单个房间
	public void addroom(String xpath1, String xpath2, String num, String xpath3, String num1, String xpath4,
			String xpath5, String num2) {
	
		// 点击添加房间按钮
		sleep("2000");
		click(xpath1);
		// 输入房间号
		input(xpath2, num);
		// 输入楼层
		input(xpath3, num1);
		// 点击门锁选项卡
		click(xpath4);
		// 输入门锁编号
		sleep("1000");
		input(xpath5, num2);
		sleep("1000");
		click("/html/body/div[1]/div[4]/div/div/div[2]/div[4]/div[2]/div/div/div[2]/ul/li/a/span[2]");
		
	
		// 点击确定
		sleep("1000");
		sleep("2000");
		click("/html/body/div[1]/div[4]/div/div/div[3]/button[1]");
		sleep("2000");
		// 点击确定弹窗
		click("//*[@id=\"layui-layer2\"]/div[3]/a");
		//alert();
	}

	// 批量添加未绑定门锁的空房间
	public void addrooms(String xpath1, String xpath2, String xpath3, String xpath4, String xpath5, String xpath6,
			String num, String num1, String num2, String num3) {

		// 填写房间编号 n为房间数
		int n = 0;
		// j为楼层数 num2为楼层房间数 num3为第几栋楼
		for (int j = 0, l = 1; j < Integer.parseInt(num1) / Integer.parseInt(num2) + 1; j++, l++) {
			if (n >= Integer.parseInt(num1)) {
				break;
			}
			// k为房间尾号 k<10 10为楼层房间数
			for (int k = 1; k <= Integer.parseInt(num2); k++) {
				// 点击添加房源管理
				click(xpath1);
				// 搜索栏填入相关房源
				sleep("2000");
				input(xpath2, num);
				// 点击搜索
				click(xpath3);

				// 点击添加房间按钮
				sleep("2000");
				click(xpath4);
				if (0 <= k && k < 10) {
					if (l < 10) {
						input(xpath5, num3 + "-0" + l + "-0" + k);
					} else {
						input(xpath5, num3 + "-" + l + "-0" + k);
					}
				} else if (l < 10) {
					input(xpath5, num3 + "-0" + l + "-" + k);
				} else {
					input(xpath5, num3 + "-" + l + "-" + k);
				}

				// 点击确定
				click(xpath6);
				sleep("2000");
				// 点击确定弹窗
				alert();
				n++;
				// 创建房间数等于传入数值i，结束循环
				if (n >= Integer.parseInt(num1)) {
					break;
				}
			}

		}
	}

	// 绑定管理员,同一房源批量绑定同一管理员
	public void addAdmin(String xpath1, String xpath2, String xpath3, String xpath4, String xpath5, String num) {
		int room = Integer.parseInt(xpath2);
		for (int i = 0; i < Integer.parseInt(num); i++) {
			// 点击添加房源管理
			click("//*[@id=\"house_manage_sp\"]/a/span");
			// 搜索栏填入相关房源
			sleep("2000");
			input("//*[@id=\"search_name\"]", xpath1);
			// 点击搜索
			click("//*[@id=\"search_house\"]");

			// 点击房间按钮
			sleep("2000");
			click("//*[@id=\"" + (room + i) + "\"]/div[1]/span");
			// 点击添加按钮
			sleep("2000");
			click("//*[@id=\"sure_add\"]");
			//点击取消
			sleep("1000");
			click("//*[@id=\"cancel_add\"]");
			// 再次点击添加按钮
			sleep("2000");
			click("//*[@id=\"sure_add\"]");
			// 填写姓名
			sleep("2000");
			input("//*[@id=\"name\"]", xpath3);
			// 填写联系电话
			input("//*[@id=\"mobile\"]", xpath4);
			// 填写身份证
			input("//*[@id=\"card\"]", xpath5);
			// 点击添加
			click("//*[@id=\"addChild3\"]/div/div[3]/button[1]");
			sleep("2000");
			// 点击确定弹窗
			alert();
			sleep("1000");
			click("//*[@id=\"cancel_add\"]");
			sleep("1000");
		}
	}

	// 增加管理员并选择角色权限
	public void addgl(String name, String pwd, String role) {
		// 点击账号管理
		click("//*[@id=\"account_power\"]/a/span");
		// 点击新增账号
		click("//*[@id=\"is_add_account\"]");
		// 填写用户名
		input("//*[@id=\"account_name\"]", name);
		// 填写密码
		input("//*[@id=\"account_pw\"]", pwd);
		// 确认用户密码
		input("//*[@id=\"again_account_pw\"]", pwd);
		// 选择用户角色
		click("/html/body/div[1]/div[3]/div/div/div[2]/div[4]/div[2]/div/button/div/div/div");
		input("/html/body/div[1]/div[3]/div/div/div[2]/div[4]/div[2]/div/div/div[1]/input", role);
		click("/html/body/div[1]/div[3]/div/div/div[2]/div[4]/div[2]/div/div/div[2]/ul/li/a");
		// 点击确定
		click("//*[@id=\"sure_adds\"]");
		sleep("1000");
		// 点击确定弹窗
		alert();
	}

	// 添加门卡,根据设备号绑定门卡,每个房间添加4张
	public void bdcard(String xpath1, String num, String num1, String num2, String num3, String num4, String num5) {
		String number1,number2;
		number2="18070101000";
		number1="18070101000";
		switch (num.length()){
		case 1:
			number2=number1.concat("000"+num);
			break;
		case 2:
			number2=number1.concat("00"+num);
			break;
		case 3:
			number2=number1.concat("0"+num);
			break;
		case 4:
			number2=number1.concat(num);
			break;
		default:
			System.out.println("设备号超出范围！");
		}
		// 点击房源管理
		click("//*[@id=\"house_manage_sp\"]/a/span");
		// 点击输入设备号
		input("//*[@id=\"search_deviceNum\"]", number2);
		// 查询
		sleep("2000");
		click("//*[@id=\"search_house\"]");
		// sleep("2000");
		// 点击房间
		click(xpath1);
		// sleep("1000");

		// 点击绑定门卡
		click("//*[@id=\"add_icCard\"]");
		// sleep("1000");
		// 输入门卡名称
		input("//*[@id=\"cardAlias\"]", num1 + 1);
		// 输入门卡卡号
		input("//*[@id=\"cardNum\"]", num2);
		// sleep("500");
		// 点击确定
		click("//*[@id=\"sure_tijiao\"]");
		// 点击确定弹窗
		sleep("2000");
		alert();
		sleep("2000");

		// 点击绑定门卡
		click("//*[@id=\"add_icCard\"]");
		// sleep("1000");
		// 输入门卡名称
		input("//*[@id=\"cardAlias\"]", num1 + 2);
		// 输入门卡卡号
		input("//*[@id=\"cardNum\"]", num3);
		// sleep("500");
		// 点击确定
		click("//*[@id=\"sure_tijiao\"]");
		// 点击确定弹窗
		sleep("2000");
		alert();
		sleep("2000");

		// 点击绑定门卡
		click("//*[@id=\"add_icCard\"]");
		// sleep("1000");
		// 输入门卡名称
		input("//*[@id=\"cardAlias\"]", num1 + 3);
		// 输入门卡卡号
		input("//*[@id=\"cardNum\"]", num4);
		// sleep("500");
		// 点击确定
		click("//*[@id=\"sure_tijiao\"]");
		// 点击确定弹窗
		sleep("2000");
		alert();
		sleep("2000");

		// 点击绑定门卡
		click("//*[@id=\"add_icCard\"]");
		// sleep("1000");
		// 输入门卡名称
		input("//*[@id=\"cardAlias\"]", num1 + 4);
		// 输入门卡卡号
		input("//*[@id=\"cardNum\"]", num5);
		// sleep("500");
		// 点击确定
		click("//*[@id=\"sure_tijiao\"]");
		// 点击确定弹窗
		sleep("2000");
		alert();

		sleep("2000");

	}

	// 将空房间绑定门锁设备号，并绑定固定管理员
	public void bdadmin(String xpath1, String num1, String num2, String num3, String num4, String num5) {
		String number1,number2;
		number2="18070101000";
		number1="18070101000";
		switch (num2.length()){
		case 1:
			number2=number1.concat("000"+num2);
			break;
		case 2:
			number2=number1.concat("00"+num2);
			break;
		case 3:
			number2=number1.concat("0"+num2);
			break;
		case 4:
			number2=number1.concat(num2);
			break;
		default:
			System.out.println("设备号超出范围！");
		}
		
		// 房间xpath，房源名，门锁编号，姓名，电话，身份证
		sleep("2000");
		// 点击房源管理
		click("//*[@id=\"house_manage_sp\"]/a/span");
		sleep("2000");
		// 写入房源名称
		input("//*[@id=\"search_name\"]", num1);
		// 点击搜索
		click("//*[@id=\"search_house\"]");
		//向下移动像素
//		sleep("2000");
//		pageNext(num6);
		// 点击房间按钮
		sleep("2000");
		click(xpath1);
		// 点击修改
		click("//*[@id=\"limit_edit\"]");
		// 填入门锁编号
		input("//*[@id=\"update_deviceNum\"]", number2);
		// 点击保存修改
		click("//*[@id=\"addChild1\"]/div/div[3]/button[1]");
		// 点击确定弹窗
		sleep("2000");
		alert();
		sleep("2000");
		// 点击添加按钮
		click("//*[@id=\"sure_add\"]");
		//点击取消
		sleep("1000");
		click("//*[@id=\"cancel_add\"]");
		// 再次点击添加按钮
		// 填写姓名
		sleep("2000");
		input("//*[@id=\"name\"]", num3);
		// 填写联系电话
		input("//*[@id=\"mobile\"]", num4);
		// 填写身份证
		input("//*[@id=\"card\"]", num5);
		// 点击添加
		click("//*[@id=\"addChild3\"]/div/div[3]/button[1]");
		sleep("2000");
		// 点击确定弹窗
		alert();
		sleep("2000");
		click("//*[@id=\"cancel_add\"]");
		sleep("2000");

	}
	
	//生成二维码
	public void addewm(String  num) {
		getUrl("https://cli.im/");
		input("//*[@id=\"text-content\"]",num);
		click("//*[@id=\"click-create\"]");
		sleep("5000");

	}
	
	//向配置中添加门锁
	public void addpz(String xpath1, String xpath2, String xpath3,String xpath4,String xpath5,String num) {
		// 点击配置门锁
		click(xpath1);
		sleep("1000");
		// 填写设备号
		input(xpath2, num);
		sleep("1000");
		//搜索
		click(xpath3);
		sleep("1000");
		//勾选
		click(xpath4);
		sleep("1000");
		//添加
		click(xpath5);
		
		sleep("1000");
		sleep("1000");
		// 点击确定弹窗
		alert();
		sleep("1000");
	}
	
	

}
