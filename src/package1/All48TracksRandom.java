package package1;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import java.util.HashMap;

public class All48TracksRandom {

	static String filename = "C:\\Users\\User\\Desktop\\genlist.txt";

	public static void main(String[] args) {

		HashMap<Integer, String> tracks = new HashMap<Integer, String>();

		tracks.put(1, "Mario Kart Stadium");
		tracks.put(2, "Water Park");
		tracks.put(3, "Sugar Rush");
		tracks.put(4, "Thwomp Ruins");

		tracks.put(5, "Mario Circuit");
		tracks.put(6, "Toad Harbor");
		tracks.put(7, "Twisted Mansion");
		tracks.put(8, "Shy Guy Falls");

		tracks.put(9, "Sunshine Airport");
		tracks.put(10, "Dolphin Shoals");
		tracks.put(11, "Electrodrome");
		tracks.put(12, "Mount Wario");

		tracks.put(13, "Cloudtop Cruise");
		tracks.put(14, "Bone-Dry Dunes");
		tracks.put(15, "Bowser's Castle");
		tracks.put(16, "Rainbow Road");

		tracks.put(17, "GCN Yoshi Circuit");
		tracks.put(18, "Exitebike Arena");
		tracks.put(19, "Dragon Driftway");
		tracks.put(20, "Mute City");

		tracks.put(21, "GCN Baby Park");
		tracks.put(22, "GBA Cheese Land");
		tracks.put(23, "Wild Woods");
		tracks.put(24, "Animal Crossing");

		tracks.put(25, "Wii Moo Moo Meadows");
		tracks.put(26, "GBA Mario Circuit");
		tracks.put(27, "DS Dry Dry Docks");
		tracks.put(28, "N64 Toad's Turnpike");

		tracks.put(29, "GCN Dry Dry Desert");
		tracks.put(30, "SNES Donut Plains 3");
		tracks.put(31, "N64 Royal Raceway");
		tracks.put(32, "3DS DK Jungle");

		tracks.put(33, "DS Wario Stadium");
		tracks.put(34, "GCN Sherbet Land");
		tracks.put(35, "3DS Music Park");
		tracks.put(36, "N64 Canyon Circuit");

		tracks.put(37, "DS Tick-Tock Clock");
		tracks.put(38, "3DS Piranha Pipeline");
		tracks.put(39, "Wii Grumble Volcano");
		tracks.put(40, "N64 Rainbow Road");

		tracks.put(41, "Wii Wario's Gold Mine");
		tracks.put(42, "SNES Rainbow Road");
		tracks.put(43, "Ice Ice Outpost");
		tracks.put(44, "Hyrule Circuit");

		tracks.put(45, "3DS Neo Bowser City");
		tracks.put(46, "Gibbon Road");
		tracks.put(47, "Super Bell Subway");
		tracks.put(48, "Big Blue");

		int blockCounter = 1;
		int withinBlockCounter = 0;

		// Settings!

		Boolean skipBadTracks = true;
		int blockSize = 5;

		// End of settings

		Date date = new Date();

		ArrayList<Integer> shuffledNumbers = new ArrayList<Integer>();
		ArrayList<Integer> badNumbers = new ArrayList<Integer>();
		String stringified = "temporary";
		int lengthPerNumber = 2;
		int recordedLength = 0;

		for (int i = 1; i <= 48; i++) {
			shuffledNumbers.add(i);
		}

		Collections.shuffle(shuffledNumbers);

		if (skipBadTracks) {
			badNumbers.add(18);// exitebike
			badNumbers.add(27);// DS dry dry docks
			badNumbers.add(28);// N64 toad's turnpike
			badNumbers.add(29);// GCN dry dry desert
			badNumbers.add(30);// SNES donut plains 3
		}

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			
			for (int i = 0; i <= 47; i++) {// cycle through numbers (arrayList is already shuffled)
				if (badNumbers.contains(shuffledNumbers.get(i))) {
					printlnToConsoleAndTextFile("(Skipped) " + shuffledNumbers.get(i) 
					+ " - " + tracks.get(shuffledNumbers.get(i)), out);
					// skip one
				} else {
					if ((withinBlockCounter) % blockSize == 0) {// new line every 5 numbers
						printlnToConsoleAndTextFile("", out);
						printlnToConsoleAndTextFile("Block " + blockCounter, out);
						blockCounter++;
					}
					withinBlockCounter++;

					stringified = Integer.toString(shuffledNumbers.get(i));
					recordedLength = stringified.length();

					printToConsoleAndTextFile(stringified, out);

					while (recordedLength < lengthPerNumber) {
						recordedLength++;
						printToConsoleAndTextFile(" ", out);
					}
					printToConsoleAndTextFile(" - " + tracks.get(shuffledNumbers.get(i)), out);
					printlnToConsoleAndTextFile("", out);
				}

			}
			out.close();
		} catch (Exception e) {
		}

		// Random random = new Random();
		// System.out.println( "der " + random.nextInt(200) ); // :)
	}

	public static void printlnToConsoleAndTextFile(String input, BufferedWriter out) {
		try {
			out.write(input);
			System.out.println(input);
			out.newLine();
		} catch (Exception e) {
		}
	}

	public static void printToConsoleAndTextFile(String input, BufferedWriter out) {
		try {
			out.write(input);
			System.out.print(input);
		} catch (Exception e) {
		}

	}

	public static String getLongDate(Date inputDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String stringifiedDate = formatter.format(inputDate);
		String finalResult = "";

		int month = Integer.parseInt(stringifiedDate.substring(3, 5));
		String monthString;
		switch (month) {
		case 1:
			monthString = "January";
			break;
		case 2:
			monthString = "February";
			break;
		case 3: {
			monthString = "March";
			break;
		}
		case 4:
			monthString = "April";
			break;
		case 5:
			monthString = "May";
			break;
		case 6:
			monthString = "June";
			break;
		case 7:
			monthString = "July";
			break;
		case 8:
			monthString = "August";
			break;
		case 9:
			monthString = "September";
			break;
		case 10:
			monthString = "October";
			break;
		case 11:
			monthString = "November";
			break;
		case 12:
			monthString = "December";
			break;
		default:
			monthString = "Invalid month";
			break;
		}
		finalResult += (monthString + " ");// month
		finalResult += (stringifiedDate.substring(0, 2) + ", ");// day
		finalResult += (stringifiedDate.substring(6, 10) + "  ");// year
		finalResult += (stringifiedDate.substring(11));// hour, min, sec

		return finalResult;
	}
}
