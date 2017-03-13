/*
 * Vamshedhar Reddy Chintala
 * 800988045
 */


Programming Project 1: LZW Compression


Programming Langauage: Java

Java Version: 8

JRE Version: 1.8


----------- Project Details --------------

The Lempel–Ziv–Welch (LZW) algorithm is a lossless data compression algorithm. In this project we are going to implement the LZW Compression algorithm. It has two parts. Encoding/Compression of a given file and Decoding/Decompression of the encoded file. 

The following project contains 2 class files and a readme file. 

	1) Encoder.java file which contains LZW Compression's Encoder algorithm in Encoder class and
	2) Decoder.java file which contains LZW Compression's Decoder algorithm in Decoder class



----------- Project Design  --------------

Data Strucrute Design:

	Encoder Class: 
		- To store ENCODING_TABLE an HashMap was used with Sring keys(strings) and integer values(Codes)
		- For all other parameters default data structures like Integer, String and Character were used

	Decoder Class: 
		- To store ENCODING_TABLE an HashMap was used with integer keys(Codes) and integer values(strings)
		- For all other parameters default data structures like Integer, String and Character were used



Program Design:

	Encoder Class:
		- The program takes input text file and bit length as arguments. Here bit lenght is used for setting a MAX_TABLE_SIZE of the encoding table(HashMap). Then it iteratively initiates the HashMap to insert all 256 ASCII charactes to the encoding table  
		- Then it uses java's FileReader class to read the input text file one character at a time and while reading itself the Encoding algorithim of the LWZ Compression is used to generate codes and those codes are written into an output file using java's BufferedWriter and UTF-16BE encryption
		- In the whole process if there are any errors in the input or the data then proper errors are raised

	Decoder Class:
		- The program takes input encoded file and bit length as arguments. Here bit lenght is used for setting a MAX_TABLE_SIZE of the encoding table(HashMap). Then it iteratively initiates the HashMap to insert all 256 ASCII charactes to the encoding table.  
		- Now, the program uses java's InputStreamReader class to read the UTF-16BE encoded file to integers one after another. While reading the codes itself Decoding algorithm of LZW is used to decode the codes, store the new codes to encoding table and writes the decoded strings to the output file using java's BufferedWriter class
		- In the whole process if there are any errors in the input or the data then proper errors are raised



--------- Run Encoder Program ------------

First complite the Encoder java class using command in command line
	
	javac Encoder.java

Then to compress a file using LZW Compression, use the command in the following format

	java Encoder <input filename or filepath> <bitlength>

	Ex: java Encoder ~/Desktop/LZW/input2.txt 12

Encoded files is created in the same location as the input file. 

Note: if only file name is given in the command line arguments, the file must be in the same location as the java class file


--------- Run Decoder Program ------------

First complite the Decoder java class using command in command line
	
	javac Decoder.java

Then to decode the file encoded using the LZW Compression, use the command in the following format

	java Decoder <encoded filename or filepath> <bitlength>

	Ex: java Encoder ~/Desktop/LZW/input2.lzw 12

Decoded files is created in the same location as the input file. 

Note: if only file name is given in the command line arguments, the file must be in the same location as the java class file


-------- What works and What fails ----------

	- This program works only for compression of text file(.txt extension) which contains only ASCII characters. Files of other format cannot be compressed using this program. 
	- Typically the bit length is expected to be in range of 9 to 12. But if the bit lenght give is less than 9 then the compression will work but it won't be efficient. It will convert each character of the text file into its ASCII code and store which will not actually compress the file but increase its size. If the bit length is more than 12 then the program consumes a lot of memory for the encoding table
