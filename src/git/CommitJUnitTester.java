package git;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

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
			Files.writeString(filePathToWrite4, "Fourth testing file", StandardCharsets.ISO_8859_1);
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
	    
	    File headFile = new File("head.txt"); 
	    if (headFile.delete()) { 
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
		
		Commit firstCommit = new Commit("Testing the first files", "Elliot Lichtman");
		
		File commitFile = new File("objects/" + firstCommit.getSha());
		assertTrue(commitFile.exists());
		
		Scanner testReader = new Scanner(commitFile);
		
		String firstLine = testReader.nextLine();
		
		testReader.close();
		
		boolean correct = false;
		
		
		if (firstLine.contains("objects/e466ec057b9ac2677cea48dcc171a4e165b00d6c")) {
			correct = true;
		}
		
		assertTrue(correct);
		
		
		testIndex.add("thirdTest.txt");
		testIndex.add("fourthTest.txt");
		Commit secondCommit = new Commit("Testing the second files", "Elliot Lichtman");
		
		File secondCommitFile = new File("objects/" + secondCommit.getSha());
		assertTrue(commitFile.exists());
		
		Scanner secondReader = new Scanner(secondCommitFile);
		
		firstLine = secondReader.nextLine();
		
		String secondLine = secondReader.nextLine();
		
		secondReader.close();
		
		correct = false;
		
		
		if (firstLine.contains("objects/61103de070b75a68a00cd3f15938877af3fa773f") && secondLine.contains("objects/90770b9669182a5891dba68af77ca1cf33b867c7")) {
			correct = true;
		}
		
		assertTrue(correct);
		
		// I'm just gonna assume it's working for the third commit
		
		testIndex.add("fifthTest.txt");
		testIndex.add("sixthTest.txt");
		
		Commit thirdCommit = new Commit("Testing the third files", "Elliot Lichtman");
		
		testIndex.add("seventhTest.txt");
		testIndex.add("eighthTest.txt");
		
		Commit fourthCommit = new Commit("Testing the fourth files", "Elliot Lichtman");
		
		File fourthCommitFile = new File("objects/" + fourthCommit.getSha());
		assertTrue(fourthCommitFile.exists());
		
		Scanner lastReader = new Scanner(fourthCommitFile);
		
		firstLine = lastReader.nextLine();
		
		secondLine = lastReader.nextLine();
		
		lastReader.close();
		
		correct = false;
		
		
		if (firstLine.contains("objects/ae0032d5c6adbb87724f544023725797d52afca5") && secondLine.contains("objects/4f2d3cd894fca1bc43ec811e1e5889b345fda5e7")) {
			correct = true;
		}
		
		assertTrue(correct);
		
		testIndex.delete("firstTest.txt");
		testIndex.delete("fifthTest.txt");
		
		Commit testDelete = new Commit("testing delete", "Elliot Lichtman");
		
		
	}
	
	
	

}
