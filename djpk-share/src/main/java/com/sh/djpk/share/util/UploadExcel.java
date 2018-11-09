package com.sh.djpk.share.util;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Damai Indonesia
 * 
 */
public class UploadExcel {
	private Map<Integer, Object[]> mappingHeaders = new HashMap<Integer, Object[]>();	
	private Map<Integer, Map<Integer, Object[]>> mapDetails = new HashMap<Integer, Map<Integer, Object[]>>();
	
	public Map<String,Object> datarow = new HashMap<String, Object>();
	
	public List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();

	private HashSet<Object[]> duplicatedData = new HashSet<Object[]>();
	private List<String[]> listInvalidData = new ArrayList<>();
	
	private int invalidData = 0;
	private int validData = 0;
	private int redundant = 0;
	
	public int totalData()
	{
		int totalData = 0;
		boolean same = false;
		Object[] obj = new Object[mapDetails.size()];
		for(int i = 0; i < mapDetails.size(); i++)
		{
			totalData += mapDetails.get(i).size();
			Map<Integer,Object[]> tempDetails = mapDetails.get(i);
			Map<Integer,Object[]> currDetails = mapDetails.get(i);
			HashSet<Object[]> duplicated = new HashSet<Object[]>();
			for(int j = 0; j < currDetails.size(); j++){				
				Object[] currD = currDetails.get(j);
				for(int k = 0; k < tempDetails.size(); k++){
					Object[] tempD = tempDetails.get(k);
					if(j!=k){
						int cntCellSame = 0;
						
						for(int cr = 2; cr < currD.length; cr++) {
							if((currD[cr] != null && tempD[cr] != null) && currD[cr].equals(tempD[cr])) {
								cntCellSame++;
							} 
							else if(currD[cr] == null && tempD[cr] == null) {
								cntCellSame++;
							}
//							System.out.println(currD[cr] + " " + tempD[cr]);
						}
//						System.out.println(cntCellSame + " == " + currD.length);
//						System.out.println("");
						if(cntCellSame == (currD.length-2)){
							same = true;														
						} else {
							same = false;
						}
						if(same){
							duplicated.add(currD);
						}
					}	
				}
			}
			obj[i] = duplicated.size();
			duplicatedData.addAll(duplicated);
		}
		
		for(Object ob : obj){
//			System.out.println("ob = " + ob);
			redundant = redundant + (Integer) ob;
		}
		return totalData;
	}
	
	public int invalidData()
	{
		return invalidData;
	}
	
	public int validData()
	{
		return validData;
	}
	
	public int redundantData()
	{
		return redundant;
	}
	
	
//	public void readfile(InputStream inpStream) {
//		try {
////			FileInputStream fstream = new FileInputStream("D:\testing.csv");
//			DataInputStream in = new DataInputStream(inpStream);
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			String strLine;
//			int i = 1;
//			int seqno = 1;
//			while ((strLine = br.readLine()) != null) {
//				StringTokenizer st1 = new StringTokenizer(strLine, ",");
//				List<String> l1 = new ArrayList<String>();
//				
//				while (st1.hasMoreTokens()) {
//					l1.add(st1.nextToken());
//					
//				}
////				try {
////					boolean retu = writenameinsheet(l1);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
//				System.out.println(l1);
//				seqno++;
//				i = 1;
//			}
//			
//			
//			in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	public void readFile(InputStream inpStream)
	{
		if (inpStream == null) {
			System.out.println("Filenya null");
            throw new NullPointerException();
        }
		
		List<String> head = new ArrayList<String>();
		
		try {	
			
//			System.out.println("Filenya ada isinya " + inpStream.read());
            //FileInputStream file = new FileInputStream(new File(pathname));
 
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(inpStream);
            int cntSheet = 0;
            int sheetSeq = 1;
            System.out.println("jumlah sheet nya " + workbook.getNumberOfSheets());
            for(int i=0;i<workbook.getNumberOfSheets();i++) 
            {
            	
            	System.out.println("jumlah sheet nya " + workbook.getNumberOfSheets());
    			Map<Integer, Object[]> mappingDetails = new HashMap<Integer, Object[]>();
	            //Get first/desired sheet from the workbook
	            HSSFSheet sheet = workbook.getSheetAt(i);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            int rowCnt = 1;
	            int rowDet = 0;
            	int sizes = 0;
	            while (rowIterator.hasNext()) 
	            {
	                Row row = rowIterator.next();
//	                System.out.println("lastCellNum " + i + " = " + row.getLastCellNum());
	                //For each row, iterate through all the columns

                	int cntR = 2;
                    if(rowCnt == 1) 
                    {
                    	sizes = row.getLastCellNum();            			
                	}

                	Object[] object = new Object[sizes + 2];
//                    System.out.println(object.length);
                	String nValue = null;
                	for(int cellnum=0; cellnum < sizes; cellnum++)
                	{
    	                Cell cell = row.getCell(cellnum);
    	                int cellType = getCellType(cell);
    	                switch (cellType) 
	                    {
	                        case Cell.CELL_TYPE_NUMERIC:
	                        	nValue = "Ada";
	                        	object[cntR] = parseDouble(cell.getNumericCellValue());
//	                            System.out.print(cell.getNumericCellValue() + "t");
	                            break;
	                        case Cell.CELL_TYPE_STRING:	                        	
	                        	nValue = "Ada";
	                        	object[cntR] = cell.getStringCellValue();
//	                            System.out.print(cell.getStringCellValue() + "t");
	                            break;	    
	                        case Cell.CELL_TYPE_ERROR:
	                        	nValue = "Kesalahan";
	                        	break;
	                        case Cell.CELL_TYPE_BLANK:
	                        	object[cntR] = null;
	                        	break;
	                    }
	                    cntR++;
                	}
//	                while (cellIterator.hasNext()) 
//	                {
//	                    Cell cell = cellIterator.next();
//	                    //Check the cell type and format accordingly
//	                    switch (cell.getCellType()) 
//	                    {
//	                        case Cell.CELL_TYPE_NUMERIC:
//	                        	nValue = "Ada";
//	                        	object[cntR] = cell.getNumericCellValue();
////	                            System.out.print(cell.getNumericCellValue() + "t");
//	                            break;
//	                        case Cell.CELL_TYPE_STRING:	                        	
//	                        	nValue = "Ada";
//	                        	object[cntR] = cell.getStringCellValue();
////	                            System.out.print(cell.getStringCellValue() + "t");
//	                            break;	    
//	                        case Cell.CELL_TYPE_ERROR:
//	                        	nValue = "Kesalahan";
//	                        	break;
//	                        case Cell.CELL_TYPE_BLANK:
//	                        	object[cntR] = null;
//	                        	break;
//	                    }
//	                    cntR++;
//	                }
                    
                    if(rowCnt == 1) 
                    {
                    	object[0] = "Sheet";
                    	object[1] = "Row";
	                    mappingHeaders.put(cntSheet, object);
//	                    head =(List<String>()) object;
	                    for (int a=2; a < object.length; a++){
	                    	head.add((String) object[a]);
	                    	
	                    }
	                    System.out.println(head);
                    } 
                    else 
                    {
                    	if(nValue != null)
                    	{
                    		if(nValue.equals("Kesalahan"))
                    		{
                    			++invalidData;
                    		} 
                    		else 
                    		{
                    			++validData;
                            	object[0] = sheetSeq + i;
                            	object[1] = rowCnt;
                    			mappingDetails.put(rowDet, object);
                    			datarow = new HashMap<String, Object>();
                    			for (int b = 0; b<head.size(); b++ ){
                    				datarow.put(head.get(b), object[b+2]);
                    			}
                    			listData.add(datarow);
                    			
                    		}
                    	}
    	                rowDet++;
                    }
                    
	                System.out.print("\n");
	                rowCnt++;
	            }
	            
	            mapDetails.put(cntSheet, mappingDetails);
                cntSheet++;
            }
            System.out.println("======================" + listData);
            inpStream.close();
        } 
        catch (Exception e) 
        {
        	try {
                if(inpStream != null){
					inpStream.close();
                }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
	}
	
	public List<Map<String, Object>> readContent(InputStream inpStream)
	{
		if (inpStream == null) {
			System.out.println("Filenya null");
            throw new NullPointerException();
        }
		
		List<String> head = new ArrayList<String>();
		
		try {	
			
//			System.out.println("Filenya ada isinya " + inpStream.read());
            //FileInputStream file = new FileInputStream(new File(pathname));
 
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(inpStream);
            int cntSheet = 0;
            int sheetSeq = 1;
//            System.out.println("jumlah sheet nya " + workbook.getNumberOfSheets());
            for(int i=0;i<workbook.getNumberOfSheets();i++) 
            {
            	
//            	System.out.println("jumlah sheet nya " + workbook.getNumberOfSheets());
//    			Map<Integer, Object[]> mappingDetails = new HashMap<Integer, Object[]>();
	            //Get first/desired sheet from the workbook
	            HSSFSheet sheet = workbook.getSheetAt(i);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            int rowCnt = 1;
	            int rowDet = 0;
            	int sizes = 0;
	            while (rowIterator.hasNext()) 
	            {
	                Row row = rowIterator.next();
//	                System.out.println("lastCellNum " + i + " = " + row.getLastCellNum());
	                //For each row, iterate through all the columns

                	int cntR = 2;
                    if(rowCnt == 1) 
                    {
                    	sizes = row.getLastCellNum();            			
                	}

                	Object[] object = new Object[sizes + 2];
//                    System.out.println(object.length);
                	String nValue = null;
                	for(int cellnum=0; cellnum < sizes; cellnum++)
                	{
    	                Cell cell = row.getCell(cellnum);
    	                int cellType = getCellType(cell);
    	                switch (cellType) 
	                    {
	                        case Cell.CELL_TYPE_NUMERIC:
	                        	nValue = "Ada";
	                        	cell.setCellType(Cell.CELL_TYPE_STRING);
	                        	String d1 = cell.getStringCellValue();
//	                        	System.out.print(cell.getStringCellValue() + "t");
	                        	if ((d1.contains("E"))||(d1.contains("."))){
	                        		object[cntR] = parseBigdecimal(d1);
	                        	}else{
	                        		object[cntR] = parseBigdecimal(d1);
//	                        		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//	                        		object[cntR] = parseDouble(cell.getNumericCellValue());
	                        	}
	                        	
//	                        	object[cntR] = parseBigdecimal(cell.getNumericCellValue());
//	                            System.out.print(cell.getNumericCellValue() + "t");
	                            break;
	                        case Cell.CELL_TYPE_STRING:	                        	
	                        	nValue = "Ada";
	                        	object[cntR] = cell.getStringCellValue();
//	                            System.out.print(cell.getStringCellValue() + "t");
	                            break;	    
	                        case Cell.CELL_TYPE_ERROR:
	                        	nValue = "Kesalahan";
	                        	break;
	                        case Cell.CELL_TYPE_BLANK:
	                        	object[cntR] = null;
	                        	break;
	                    }
	                    cntR++;
                	}
//	                while (cellIterator.hasNext()) 
//	                {
//	                    Cell cell = cellIterator.next();
//	                    //Check the cell type and format accordingly
//	                    switch (cell.getCellType()) 
//	                    {
//	                        case Cell.CELL_TYPE_NUMERIC:
//	                        	nValue = "Ada";
//	                        	object[cntR] = cell.getNumericCellValue();
////	                            System.out.print(cell.getNumericCellValue() + "t");
//	                            break;
//	                        case Cell.CELL_TYPE_STRING:	                        	
//	                        	nValue = "Ada";
//	                        	object[cntR] = cell.getStringCellValue();
////	                            System.out.print(cell.getStringCellValue() + "t");
//	                            break;	    
//	                        case Cell.CELL_TYPE_ERROR:
//	                        	nValue = "Kesalahan";
//	                        	break;
//	                        case Cell.CELL_TYPE_BLANK:
//	                        	object[cntR] = null;
//	                        	break;
//	                    }
//	                    cntR++;
//	                }
                    
                    if(rowCnt == 1) 
                    {
                    	object[0] = "Sheet";
                    	object[1] = "Row";
	                    mappingHeaders.put(cntSheet, object);
//	                    head =(List<String>()) object;
	                    for (int a=2; a < object.length; a++){
	                    	head.add((String) object[a]);
	                    	
	                    }
	                    System.out.println(head);
                    } 
                    else 
                    {
                    	if(nValue != null)
                    	{
                    		if(nValue.equals("Kesalahan"))
                    		{
                    			++invalidData;
                    		} 
                    		else 
                    		{
                    			++validData;
                            	object[0] = sheetSeq + i;
                            	object[1] = rowCnt;
//                    			mappingDetails.put(rowDet, object);
                    			datarow = new HashMap<String, Object>();
                    			for (int b = 0; b<head.size(); b++ ){
                    				datarow.put(head.get(b), object[b+2]);
                    			}
                    			listData.add(datarow);
                    			
                    		}
                    	}
    	                rowDet++;
                    }
                    
//	                System.out.print("\n");
	                rowCnt++;
	            }
	            
//	            mapDetails.put(cntSheet, mappingDetails);
                cntSheet++;
            }
            System.out.println("======================" + listData);
            inpStream.close();
        } 
        catch (Exception e) 
        {
        	try {
                if(inpStream != null){
					inpStream.close();
                }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
		
		return listData;
	}
	
	public List<Map<String, Object>> readContent1(InputStream inpStream)
	{
		if (inpStream == null) {
			System.out.println("Filenya null");
            throw new NullPointerException();
        }
		
		List<String> head = new ArrayList<String>();
		
		try {	
			
//			System.out.println("Filenya ada isinya " + inpStream.read());
            //FileInputStream file = new FileInputStream(new File(pathname));
 
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(inpStream);
            int cntSheet = 0;
            int sheetSeq = 1;
//            System.out.println("jumlah sheet nya " + workbook.getNumberOfSheets());
            for(int i=0;i<workbook.getNumberOfSheets();i++) 
            {
            	
//            	System.out.println("jumlah sheet nya " + workbook.getNumberOfSheets());
//    			Map<Integer, Object[]> mappingDetails = new HashMap<Integer, Object[]>();
	            //Get first/desired sheet from the workbook
	            HSSFSheet sheet = workbook.getSheetAt(i);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            int rowCnt = 1;
	            int rowDet = 0;
            	int sizes = 0;
	            while (rowIterator.hasNext()) 
	            {
	                Row row = rowIterator.next();
//	                System.out.println("lastCellNum " + i + " = " + row.getLastCellNum());
	                //For each row, iterate through all the columns

                	int cntR = 2;
                    if(rowCnt == 1) 
                    {
                    	sizes = row.getLastCellNum();            			
                	}

                	Object[] object = new Object[sizes + 2];
//                    System.out.println(object.length);
                	String nValue = null;
                	for(int cellnum=0; cellnum < sizes; cellnum++)
                	{
    	                Cell cell = row.getCell(cellnum);
    	                int cellType = getCellType(cell);
    	                switch (cellType) 
	                    {
	                        case Cell.CELL_TYPE_NUMERIC:
	                        	nValue = "Ada";
	                        	object[cntR] = parseDouble(cell.getNumericCellValue());
//	                            System.out.print(cell.getNumericCellValue() + "t");
	                            break;
	                        case Cell.CELL_TYPE_STRING:	                        	
	                        	nValue = "Ada";
	                        	object[cntR] = cell.getStringCellValue();
//	                            System.out.print(cell.getStringCellValue() + "t");
	                            break;	    
	                        case Cell.CELL_TYPE_ERROR:
	                        	nValue = "Kesalahan";
	                        	break;
	                        case Cell.CELL_TYPE_BLANK:
	                        	object[cntR] = null;
	                        	break;
	                    }
	                    cntR++;
                	}
//	                while (cellIterator.hasNext()) 
//	                {
//	                    Cell cell = cellIterator.next();
//	                    //Check the cell type and format accordingly
//	                    switch (cell.getCellType()) 
//	                    {
//	                        case Cell.CELL_TYPE_NUMERIC:
//	                        	nValue = "Ada";
//	                        	object[cntR] = cell.getNumericCellValue();
////	                            System.out.print(cell.getNumericCellValue() + "t");
//	                            break;
//	                        case Cell.CELL_TYPE_STRING:	                        	
//	                        	nValue = "Ada";
//	                        	object[cntR] = cell.getStringCellValue();
////	                            System.out.print(cell.getStringCellValue() + "t");
//	                            break;	    
//	                        case Cell.CELL_TYPE_ERROR:
//	                        	nValue = "Kesalahan";
//	                        	break;
//	                        case Cell.CELL_TYPE_BLANK:
//	                        	object[cntR] = null;
//	                        	break;
//	                    }
//	                    cntR++;
//	                }
                    
                    if(rowCnt == 1) 
                    {
                    	object[0] = "Sheet";
                    	object[1] = "Row";
	                    mappingHeaders.put(cntSheet, object);
//	                    head =(List<String>()) object;
	                    for (int a=2; a < object.length; a++){
	                    	head.add((String) object[a]);
	                    	
	                    }
	                    System.out.println(head);
                    } 
                    else 
                    {
                    	if(nValue != null)
                    	{
                    		if(nValue.equals("Kesalahan"))
                    		{
                    			++invalidData;
                    		} 
                    		else 
                    		{
                    			++validData;
                            	object[0] = sheetSeq + i;
                            	object[1] = rowCnt;
//                    			mappingDetails.put(rowDet, object);
                    			datarow = new HashMap<String, Object>();
                    			for (int b = 0; b<head.size(); b++ ){
                    				datarow.put(head.get(b), object[b+2]);
                    			}
                    			listData.add(datarow);
                    			
                    		}
                    	}
    	                rowDet++;
                    }
                    
//	                System.out.print("\n");
	                rowCnt++;
	            }
	            
//	            mapDetails.put(cntSheet, mappingDetails);
                cntSheet++;
            }
            System.out.println("======================" + listData);
            inpStream.close();
        } 
        catch (Exception e) 
        {
        	try {
                if(inpStream != null){
					inpStream.close();
                }
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
		
		return listData;
	}
	
	public static void main(String[] args) 
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("E:\\Contoh_LO_Prov_Banten_2017_14.xls"));

        	UploadExcel exl = new UploadExcel();
//        	exl.readFile(file);
//        	exl.readfile(file);
        	System.out.println("Isi content " + exl.readContent(file));
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

	public Map<Integer, Object[]> getMappingHeaders() {
		return mappingHeaders;
	}

	public void setMappingHeaders(Map<Integer, Object[]> mappingHeaders) {
		this.mappingHeaders = mappingHeaders;
	}

	public Map<Integer, Map<Integer, Object[]>> getMapDetails() {
		return mapDetails;
	}

	public void setMapDetails(Map<Integer, Map<Integer, Object[]>> mapDetails) {
		this.mapDetails = mapDetails;
	}
		
	public HashSet<Object[]> getDuplicatedData() {
		return duplicatedData;
	}

	public void setDuplicatedData(HashSet<Object[]> duplicatedData) {
		this.duplicatedData = duplicatedData;
	}

	public List<String[]> getListInvalidData() {
		List<String[]> newObj = new ArrayList<String[]>();

		int sheet = 1;
		int row = 2;
		for(int i = 0; i < mapDetails.size(); i++)
		{
			sheet = sheet + i;
			Map<Integer,Object[]> currDetails = mapDetails.get(i);
			Object[] head = mappingHeaders.get(i);
			for(int l = 0; l < currDetails.size(); l++)
			{
				row = row + l;
				Object[] detail = currDetails.get(l);
				if(duplicatedData != null)
				{
					String[] obj = new String[3];
					Iterator<Object[]> dupli = duplicatedData.iterator();
					while(dupli.hasNext())
					{
						Object[] dup = dupli.next();
						boolean retval = Arrays.equals(detail, dup);
						if(retval)
						{
							obj[0] = dup[0] + " ";
							obj[1] = dup[1] + " ";
							String detailData = "";
							for(int k = 2; k < head.length; k++)
							{						
								if(k == 2)
								{
									detailData = (String) head[k] + " : " + dup[k];
								} 
								else if(k > 2 && k < head.length) 
								{
									detailData += "," + (String) head[k] + " : " + dup[k];
								} 
								else 
								{
									detailData += "," + (String) head[k] + " : " + dup[k];
								}
							}
							obj[2] = detailData;
							
//							System.out.println(obj[0] + obj[1] + obj[2]);

							newObj.add(obj);
						}
					}
				}			
			}
			listInvalidData = newObj;
		}
		return listInvalidData;
	}

	public void setListInvalidData(List<String[]> listInvalidData) {
		this.listInvalidData = listInvalidData;
	}		
	
	private long parseDouble(double value){
		long valueLong = 0;
		if(value > 0){
			Double doubleValue = new Double(value);
			valueLong = doubleValue.longValue();
		}
		return valueLong;
	}
	private long parseDouble1(String value){
		long valueLong = 0;
		if(value != null){
			Double doubleValue = new Double(value);
			valueLong = doubleValue.longValue();
		}
		return valueLong;
	}
	
	private BigDecimal parseBigdecimal(String value){
		BigDecimal valueLong = new BigDecimal(0);
		if(value != null){
			BigDecimal doubleValue = new BigDecimal(value);
			valueLong = doubleValue;
		}
		return valueLong;
	}
	
	private int getCellType(Cell cell){		
		if(cell != null){
			return cell.getCellType();
		}
		return 3;
	}

	public Map<String, Object> getDatarow() {
		return datarow;
	}

	public void setDatarow(Map<String, Object> datarow) {
		this.datarow = datarow;
	}

	public List<Map<String, Object>> getListData() {
		return listData;
	}

	public void setListData(List<Map<String, Object>> listData) {
		this.listData = listData;
	}
}