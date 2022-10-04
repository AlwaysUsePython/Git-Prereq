package git;

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

public class Commit {
	
	private Tree commitTree;
	private String summary;
	private String author;
	private Commit next;
	private Commit previous;

	public Commit(String sum, String a, Commit prev) throws Exception {
		summary=sum;
		author=a;
		previous = prev;
		if (previous!=null) {
			previous.setNext(this);
		}
		next = null;
		
		ArrayList<String> listOfFiles = new ArrayList<String>();
		
		String treeStr = "tree";
		
		treeStr += " : ";
		
		// add the has without the "\objects" using Asher's built in method
		treeStr += prev.getTreeName();
		
		
		commitTree = new Tree(listOfFiles);
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
	
	public String getPreviousFileName() { 
		if (previous!=null) {
			return previous.getSha(); 
		}
		return null; 
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
		out.println(commitTree.getFN());
		out.println("objects/"+getPreviousFileName());
		out.println("objects/"+getNextFileName());
		out.println(author);
		out.println(getDate());
		out.println(summary);
		out.close();
	}
	
	private void makeFile(String s) throws IOException {
		Path newFilePath = Paths.get(s);
	    Files.createFile(newFilePath);
	}
	
	public String getTreeName() {
		return commitTree.getFN();
	}
}


