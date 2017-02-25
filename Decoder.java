/*
	Vamshedhar Reddy Chintala
	800988045
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Decoder {

	public static void main(String[] args) throws IOException {

		String filename = args[0];
		double BIT_LENGTH = Double.parseDouble(args[1]);
		int MAX_TABLE_SIZE = (int) Math.pow(2.0, BIT_LENGTH);
		
		HashMap<Integer, String> encoding_table = new HashMap<Integer, String>(); 
		
		for(int i = 0; i < 256; i++){
			encoding_table.put(i, Character.toString((char) i));
		}
		
		int TABLE_SIZE = 256;
		
		int pos = filename.lastIndexOf(".");
		String fileTitle = filename.substring(0, pos);
		
		FileWriter writer = new FileWriter(fileTitle + "_decoded.txt");
		
		FileInputStream inputStream = new FileInputStream(filename);
		Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));
		
		int code = reader.read();
		
		String oldString = encoding_table.get(code);
		String newString = "";
		
		// System.out.print(oldString);
		writer.write(oldString);
		
        while ((code = reader.read()) != -1) {
			if(!encoding_table.containsKey(code)){
				newString = oldString + oldString.charAt(0);
			} else{
				newString = encoding_table.get(code);
			}
           
			// System.out.print(newString);
           	writer.write(newString);
           
           	if(TABLE_SIZE < MAX_TABLE_SIZE){
        		encoding_table.put(TABLE_SIZE++, oldString + newString.charAt(0));
           	}
           
           	oldString = newString;
        }
        
        inputStream.close();
		writer.close();
	}

}
