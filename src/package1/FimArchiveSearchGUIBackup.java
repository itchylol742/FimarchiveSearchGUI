package package1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.TextArea;
import java.awt.Font;
import javax.swing.JRadioButton;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import javax.swing.ButtonGroup;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Checkbox;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.SystemColor;

public class FimArchiveSearchGUIBackup {

	// to do: sort by date
	// filter by uploaded date
	// show sequels and prequels

	// Logic

	static ArrayList<FimfictionStory> resultsArray = new ArrayList<FimfictionStory>();

	static ArrayList<String> arrayOfUselessProperties = new ArrayList<String>();

	static ArrayList<String> goodTags = new ArrayList<String>();
	static ArrayList<String> badTags = new ArrayList<String>();

	static int indents = 0;
	static int updateInterval = 5000;
	static int numberOfStoriesScanned = 0;

	static boolean stillGood = true;

	static FimfictionStory temp = new FimfictionStory();

	static int maxNumberOfResultsShown = 1000;

	static int minLikesInt = -1;
	static int minWordsInt = -1;
	static int maxWordsInt = -1;

	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	// GUI

	private JFrame frmTest;

	static TextField goodTag1 = new TextField();
	static TextField goodTag2 = new TextField();
	static TextField goodTag3 = new TextField();
	static TextField goodTag4 = new TextField();
	static TextField goodTag5 = new TextField();

	static TextField badTag1 = new TextField();
	static TextField badTag2 = new TextField();
	static TextField badTag3 = new TextField();
	static TextField badTag4 = new TextField();
	static TextField badTag5 = new TextField();

	static ArrayList<TextField> textAreasArrayList = new ArrayList<TextField>();

	static TextArea textArea = new TextArea();

	static JRadioButton views = new JRadioButton("Views");
	static JRadioButton likes = new JRadioButton("Likes");
	static JRadioButton words = new JRadioButton("Words");

	static Checkbox completedOnlyCheckbox = new Checkbox("Completed stories only");

	private ButtonGroup searchByRadioButtons = new ButtonGroup();
	static TextField minLikesText = new TextField();
	static TextField minWordsText = new TextField();
	static TextField maxWordsText = new TextField();

	static TextField latestAllowedPublishDate = new TextField();
	static TextField earliestAllowedPublishDate = new TextField();

	private final Label label_3 = new Label("Minimum Likes");
	private final Label label_4 = new Label("Minimum Words");
	private final Label label_5 = new Label("Maximum Words");
	private final Label label_6 = new Label("YYYY/MM/DD");
	private final Label label_8 = new Label("Published after");
	private final Label label_9 = new Label("Published before");
	private final JRadioButton rdbtnNewestFirst = new JRadioButton("Newest First");
	private final JRadioButton rdbtnOldestFirst = new JRadioButton("Oldest First");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FimArchiveSearchGUIBackup window = new FimArchiveSearchGUIBackup();
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
	public FimArchiveSearchGUIBackup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Logic

		arrayOfUselessProperties.clear();
		arrayOfUselessProperties.add("archive");
		arrayOfUselessProperties.add("avatar");
		arrayOfUselessProperties.add("color");
		arrayOfUselessProperties.add("cover_image");
		arrayOfUselessProperties.add("chapters");

		// GUI

		frmTest = new JFrame();
		frmTest.setTitle("Fimfiction Archive Searcher");
		frmTest.setBounds(150, 25, 1394, 953);
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTest.getContentPane().setLayout(null);

		textArea = new TextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea.setBounds(78, 305, 1105, 599);
		frmTest.getContentPane().add(textArea);

		// radio buttons for sorting

		views.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(views);
		views.setBounds(935, 50, 109, 23);
		frmTest.getContentPane().add(views);

		likes.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(likes);
		likes.setBounds(935, 76, 109, 23);
		frmTest.getContentPane().add(likes);

		words.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(words);
		words.setBounds(935, 102, 109, 23);
		frmTest.getContentPane().add(words);

		//

		Button button = new Button("Search");
		button.setFont(new Font("Arial", Font.PLAIN, 99));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					updateTextArea();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button.setBounds(339, 71, 355, 213);
		frmTest.getContentPane().add(button);

		goodTag1.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag1.setBounds(108, 58, 164, 34);
		frmTest.getContentPane().add(goodTag1);

		goodTag2.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag2.setBounds(108, 106, 164, 34);
		frmTest.getContentPane().add(goodTag2);

		goodTag3.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag3.setBounds(108, 156, 164, 34);
		frmTest.getContentPane().add(goodTag3);

		Label label = new Label("Sort By");
		label.setFont(new Font("Arial", Font.PLAIN, 25));
		label.setBounds(935, 10, 126, 34);
		frmTest.getContentPane().add(label);

		Label label_1 = new Label("Good Tags");
		label_1.setFont(new Font("Arial", Font.PLAIN, 25));
		label_1.setBounds(108, 10, 126, 34);
		frmTest.getContentPane().add(label_1);

		Label label_2 = new Label("Bad Tags");
		label_2.setFont(new Font("Arial", Font.PLAIN, 25));
		label_2.setBounds(748, 10, 126, 34);
		frmTest.getContentPane().add(label_2);

		badTag1.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag1.setBounds(743, 61, 164, 34);
		frmTest.getContentPane().add(badTag1);

		badTag2.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag2.setBounds(743, 106, 164, 34);
		frmTest.getContentPane().add(badTag2);

		badTag3.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag3.setBounds(743, 156, 164, 34);
		frmTest.getContentPane().add(badTag3);
		goodTag4.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag4.setBounds(108, 204, 164, 34);

		frmTest.getContentPane().add(goodTag4);
		goodTag5.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag5.setBounds(108, 250, 164, 34);

		frmTest.getContentPane().add(goodTag5);
		badTag4.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag4.setBounds(743, 204, 164, 34);

		frmTest.getContentPane().add(badTag4);
		badTag5.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag5.setBounds(743, 250, 164, 34);

		textAreasArrayList.add(goodTag1);
		textAreasArrayList.add(goodTag2);
		textAreasArrayList.add(goodTag3);
		textAreasArrayList.add(goodTag4);
		textAreasArrayList.add(goodTag5);

		textAreasArrayList.add(badTag1);
		textAreasArrayList.add(badTag2);
		textAreasArrayList.add(badTag3);
		textAreasArrayList.add(badTag4);
		textAreasArrayList.add(badTag5);

		frmTest.getContentPane().add(badTag5);

		completedOnlyCheckbox.setFont(new Font("Arial", Font.PLAIN, 18));
		completedOnlyCheckbox.setBounds(935, 215, 225, 23);
		frmTest.getContentPane().add(completedOnlyCheckbox);
		minLikesText.setFont(new Font("Arial", Font.PLAIN, 20));
		minLikesText.setText("10");
		minLikesText.setBounds(1197, 58, 164, 34);

		frmTest.getContentPane().add(minLikesText);
		minWordsText.setText("10");
		minWordsText.setFont(new Font("Arial", Font.PLAIN, 20));
		minWordsText.setBounds(1197, 138, 164, 34);

		frmTest.getContentPane().add(minWordsText);
		maxWordsText.setText("10");
		maxWordsText.setFont(new Font("Arial", Font.PLAIN, 20));
		maxWordsText.setBounds(1197, 219, 164, 34);

		frmTest.getContentPane().add(maxWordsText);
		label_3.setFont(new Font("Arial", Font.PLAIN, 20));
		label_3.setBounds(1197, 10, 158, 34);

		frmTest.getContentPane().add(label_3);
		label_4.setFont(new Font("Arial", Font.PLAIN, 20));
		label_4.setBounds(1197, 102, 158, 34);

		frmTest.getContentPane().add(label_4);
		label_5.setFont(new Font("Arial", Font.PLAIN, 20));
		label_5.setBounds(1197, 179, 158, 34);

		frmTest.getContentPane().add(label_5);
		latestAllowedPublishDate.setFont(new Font("Arial", Font.PLAIN, 21));
		latestAllowedPublishDate.setBounds(1197, 529, 164, 34);

		frmTest.getContentPane().add(latestAllowedPublishDate);
		label_6.setForeground(Color.GRAY);
		label_6.setFont(new Font("Arial", Font.PLAIN, 15));
		label_6.setBounds(1197, 386, 158, 23);

		frmTest.getContentPane().add(label_6);
		earliestAllowedPublishDate.setFont(new Font("Arial", Font.PLAIN, 21));
		earliestAllowedPublishDate.setBounds(1197, 334, 164, 34);

		frmTest.getContentPane().add(earliestAllowedPublishDate);
		label_8.setFont(new Font("Arial", Font.PLAIN, 20));
		label_8.setBounds(1197, 294, 158, 34);

		frmTest.getContentPane().add(label_8);
		label_9.setFont(new Font("Arial", Font.PLAIN, 20));
		label_9.setBounds(1197, 489, 158, 34);

		frmTest.getContentPane().add(label_9);
		rdbtnNewestFirst.setFont(new Font("Arial", Font.PLAIN, 18));
		rdbtnNewestFirst.setBounds(935, 128, 158, 23);

		frmTest.getContentPane().add(rdbtnNewestFirst);
		rdbtnOldestFirst.setFont(new Font("Arial", Font.PLAIN, 18));
		rdbtnOldestFirst.setBounds(935, 156, 158, 23);

		frmTest.getContentPane().add(rdbtnOldestFirst);

		Label label_7 = new Label("Made\r\n by /u/itchylol742");
		label_7.setForeground(Color.GRAY);
		label_7.setFont(new Font("Arial", Font.PLAIN, 15));
		label_7.setBounds(1197, 855, 158, 23);
		frmTest.getContentPane().add(label_7);

		JTextArea txtrLeave = new JTextArea();
		txtrLeave.setForeground(Color.GRAY);
		txtrLeave.setFont(new Font("Arial", Font.PLAIN, 15));
		txtrLeave.setBackground(SystemColor.menu);
		txtrLeave.setEditable(false);
		txtrLeave.setText("Leave fields empty for \r\nno limit/filter");
		txtrLeave.setBounds(1197, 654, 164, 54);
		frmTest.getContentPane().add(txtrLeave);

		loadSettings();
		goodPrint("- Instructions:\n");
		goodPrint("- Tags are not case sensitive. Type in proper caps, no caps, or all caps if you want\n");
		goodPrint("\n");
		goodPrint("- Notes:\n");
		goodPrint(
				"- Some stories have -1 likes due to metadata error, so even setting min likes to 0 will filter them out\n");
		goodPrint("- Some stories have 0 (or very low number) words due to metadata error...?,\n");
		goodPrint("    - Or maybe somehow those short stories got on the site\n");
		goodPrint("- Dates are in format YYYY/MM/DD\n");
		goodPrint("- Limited to 5 good tags and 5 bad tags. Hardcoded\n");

	}

	// my methods below

	public static void goodPrint(String theString) {
		textArea.append(theString);
	}

	public static void saveSettings() {
		String filename = "settings.txt";
		goodTags.clear();
		badTags.clear();
		readTextBoxesIntoMemory();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			out.write("5 good tags then 5 bad tags. Hardcoded");
			out.newLine();

			for (int i = 0; i < 5; i++) {// good tags
				out.write("GOOD");
				if (i < goodTags.size()) {
					out.write(goodTags.get(i));
				}
				out.newLine();
			}
			for (int i = 5; i < 10; i++) {// bad tags
				out.write("BAD ");
				if (i - 5 < badTags.size()) {
					out.write(badTags.get(i - 5));
				}
				out.newLine();
			}

			out.write("Sort by:");
			out.newLine();
			if (views.isSelected()) {
				out.write("views");
				out.newLine();
			} else if (likes.isSelected()) {
				out.write("likes");
				out.newLine();
			} else if (words.isSelected()) {
				out.write("words");
				out.newLine();
			} else {
				out.write("default");
				out.newLine();
			}
			out.write("Min likes, min words, and max words");
			out.newLine();
			out.write(minLikesText.getText());
			out.newLine();
			out.write(minWordsText.getText());
			out.newLine();
			out.write(maxWordsText.getText());
			out.newLine();

			out.write("Completed stories only? y/n");
			out.newLine();
			if (completedOnlyCheckbox.getState()) {
				out.write("y");
				out.newLine();
			} else {
				out.write("n");
				out.newLine();
			}

			out.close();
		} catch (Exception e) {
		}
	}

	public static void loadSettings() {
		String filename = "settings.txt";

		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();// skip first line
			for (int i = 0; i < 10; i++) {
				line = br.readLine();
				if (line.length() > 3) {
					textAreasArrayList.get(i).setText(line.substring(4));
				}
			}

			line = br.readLine();// skip this line
			line = br.readLine();// view order

			if (line.equals("likes")) {
				likes.setSelected(true);
			} else if (line.equals("views")) {
				views.setSelected(true);
			} else if (line.equals("words")) {
				words.setSelected(true);
			} else {
				// nothing
			}
			line = br.readLine();// skip this line
			line = br.readLine();// min likes
			minLikesText.setText(line);
			line = br.readLine();// min words
			minWordsText.setText(line);
			line = br.readLine();// max words
			maxWordsText.setText(line);

			line = br.readLine();// skip this line
			line = br.readLine();// completed only
			if (line.equals("y")) {
				completedOnlyCheckbox.setState(true);
			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readTextBoxesIntoMemory() {

		// Good tags
		if (goodTag1.getText().length() > 1) {
			goodTags.add(goodTag1.getText().trim());// this could be optimized by looping through a for loop
		}
		if (goodTag2.getText().length() > 1) {
			goodTags.add(goodTag2.getText().trim());
		}
		if (goodTag3.getText().length() > 1) {
			goodTags.add(goodTag3.getText().trim());
		}
		if (goodTag4.getText().length() > 1) {
			goodTags.add(goodTag4.getText().trim());
		}
		if (goodTag5.getText().length() > 1) {
			goodTags.add(goodTag5.getText().trim());
		}

		// Bad tags
		if (badTag1.getText().length() > 1) {
			badTags.add(badTag1.getText().trim());
		}
		if (badTag2.getText().length() > 1) {
			badTags.add(badTag2.getText().trim());
		}
		if (badTag3.getText().length() > 1) {
			badTags.add(badTag3.getText().trim());
		}
		if (badTag4.getText().length() > 1) {
			badTags.add(badTag4.getText().trim());
		}
		if (badTag5.getText().length() > 1) {
			badTags.add(badTag5.getText().trim());
		}

		// Min likes, min words, max words
		if (minLikesText.getText().length() > 0) {
			minLikesInt = Integer.parseInt(minLikesText.getText());
		}
		if (minWordsText.getText().length() > 0) {
			minWordsInt = Integer.parseInt(minWordsText.getText());
		}
		if (maxWordsText.getText().length() > 0) {
			maxWordsInt = Integer.parseInt(maxWordsText.getText());
		}

		// if (EarliestAllowedPublishDate.getText().length() > 0) {
		// minLikesInt = Integer.parseInt(minLikesText.getText());
		// }
		// if (minWordsText.getText().length() > 0) {
		// minWordsInt = Integer.parseInt(minWordsText.getText());
		// }

	}

	public static void updateTextArea() throws ParseException {
		saveSettings();
		minLikesInt = -1;
		minWordsInt = -1;
		maxWordsInt = -1;

		textArea.setText("");
		goodTags.clear();
		badTags.clear();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

		arrayOfUselessProperties.clear();
		arrayOfUselessProperties.add("archive");
		arrayOfUselessProperties.add("avatar");
		arrayOfUselessProperties.add("color");
		arrayOfUselessProperties.add("cover_image");
		arrayOfUselessProperties.add("chapters");

		try {
			String name = "default";

			readTextBoxesIntoMemory();

			JsonReader reader = new JsonReader(new FileReader("index.json"));
			while (true) {
				JsonToken token = reader.peek();
				switch (token) {
				case BEGIN_ARRAY:
					reader.beginArray();
					indents++;
					break;
				case END_ARRAY:
					indents--;
					reader.endArray();
					break;
				case BEGIN_OBJECT:
					indents++;
					reader.beginObject();
					break;
				case END_OBJECT:
					indents--;
					if (indents == 1) {
						// End of story data
						// Checking is complete
						if (completedOnlyCheckbox.getState()) {// is complete?
							if (!temp.completionStatus.equals("complete")) {
								stillGood = false;
							}
						}
						// check min likes
						if (minLikesInt != -1 && stillGood) {
							if (temp.likes < minLikesInt) {
								stillGood = false;
							}
						}
						// check min words
						if (minWordsInt != -1 && stillGood) {
							if (temp.words < minWordsInt) {
								stillGood = false;
							}
						}
						// check max words
						if (maxWordsInt != -1 && stillGood) {
							if (temp.words > maxWordsInt) {
								stillGood = false;
							}
						}

						// Checking good tags
						if (stillGood) {
							//temp.originalTags = new ArrayList<String>(temp.tags);
							for (int i = 0; i < temp.tags.size(); i++) {
								temp.tags.set(i, temp.tags.get(i).toLowerCase());
							}
							for (int i = 0; i < goodTags.size(); i++) {
								if (temp.tags.contains(goodTags.get(i).toLowerCase())) {
									// still good
								} else {
									stillGood = false;
									break;
								}
							}
						}
						// Checking bad tags
						if (stillGood) {
							for (int i = 0; i < badTags.size(); i++) {
								if (temp.tags.contains(badTags.get(i).toLowerCase())) {
									stillGood = false;
									break;
								} else {
									// still good
								}
							}
						}

						if (stillGood) {
							//temp.tags = temp.originalTags;
							resultsArray.add(temp);
						}

						if (numberOfStoriesScanned % updateInterval == 0) {
							goodPrint("searching " + numberOfStoriesScanned + ", " + resultsArray.size()
									+ " good stories\n");

						}

						temp = new FimfictionStory();// reset temp variables
						stillGood = true;
					}
					reader.endObject();
					break;
				case NAME:
					if (indents == 1) {
						// goodPrint("--Start of new story data--\n");
						numberOfStoriesScanned++;
					}
					name = reader.nextName();
					// indent();
					// goodPrint(name + " " + indents + "\n");

					if (arrayOfUselessProperties.contains(name)) {
						// indent();
						// goodPrint("--skipping useless property " + name + "--\n");
						reader.skipValue();
					}
					break;
				case STRING:
					String s = reader.nextString();
					if (indents == 4 && name.equals("name")) {// tags
						temp.tags.add(s);
					} else if (indents == 2 && name.equals("title")) {
						temp.title = s;
					} else if (indents == 2 && name.equals("date_published")) {
						temp.datePublishedString = s.substring(0, 10).replace('-', '/');
						temp.datePublishedObject = formatter.parse(s.substring(0, 10).replace('-', '/'));
					} else if (indents == 3 && name.equals("name")) {
						temp.author = s;
					} else if (name.equals("completion_status")) {
						temp.completionStatus = s;
					} else if (name.equals("description_html")) {
						temp.description = s;
					}

					break;
				case NUMBER:
					int n = reader.nextInt();
					if (name.equals("num_likes")) {
						temp.likes = n;
					} else if (name.equals("num_dislikes")) {
						temp.dislikes = n;
					} else if (name.equals("num_views")) {
						temp.views = n;
					} else if (indents == 2 && name.equals("num_words")) {
						// goodPrint("word count " + n + "\n");
						temp.words = n;
					} else if (name.equals("prequel")) {
						temp.prequelID = (n);
						// goodPrint("preq " + n);
					}
					break;
				case BOOLEAN:
					// boolean b = reader.nextBoolean();
					reader.nextBoolean();
					break;
				case NULL:
					reader.nextNull();
					break;
				case END_DOCUMENT:

					if (views.isSelected()) {
						Collections.sort(resultsArray, FimfictionStory.ViewsComparator);
					} else if (likes.isSelected()) {
						Collections.sort(resultsArray, FimfictionStory.LikesComparator);
					} else if (words.isSelected()) {
						Collections.sort(resultsArray, FimfictionStory.WordsComparator);
					}
					boolean tooManyResults = false;
					String storyInfo = "";
					for (int i = 0; i < resultsArray.size(); i++) {
						storyInfo += ("Title: " + resultsArray.get(i).title + "\n");
						storyInfo += ("Author: " + resultsArray.get(i).author + "\n");

						resultsArray.get(i).removeHtmlFormattingFromDescription();

						storyInfo += ("Description: " + resultsArray.get(i).description + "\n");
						if (resultsArray.get(i).prequelID != -1) {
							storyInfo += ("Prequel ID: " + resultsArray.get(i).prequelID + "\n");
						}
						storyInfo += ("Date Published: " + resultsArray.get(i).datePublishedString + "\n");
						storyInfo += ("Likes: " + resultsArray.get(i).likes);
						storyInfo += (", Dislikes: " + resultsArray.get(i).dislikes + "\n");
						storyInfo += ("Views: " + resultsArray.get(i).views + "\n");
						storyInfo += ("Tags: " + resultsArray.get(i).tags + "\n");
						storyInfo += ("Word Count: " + resultsArray.get(i).words + "\n");
						storyInfo += ("\n");
						if (i % 10 == 0) {
							goodPrint(storyInfo);
							storyInfo = "";
						}

						if (i > maxNumberOfResultsShown) {
							tooManyResults = true;
							goodPrint(storyInfo);
							storyInfo = "";
							break;
						}
					}
					goodPrint(storyInfo);
					storyInfo = "";

					if (tooManyResults) {
						goodPrint("--Too many results, only showing " + maxNumberOfResultsShown + " to avoid lag\n");
					}
					goodPrint("--End of scanning--\n");
					goodPrint("--Total stories scanned: " + numberOfStoriesScanned + "--\n");
					goodPrint("--Results: " + resultsArray.size() + "--\n");
					if (views.isSelected()) {
						goodPrint("--Sorting by views--\n");
					} else if (likes.isSelected()) {
						goodPrint("--Sorting by likes--\n");
					} else if (words.isSelected()) {
						goodPrint("--Sorting by words--\n");
					}

					resultsArray.clear();
					numberOfStoriesScanned = 0;
					reader.close();

					return;
				}
			}
		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
