package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Index {
	//private boolean bool = false;
	private static HashMap<String, String> hm;
	
	public Index () throws IOException {
//		File file = new File("/Users/asher/eclipse-workspace/Git Prereq");
//		//creating the directory`
//		bool = file.mkdir();
		hm = new HashMap<>();
	}
	
	public void init() throws IOException {
		makeFile("index.txt");
		new File ("objects/").mkdirs();
		
		makeFile("head.txt");
		new File ("objects/").mkdirs();
	}
	
	public static void add(String fn) throws IOException {
		Blob blobby = new Blob (fn);
		
		BufferedReader br = new BufferedReader(new FileReader ("index.txt"));
		String str = "";
		while (br.ready()) {
			str += ""+(char)br.read();
		}
		
		if (!hm.containsKey(fn.substring(0,fn.length()-4))) {
			hm.put(fn.substring(0,fn.length()-4), blobby.getName());
			PrintWriter fw = new PrintWriter("index.txt");
			fw.print(str+fn.substring(0,fn.length()-4)+" : "+blobby.getName()+"\n");
			fw.close();
		}
		br.close();
	}
	
	public static void delete(String fn) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader ("index.txt"));
		String str = "";
		while (br.ready()) {
			str += ""+(char)br.read();
		}
		
		PrintWriter fw = new PrintWriter("index.txt");
		fw.print(str + "deleted " + fn.substring(0,fn.length()-4));
		fw.close();
		
		File original = new File(fn);
		
		String text = "";
		
		br = new BufferedReader(new FileReader (original));
		while (br.ready()) {
			text += (char)br.read();
		}
		br.close();
		String encrypted = Blob.encryptThisString(text);
		String FILE_NAME="objects/"+encrypted+".txt";
		
		File toDelete = new File (FILE_NAME);
		
		System.out.println(toDelete.delete());
	}
	
	public static void remove(String fn) throws IOException {
		if (hm.containsKey(fn.substring(0,fn.length()-4))) {
			String str = Blob.encryptThisString(fn);
			hm.remove(fn.substring(0,fn.length()-4), str);
			
			
			BufferedReader br = new BufferedReader(new FileReader ("index.txt"));
			PrintWriter pw = new PrintWriter("temp.txt");
			while (br.ready()) {
				String read = br.readLine();
				if (read.indexOf(fn.substring(0,fn.length()-4))<0) {
					pw.print (read);
				}
			}
			br.close();
			pw.close();
			File ind = new File ("index.txt");
			File temp = new File ("temp.txt");
			ind = temp;
			temp.delete();
			
			File file = new File (fn);
			file.delete();
		}
		
	}
	
	public static void makeFile(String s) throws IOException {
		Path newFilePath = Paths.get(s);
	    Files.createFile(newFilePath);
	}
	
	public static void resetHashMap() {
		hm = new HashMap<String, String>();
	}
}
