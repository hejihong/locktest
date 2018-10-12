package com.dn.jerick;
/*
 * author   jerick
 * version  1.0.0
 * 自动化测试类,集成UI,接口和APP的测试关键字,通过读取Excel测试用例文档,进行自动化测试
 * 
 * 创建一个可读写的workbook,把要读写的Excel读到缓存空间,针对单元格进行读写,读写之后进行保存
 * 
 * 如何用Java进行接口测试
 * 用Java.net包进行http接口的发包收包,自己写一个json pass的类进行解包,校验结果
 * 用Excel驱动测试,方便用例维护
 * 
 * json是键值对,如果把它解析成map数据类型,要校验结果就很简单,所以通过jsonObject开源包,再写了一个递归调用的方法解析json
 * 主要是用来解析多层包含列表的,有数组的json类型
 * 
 * 编程做接口测试和工具做接口测试的优缺点
 * 工具做接口测试的最大的缺点是结果的验证以及用例的维护,因为一般的工具,比如loadrunner,JMeter他们都存在一些通病,就是我校验结果的时候
 * 只能用他们给我提供的一些方法,我的用例只能写成脚本的形式,或者说写成xml的形式,这样既不利于读,也不利于维护!
 * 编程就不一样了,本身接口测试就偏简单,我们只需要发包,收包,解析,验证四个阶段.发包可以用Java.net,解析可以用jsonObject
 * 验证可以自己写一些符合接口的字符串验证方法,所以它更加灵活,另外我们可以结合数据驱动,用Excel来进行用例的管理驱动测试,
 * 这样,我们无论是前期写用例还是后期维护用例,它的效率和质量都有大大的提高,而且我们的自动化程度也可以达到一个非常高的高度
 * 
 * */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.dn.APP.runAppCase;
import com.dn.UI.U_KeyWords;
import com.dn.inter.I_KeyWords;

public class AutoTest {
	public static String handle = null;
	public static List<String> handles =new ArrayList<String>();
	private static String filepath;
	
	//***************************   主函数    ***************************************************
	
	public static void main(String[] args)throws IOException {		
		String type = args[0];					//传入值  1.UI  2.接口   3.APP
		//获取项目的绝对路径
		File directory = new File("");
		filepath = directory.getCanonicalPath();
		String filename = "/case/";				//用例文件来源在case文件夹中
		String fileres = "/case/result-";		//用例执行结果放入case文件夹,文件名前加result-
		
		try {
			System.out.println("log::info:文件路径:" + filepath);
			switch(type){						//根据传入值选择执行的用例文件
			case "1":
				filename += "UICases.xls";
				fileres += "UICases.xls";
				break;
			case "2":
				filename += "InterfaceCases.xls";
				fileres += "InterfaceCases.xls";
				break;
			case "3":
				filename += "APPCases.xls";
				fileres += "APPCases.xls";
				break;
			default:
				filename += "UICase.xls";
				fileres += "UICase.xls";
				System.out.println("log::error:类型错误!已默认执行UI自动化!");
				break;
			}
			GetCase(filepath +filename,filepath +fileres,type);//设置用例文件的路径,结果的路径,执行类型
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("log::error:获取文件位置失败,请检查!");
			e.printStackTrace();
		}
		
		
		System.out.println("输入回车退出...");
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	

//	public static void main(String[] args)throws IOException {
//		sendUrl url = new sendUrl();
//		url.saveCookie();
//		String str = url.sendGet("http://www.hsjcjwh.top/dnAT/", "");
//		str = url.sendPost("http://www.hsjcjwh.top/dnAT/login","loginName=dn1&password=dn1111");					//传入登录接口,用户信息
//		str = url.Upload("http://www.hsjcjwh.top/dnAT/UPload", "name =UI &filename = login.xls");   	//传入接口地址,文件地址		
//		System.out.println(str);
//		
//	}
	
	//***************************    读写Excel函数         ********************************************************************
	
	public static void GetCase(String filename,String fileres,String type){		//传入用例名,结果名,执行类型
		//打开Excel
		ExcelReader excelr = new ExcelReader (filename);			//读Excel
		ExcelWriter excelw = new ExcelWriter (filename,fileres);	//写Excel
		List<String> line = null;				//链表
		I_KeyWords inter = new I_KeyWords(excelw);	//接口关键字
		runAppCase app	=new runAppCase(excelw);		//APP关键字
		U_KeyWords ui = new U_KeyWords(excelw);		//UI关键字
		
		excelr.useSheet(0);						//Excel用的sheet页面
		
		for (int i =0;i<excelr.getRows(0);i++){
			line = excelr.ReadLine(i);
			System.out.println(line);
			if (line.get(0).length()<1){
				switch(type){
				case "1":
					runUI(ui,line,i,excelw);
					break;
				case "2":
					runInter(inter,line,i,excelw);
					break;
				case "3":
					runApp(app,line,i,excelw);
					break;
				default:
					runUI(ui,line,i,excelw);
					break;
					
					
				}
			}
		}
		excelr.close();
		excelw.close();
	}

	//***************************	APP函数	*************************************************************
	
	private static void runApp(runAppCase app, List<String> line, int i, ExcelWriter excelw) {
		// TODO Auto-generated method stub
		//执行指定关键字
		app.line=i;
		switch (line.get(2)){
		case "runapp":
			app.runApp(line.get(5), line.get(3), line.get(4));
			break;
		case "runappium":
			app.runAppium( "Android",line.get(5),line.get(3), line.get(4));
			break;
		case "clickbyxpath":
			app.clickByXpath(line.get(3));
			break;
		case "clickbycoordinate":
			app.clickbycoordinate(line.get(3));
			break;
		case "inputbyxpath":
			app.inputByXpath(line.get(3),line.get(4));
			break;
		case "wait":
			app.wait(Integer.parseInt(line.get(4)));
			break;
		case "wait2000":
			app.wait(2000);
			break;
		case "quit":
			app.quit();
			break;
		case "runadb":
			app.runAdb(line.get(3));
			break;
		case "runcmd":
			app.runCmd(line.get(3));
			break;
		case "presskey":
			app.pressKey(Integer.parseInt(line.get(3).toString()));
			break;
		case "geturl":
			app.getUrl(line.get(3));
			break;
		case "gettext":
			app.getText(line.get(3));
			break;
		case "runavd":
			app.runAVD(line.get(3), Integer.parseInt(line.get(4).toString()));
			break;
		case "adbdevice":
			app.adbDevice(line.get(3));
			break;
		case "getcmd":
			app.getCmd(line.get(3));
			break;
		case "isportusing":
			app.isPortUsing(Integer.parseInt(line.get(3)));
			break;
		case "inputbyacid":
			app.inputByAcid(line.get(3), line.get(4));
			break;
		case "clickbuacid":
			app.clickByAcid(line.get(3));
			break;
		case "input":
			app.input(line.get(3));
			break;
		case "pageup":
			app.pageup();
			break;
		case "pagedown":
			app.pagedown();
			break;
		case "exit":
			app.exit();
			break;
		case "smoothplate":
			app.smoothplate(line.get(3), line.get(4), Integer.parseInt(line.get(5)));
			break;
		case "openlock":
			app.openLock(line.get(3), line.get(4),line.get(5), line.get(6), line.get(7));
			break;
		case "openlockxpath":
			app.openLockXpath();
			break;
		case "openlockok":
			app.openLockOk();
			break;
		case "openlockauto":
			app.openLockAuto();
			break;
		case "wait40000":
			app.wait(40000);
			break;
		default:
			System.out.println("关键字未实现!");
		}
	}

	//*****************************		接口函数	   ***********************************************************
	
	private static void runInter(I_KeyWords inter, List<String> line, int r, ExcelWriter excelw) {
		// TODO Auto-generated method stub
		//执行指定关键字
		inter.line =r;
		switch (line.get(2)){
		case "get":
			inter.get(line.get(3), line.get(4).toString());
			break;
		case "post":
			inter.post(line.get(3), line.get(4).toString());
			break;
//		case "upload":
//			inter.upload(line.get(3), line.get(4).toString());
//			break;
//		case "savecookie":
//			inter.savecookie();
//			break;
//		case "clearcookie":
//			inter.clearcookie();
		default:
			System.out.println("关键字未实现!");
		}
		//校验返回值
		int index = 0;		
			try {
				index = Integer.parseInt(line.get(8).toString()) -1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			try {
				switch (line.get(5)){
				case "assertequel":
					excelw.writeCell(r, 9,  inter.assertequel(index, line.get(6),line.get(7)));
					break;
				case "assertunequel":
					excelw.writeCell(r, 9,  inter.assertunequel(index, line.get(6),line.get(7)));
					break;
				case "assertcontains":
					excelw.writeCell(r, 9,  inter.assertcontains(index, line.get(6),line.get(7)));
					break;
				case "assertnull":
					excelw.writeCell(r, 9,  inter.assertnull(index, line.get(6)));
					break;
					default :
						excelw.writeCell(r, 9,"关键字未实现!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		
		
		
		
	}

	//************************		UI函数		************************************************************
	
	private static void runUI(U_KeyWords ui, List<String> line, int i, ExcelWriter excelw) {
		// TODO Auto-generated method stub
		//执行指定关键字
		
		ui.line=i;
		switch (line.get(2)){
		case "openbrowser":
			ui.openBrowser(line.get(3), filepath);
			break;
		case "closebrowser":
			ui.closeBrowser();
			break;
		case "geturl":
			ui.getUrl(line.get(3));
			break;
		case "input":
			ui.input(line.get(3), line.get(4));
			break;
		case "click":
			ui.click(line.get(3));
			break;
		case "closeoldwindow":
			ui.closeOldWin();;
			break;
		case "sleep":
			ui.sleep(line.get(3));
			break;
		case "assertcontains":
			ui.assertcontains(line.get(3));
			break;
		case "gettitle":
			ui.getTitle();
			break;
		case "bigwin":
			ui.bigwin();
			break;
		case "clinkclassname":
			ui.clickClassName(line.get(3));
			break;
		case "closenewwin":
			ui.closeNewWin();
			break;
		case "gettext":
			ui.getText(line.get(3));
			break;
		case "intoiframe":
			ui.intoIframe(line.get(3));
			break;
		case "outiframe":
			ui.outIframe();
			break;
		case "hover":
			ui.hover(line.get(3));
			break;
		case "getlink":
			ui.getLink(line.get(3));
			break;
		case "submit":
			ui.submit(line.get(3));
			break;
		case "exejs":
			ui.exeJS(line.get(3));
			break;
		case "select":
			ui.select(line.get(3), line.get(4));
			break;
		case "inputpicture":
			ui.inputpicture(line.get(3), line.get(4));
			break;
		case "refresh":
			ui.refresh();
			break;
		case "back":
			ui.back();
			break;
		case "forward":
			ui.forward();
			break;
		case "alert":
			ui.alert();
			break;
		case "snapshot":			
			ui.snapshotcc( line.get(3),line.get(4));
			break;
		case "sysout":
			System.out.println(line.get(3));
			break;
		case "pagedown":			
			ui.pageDown();
			break;
		case "pagenext":			
			ui.pageNext(line.get(3));
			break;
		case "addnum":
			ui.addnum(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8),line.get(9),line.get(10));
			break;
		case "addnumb":
			ui.addnumb(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8),line.get(9));
			break;
		case "addr":
			ui.addr(line.get(3),line.get(4),line.get(5));
			break;
		case "addroom":
			ui.addroom(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8),line.get(9),line.get(10),line.get(11),line.get(12),line.get(13));
			break;
		case "addrooms":
			ui.addrooms(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8),line.get(9),line.get(10),line.get(11),line.get(12));
			break;
		case "addadmin":
			ui.addAdmin(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8));
			break;	
		case "addgl":
			ui.addgl(line.get(3),line.get(4),line.get(5));
			break;
		case "bdcard":
			ui.bdcard(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8),line.get(9));
			break;
		case "bdadmin":
			ui.bdadmin(line.get(3),line.get(4),line.get(5),line.get(6),line.get(7),line.get(8));
			break;
		default:
			System.out.println("关键字未实现!");
		}
	}
	
	
	
	
	
	
	
	
}
