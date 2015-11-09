//======================================================================================
// Copyright Good Code Inc.
//
// Distributed under the Apache License Version 2.0.
// http://choosealicense.com/licenses/apache-2.0/
//======================================================================================
package com.goodcode.guess.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility class to perform common functions
 * @author Jevon Gill
 */
public class GuesserUtils {
	
	/**
	 * Reads text file and returns a string.
	 * @param fileName the name of the file. Must be present in the src/main/resources
	 * directory.
	 * @return resultString the contents of the file
	 * @throws IOException 
	 */
	public static String readTxtFileToString(String fileName) throws IOException{
		
		InputStream in = GuesserUtils.class.getResourceAsStream(fileName);
		if(in==null){
			throw new IOException("File not found");
		}
		StringBuffer resultString = new StringBuffer(); 			
   	 	String str = new String();           
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            if (in != null) {                            
                while ((str = reader.readLine()) != null) {    
                    resultString.append(str + "\n" );
                }                
            }
        } finally {
            try { in.close(); } catch (Throwable ignore) {}
        }        
        return resultString.toString();
	}
}
