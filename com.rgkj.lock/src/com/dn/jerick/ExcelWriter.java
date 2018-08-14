package com.dn.jerick;

import java.io.File;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Dec 12, 2016 Excel组件
 * 
 * @author perlly
 * @version 1.0
 * @since 1.0
 */
//向Excel写入数据的类
public class ExcelWriter {

	private WritableWorkbook wbook = null;		//内存中创建一个写入的表
	private Workbook book = null;				//内存中创建一个空表
	private WritableSheet sheet;				//内存中Excel表的下标sheet
	private Label label;						//jxl用label来表示


	// 初始化excle文件
	public ExcelWriter(String file1, String file2) {		//传入Excel的地址,存入的Excel地址
		try {
			book = Workbook.getWorkbook(new File(file1));			//将Excel的内容存入内存中的book中
			wbook = Workbook.createWorkbook(new File(file2), book);	//将内存中的book内容存入内存中wbook表并写入到file2地址(在内存中完成空间上的复制)
			sheet = wbook.getSheet(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("log::error：写入Excel文件失败。");
			e.printStackTrace();
			book = null;
			wbook=null;
		}
	}

	// 写入指定行一行数据
	public int writeRows(int r, ArrayList<String> list) {		//传入行数,要写入的数据
		if (wbook != null) {
			for (int i = 0; i < list.size(); i++) {
				try {
					label = new Label(r, i, list.get(i));
					sheet.addCell(label);
					return 0;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("log::error：写入行内容失败。");
					e.printStackTrace();
				}
			}
		} else
			System.out.println("log::error：没有打开Excel文件，无法写入数据。");
		return -1;
	}

	// 写入一个单元格数据
	public int writeCell(int i, int j, String c) {		//传入横纵坐标和数据
		if (wbook != null) {
			try {
				label = new Label(j, i, c);
				sheet.addCell(label);
				return 0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("log::error：写入单元格失败。");
				e.printStackTrace();
			}
		} else
			System.out.println("log::error：没有打开Excel文件，无法写入数据。");
		return -1;
	}

	public void close() {
		if (wbook != null) {
			try {
				wbook.write();
				wbook.close();
				book.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
