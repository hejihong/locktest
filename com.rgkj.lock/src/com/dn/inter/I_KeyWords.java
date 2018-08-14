package com.dn.inter;

import com.dn.jerick.ExcelWriter;
import com.dn.jerick.jsonPase;

//********************	接口关键字	*************************************************

public class I_KeyWords {
	public sendUrl url;
	public jsonPase json;
	public String str;
	public ExcelWriter excel;
	public int line = 0;

	public I_KeyWords(ExcelWriter excelw) { // 成员函数
		excel = excelw;
		url = new sendUrl();
		json = new jsonPase();
	}

	// post关键字传入url,参数(用户名,密码)
	public String post(String u, String param) {

		str = url.sendPost(u, param);
		if (str == null || str.length() < 2) {
			excel.writeCell(line, 10, subStr(url.getExp())); // 捕获异常
		} else
			excel.writeCell(line, 10, subStr(str));

		System.out.println(str);
		json.jsonList.clear();
		json.Pase(str, 0, false);

		return str;
	}
	
	//只提取200个以内的字符返回!
	public static String subStr(String s){
		try {
			return s.substring(0,200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return s;
		}
	}

	// get关键字传入url,参数(用户名,密码)
//	public String get(String u, String param) {
//		str = url.sendGet(u, param);
//		System.out.println(url.getExp()); // 捕获异常
//		json.jsonList.clear();
//		json.Pase(str, 0, false);
//		return str;
//	}
	 public String get(String u, String param) {
	 str = url.sendGet(u, param);
	 if(str==null||str.length()<2){
	 excel.writeCell(line,10,subStr(url.getExp())); //捕获异常
	 }else
	 excel.writeCell(line,10,subStr(str));
	 json.jsonList.clear();
	 json.Pase(str, 0, false);
	 return str;
	 }

	 //断言比较(检验),校验包含的内容
	public String assertequel(int index, String key, String value) { // 断言传入json层数,校验内容,校验值
		json.Pase(str, 0, false);
		if (json.jsonList.get(0).get("status") != null && json.jsonList.get(index).get(key).toString().equals(value)) {
			return "Pass";

		} else {
			return "Fail";
		}

	}

	//断言比较(检验),校验不包含的内容
	public String assertunequel(int index, String key, String value) { // 断言传入json层数,校验内容,校验值
		json.Pase(str, 0, false);
		if (json.jsonList.get(0).get("status") != null && !json.jsonList.get(index).get(key).toString().equals(value)) {
			return "Pass";

		} else {
			return "Fail";
		}

	}

	//断言比较(检验),校验包含的内容
	public String assertcontains(int index, String key, String value) { // 断言传入json层数,校验内容,校验值
		if (json.jsonList.get(0).get("msg") != null && !json.jsonList.get(index).get(key).toString().contains(value)) {
			return "Pass";

		} else {
			return "Fail";
		}

	}

	//断言比较(检验),校验值为空
	public String assertnull(int index, String key) { // 断言传入json层数,校验内容
		if (json.jsonList.get(index).get(key) == null) {
			return "Pass";

		} else {
			return "Fail";
		}

	}

	//清楚登录态
	public void clearcookie() {
		try {
			url.clearCookie();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
