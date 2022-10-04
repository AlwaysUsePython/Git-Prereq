package git;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitJUnitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Path filePathToWrite = Paths.get("firstTest.txt");
		try {
			// hash: ff9f6ae1c8d4ca16e67f15f4ae1d10e9bc69beaa
			Files.writeString(filePathToWrite, "First testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite2 = Paths.get("secondTest.txt");
		try {
			// hash: 4a4e0e220c01d6170a3e057cc39c322c3bdd0755
			Files.writeString(filePathToWrite2, "Second testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite3 = Paths.get("thirdTest.txt");
		try {
			// hash: 4a4e0e220c01d6170a3e057cc39c322c3bdd0755
			Files.writeString(filePathToWrite3, "Third testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite4 = Paths.get("fourthTest.txt");
		try {
			Files.writeString(filePathToWrite3, "Fourth testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite5 = Paths.get("fifthTest.txt");
		try {
			// hash: ff9f6ae1c8d4ca16e67f15f4ae1d10e9bc69beaa
			Files.writeString(filePathToWrite5, "Fifth testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite6 = Paths.get("sixthTest.txt");
		try {
			// hash: 4a4e0e220c01d6170a3e057cc39c322c3bdd0755
			Files.writeString(filePathToWrite6, "Sixth testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite7 = Paths.get("seventhTest.txt");
		try {
			// hash: 4a4e0e220c01d6170a3e057cc39c322c3bdd0755
			Files.writeString(filePathToWrite7, "Seventh testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
		Path filePathToWrite8 = Paths.get("eighthTest.txt");
		try {
			Files.writeString(filePathToWrite8, "Eighth testing file", StandardCharsets.ISO_8859_1);
		}
		catch (IOException exception) {
			System.out.println("Write failed");
		}
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
		// delete the index file. we will rewrite soon
	    File firstFile = new File("firstTest.txt"); 
	    if (firstFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File secondFile = new File("secondTest.txt"); 
	    if (secondFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File thirdFile = new File("thirdTest.txt"); 
	    if (thirdFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File fourthFile = new File("fourthTest.txt"); 
	    if (fourthFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File fifthFile = new File("fifthTest.txt"); 
	    if (fifthFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File sixthFile = new File("sixthTest.txt"); 
	    if (sixthFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File seventhFile = new File("seventhTest.txt"); 
	    if (seventhFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File eighthFile = new File("eighthTest.txt"); 
	    if (eighthFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
	    
	    File indexFile = new File("index.txt"); 
	    if (indexFile.delete()) { 
	      System.out.println("Final delete successful");
	    } else {
	      System.out.println("Failed to delete the file.");
	    } 
		
	}
	
	@Test
	void testCommit() throws Exception {
		Index testIndex = new Index();
		testIndex.init();
		
		File indexFile = new File("index.txt");
		
		assertTrue(indexFile.exists());
		
		testIndex.add("firstTest.txt");
		testIndex.add("secondTest.txt");
		
		Commit firstCommit = new Commit("Testing the first files", "Elliot Lichtman", null);
		
		File commitFile = new File("objects/" + firstCommit.getSha());
		assertTrue(commitFile.exists());
		
		
	}
	
	
	

}
