package git;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;  

public class Commit {
	
	private Tree commitTree;
	private String summary;
	private String author;
	private Commit next;
	private String previous;

	public Commit(String sum, String a) throws Exception {
		
		File head = new File("head.txt");
		
		Scanner headReader = new Scanner(head);
		String parentTree = null;
		
		try{
			String parentPointer = headReader.nextLine();
			
			Scanner parentReader = new Scanner(new File(parentPointer));
			
			parentTree = parentReader.nextLine().substring(8);
			
			previous = parentPointer;
			
			
			
		}
		catch(Exception e){
			previous = null;
		}
		headReader.close();
		
		summary=sum;
		author=a;
		
		next = null;
		
		ArrayList<String> listOfFiles = new ArrayList<String>();
		
		
		Scanner indexReader = new Scanner(new File("index.txt"));
		boolean treeChange = false;
		
		while (indexReader.hasNext()) {
			String indexStr = indexReader.nextLine();
			
			if (indexStr.charAt(0) == 'd') {
				treeChange = true;
			}
			else {
				String fileStr = "blob :";
				fileStr += indexStr.substring(indexStr.indexOf(':')+1);
				fileStr += " ";
				// should I have made a variable for indexStr.indexOf(':') if I'm using it twice?
				// yes.
				// did I realize that too late and am now too lazy to make it?
				// yes.
				// did it take longer to write these comments than it would have to fix it?
				// shhhhhhhhhhhhhh
				fileStr += indexStr.substring(0, indexStr.indexOf(':') - 1);
				
				listOfFiles.add(fileStr);
			}
		}
		
		indexReader.close();
		
		if (previous != null && treeChange == false) {
			String treeStr = "tree";
			
			treeStr += " : ";
			
			// add the has without the "\objects" using Asher's built in method
			treeStr += parentTree;
			
			
			listOfFiles.add(treeStr);
		}
		else if (treeChange == true) {
			
			ArrayList<String> blobs = traverseTree(parentTree);
			
			for (String blob : blobs) {
				System.out.println(blob);
				listOfFiles.add(blob);
			}
			
		}
		
		
		
		commitTree = new Tree(listOfFiles);
		
		if (treeChange == true) {
			System.out.println(commitTree.getFN());
		}
		
		clearIndex();
		
		writeFile();
		
		if (treeChange == true) {
			System.out.println("working");
		}
		
		if (previous!=null) {
			File prevFile = new File(previous);
			
			Scanner prevReader = new Scanner(prevFile);
			
			String prevText = "";
			
			for (int i = 0; i < 2; i ++) {
				prevText += prevReader.nextLine() + "\n";
			}
			prevText += "objects/" + getSha() + "\n";
			
			prevReader.nextLine();
			
			for (int i = 0; i < 3; i ++) {
				prevText += prevReader.nextLine() + "\n";
			}
			prevReader.close();
			
			PrintWriter updater = new PrintWriter(prevFile);
			
			updater.write(prevText);
			
			updater.close();
			
			
		}
		
		// updating head
		
		PrintWriter headWriter = new PrintWriter(head);
		
		headWriter.write("objects/" + getSha());
		
		headWriter.close();
		
	
	}
	
	public ArrayList<String> traverseTree(String treePath) throws FileNotFoundException{
		
		System.out.println("objects/" + treePath + "done");
		
		File treeFile = new File("objects/" + treePath);
		
		ArrayList<String> newBlobs = new ArrayList<String>();
		
		Scanner scanny = new Scanner(treeFile);
		
		while (scanny.hasNext()) {
			String nextLine = scanny.nextLine();
			if (nextLine.charAt(0) == 'b'){
				
				File testFile = new File("objects/" + nextLine.substring(7, 47) + ".txt"); 
				System.out.println("objects/" + nextLine.substring(7, 47) + ".txt");
				if (testFile.exists())
					newBlobs.add(nextLine);
			}
			else {
				for (String blob : traverseTree(nextLine.substring(7))) {
					newBlobs.add(blob);
				}
			}
		}
		scanny.close();
		return newBlobs;
		
	}
	
	public void clearIndex() throws IOException {
		
		//File indexFile = new File("index.txt");
		//System.out.println(indexFile.exists());
		//System.out.println(indexFile.delete());
		
		File indexFile = new File("index.txt");
		try{
			indexFile.delete();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		System.out.println("deleted file");
		
		
		try {
			makeFile("index.txt");
			new File ("objects/").mkdirs();
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		System.out.println("reset file");
		Index.resetHashMap();
	}
	
	public String getSha () {
		String str = commitTree.getFN()+""+summary;
		return encryptThisString (str);
	}
	
	 public static String encryptThisString(String input)
	    {
	        try {
	            // getInstance() method is called with algorithm SHA-1
	            MessageDigest md = MessageDigest.getInstance("SHA-1");
	 
	            // digest() method is called
	            // to calculate message digest of the input string
	            // returned as array of byte
	            byte[] messageDigest = md.digest(input.getBytes());
	 
	            // Convert byte array into signum representation
	            BigInteger no = new BigInteger(1, messageDigest);
	 
	            // Convert message digest into hex value
	            String hashtext = no.toString(16);
	 
	            // Add preceding 0s to make it 32 bit
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
	 
	            // return the HashText
	            return hashtext;
	        }
	 
	        // For specifying wrong message digest algorithms
	        catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }

	public Object getDate() { 
		return java.time.LocalDate.now(); 
	}
	
	
	public String getNextFileName() { 
		if (next!=null) {
			return next.getSha(); 
		}
		return null;
	}

	private void setNext(Commit nx) { 
		next = nx; 
	}
	
	public void writeFile () throws IOException {
		makeFile ("objects/"+this.getSha());
		PrintWriter out = new PrintWriter ("objects/"+this.getSha());
		out.println("objects/" + commitTree.getFN());
		if (previous == null) {
			out.println();
		}
		else {
			out.println(previous);
		}
		if (next == null) {
			out.println();
		}
		else {
			out.println("objects/"+getNextFileName());
		}
		out.println(author);
		out.println(getDate());
		out.println(summary);
		out.close();
	}
	
	public void updateFile() throws IOException {
		Files.delete(Paths.get("objects/"+this.getSha()));
		writeFile();
	}
	
	private void makeFile(String s) throws IOException {
		Path newFilePath = Paths.get(s);
	    Files.createFile(newFilePath);
	}
	
	public String getTreeName() {
		return commitTree.getFN();
	}
}


