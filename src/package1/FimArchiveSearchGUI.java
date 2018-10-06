package package1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.TextArea;
import java.awt.Font;
import javax.swing.JRadioButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import javax.jws.soap.SOAPBinding.Use;
import javax.swing.ButtonGroup;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
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
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.Icon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class FimArchiveSearchGUI {
	
	
	static boolean useSampleIndex = false;

	// to do: show sequels and prequels
	// autocomplete!?

	// Logic

	static ArrayList<FimfictionStory> inputStoriesFromSerializableArray = new ArrayList<FimfictionStory>();
	static ArrayList<FimfictionStory> resultsArray = new ArrayList<FimfictionStory>();

	static ArrayList<String> arrayOfUselessProperties = new ArrayList<String>();

	static ArrayList<String> goodTags = new ArrayList<String>();
	static ArrayList<String> badTags = new ArrayList<String>();

	static String titleContainsString = "";
	static String descriptionContainsString = "";

	static int indents = 0;
	static int numberOfStoriesScanned = 0;

	static boolean stillGood = true;
	static boolean tooManyResults = false;

	static FimfictionStory temp = new FimfictionStory();

	static int minLikesInt = -1;
	static int minWordsInt = -1;
	static int maxWordsInt = -1;
	static float minPercentageRatingFloat = -1;

	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
	static final int maxNumberOfResultsShown = 500;
	static final int updateInterval = 10000;
	static final int resultBufferPrintInterval = 10;
	static final int descriptionMaxLineLength = 120;// unused, try to get it working without ruining the description

	static String sortByString = "default";

	static Date latestAllowedPublishDateObject;
	static Date earliestAllowedPublishDateObject;

	static boolean checkEarliestAllowedPublishDate = false;
	static boolean checkLatestAllowedPublishDate = false;
	static boolean hideStoriesWithUnkownPublishDate = false;
	static boolean completedStoriesOnly = false;

	static long startTimeMilliseconds = 0;
	static DecimalFormat df = new DecimalFormat();

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
	static TextField titleContainsTextBox = new TextField();
	static TextField descriptionContainsTextBox = new TextField();

	static ArrayList<TextField> textAreasArrayList = new ArrayList<TextField>();

	static TextArea textArea = new TextArea();

	static JRadioButton viewsRadioButton = new JRadioButton("Views");
	static JRadioButton likesRadioButton = new JRadioButton("Likes");
	static JRadioButton percentRatingRadioButton = new JRadioButton("Percent Rating");
	static JRadioButton wordsRadioButton = new JRadioButton("Words");
	static JRadioButton newestFirstRadioButton = new JRadioButton("Newest First");
	static JRadioButton oldestFirstRadioButton = new JRadioButton("Oldest First");

	static Checkbox completedOnlyCheckbox = new Checkbox("Completed stories only");
	static Checkbox writeResultsCheckbox = new Checkbox();
	static Checkbox useSubsetCheckbox = new Checkbox();

	private ButtonGroup searchByRadioButtons = new ButtonGroup();

	static TextField minLikesText = new TextField();
	static TextField minWordsText = new TextField();
	static TextField maxWordsText = new TextField();
	static TextField minPercentRatingTextBox = new TextField();

	static TextField latestAllowedPublishDate = new TextField();
	static TextField earliestAllowedPublishDate = new TextField();

	static Checkbox hideUnknownPublishDateCheckbox = new Checkbox();

	private final Label label_3 = new Label("Minimum Likes");
	private final Label label_4 = new Label("Minimum Words");
	private final Label label_5 = new Label("Maximum Words");
	private final Label label_6 = new Label("YYYY/MM/DD");
	private final Label label_8 = new Label("Published after");
	private final Label label_9 = new Label("Published before");
	private final JPanel panel = new JPanel();
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panel_1 = new JPanel();
	private final JTextArea txtrMadeByuitchylol = new JTextArea();
	private final Label label_12 = new Label("Description Contains");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FimArchiveSearchGUI window = new FimArchiveSearchGUI();
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
	public FimArchiveSearchGUI() {
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
		frmTest.setTitle("Fimarchive Searcher by itchylol742");
		frmTest.setBounds(150, 25, 1394, 953);
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTest.getContentPane().setLayout(new BorderLayout(0, 0));
		tabbedPane.setToolTipText("");
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setFont(new Font("Arial", Font.PLAIN, 18));

		frmTest.getContentPane().add(tabbedPane);
		tabbedPane.addTab("Search", (Icon) null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		panel.setBackground(SystemColor.menu);
		panel.setLayout(null);

		JTextArea txtrLeave = new JTextArea();
		txtrLeave.setBounds(1182, 780, 164, 45);
		panel.add(txtrLeave);
		txtrLeave.setForeground(Color.GRAY);
		txtrLeave.setFont(new Font("Arial", Font.PLAIN, 15));
		txtrLeave.setBackground(SystemColor.menu);
		txtrLeave.setEditable(false);
		txtrLeave.setText("Leave fields empty for \r\nno limit/filter");

		textArea = new TextArea();
		textArea.setBounds(27, 282, 1133, 599);
		panel.add(textArea);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		latestAllowedPublishDate.setBounds(1182, 560, 164, 34);
		panel.add(latestAllowedPublishDate);
		latestAllowedPublishDate.setFont(new Font("Arial", Font.PLAIN, 21));
		label_9.setBounds(1182, 520, 158, 34);
		panel.add(label_9);
		label_9.setFont(new Font("Arial", Font.PLAIN, 20));
		label_6.setBounds(1182, 489, 158, 23);
		panel.add(label_6);
		label_6.setForeground(Color.GRAY);
		label_6.setFont(new Font("Arial", Font.PLAIN, 15));
		earliestAllowedPublishDate.setBounds(1182, 440, 164, 34);
		panel.add(earliestAllowedPublishDate);
		earliestAllowedPublishDate.setFont(new Font("Arial", Font.PLAIN, 21));
		label_8.setBounds(1182, 400, 158, 34);
		panel.add(label_8);
		label_8.setFont(new Font("Arial", Font.PLAIN, 20));
		maxWordsText.setBounds(1182, 320, 164, 34);
		panel.add(maxWordsText);
		maxWordsText.setText("10");
		maxWordsText.setFont(new Font("Arial", Font.PLAIN, 20));
		label_5.setBounds(1182, 280, 158, 34);
		panel.add(label_5);
		label_5.setFont(new Font("Arial", Font.PLAIN, 20));
		minWordsText.setBounds(1182, 240, 164, 34);
		panel.add(minWordsText);
		minWordsText.setText("10");
		minWordsText.setFont(new Font("Arial", Font.PLAIN, 20));
		label_4.setBounds(1182, 200, 158, 34);
		panel.add(label_4);
		label_4.setFont(new Font("Arial", Font.PLAIN, 20));
		minLikesText.setBounds(1182, 40, 164, 34);
		panel.add(minLikesText);
		minLikesText.setFont(new Font("Arial", Font.PLAIN, 20));
		minLikesText.setText("10");
		label_3.setBounds(1182, 0, 158, 34);
		panel.add(label_3);
		label_3.setFont(new Font("Arial", Font.PLAIN, 20));
		completedOnlyCheckbox.setBounds(913, 222, 225, 23);
		panel.add(completedOnlyCheckbox);

		completedOnlyCheckbox.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(oldestFirstRadioButton);
		oldestFirstRadioButton.setBounds(909, 170, 158, 23);
		panel.add(oldestFirstRadioButton);
		oldestFirstRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(newestFirstRadioButton);
		newestFirstRadioButton.setBounds(909, 144, 158, 23);
		panel.add(newestFirstRadioButton);
		newestFirstRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
		wordsRadioButton.setBounds(909, 118, 109, 23);
		panel.add(wordsRadioButton);

		wordsRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(wordsRadioButton);
		likesRadioButton.setBounds(909, 66, 109, 23);
		panel.add(likesRadioButton);

		likesRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(likesRadioButton);
		viewsRadioButton.setBounds(909, 40, 109, 23);
		panel.add(viewsRadioButton);

		// radio buttons for sorting

		viewsRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
		searchByRadioButtons.add(viewsRadioButton);

		Label label = new Label("Sort By");
		label.setBounds(909, 0, 126, 34);
		panel.add(label);
		label.setFont(new Font("Arial", Font.PLAIN, 25));

		Label label_2 = new Label("Bad Tags");
		label_2.setBounds(723, 0, 126, 34);
		panel.add(label_2);
		label_2.setFont(new Font("Arial", Font.PLAIN, 25));
		badTag1.setBounds(718, 48, 164, 34);
		panel.add(badTag1);

		badTag1.setFont(new Font("Arial", Font.PLAIN, 21));

		badTag2.setBounds(718, 96, 164, 34);
		panel.add(badTag2);

		badTag2.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag3.setBounds(718, 146, 164, 34);
		panel.add(badTag3);

		badTag3.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag4.setBounds(718, 194, 164, 34);
		panel.add(badTag4);
		badTag4.setFont(new Font("Arial", Font.PLAIN, 21));
		badTag5.setBounds(718, 240, 164, 34);
		panel.add(badTag5);
		badTag5.setFont(new Font("Arial", Font.PLAIN, 21));

		//

		Button button = new Button("Search");
		button.setBounds(299, 194, 355, 81);
		panel.add(button);
		button.setFont(new Font("Arial", Font.PLAIN, 46));
		goodTag5.setBounds(80, 240, 164, 34);
		panel.add(goodTag5);
		goodTag5.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag4.setBounds(80, 194, 164, 34);
		panel.add(goodTag4);
		goodTag4.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag3.setBounds(80, 146, 164, 34);
		panel.add(goodTag3);

		goodTag3.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag2.setBounds(80, 96, 164, 34);
		panel.add(goodTag2);

		goodTag2.setFont(new Font("Arial", Font.PLAIN, 21));
		goodTag1.setBounds(80, 48, 164, 34);
		panel.add(goodTag1);

		goodTag1.setFont(new Font("Arial", Font.PLAIN, 21));

		Label label_1 = new Label("Good Tags");
		label_1.setBounds(80, 0, 126, 34);
		panel.add(label_1);
		label_1.setFont(new Font("Arial", Font.PLAIN, 25));

		hideUnknownPublishDateCheckbox.setFont(new Font("Arial", Font.PLAIN, 18));
		hideUnknownPublishDateCheckbox.setBounds(1166, 634, 14, 23);
		panel.add(hideUnknownPublishDateCheckbox);

		JTextArea txtrHideStoriesWith = new JTextArea();
		txtrHideStoriesWith.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				hideUnknownPublishDateCheckbox.setState(!hideUnknownPublishDateCheckbox.getState());
			}
		});
		txtrHideStoriesWith.setText("Hide stories with \r\nunknown publish dates");
		txtrHideStoriesWith.setForeground(Color.BLACK);
		txtrHideStoriesWith.setFont(new Font("Arial", Font.PLAIN, 18));
		txtrHideStoriesWith.setEditable(false);
		txtrHideStoriesWith.setBackground(SystemColor.menu);
		txtrHideStoriesWith.setBounds(1182, 634, 187, 60);
		panel.add(txtrHideStoriesWith);
		txtrMadeByuitchylol.setText("Made by itchylol742");
		txtrMadeByuitchylol.setForeground(Color.GRAY);
		txtrMadeByuitchylol.setFont(new Font("Arial", Font.PLAIN, 15));
		txtrMadeByuitchylol.setEditable(false);
		txtrMadeByuitchylol.setBackground(SystemColor.menu);
		txtrMadeByuitchylol.setBounds(1182, 836, 164, 34);

		panel.add(txtrMadeByuitchylol);

		Label label_7 = new Label("Minimum % Rating");
		label_7.setFont(new Font("Arial", Font.PLAIN, 20));
		label_7.setBounds(1182, 80, 181, 34);
		panel.add(label_7);

		minPercentRatingTextBox.setText("60");
		minPercentRatingTextBox.setFont(new Font("Arial", Font.PLAIN, 20));
		minPercentRatingTextBox.setBounds(1182, 120, 164, 34);
		panel.add(minPercentRatingTextBox);

		searchByRadioButtons.add(percentRatingRadioButton);
		percentRatingRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
		percentRatingRadioButton.setBounds(909, 92, 181, 23);
		panel.add(percentRatingRadioButton);

		writeResultsCheckbox.setLabel("Write results to file");
		writeResultsCheckbox.setFont(new Font("Arial", Font.PLAIN, 18));
		writeResultsCheckbox.setBounds(1166, 700, 180, 23);
		panel.add(writeResultsCheckbox);

		useSubsetCheckbox.setLabel("optimizedIndex.json");
		useSubsetCheckbox.setFont(new Font("Arial", Font.PLAIN, 18));
		useSubsetCheckbox.setBounds(1166, 729, 203, 23);
		panel.add(useSubsetCheckbox);

		Label label_11 = new Label("Title Contains");
		label_11.setFont(new Font("Arial", Font.PLAIN, 25));
		label_11.setBounds(299, 0, 168, 34);
		panel.add(label_11);

		titleContainsTextBox.setFont(new Font("Arial", Font.PLAIN, 21));
		titleContainsTextBox.setBounds(299, 48, 355, 34);
		panel.add(titleContainsTextBox);
		descriptionContainsTextBox.setText("<dynamic");
		descriptionContainsTextBox.setFont(new Font("Arial", Font.PLAIN, 21));
		descriptionContainsTextBox.setBounds(299, 146, 355, 34);

		panel.add(descriptionContainsTextBox);
		label_12.setFont(new Font("Arial", Font.PLAIN, 25));
		label_12.setBounds(299, 96, 257, 34);

		panel.add(label_12);

		tabbedPane.addTab("Sequel/Prequel finder (unfinished)", null, panel_1, null);
		tabbedPane.setEnabledAt(1, true);
		panel_1.setLayout(null);

		TextField storyIDField = new TextField();
		storyIDField.setFont(new Font("Arial", Font.PLAIN, 21));
		storyIDField.setBounds(62, 140, 217, 34);
		panel_1.add(storyIDField);

		JTextArea txtrEnterTheStory = new JTextArea();
		txtrEnterTheStory.setText(
				"Enter the story ID of a story, and it will find any sequels and prequels the story has.\r\nA story can only have maximum 1 prequel, but it can have any number of sequels. ");
		txtrEnterTheStory.setForeground(Color.GRAY);
		txtrEnterTheStory.setFont(new Font("Arial", Font.PLAIN, 15));
		txtrEnterTheStory.setEditable(false);
		txtrEnterTheStory.setBackground(SystemColor.menu);
		txtrEnterTheStory.setBounds(62, 36, 553, 45);
		panel_1.add(txtrEnterTheStory);

		Label label_10 = new Label("Story ID");
		label_10.setFont(new Font("Arial", Font.PLAIN, 20));
		label_10.setBounds(62, 87, 158, 34);
		panel_1.add(label_10);

		Button button_1 = new Button("Find sequels/prequels");
		button_1.setFont(new Font("Arial", Font.PLAIN, 20));
		button_1.setBounds(301, 140, 217, 34);
		panel_1.add(button_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setBounds(161, 265, 421, 54);
		panel_1.add(comboBox);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (useSubsetCheckbox.getState()) {
						// subset
						updateTextAreaFromOptimizedIndex();
					} else {
						// normal operation
						createOptimizedIndex();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		loadTagTextAreasIntoTagTestAreasArrayList();

		loadSettings();
		goodPrint("IMPORTANT! If this is your first time running the program, have optimizedIndex.json UNCHECKED\n");
		goodPrint("This will created optimizedIndex.json from information found in index.json\n");
		goodPrint("After that, run the program with optimizedIndex.json checked\n");
		goodPrint("optimizedIndex.json is 1/5th the size of index.json. This makes the program 5 times faster!\n");
		goodPrint("\n");

		goodPrint("- Tags are not case sensitive. Type in proper caps, no caps, or all caps if you want\n");
		goodPrint(
				"- Some stories have -1 likes due to metadata error, so even setting min likes to 0 will filter them out\n");
		goodPrint("- Some stories have 0 (or very low number) words due to metadata error...?,\n");
		goodPrint("    - Or maybe somehow those short stories got on the site\n");
		goodPrint("- Percentage rating assumes there's 1 more like and 1 more dislike than there actually is\n");
		goodPrint("    - The +1 makes sure stories with few ratings don't get perfect score\n");
		goodPrint("- Dates are in the format YYYY/MM/DD\n");
		goodPrint("    - but you don't need 0's before the month and day if it's less than 10\n");
		goodPrint("    - For exmaple, 2015/02/03 is valid and 2015/2/3 is also valid\n");

	}

	// my methods below

	public static void goodPrint(String theString) {
		textArea.append(theString);
	}

	public static void loadTagTextAreasIntoTagTestAreasArrayList() {
		textAreasArrayList.clear();

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
	}

	public static void saveSettings() throws ParseException {
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
			if (viewsRadioButton.isSelected()) {
				out.write("views");
				out.newLine();
			} else if (likesRadioButton.isSelected()) {
				out.write("likes");
				out.newLine();
			} else if (percentRatingRadioButton.isSelected()) {
				out.write("percent");
				out.newLine();
			} else if (wordsRadioButton.isSelected()) {
				out.write("words");
				out.newLine();
			} else if (newestFirstRadioButton.isSelected()) {
				out.write("newest");
				out.newLine();
			} else if (oldestFirstRadioButton.isSelected()) {
				out.write("oldest");
				out.newLine();
			} else {
				out.write("default");
				out.newLine();
			}
			out.write("Min likes, min words, max words, min % rating - blank for no filter");
			out.newLine();
			out.write(minLikesText.getText());
			out.newLine();
			out.write(minWordsText.getText());
			out.newLine();
			out.write(maxWordsText.getText());
			out.newLine();
			out.write(minPercentRatingTextBox.getText());
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

			out.write("earliest allowed publish date in the format YYYY/MM/DD - blank for no filter");
			out.newLine();

			out.write(earliestAllowedPublishDate.getText());
			out.newLine();

			out.write("latest allowed publish date in the format YYYY/MM/DD - blank for no filter");
			out.newLine();

			out.write(latestAllowedPublishDate.getText());
			out.newLine();

			out.write("Hide stories with unknown publish dates? y/n");
			out.newLine();

			if (hideUnknownPublishDateCheckbox.getState()) {
				out.write("y");
				out.newLine();
			} else {
				out.write("n");
				out.newLine();
			}

			out.write("Save results to results.txt? y/n");
			out.newLine();

			if (writeResultsCheckbox.getState()) {
				out.write("y");
				out.newLine();
			} else {
				out.write("n");
				out.newLine();
			}

			out.write("Use subset? y/n");
			out.newLine();

			if (useSubsetCheckbox.getState()) {
				out.write("y");
				out.newLine();
			} else {
				out.write("n");
				out.newLine();
			}

			out.write("Title contains:");
			out.newLine();

			out.write(titleContainsTextBox.getText());
			out.newLine();

			out.write("Description contains:");
			out.newLine();

			out.write(descriptionContainsTextBox.getText());
			out.newLine();

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
			System.out.println(line);
			for (int i = 0; i < 10; i++) {
				line = br.readLine();
				System.out.println(line + " assign to textAreasArrayList index " + i);
				if (line.length() > 3) {
					textAreasArrayList.get(i).setText(line.substring(4));
				}
			}

			line = br.readLine();// skip line "Sort by:"
			System.out.println(line);
			line = br.readLine();// sort order
			System.out.println(line);

			if (line.equals("likes")) {
				likesRadioButton.setSelected(true);
			} else if (line.equals("views")) {
				viewsRadioButton.setSelected(true);
			} else if (line.equals("words")) {
				wordsRadioButton.setSelected(true);
			} else if (line.equals("newest")) {
				newestFirstRadioButton.setSelected(true);
			} else if (line.equals("oldest")) {
				oldestFirstRadioButton.setSelected(true);
			} else if (line.equals("percent")) {
				percentRatingRadioButton.setSelected(true);
			} else {
				// nothing
			}
			line = br.readLine();// skip line "Min likes, min words, max words, min % rating - blank for no
									// filter"

			line = br.readLine();// min likes
			minLikesText.setText(line);
			line = br.readLine();// min words
			minWordsText.setText(line);
			line = br.readLine();// max words
			maxWordsText.setText(line);
			line = br.readLine();// max words
			minPercentRatingTextBox.setText(line);

			line = br.readLine();// skip this line
			line = br.readLine();// completed stories only?
			if (line.equals("y")) {
				completedOnlyCheckbox.setState(true);
			}
			line = br.readLine();// skip this line
			line = br.readLine();// earliest allowed publish date
			earliestAllowedPublishDate.setText(line);

			line = br.readLine();// skip this line
			line = br.readLine();// latest allowed publish date
			latestAllowedPublishDate.setText(line);

			line = br.readLine();// skip this line
			line = br.readLine();// skip stories with unkown publish dates?
			if (line.equals("y")) {
				hideUnknownPublishDateCheckbox.setState(true);
			}

			line = br.readLine();// skip this line
			line = br.readLine();// skip stories with unkown publish dates?
			if (line.equals("y")) {
				writeResultsCheckbox.setState(true);
			}

			line = br.readLine();// skip this line
			line = br.readLine();// use subset??
			if (line.equals("y")) {
				useSubsetCheckbox.setState(true);
			}

			line = br.readLine();// skip this line
			line = br.readLine();// title contains
			titleContainsTextBox.setText(line);

			line = br.readLine();// skip this line
			line = br.readLine();// description contains
			descriptionContainsTextBox.setText(line);

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readTextBoxesIntoMemory() throws ParseException {
		goodTags.clear();
		badTags.clear();
		
		minLikesInt = -1;
		minWordsInt = -1;
		maxWordsInt = -1;
		minPercentageRatingFloat = -1;
		resultsArray.clear();
		descriptionContainsString = "";
		titleContainsString = "";
		

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
		if (minPercentRatingTextBox.getText().length() > 0) {
			minPercentageRatingFloat = Float.parseFloat(minPercentRatingTextBox.getText());
		}

		// Publish date limits

		checkEarliestAllowedPublishDate = false;
		checkLatestAllowedPublishDate = false;

		if (earliestAllowedPublishDate.getText().length() > 0) {
			earliestAllowedPublishDateObject = formatter.parse(earliestAllowedPublishDate.getText());
			checkEarliestAllowedPublishDate = true;
		}
		if (latestAllowedPublishDate.getText().length() > 0) {
			latestAllowedPublishDateObject = formatter.parse(latestAllowedPublishDate.getText());
			checkLatestAllowedPublishDate = true;
		}

		// hide unknown publish dates
		if (hideUnknownPublishDateCheckbox.getState()) {
			hideStoriesWithUnkownPublishDate = true;
		} else {
			hideStoriesWithUnkownPublishDate = false;
		}

		// completed stories only
		if (completedOnlyCheckbox.getState()) {
			completedStoriesOnly = true;
		} else {
			completedStoriesOnly = false;
		}

		// sort by
		if (viewsRadioButton.isSelected()) {// sorting
			sortByString = "views";
		} else if (likesRadioButton.isSelected()) {
			sortByString = "likes";
		} else if (percentRatingRadioButton.isSelected()) {
			sortByString = "percent";
		} else if (wordsRadioButton.isSelected()) {
			sortByString = "words";
		} else if (oldestFirstRadioButton.isSelected()) {
			sortByString = "oldest";
		} else if (newestFirstRadioButton.isSelected()) {
			sortByString = "newest";
		}

		// title contains
		if (titleContainsTextBox.getText().length() > 0) {
			titleContainsString = titleContainsTextBox.getText();
		}

		// title contains
		if (descriptionContainsTextBox.getText().length() > 0) {
			descriptionContainsString = descriptionContainsTextBox.getText();
		}

	}

	public static void createOptimizedIndex() throws ParseException {

		df.setMaximumFractionDigits(2);

		startTimeMilliseconds = System.currentTimeMillis();
		saveSettings();

		numberOfStoriesScanned = 0;

		textArea.setText("");
		goodTags.clear();
		badTags.clear();

		tooManyResults = false;

		try {
			String name = "default";

			readTextBoxesIntoMemory();

			goodPrint(" --- Creating optimized index --- \n");
			goodPrint("\n");

			
			JsonReader reader = new JsonReader(new FileReader("index.json"));
			
			if (useSampleIndex) {
				reader = new JsonReader(new FileReader("sampleIndex.json"));
			}
			
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

						resultsArray.add(temp);

						if (numberOfStoriesScanned % updateInterval == 0) {
							goodPrint("Scanned " + numberOfStoriesScanned + " stories\n");

						}

						temp = new FimfictionStory();// reset temp variables
						stillGood = true;
					}
					reader.endObject();
					break;
				case NAME:
					name = reader.nextName();
					if (indents == 1) {
						// goodPrint("--Start of new story data--\n");
						numberOfStoriesScanned++;
					}
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
						temp.datePublishedObject = formatter.parse(temp.datePublishedString);
					} else if (indents == 3 && name.equals("name")) {
						temp.author = s;
					} else if (name.equals("completion_status")) {
						temp.completionStatus = s;
					} else if (name.equals("description_html")) {
						temp.description = s;
					} else if (name.equals("content_rating")) {
						temp.contentRating = s;
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
					} else if (name.equals("num_words") && indents == 2) {
						temp.words = n;
					} else if (indents == 2 && name.equals("id")) {
						temp.ID = n;
					} else if (name.equals("prequel")) {
						temp.prequelID = (n);
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

					goodPrint("--End of scanning--\n");

					goodPrint("--Total stories scanned: " + numberOfStoriesScanned + "--\n");
					if (viewsRadioButton.isSelected()) {
						goodPrint("--Sorting by views--\n");
					} else if (likesRadioButton.isSelected()) {
						goodPrint("--Sorting by likes--\n");
					} else if (wordsRadioButton.isSelected()) {
						goodPrint("--Sorting by words--\n");
					}

					goodPrint("Creating optimizedIndex.json (takes about 6 seconds). The program will appear to freeze\n");

					try (Writer writer = new FileWriter("optimizedIndex.json")) {
						Gson gson = new GsonBuilder().create();
						gson.toJson(resultsArray, writer);
					}
					goodPrint("Done creating optimizedIndex.json\n");
					goodPrint("Now run the program with \"optimizedIndex.json\" selected");

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

	public static void updateTextAreaFromOptimizedIndex() throws IOException, ParseException {
		saveSettings();
		tooManyResults = false;

		String storyInfo = "";
		numberOfStoriesScanned = 0;
		textArea.setText("");
		startTimeMilliseconds = System.currentTimeMillis();

		ArrayList<String> originalTags = new ArrayList<String>();

		int indents = 0;
		String name = "filler";

		readTextBoxesIntoMemory();

		goodPrint(" --- Search Options --- \n");
		goodPrint(" --- Searching from subset of all stories --- \n");
		goodPrint("--Searching for stories with tags: " + goodTags + "\n");
		goodPrint("--and without tags: " + badTags + "\n");
		goodPrint("--Sorted by " + sortByString + "\n");
		if (minLikesInt != -1) {
			goodPrint("--Min likes " + minLikesInt + "\n");
		}
		if (minWordsInt != -1) {
			goodPrint("--Min words " + minWordsInt + "\n");
		}
		if (maxWordsInt != -1) {
			goodPrint("--Max words " + maxWordsInt + "\n");
		}
		if (checkEarliestAllowedPublishDate) {
			goodPrint("--Only stories published after " + earliestAllowedPublishDate.getText() + "\n");
		}
		if (checkLatestAllowedPublishDate) {
			goodPrint("--Only stories published before " + latestAllowedPublishDate.getText() + "\n");
		}
		if (completedStoriesOnly) {
			goodPrint("--Only completed stories\n");
		}
		if (hideStoriesWithUnkownPublishDate) {
			goodPrint("--Hide stories with unknown publish date\n");
		}

		goodPrint("\n");
		JsonReader reader = new JsonReader(new FileReader("optimizedIndex.json"));
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
				if (name.equals("completionStatus")) {
					// if the last scanned property was completionStatus, the story is now fully
					// scanned and can be checked

					// no parameters because it uses the static variable temp

					checkIfStoryMeetsRequirements();

					if (stillGood) {
						// temp.tags = originalTags;
						resultsArray.add(temp);
					}

					temp = new FimfictionStory();// reset temp
					numberOfStoriesScanned++;
					if (numberOfStoriesScanned % updateInterval == 0) {
						goodPrint("Scanned " + numberOfStoriesScanned + ", found " + resultsArray.size()
								+ " results so far\n");

					}
				}
				reader.endObject();
				break;
			case NAME:
				name = reader.nextName();

				break;
			case STRING:
				String s = reader.nextString();
				if (name.equals("tags")) {
					temp.tags.add(s);

				} else if (name.equals("title")) {
					temp.title = s;
				} else if (name.equals("author")) {
					temp.author = s;
				} else if (name.equals("contentRating")) {
					temp.contentRating = s;
				} else if (name.equals("datePublishedString")) {
					temp.datePublishedString = s;
					if (!s.equals("unknown date")) {
						temp.datePublishedString = s.substring(0, 10).replace('-', '/');
						temp.datePublishedObject = formatter.parse(temp.datePublishedString);
					}
				} else if (name.equals("description")) {
					temp.description = s;
				} else if (name.equals("completionStatus")) {
					temp.completionStatus = s;
				}

				break;
			case NUMBER:
				double n = reader.nextDouble();
				if (name.equals("words")) {
					temp.words = (int) n;
				} else if (name.equals("likes")) {
					temp.likes = (int) n;
				} else if (name.equals("dislikes")) {
					temp.dislikes = (int) n;
				} else if (name.equals("ID")) {
					temp.ID = (int) n;
				} else if (name.equals("prequelID")) {
					temp.prequelID = (int) n;
				} else if (name.equals("views")) {
					temp.views = (int) n;
				}

				break;
			case BOOLEAN:
				boolean b = reader.nextBoolean();
				break;
			case NULL:
				reader.nextNull();
				break;
			case END_DOCUMENT:

				printResultsToTextArea();

				goodPrint("--End of scanning--\n");
				goodPrint("--Search completed in " + (float) (System.currentTimeMillis() - startTimeMilliseconds) / 1000
						+ " seconds--\n");

				if (tooManyResults) {
					goodPrint("--Too many results, only showing " + maxNumberOfResultsShown + " to avoid lag\n");
				}

				// numberOfStoriesScanned--;

				goodPrint("--Total stories scanned: " + numberOfStoriesScanned + "--\n");
				goodPrint("--Results: " + resultsArray.size() + "--\n");
				if (viewsRadioButton.isSelected()) {
					goodPrint("--Sorting by views--\n");
				} else if (likesRadioButton.isSelected()) {
					goodPrint("--Sorting by likes--\n");
				} else if (percentRatingRadioButton.isSelected()) {
					goodPrint("--Sorting by percent rating--\n");
				} else if (wordsRadioButton.isSelected()) {
					goodPrint("--Sorting by words--\n");
				} else if (newestFirstRadioButton.isSelected()) {
					goodPrint("--Sorting by newest first--\n");
				} else if (oldestFirstRadioButton.isSelected()) {
					goodPrint("--Sorting by oldest first--\n");
				}

				// Save results to results.txt
				if (writeResultsCheckbox.getState()) {
					try {
						String filename = "results.txt";
						BufferedWriter out = new BufferedWriter(new FileWriter(filename));
						out.write(" ");
						out.close();

						PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
						for (String line : textArea.getText().split("\\n")) {
							out1.println(line);
						}
						out1.close();
					} catch (Exception e) {
						// oops
					}
				}

				resultsArray.clear();
				numberOfStoriesScanned = 0;
				reader.close();
				return;
			}
		}
	}

	public static boolean checkIfStoryMeetsRequirements() {
		stillGood = true;
		// End of story data
		// Checking is complete
		temp.calculateRating();
		if (completedStoriesOnly) {// is complete?
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
		// // check min words
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
		// // check percentage rating
		if (minPercentageRatingFloat != -1 && stillGood) {
			if (temp.percentRating < minPercentageRatingFloat) {
				stillGood = false;
			}
		}

		if (checkLatestAllowedPublishDate && stillGood && temp.datePublishedObject != null) {
			if (temp.datePublishedObject.after(latestAllowedPublishDateObject)) {
				stillGood = false;
			}
		}

		if (checkEarliestAllowedPublishDate && stillGood && temp.datePublishedObject != null) {
			if (temp.datePublishedObject.before(earliestAllowedPublishDateObject)) {
				stillGood = false;
			}
		}

		if (stillGood && hideStoriesWithUnkownPublishDate && temp.datePublishedString.equals("unknown date")) {
			stillGood = false;
		}

		if (stillGood && titleContainsString.length() > 0) {
			if (!temp.title.toLowerCase().contains(titleContainsString.toLowerCase())) {
				stillGood = false;
			}
		}
		
		if (stillGood && descriptionContainsString.length() > 0) {
			if (!temp.description.toLowerCase().contains(descriptionContainsString.toLowerCase())) {
				stillGood = false;
			}
		}

		// Checking good tags
		ArrayList<String> lowerCaseTags = new ArrayList<String>(temp.tags);

		if (stillGood) {
			for (int i = 0; i < lowerCaseTags.size(); i++) {
				// change the tags to lowercase for non-case sensitive tag matching
				lowerCaseTags.set(i, temp.tags.get(i).toLowerCase());
			}
			for (int i = 0; i < goodTags.size(); i++) {
				if (lowerCaseTags.contains(goodTags.get(i).toLowerCase())) {
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
				if (lowerCaseTags.contains(badTags.get(i).toLowerCase())) {
					stillGood = false;
					break;
				} else {
					// still good
				}
			}
		}
		return true;
	}

	public static void printResultsToTextArea() {
		String storyInfo = "";
		if (viewsRadioButton.isSelected()) {// Sorting
			Collections.sort(resultsArray, FimfictionStory.ViewsComparator);
		} else if (likesRadioButton.isSelected()) {
			Collections.sort(resultsArray, FimfictionStory.LikesComparator);
		} else if (percentRatingRadioButton.isSelected()) {
			Collections.sort(resultsArray, FimfictionStory.PercentRatingComparator);
		} else if (wordsRadioButton.isSelected()) {
			Collections.sort(resultsArray, FimfictionStory.WordsComparator);
		} else if (oldestFirstRadioButton.isSelected()) {
			// no action required
		} else if (newestFirstRadioButton.isSelected()) {
			Collections.reverse(resultsArray);
		}
		// output final results to screen
		storyInfo = "";
		for (int i = 0; i < resultsArray.size(); i++) {
			temp.calculateRating();
			// goodPrint("------" + temp + "------\n");
			temp = resultsArray.get(i);
			storyInfo += ("--Story Title: " + temp.title + ", ");
			storyInfo += ("Author: " + temp.author + ", ");
			storyInfo += ("ID: " + temp.ID + "\n");
			temp.removeHtmlFormattingFromDescription();
			if (temp.prequelID != -1) {
				storyInfo += ("--Prequel ID: " + temp.prequelID + "\n");
			}
			storyInfo += ("--Description: ");
			
			storyInfo += (temp.description + "\n");
			
			storyInfo += ("--Date Published: " + temp.datePublishedString + ", Completion: "
					+ temp.completionStatus.substring(0, 1).toUpperCase() + temp.completionStatus.substring(1) + "\n");
			storyInfo += ("--Likes: " + temp.likes + ", Dislikes: " + temp.dislikes + ", ");
			storyInfo += ("Percentage Rating: " + df.format(temp.percentRating) + "%, ");
			storyInfo += ("Views: " + temp.views + ", Word Count: " + temp.words + "\n");
			storyInfo += ("--Tags: " + temp.tags + "\n");
			storyInfo += ("--Content rating: " + temp.contentRating.substring(0, 1).toUpperCase()
					+ temp.contentRating.substring(1) + "\n");
			storyInfo += ("\n");
			if (i % resultBufferPrintInterval == 0) {
				goodPrint(storyInfo);
				storyInfo = "";
			}
			if (i > maxNumberOfResultsShown) {
				tooManyResults = true;
				break;
			}
		}
		goodPrint(storyInfo);
		storyInfo = "";
	}

	public static void indent(int ind) {
		for (int i = 0; i < ind; i++) {
			System.out.print("    ");
		}
	}
}
