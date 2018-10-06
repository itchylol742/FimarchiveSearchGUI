package package1;

import java.util.ArrayList;
import java.io.*;

public class ArrayListSerialization {
	public static void main(String[] args) {
		ArrayList<FimfictionStory> arrayListOfStuff = new ArrayList<FimfictionStory>();
		arrayListOfStuff.add(new FimfictionStory(12));
		arrayListOfStuff.add(new FimfictionStory(13337));
		
		arrayListOfStuff.get(0).author = "author one";
		arrayListOfStuff.get(1).author = "author two";

		try {
			FileOutputStream fos = new FileOutputStream("myfile");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			System.out.println("about to write");
			oos.writeObject(arrayListOfStuff);
			System.out.println("written");
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("done");
	}
}