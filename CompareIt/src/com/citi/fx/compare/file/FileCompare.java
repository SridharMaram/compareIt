package com.citi.fx.compare.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.citi.fx.compare.util.CompareConst;

public class FileCompare {

	public static void main(String[] args){
		//binaryFileCompare(new File(),new File());
	}
	public static boolean binaryFileCompare(File file1,File file2){
		
		if(file1.length() != file2.length()){
	        return false;
	 }

	 try(   BufferedInputStream in1 =new BufferedInputStream(new FileInputStream(file1));
			 BufferedInputStream in2 =new BufferedInputStream(new FileInputStream(file2));
	 ){

	      int value1,value2;
	      do{
	           value1 = in1.read();
	           value2 = in2.read();
	           if(value1 !=value2){
	           return false;
	           }
	      }while(value1 >=0);
	 } catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	 return true;
	 
     }
	public static boolean areFilesEqual(File file1,File file2){
		boolean isEqual=true;
		BufferedReader fileRead2 = null;
		BufferedReader fileRead1=null;
	
		try {
			fileRead1 = new BufferedReader(new FileReader(file1));
			fileRead2 = new BufferedReader(new FileReader(file2));
			
			String line1 = fileRead1.readLine();
			String line2 = fileRead2.readLine();

			while (line1 != null || line2 != null)
			{
				if(line1 == null || line2 == null)
				{
					isEqual=false;
					break;
				}else if(!line1.trim().equalsIgnoreCase(line2.trim()))
				{
					isEqual=false;
					break;
				}
				line1=fileRead1.readLine();
				line2=fileRead2.readLine();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				fileRead2.close();
				fileRead1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return isEqual;
	}
	
	public static String[] fileCompare(File file1,File file2){

		
		StringBuilder firstFile=new StringBuilder();
		StringBuilder secondFile=new StringBuilder();
		
		BufferedReader fileRead2 = null;
		BufferedReader fileRead1=null;
		String first="";
		String second="";
		
		try {
			fileRead1 = new BufferedReader(new FileReader(file1));
			fileRead2 = new BufferedReader(new FileReader(file2));

			String line1 = fileRead1.readLine();
			String line2 = fileRead2.readLine();

			while (line1 != null || line2 != null)
			{
				if(line1 == null || line2 == null)
				{
					
				    if(line1!=null && line2==null){
				    	firstFile.append(CompareConst.FIRST_FILE_PREPEND).append(line1).append(CompareConst.FILE_APPEND);
				    	secondFile.append(CompareConst.SECOND_FILE_PREPEND_EMPTY);
				    }else if(line2!=null && line1==null){
				    	secondFile.append(CompareConst.SECOND_FILE_PREPEND).append(line2).append(CompareConst.FILE_APPEND);
				    	firstFile.append(CompareConst.FIRST_FILE_PREPEND_EMPTY);
				    }
					second="";
					first="";
					
				}
				else if(!line1.equalsIgnoreCase(line2))
				{
				
					
					if(line1.trim().isEmpty() && !line2.trim().isEmpty()){
						
						line1=fileRead1.readLine();
						if(line1!=null && line1.trim().equalsIgnoreCase(line2.trim())){
							firstFile.append(CompareConst.FIRST_FILE_PREPEND_EMPTY);
							secondFile.append(CompareConst.SECOND_FILE_PREPEND_EMPTY);
							firstFile.append(CompareConst.FILE_PREPEND).append(line1).append(CompareConst.FILE_APPEND);
							secondFile.append(CompareConst.FILE_PREPEND).append(line2).append(CompareConst.FILE_APPEND);
							second=first="";
						}else{
							secondFile.append(CompareConst.SECOND_FILE_PREPEND).append(line2).append(CompareConst.SECOND_FILE_PREPEND);
							firstFile.append(CompareConst.FIRST_FILE_PREPEND_EMPTY);
							second="";
							first=line1;
						}
						
					
						
					}else if(line2.trim().isEmpty() && !line1.trim().isEmpty()){
						line2=fileRead2.readLine();
						if(line2!=null && line2.trim().equalsIgnoreCase(line1.trim())){
							firstFile.append(CompareConst.FIRST_FILE_PREPEND_EMPTY);
							secondFile.append(CompareConst.SECOND_FILE_PREPEND_EMPTY);
							firstFile.append(CompareConst.FILE_PREPEND).append(line1).append(CompareConst.FILE_APPEND);
							secondFile.append(CompareConst.FILE_PREPEND).append(line2).append(CompareConst.FILE_APPEND);
							
							second=first="";
							
						}else{
							firstFile.append(CompareConst.FIRST_FILE_PREPEND).append(line1).append(CompareConst.FILE_APPEND);
							secondFile.append(CompareConst.SECOND_FILE_PREPEND_EMPTY);
							second=line2;
							first="";
						}
						
					}
					
					else{
					firstFile.append(CompareConst.FIRST_FILE_PREPEND).append(line1).append(CompareConst.FILE_APPEND).append(CompareConst.FIRST_FILE_PREPEND_EMPTY);
					secondFile.append(CompareConst.SECOND_FILE_PREPEND_EMPTY).append(CompareConst.SECOND_FILE_PREPEND).append(line2).append(CompareConst.FILE_APPEND);
					second="";
					first="";
					}
					
				
				
				}else{
					firstFile.append(CompareConst.FILE_PREPEND).append(line1).append(CompareConst.FILE_APPEND);
					secondFile.append(CompareConst.FILE_PREPEND).append(line2).append(CompareConst.FILE_APPEND);
					second="";
					first="";
				
				}
				if(first.isEmpty() && !second.isEmpty()){// TODO: Null pointer Exception
					line1 = fileRead1.readLine();
					line2=second;
				}else if(!first.isEmpty() && second.isEmpty()){
					line2 = fileRead2.readLine();
					line1=first;
					
				}else{
				line1 = fileRead1.readLine();
				line2 = fileRead2.readLine();
				}

			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fileRead1.close();
				fileRead2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return new String[]{firstFile.toString(),secondFile.toString()};

	}
}
