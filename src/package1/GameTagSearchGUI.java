package package1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Label;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.List;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
//import java.io.FileWriter;

public class GameTagSearchGUI {

	static ArrayList<String> goodTags = new ArrayList<String>();
	static ArrayList<String> badTags = new ArrayList<String>();

	static TextArea textArea_1 = new TextArea();

	static ArrayList<TextField> textAreasArrayList = new ArrayList<TextField>();

	private JFrame frmTest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameTagSearchGUI window = new GameTagSearchGUI();
					window.frmTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameTagSearchGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		System.out.println("initialize start");

		frmTest = new JFrame();
		frmTest.setFont(new Font("Algerian", Font.PLAIN, 12));
		frmTest.setTitle("test");

		frmTest.setBounds(100, 100, 1207, 947);
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTest.getContentPane().setLayout(null);
		textArea_1.setFont(new Font("Consolas", Font.PLAIN, 18));

		textArea_1.setBounds(74, 280, 967, 618);
		frmTest.getContentPane().add(textArea_1);

		TextField goodTag1 = new TextField();
		goodTag1.setFont(new Font("Dialog", Font.PLAIN, 18));
		goodTag1.setBounds(93, 56, 274, 39);
		frmTest.getContentPane().add(goodTag1);

		TextField goodTag2 = new TextField();
		goodTag2.setFont(new Font("Dialog", Font.PLAIN, 18));
		goodTag2.setBounds(93, 113, 274, 39);
		frmTest.getContentPane().add(goodTag2);

		TextField goodTag3 = new TextField();
		goodTag3.setFont(new Font("Dialog", Font.PLAIN, 18));
		goodTag3.setBounds(93, 173, 274, 39);
		frmTest.getContentPane().add(goodTag3);

		TextField badTag1 = new TextField();
		badTag1.setFont(new Font("Dialog", Font.PLAIN, 18));
		badTag1.setBounds(733, 56, 274, 39);
		frmTest.getContentPane().add(badTag1);

		TextField badTag2 = new TextField();
		badTag2.setFont(new Font("Dialog", Font.PLAIN, 18));
		badTag2.setBounds(734, 113, 274, 39);
		frmTest.getContentPane().add(badTag2);

		TextField badTag3 = new TextField();
		badTag3.setFont(new Font("Dialog", Font.PLAIN, 18));
		badTag3.setBounds(734, 173, 274, 39);
		frmTest.getContentPane().add(badTag3);

		TextField goodTag4 = new TextField();
		goodTag4.setFont(new Font("Dialog", Font.PLAIN, 18));
		goodTag4.setBounds(93, 235, 274, 39);
		frmTest.getContentPane().add(goodTag4);

		TextField badTag4 = new TextField();
		badTag4.setFont(new Font("Dialog", Font.PLAIN, 18));
		badTag4.setBounds(733, 235, 274, 39);
		frmTest.getContentPane().add(badTag4);

		textAreasArrayList.add(goodTag1);
		textAreasArrayList.add(goodTag2);
		textAreasArrayList.add(goodTag3);
		textAreasArrayList.add(goodTag4);

		textAreasArrayList.add(badTag1);
		textAreasArrayList.add(badTag2);
		textAreasArrayList.add(badTag3);
		textAreasArrayList.add(badTag4);

		Label label = new Label("Good tags");
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setBounds(96, 10, 248, 40);
		frmTest.getContentPane().add(label);

		Label label_1 = new Label("Bad tags");
		label_1.setFont(new Font("Dialog", Font.PLAIN, 20));
		label_1.setBounds(733, 10, 248, 40);
		frmTest.getContentPane().add(label_1);

		Button button = new Button("Search");
		button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 66));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_1.setText("");
				goodTags.clear();
				badTags.clear();

				if (goodTag1.getText().length() > 0) {
					goodTags.add(goodTag1.getText());
				}
				if (goodTag2.getText().length() > 0) {
					goodTags.add(goodTag2.getText());
				}
				if (goodTag3.getText().length() > 0) {
					goodTags.add(goodTag3.getText());
				}
				if (goodTag4.getText().length() > 0) {
					goodTags.add(goodTag4.getText());
				}
				//if (goodTag5.getText().length() > 0) {
					//goodTags.add(goodTag5.getText());
				//}

				if (badTag1.getText().length() > 0) {
					badTags.add(badTag1.getText());
				}
				if (badTag2.getText().length() > 0) {
					badTags.add(badTag2.getText());
				}
				if (badTag3.getText().length() > 0) {
					badTags.add(badTag3.getText());
				}
				if (badTag4.getText().length() > 0) {
					badTags.add(badTag4.getText());
				}
				//if (badTag5.getText().length() > 0) {
					//badTags.add(badTag5.getText());
				//}

				update();
				saveTags();

			}
		});
		button.setBounds(387, 113, 323, 114);
		frmTest.getContentPane().add(button);
		
		Button button_1 = new Button("Clear");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("clearing all fields");
				for (int i = 0; i <  textAreasArrayList.size(); i++) {
					textAreasArrayList.get(i).setText("");
				}
			}
		});
		button_1.setFont(new Font("Dialog", Font.PLAIN, 18));
		button_1.setBounds(476, 56, 126, 39);
		frmTest.getContentPane().add(button_1);

		initializeTags();

		System.out.println("initialize end");
	}

	public static void goodPrintln(String theString) {
		textArea_1.append(theString);
		textArea_1.append("\n");

	}

	public static void initializeTags() {
		String filename = "C:/Desktop 2/initialTags.txt";
		int goodCount = 0;
		int badCount = 0;

		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				if (line.substring(0, 4).equals("GOOD")) {
					System.out.println("hek ya! " + line);
					textAreasArrayList.get(goodCount).setText(line.substring(4));
					goodCount++;
				}
				else {
					System.out.println("nope.avi" + line);
					textAreasArrayList.get(badCount + 5).setText(line.substring(4));
					badCount++;
				}

			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveTags() {
		String filename = "C:/Desktop 2/initialTags.txt";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));

			for (int i = 0; i < goodTags.size(); i++) {
				out.write("GOOD" + goodTags.get(i));
				out.newLine();
			}

			for (int i = 0; i < badTags.size(); i++) {
				out.write("BAD " + badTags.get(i));
				out.newLine();
			}

			out.close();
		} catch (Exception e) {
		}
	}

	public static void update() {
		String filename = "C:/Desktop 2/gamelist.txt";
		//filename = "C:/Desktop 2/index.json";

		boolean stillGood = true;

		BufferedReader br = null;
		String line = "";
		String lowercaseLine;
		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				
				lowercaseLine = line.toLowerCase();
				stillGood = true;

				for (int i = 0; i < goodTags.size(); i++) {
					if (lowercaseLine.contains(goodTags.get(i).toLowerCase())) {
						
					} else {
						stillGood = false;
						break;
					}
				}
				if (stillGood) {
					for (int i = 0; i < badTags.size(); i++) {
						if (lowercaseLine.contains(badTags.get(i).toLowerCase())) {
							stillGood = false;
							break;
						} else {

						}
					}
				}

				if (stillGood) {
					goodPrintln(line);
				}
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
