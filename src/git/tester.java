package git;

import java.io.IOException;

public class tester {

	public static void main(String[] args) throws Exception {
		//Blob b = new Blob ("/Users/asher/eclipse-workspace/Git Prereq/something.txt");
//		System.out.println(b.encryptThisString("abcdefg"));
		Index i = new Index();
		i.init();
		i.add("test.txt");
		
		Commit firstCommit = new Commit("testing", "Elliot Lichtman", null);
		
		i.add("test2.txt");
		
		Commit secondCommit = new Commit("second test!", "Elliot Lichtman", firstCommit);
	}

}
