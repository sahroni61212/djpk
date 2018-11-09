package com.sh.djpk.econsole.ui.controller;

import java.io.File;
import java.io.FileInputStream;

import com.sh.djpk.share.util.JsonUtil;
import com.sh.djpk.share.util.UploadExcel;

public class testmainargs {
	
	public static void main(String[] args) 
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("E:\\lpe-Gorontalo-15.xls"));

        	UploadExcel exl = new UploadExcel();
//        	exl.readFile(file);
//        	exl.readfile(file);
        	System.out.println("Isi content " + JsonUtil.getJson(exl.readContent(file)));
        	System.out.println("Isi content " + JsonUtil.getJson(exl.readContent(file)));
//        	System.out.println("Total Data = " + exl.totalData());
//        	System.out.println("Total Invalid Data = " + exl.invalidData());
//        	System.out.println("Total Valid Data = " + exl.validData());
//        	System.out.println("Total Redundant Data = " + exl.redundantData());
//        	System.out.println("Total List Invalid Data = " + exl.getListInvalidData().size());
// 
//            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//            
//            for(int i=0;i<workbook.getNumberOfSheets();i++){
//	            //Get first/desired sheet from the workbook
//	            XSSFSheet sheet = workbook.getSheetAt(i);
//	 
//	            //Iterate through each rows one by one
//	            Iterator<Row> rowIterator = sheet.iterator();
//	            
//	            while (rowIterator.hasNext()) 
//	            {
//	                Row row = rowIterator.next();
//	                //For each row, iterate through all the columns
//	                Iterator<Cell> cellIterator = row.cellIterator();
//	                 
//	                while (cellIterator.hasNext()) 
//	                {
//	                    Cell cell = cellIterator.next();
//	                    //Check the cell type and format accordingly
//	                    switch (cell.getCellType()) 
//	                    {
//	                        case Cell.CELL_TYPE_NUMERIC:
//	                            System.out.print(cell.getNumericCellValue() + "t");
//	                            break;
//	                        case Cell.CELL_TYPE_STRING:
//	                            System.out.print(cell.getStringCellValue() + "t");
//	                            break;
//	                    }
//	                }
//	                System.out.print("\n");
//	            }
//	            file.close();
//        	
//            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

}
