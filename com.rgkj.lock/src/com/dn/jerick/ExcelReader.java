package com.dn.jerick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Dec 12, 2016 Excel组件
 * 
 * @author perlly
 * @version 1.0
 * @since 1.0
 */
//读取Excel的类
public class ExcelReader {

	private Workbook book = null;		//内存中创建一个表
	private Sheet sheet;				//Excel表的下标sheet
	private Cell[] cell;				//定义一个数组
	private int r_m, l_m;				//表格的行数,列数
	private List<String> line = null;	

	// 初始化excle文件
	public ExcelReader(String filePath) {		//传入文件地址变量
		try {
			Workbook book1 = Workbook.getWorkbook(new File(filePath));		//读取Excel文件内容
			book = book1;													//将读取的Excel内容保存到内存中的表里
			// System.out.println("?????"+book.getSheet(0).getCell(3,
			// 1).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("log::error：读取Excel文件失败。");
			e.printStackTrace();
			book = null;
		}
	}

	// 获取文件行数
	public int getRows(int shet) {			//传入文件的下标sheet
		if (book != null) {						
			sheet = book.getSheet(shet);	
			return sheet.getRows();			//返回文件共有多少行
		} else
			System.out.println("log::error：没有打开Excel文件，无法获取行数。");

		return 0;
	}

	// 获取文件列数
	public int getCols(int shet) {
		if (book != null) {
			sheet = book.getSheet(shet);
			return sheet.getColumns();		//返回文件共有多少列
		} else
			System.out.println("log::error：没有打开Excel文件，无法获取列数。");

		return 0;
	}

	// 指定sheet表格
	public void useSheet(int shet) {		//传入文件的下标sheet
		if (book != null) {
			sheet = book.getSheet(shet);
			r_m = sheet.getRows();			//表格的行数
			l_m = sheet.getColumns();		//表格的列数
		} else
			System.out.println("log::error：没有打开Excel文件，无法选的表格。");
	}

	// 读取某一行的数据
	public List<String> ReadLine(int l) {		//传入要读取的行数
		line = new ArrayList<String>();			//创建一个列表
		try {
			if (sheet != null) {
				if (l < r_m) {
					cell = sheet.getRow(l);		//读取一整行单元格到cell数组,cell存的是单元格,而不是单元格里的内容
					for (int i = 0; i < cell.length; i++) {
						line.add(cell[i].getContents());	//将单元格里的内容按顺序存到line列表中
					}
				}
			} else
				System.out.println("log::error：没有打开Excel文件，无法读取文件。");
		} catch (Exception e) {
			System.out.println("log::error：Excel操作不合法，请检查程序。");
			e.printStackTrace();
		}
		return line;
	}

	public void close() {
		if (book != null) {
			try {
				book.close();		//将打开的Excel表关闭
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
