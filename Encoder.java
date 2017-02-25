/*
	Vamshedhar Reddy Chintala
	800988045
*/

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Encoder {
	
	private static BufferedReader inputStream;

	public static void main(String[] args) throws IOException {
		
		String filename = args[0];
		double BIT_LENGTH = Double.parseDouble(args[1]);
		int MAX_TABLE_SIZE = (int) Math.pow(2.0, BIT_LENGTH);
		
		HashMap<String, Integer> encoding_table = new HashMap<String, Integer>(); 
		
		for(int i = 0; i < 256; i++){
			encoding_table.put(Character.toString((char) i), i);
		}
		
		int TABLE_SIZE = 256;
		
		int pos = filename.lastIndexOf(".");
		String fileTitle = filename.substring(0, pos);
		
		String stringToHash = "";
		
		FileOutputStream outputStream = new FileOutputStream(fileTitle+ ".lzw");
		Writer writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-16BE"));
		
		inputStream = new BufferedReader(new FileReader(filename));
		int c = 0;
		
		while ((c = inputStream.read()) != -1) {
			char ch = (char) c;
			if(encoding_table.containsKey(stringToHash + ch)){
				stringToHash = stringToHash + ch;
			} else{
//				System.out.println(encoding_table.get(stringToHash));
				writer.write(encoding_table.get(stringToHash));
				if(TABLE_SIZE < MAX_TABLE_SIZE){
					encoding_table.put(stringToHash + ch, TABLE_SIZE++);
				}
				stringToHash = Character.toString(ch);
			}
		}
		
//		System.out.println(encoding_table.get(stringToHash));
		writer.write(encoding_table.get(stringToHash));
		writer.flush();
		
		inputStream.close();
		outputStream.close();
	}

}
