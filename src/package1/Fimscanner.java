package package1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Fimscanner {

	public static void main(String[] args) {
		String csvFile = "C:\\Program Files (x86)\\Altitude\\install4j\\sprites.txt";
		BufferedReader br = null;
		PrintWriter output = null;
		String line = "";

		int maxCharLengthOfStoryName = 55;
		int desiredMinLengthOfStory = 100;

		try {

			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				int length = 0;
				boolean naughty = false;

				if (line.contains("(!)")) {
					naughty = true;
				}
				// separator
				String[] splitLine = line.split("/");
				String[] details;
				String nameWithDashes = "default";
				if (splitLine.length >= 6) {

					details = splitLine[5].split(" ");
					nameWithDashes = details[0];

					for (int i2 = 0; i2 < details.length; i2++) {
						if (details[i2].matches("\\d+")) {// if is int
							length = Integer.parseInt(details[i2]);
							break;
						}
					}
				}

				if (line.contains("fimfiction") && length >= desiredMinLengthOfStory) {
					String nameWithoutDashes = nameWithDashes.replace('-', ' ');
					int printLength = nameWithoutDashes.length();
					if (printLength > maxCharLengthOfStoryName) {
						printLength = maxCharLengthOfStoryName;
					}

					System.out.print(nameWithoutDashes.substring(0, printLength) + " - ");
					for (int i = 0; i < maxCharLengthOfStoryName - nameWithDashes.length(); i++) {
						System.out.print(" ");
					}

					if (length == 0) {
						System.out.print("unknown length");
					} else {
						System.out.print(length);
					}

					if (naughty) {
						System.out.print(" (!)");
					}
					System.out.println();
					// System.out.println();

					/*
					 * output.print(nameWithDashes.replace('-', ' ') + " - " + length); if (naughty)
					 * { output.print(" (!)"); } output.println(); output.println();
					 */
				} else {
					for (int i = 0; i < splitLine.length; i++) {
						// System.out.print(" [" + splitLine[i] + "] ");
					}

					// System.out.println(line);
				}
			}
			br.close();

			// output.close();

			// System.out.println(" -- output.txt updated -- ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}