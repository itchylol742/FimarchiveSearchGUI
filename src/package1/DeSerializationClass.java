package package1;

import java.io.*;
import java.util.ArrayList;

public class DeSerializationClass {
	public static void main(String[] args) {
		ArrayList<FimfictionStory> arraylist = new ArrayList<FimfictionStory>();
		try {
			FileInputStream fis = new FileInputStream("myfile");
			ObjectInputStream ois = new ObjectInputStream(fis);
			arraylist = (ArrayList<FimfictionStory>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
		for (FimfictionStory tmp : arraylist) {
			System.out.println(tmp);
		}
	}
}