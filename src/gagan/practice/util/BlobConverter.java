package gagan.practice.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;


public class BlobConverter {
	
	 public static String covertBlobToString(InputStream in) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		byte[] buf = new byte[1024]; 
		
		String blobString = "";
		try {
			
			int n = 0; 
			while ((n=in.read(buf))>=0) 
				{ 				  
				baos.write(buf, 0, n); 
			} 
			 in.close(); 
			byte[] bytes = baos.toByteArray(); 
			 blobString = new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return blobString;
	}
	
	  public static Blob covertStringToBlob(String text) {
		  Blob criteriaText =  null;
			try {
				criteriaText = new SerialBlob(text.getBytes());
			} catch (SerialException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return criteriaText;
		}

	  
}
