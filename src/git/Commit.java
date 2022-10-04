package git;

import java.io.File;
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
		
		if (previous != null) {
			String treeStr = "tree";
			
			treeStr += " : ";
			
			// add the has without the "\objects" using Asher's built in method
			treeStr += parentTree;
			
			
			listOfFiles.add(treeStr);
		}
		
		Scanner indexReader = new Scanner(new File("index.txt"));
		
		while (indexReader.hasNext()) {
			String indexStr = indexReader.nextLine();
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
		
		indexReader.close();
		
		
		commitTree = new Tree(listOfFiles);
		
		clearIndex();
		
		writeFile();
		
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
	
	public void clearIndex() throws IOException {
		
		//File indexFile = new File("index.txt");
		//System.out.println(indexFile.exists());
		//System.out.println(indexFile.delete());
		
		Files.delete(Paths.get("index.txt"));
		
		Index.makeFile("index.txt");
		new File ("objects/").mkdirs();
		
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


