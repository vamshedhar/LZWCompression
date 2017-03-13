/*
	Vamshedhar Reddy Chintala
	800988045
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Encoder {
	
	private static BufferedReader inputStream;
	private static FileOutputStream outputStream;

	public static void main(String[] args) throws IOException {

		// Input format validations
        if (args.length != 2) {
            System.out.println("Invalid Format!");
            System.out.println("Expected Format: java Encoder <filename or filepath> <bitlength>");
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

		// Initialize encoding table with all ASCII characters, with character as key and ASCII code as its value
		HashMap<String, Integer> ENCODING_TABLE = new HashMap<String, Integer>(); 
		
		for(int i = 0; i < 256; i++){
			ENCODING_TABLE.put(Character.toString((char) i), i);
		}

		int TABLE_SIZE = 256;

		// check if its a valid file
		try{

			// Encoding starts here. Reads charactes from input file, stores new codes to table and writes to encoded life
			inputStream = new BufferedReader(new FileReader(filename));
			int c = 0;

			// get file title from name
			int pos = filename.lastIndexOf(".");
			String fileTitle = filename.substring(0, pos);
			
			String stringToHash = "";
			
			// write file in UTF-16BE format
			outputStream = new FileOutputStream(fileTitle+ ".lzw");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-16BE")));
			
			while ((c = inputStream.read()) != -1) {
				char ch = (char) c;

				// check if code exists in the table
				if(ENCODING_TABLE.containsKey(stringToHash + ch)){
					stringToHash = stringToHash + ch;
				} else{

					// write to encoded file
					writer.write(ENCODING_TABLE.get(stringToHash));

					// check if the table size has not reached its limit
					if(TABLE_SIZE < MAX_TABLE_SIZE){
						// store new codes to the table and increment table size
						ENCODING_TABLE.put(stringToHash + ch, TABLE_SIZE++);
					}
					stringToHash = Character.toString(ch);
				}
			}
			
			// write the encoded part of the remaning part of string to table 
			writer.write(ENCODING_TABLE.get(stringToHash));
			writer.flush();

		} catch(FileNotFoundException e){
			System.out.println("File not found. Please give a valid filename/filepath.");
			return;
		}
		
		// close the file reader and writer
		inputStream.close();
		outputStream.close();
	}

}
