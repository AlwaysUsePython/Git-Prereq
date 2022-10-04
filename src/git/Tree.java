package git;

import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;

public class Tree {

	private String fileName = "";
	private	ArrayList<String> strings = new ArrayList<String>();
	
	public Tree (ArrayList<String> input) throws Exception{
		this.strings = input;
//		File file = new File("tester/objects"); 
//		file.mkdir();
		createFile();
	}
	
	
	public String encryptThisString (String value) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		 MessageDigest md = MessageDigest.getInstance("SHA-1");
         md.reset();
         md.update(value.getBytes("UTF-8"));
         return new BigInteger (1, md.digest()).toString(16);
	}
	
	public void createFile() throws Exception {
		String file = "";
		
		
		for (int i = strings.size()-1; i >= 0; i--) {
			file = file + strings.get(i);
			if (i - 1 != 0) {
				file += "\n";
			}
			
		}
		
		
		String stSha = encryptThisString(file);
		File file3 = new File("objects/" + stSha);
		fileName = stSha;
		PrintWriter pw = new PrintWriter(file3);
		 
		
		for (int i = strings.size()-1; i >= 0; i--) {
			pw.println(strings.get(i));
		}
		pw.close();
		
	}
	
	public String getFN() {
		return fileName;
	}
}