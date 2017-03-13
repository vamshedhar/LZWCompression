/*
	Vamshedhar Reddy Chintala
	800988045
*/

import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Decoder {

	public static void main(String[] args) throws IOException {

		// Input format validations
        if (args.length != 2) {
            System.out.println("Invalid Format!");
            System.out.println("Expected Format: java Encoder <filename> <bitlength>");
            return;
        }

		String filename = args[0];

		int BIT_LENGTH;

		// check if bit length is a valid integer
		try{
			BIT_LENGTH = Integer.parseInt(args[1]);
		} catch(NumberFormatException e){
			System.out.println("Invalid bit length!");
			return;
		}

		int MAX_TABLE_SIZE = (int) Math.pow(2, BIT_LENGTH);
		
		// Initialize encoding table with all ASCII characters, with ASCII code as key and character as its value
		HashMap<Integer, String> encoding_table = new HashMap<Integer, String>(); 
		
		for(int i = 0; i < 256; i++) {
			encoding_table.put(i, Character.toString((char) i));
		}
		
		int TABLE_SIZE = 256;
		FileInputStream inputStream;
		BufferedWriter writer;

		// check if its a valid file
		try{

			// read file in UTF-16BE format
			inputStream = new FileInputStream(filename);
			Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-16BE"));

			// get file title from name
			int pos = filename.lastIndexOf(".");
			String fileTitle = filename.substring(0, pos);
			
			// write to decoded file
			writer = new BufferedWriter(new FileWriter(fileTitle + "_decoded.txt"));
			
			// read first code to get the first character of the actual file
			int code = reader.read();
			
			String OLD_STRING = encoding_table.get(code);
			String NEW_STRING = "";
			
			// write first decoded character to the file
			writer.write(OLD_STRING);
			
	        while ((code = reader.read()) != -1) {

	        	// check if code exists in the table
				if(!encoding_table.containsKey(code)){
					NEW_STRING = OLD_STRING + OLD_STRING.charAt(0);
				} else {
					NEW_STRING = encoding_table.get(code);
				}
	           
	        	// write the decoded string to decoded file
	           	writer.write(NEW_STRING);
	           
	           	// check if the table size has not reached its limit
	           	if(TABLE_SIZE < MAX_TABLE_SIZE){
	           		// store newly decoded string to the table
	        		encoding_table.put(TABLE_SIZE++, OLD_STRING + NEW_STRING.charAt(0));
	           	}
	           
	           	OLD_STRING = NEW_STRING;

	           	writer.flush();
	        }
		} catch(FileNotFoundException e){
			System.out.println("File not found. Please give a valid filename/filepath.");
			return;
		}
		
		
        // close the file reader and writer
        inputStream.close();
		writer.close();
	}

}
