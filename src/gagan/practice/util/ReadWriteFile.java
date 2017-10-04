package gagan.practice.util;

import gagan.practice.dao.jdbc.BatchJob;
import gagan.practice.dao.jdbc.BatchJob.BatchEntry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFile {

	public static String readFile(String fileLocation){
		String fileStr = "";
		File file = new File(fileLocation);
		
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			try {
	            byte[] buff = new byte[1024];
	            int bytesRead;
	            
	            while (-1 != (bytesRead = fileInputStream.read(buff, 0, buff.length))){
	            	fileStr += new String(buff, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return fileStr;
	}
	
	public static void readTabSaperatedFile(String fileLocation, BatchJob batchJob){
		
		File file = new File(fileLocation);
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			
			String line;
			while((line = bufferedReader.readLine()) != null){
				
				BatchJob.BatchEntry batchEntry = batchJob.new BatchEntry();
				batchEntry.setEntry(line.split("\\t"));
				batchJob.getBatchEntries().add(batchEntry);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
