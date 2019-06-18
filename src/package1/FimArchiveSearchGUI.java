//Warning: contains lots of spaghetti code
//    LOTS
//Last edited June 18, 2019 by itchylol742

package package1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.TextArea;
import java.awt.Font;
import javax.swing.JRadioButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import javax.lang.model.element.VariableElement;
import javax.swing.ButtonGroup;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Checkbox;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;

import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.Icon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.SortedNumericSortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import javax.swing.BoxLayout;
import java.awt.Component;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FimArchiveSearchGUI {

	static boolean useSampleIndex = false;
	String eol;

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
	static final int maxNumberOfResultsShown = 5000;
	static final int updateInterval = 2500;
	static final int resultBufferPrintInterval = 1000000;
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
	static Checkbox LimitResultsCheckbox = new Checkbox();

	private ButtonGroup searchByRadioButtons = new ButtonGroup();

	static TextField minLikesText = new TextField();
	static TextField minWordsText = new TextField();
	static TextField maxWordsText = new TextField();
	static TextField minPercentRatingTextBox = new TextField();

	static TextField latestAllowedPublishDate = new TextField();
	static TextField earliestAllowedPublishDate = new TextField();

	private final Label label_3 = new Label("Minimum Likes");
	private final Label label_4 = new Label("Minimum Words");
	private final Label label_5 = new Label("Maximum Words");
	private final Label label_6 = new Label("YYYY/MM/DD");
	private final Label label_8 = new Label("Published after/on");
	private final Label label_9 = new Label("Published before/on");
	private final JPanel panel = new JPanel();
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panel_1 = new JPanel();
	private final JTextArea txtrMadeByuitchylol = new JTextArea();
	private final Label label_12 = new Label("Description Contains");
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final JPanel numberFilters = new JPanel();
	private final JPanel wordFilters = new JPanel();
	private final JLabel lblPrequelifThere = new JLabel("Prequel (if there is one)");
	private final JLabel lblTheStoryLooking = new JLabel("The story in question");
	private final JLabel lblSequelsIfThere = new JLabel("Sequel(s) if there is/are any");
	private final static TextField IDfield = new TextField();
	private final static TextArea sequels = new TextArea();
	private final static TextArea prequel = new TextArea();
	private final static JTextField siq = new JTextField();
	private final static Label label_14 = new Label("Author Name");
	private final static TextField authorTextField = new TextField();

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
		eol = System.lineSeparator();
		siq.setFont(new Font("Arial", Font.PLAIN, 20));
		siq.setColumns(10);
		// Logic
		arrayOfUselessProperties.clear();
		arrayOfUselessProperties.add("archive");
		arrayOfUselessProperties.add("avatar");
		arrayOfUselessProperties.add("color");
		arrayOfUselessProperties.add("cover_image");
		arrayOfUselessProperties.add("chapters");

		// GUI

		frmTest = new JFrame();
		frmTest.setTitle("Fimarchive Searcher 1.0 by itchylol742");
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
		panel.setLayout(new MigLayout("", "[80%,fill][20%,fill]", "[34%,fill][1%,fill][65%,fill]"));

		textArea = new TextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(textArea, "cell 0 2,grow");

		panel.add(numberFilters, "cell 1 0 1 3,grow");
		numberFilters.setLayout(new BoxLayout(numberFilters, BoxLayout.Y_AXIS));
		numberFilters.add(label_3);
		label_3.setFont(new Font("Arial", Font.PLAIN, 30));
		numberFilters.add(minLikesText);
		minLikesText.setFont(new Font("Arial", Font.PLAIN, 20));
		minLikesText.setText("10");

		Label label_7 = new Label("Minimum % Rating");
		numberFilters.add(label_7);
		label_7.setFont(new Font("Arial", Font.PLAIN, 30));

		Label label_13 = new Label("Decimals allowed for % rating");
		numberFilters.add(label_13);
		label_13.setForeground(Color.GRAY);
		label_13.setFont(new Font("Arial", Font.PLAIN, 20));
		numberFilters.add(minPercentRatingTextBox);

		minPercentRatingTextBox.setText("60");
		minPercentRatingTextBox.setFont(new Font("Arial", Font.PLAIN, 20));
		numberFilters.add(label_4);
		label_4.setFont(new Font("Arial", Font.PLAIN, 30));
		numberFilters.add(minWordsText);
		minWordsText.setText("10");
		minWordsText.setFont(new Font("Arial", Font.PLAIN, 20));
		numberFilters.add(label_5);
		label_5.setFont(new Font("Arial", Font.PLAIN, 30));
		numberFilters.add(maxWordsText);
		maxWordsText.setText("10");
		maxWordsText.setFont(new Font("Arial", Font.PLAIN, 20));
		numberFilters.add(label_8);
		label_8.setFont(new Font("Arial", Font.PLAIN, 30));
		numberFilters.add(earliestAllowedPublishDate);
		earliestAllowedPublishDate.setFont(new Font("Arial", Font.PLAIN, 21));
		numberFilters.add(label_6);
		label_6.setForeground(Color.GRAY);
		label_6.setFont(new Font("Arial", Font.PLAIN, 20));
		numberFilters.add(label_9);
		label_9.setFont(new Font("Arial", Font.PLAIN, 30));
		numberFilters.add(latestAllowedPublishDate);
		latestAllowedPublishDate.setFont(new Font("Arial", Font.PLAIN, 21));
		numberFilters.add(completedOnlyCheckbox);

		completedOnlyCheckbox.setFont(new Font("Arial", Font.PLAIN, 23));
		numberFilters.add(LimitResultsCheckbox);
		LimitResultsCheckbox.setLabel("Limit 5000 results (avoid lag)");

		LimitResultsCheckbox.setFont(new Font("Arial", Font.PLAIN, 23));
		numberFilters.add(writeResultsCheckbox);

		writeResultsCheckbox.setLabel("Write results to file");
		writeResultsCheckbox.setFont(new Font("Arial", Font.PLAIN, 23));
		numberFilters.add(useSubsetCheckbox);

		useSubsetCheckbox.setLabel("Done Lucene setup");
		useSubsetCheckbox.setFont(new Font("Arial", Font.PLAIN, 23));

		JTextArea txtrLeave = new JTextArea();
		numberFilters.add(txtrLeave);
		txtrLeave.setForeground(Color.GRAY);
		txtrLeave.setFont(new Font("Arial", Font.PLAIN, 20));
		txtrLeave.setBackground(SystemColor.menu);
		txtrLeave.setEditable(false);
		txtrLeave.setText("Leave fields empty for \r\nno limit/filter");
		numberFilters.add(txtrMadeByuitchylol);
		txtrMadeByuitchylol.setText("Made by itchylol742");
		txtrMadeByuitchylol.setForeground(Color.GRAY);
		txtrMadeByuitchylol.setFont(new Font("Arial", Font.PLAIN, 20));
		txtrMadeByuitchylol.setEditable(false);
		txtrMadeByuitchylol.setBackground(SystemColor.menu);

		panel.add(wordFilters, "cell 0 0");
		wordFilters.setLayout(new BoxLayout(wordFilters, BoxLayout.X_AXIS));

		JPanel panel_2 = new JPanel();
		wordFilters.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		Label label_1 = new Label("Good Tags");
		panel_2.add(label_1);
		label_1.setFont(new Font("Arial", Font.PLAIN, 30));
		panel_2.add(goodTag1);

		goodTag1.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_2.add(goodTag2);

		goodTag2.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_2.add(goodTag3);

		goodTag3.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_2.add(goodTag4);
		goodTag4.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_2.add(goodTag5);
		goodTag5.setFont(new Font("Arial", Font.PLAIN, 21));
		wordFilters.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

		Label label_2 = new Label("Bad Tags ");
		panel_3.add(label_2);
		label_2.setFont(new Font("Arial", Font.PLAIN, 30));
		panel_3.add(badTag1);

		badTag1.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_3.add(badTag2);

		badTag2.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_3.add(badTag3);

		badTag3.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_3.add(badTag4);
		badTag4.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_3.add(badTag5);
		badTag5.setFont(new Font("Arial", Font.PLAIN, 21));
		wordFilters.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

		Label label_11 = new Label("Title Contains");
		panel_4.add(label_11);
		label_11.setFont(new Font("Arial", Font.PLAIN, 30));
		panel_4.add(titleContainsTextBox);

		titleContainsTextBox.setFont(new Font("Arial", Font.PLAIN, 21));
		panel_4.add(label_12);
		label_12.setFont(new Font("Arial", Font.PLAIN, 30));
		panel_4.add(descriptionContainsTextBox);
		descriptionContainsTextBox.setText("<dynamic");
		descriptionContainsTextBox.setFont(new Font("Arial", Font.PLAIN, 21));
		label_14.setFont(new Font("Arial", Font.PLAIN, 30));

		panel_4.add(label_14);
		authorTextField.setFont(new Font("Arial", Font.PLAIN, 21));

		panel_4.add(authorTextField);

		Button button = new Button("Search");
		panel_4.add(button);
		button.setFont(new Font("Arial", Font.PLAIN, 46));
		wordFilters.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

		Label label = new Label("Sort By");
		label.setAlignment(Label.CENTER);
		panel_5.add(label);
		label.setFont(new Font("Arial", Font.PLAIN, 30));
		panel_5.add(viewsRadioButton);

		// radio buttons for sorting

		viewsRadioButton.setFont(new Font("Arial", Font.PLAIN, 24));
		searchByRadioButtons.add(viewsRadioButton);
		panel_5.add(likesRadioButton);

		likesRadioButton.setFont(new Font("Arial", Font.PLAIN, 24));
		searchByRadioButtons.add(likesRadioButton);

		searchByRadioButtons.add(percentRatingRadioButton);
		panel_5.add(percentRatingRadioButton);
		percentRatingRadioButton.setFont(new Font("Arial", Font.PLAIN, 24));
		panel_5.add(wordsRadioButton);

		wordsRadioButton.setFont(new Font("Arial", Font.PLAIN, 24));
		searchByRadioButtons.add(wordsRadioButton);
		searchByRadioButtons.add(newestFirstRadioButton);
		panel_5.add(newestFirstRadioButton);
		newestFirstRadioButton.setFont(new Font("Arial", Font.PLAIN, 24));
		searchByRadioButtons.add(oldestFirstRadioButton);
		panel_5.add(oldestFirstRadioButton);
		oldestFirstRadioButton.setFont(new Font("Arial", Font.PLAIN, 24));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (useSubsetCheckbox.getState()) {
						// find stuff (after first time run)
						updateTextAreaFromLuceneIndex();
					} else {
						// create Lucene index (first time run)
						createOptimizedIndex();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		tabbedPane.addTab("Sequel/Prequel finder", null, panel_1, null);
		tabbedPane.setEnabledAt(1, true);
		panel_1.setLayout(new MigLayout("", "[40%,grow,fill][40%,fill][20%,grow,fill]",
				"[7%,fill][4.71%,fill][2%,fill][25%,grow,fill][10%,fill][25%,fill]"));

		// miglayout2
		Label label_10 = new Label("Story ID");
		label_10.setAlignment(Label.CENTER);
		label_10.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_1.add(label_10, "cell 0 0,grow");
		JTextArea txtrEnterTheStory = new JTextArea();
		txtrEnterTheStory.setText(
				"Enter the story ID of a story, and it will find any sequels and prequels the story has.\r\nA story can only have maximum 1 prequel, but it can have any number of sequels. ");
		txtrEnterTheStory.setForeground(Color.GRAY);
		txtrEnterTheStory.setFont(new Font("Arial", Font.PLAIN, 20));
		txtrEnterTheStory.setEditable(false);
		txtrEnterTheStory.setBackground(SystemColor.menu);
		panel_1.add(txtrEnterTheStory, "cell 1 0 2 1");
		IDfield.setFont(new Font("Arial", Font.PLAIN, 20));

		panel_1.add(IDfield, "cell 0 1");

		Button button_1 = new Button("Find sequels/prequels");

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					updatePrequelAndSequels();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		button_1.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_1.add(button_1, "cell 1 1,alignx left,growy");
		prequel.setRows(5);
		prequel.setFont(new Font("Arial", Font.PLAIN, 20));

		panel_1.add(prequel, "cell 0 3 2 1");
		lblPrequelifThere.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPrequelifThere.setHorizontalAlignment(SwingConstants.CENTER);

		panel_1.add(lblPrequelifThere, "cell 2 3");

		panel_1.add(siq, "cell 0 4 2 1,growx");
		lblTheStoryLooking.setHorizontalAlignment(SwingConstants.CENTER);
		lblTheStoryLooking.setFont(new Font("Tahoma", Font.PLAIN, 24));

		panel_1.add(lblTheStoryLooking, "cell 2 4");
		sequels.setFont(new Font("Arial", Font.PLAIN, 20));

		panel_1.add(sequels, "cell 0 5 2 1");
		lblSequelsIfThere.setHorizontalAlignment(SwingConstants.CENTER);
		lblSequelsIfThere.setFont(new Font("Tahoma", Font.PLAIN, 24));

		panel_1.add(lblSequelsIfThere, "cell 2 5");

		loadTagTextAreasIntoTagTestAreasArrayList();

		loadSettings();
		goodPrint("IMPORTANT! If this is your first time running the program, have Done Lucene setup UNCHECKED" + eol);
		goodPrint("This will created the Lucene index from information found in index.json" + eol);
		goodPrint("After that, run the program with Done Lucene setup checked" + eol);
		goodPrint(eol);

		goodPrint("- Nothing is case sensitive. Type in proper caps, no caps, or all caps if you want" + eol);
		goodPrint(
				"- Some stories have -1 likes due to metadata error, so even setting min likes to 0 will filter them out"
						+ eol);
		goodPrint("- Some stories have 0 (or very low number) words due to metadata error...?," + eol);
		goodPrint("    - Or maybe somehow those short stories got on the site" + eol);
		goodPrint("- Percentage rating assumes there's 1 more like and 1 more dislike than there actually is" + eol);
		goodPrint("    - So stories with few ratings don't get perfect score" + eol);
		goodPrint("- Dates are in the format YYYY/MM/DD" + eol);
		goodPrint("    - 0's before the month and day are REQUIRED if it's less than 10" + eol);
		goodPrint("    - For exmaple, 2015/02/03 is valid, but 2015/2/3 is invalid" + eol);

	}

	// my methods below

	public static void updatePrequelAndSequels()
			throws IOException, org.apache.lucene.queryparser.classic.ParseException, java.text.ParseException {
		readTextBoxesIntoMemory();
		saveSettings();
		prequel.setText("");
		siq.setText("");
		sequels.setText("");

		if (IDfield.getText().length() == 0) {
			goodPrint(("ID is required"), 1);
			return;
		}

		// 0. Specify the analyzer for tokenizing text.
		// The same analyzer should be used for indexing and searching
		StandardAnalyzer analyzer = new StandardAnalyzer();

		// 1. create the index
		String SRC_FOLDER = "theLuceneIAAndex";

		// Directory index = new RAMDirectory();
		FSDirectory index = FSDirectory.open(Paths.get(SRC_FOLDER));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter w = new IndexWriter(index, config);

		// 2. query
		if (!isThisStringAnInteger(IDfield.getText())) {
			goodPrint("ID must be a number", 1);
			w.close();
			return;
		}
		int theIDToLookFor = Integer.parseInt(IDfield.getText());

		Query idQuery = IntPoint.newExactQuery("ID", theIDToLookFor);

		w.close();

		// 3. search
		int hitsPerPage = 999999;
		IndexReader reader1 = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader1);

		Sort sort = new Sort(new SortedNumericSortField("datePublishedInt", SortField.Type.LONG, false));

		TopDocs docs = searcher.search(idQuery, hitsPerPage, sort, true, true);
		ScoreDoc[] hits = docs.scoreDocs;

		if (hits.length == 0) {
			goodPrint("There is no story with ID " + theIDToLookFor, 2);
			return;
		}

		// 4. display results

		String endOfLine = System.lineSeparator();
		String endOfField = ", ";

		int prequelId = -1;

		StringBuilder stringBuilder = new StringBuilder("");

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			stringBuilder.append(d.get("title") + endOfField);
			stringBuilder.append(" by " + d.get("author"));
			if (Integer.parseInt(d.get("prequelID")) != -1) {
				prequelId = Integer.parseInt(d.get("prequelID"));
			} else {
				goodPrint(("The story in question has no prequel"), 1);
			}
		}

		goodPrint(stringBuilder.toString(), 2);

		if (prequelId > 0) {
			Query prequelQuery = IntPoint.newExactQuery("ID", prequelId);

			docs = searcher.search(prequelQuery, hitsPerPage, sort, true, true);
			// TopDocs docs = searcher.search(booleanQuery, hitsPerPage);
			hits = docs.scoreDocs;

			stringBuilder = new StringBuilder("");

			for (int i = 0; i < hits.length; ++i) {
				int docId = hits[i].doc;
				Document d = searcher.doc(docId);
				stringBuilder.append("--Story Title: " + d.get("title") + endOfField);
				stringBuilder.append("Author: " + d.get("author") + endOfField);
				stringBuilder.append("ID: " + d.get("ID") + endOfLine);
				if (Integer.parseInt(d.get("prequelID")) != -1) {
					stringBuilder.append("-This story has a prequel. ID of prequel: " + d.get("prequelID") + endOfLine);
					prequelId = Integer.parseInt(d.get("prequelID"));
				}

				stringBuilder.append("Description: " + d.get("description") + endOfLine);
				stringBuilder.append("Date Published: " + d.get("datePublishedString") + endOfField);
				stringBuilder.append("Completion Status: " + d.get("completionStatus") + endOfLine);
				stringBuilder.append("Likes: " + d.get("likes") + endOfField);
				stringBuilder.append("Dislikes: " + d.get("dislikes") + endOfField);
				stringBuilder.append("Percent Rating: " + d.get("percentRating") + endOfField);
				stringBuilder.append("Views: " + d.get("views") + endOfField);
				stringBuilder.append("Word Count: " + d.get("words") + endOfLine);
				stringBuilder.append("Content Rating: " + d.get("contentRating") + endOfLine);
				stringBuilder.append("Tags: " + Arrays.asList(d.get("tagsString").split(",")) + endOfLine);
				stringBuilder.append(endOfLine);
			}

			goodPrint(stringBuilder.toString(), 1);
		}

		Query sequelQuery = IntPoint.newExactQuery("prequelID", theIDToLookFor);

		docs = searcher.search(sequelQuery, hitsPerPage, sort, true, true);
		hits = docs.scoreDocs;

		stringBuilder = new StringBuilder("");

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			stringBuilder.append("--Story Title: " + d.get("title") + endOfField);
			stringBuilder.append("Author: " + d.get("author") + endOfField);
			stringBuilder.append("ID: " + d.get("ID") + endOfLine);
			if (Integer.parseInt(d.get("prequelID")) != -1) {
				stringBuilder.append(
						"This story has a prequel (obviously). ID of prequel: " + d.get("prequelID") + endOfLine);
				prequelId = Integer.parseInt(d.get("prequelID"));
			}

			stringBuilder.append("Description: " + d.get("description") + endOfLine);
			stringBuilder.append("Date Published: " + d.get("datePublishedString") + endOfField);
			stringBuilder.append("Completion Status: " + d.get("completionStatus") + endOfLine);
			stringBuilder.append("Likes: " + d.get("likes") + endOfField);
			stringBuilder.append("Dislikes: " + d.get("dislikes") + endOfField);
			stringBuilder.append("Percent Rating: " + d.get("percentRating") + endOfField);
			stringBuilder.append("Views: " + d.get("views") + endOfField);
			stringBuilder.append("Word Count: " + d.get("words") + endOfLine);
			stringBuilder.append("Content Rating: " + d.get("contentRating") + endOfLine);
			stringBuilder.append("Tags: " + Arrays.asList(d.get("tagsString").split(",")) + endOfLine);
			stringBuilder.append(endOfLine);
		}

		stringBuilder.append("Total " + hits.length + " results. ");

		goodPrint(stringBuilder.toString(), 3);
		if (hits.length == 0) {
			goodPrint("The story in question has no sequel(s)", 3);
		}
		reader1.close();
	}

	public static void goodPrint(String theString) {
		textArea.append(theString);
	}

	// whereToPrint: 1 = prequel, 2 = story in question, 3 = sequels
	public static void goodPrint(String theString, int whereToPrint) {
		switch (whereToPrint) {
		case 1:
			prequel.append(theString);
			break;
		case 2:
			siq.setText(siq.getText() + theString);
			break;
		case 3:
			sequels.append(theString);
			break;
		}
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

			if (LimitResultsCheckbox.getState()) {
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
			
			out.write("Author name:");
			out.newLine();

			out.write(authorTextField.getText());
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
				LimitResultsCheckbox.setState(true);
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
			
			line = br.readLine();// skip this line
			line = br.readLine();// author name
			authorTextField.setText(line);

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
		if (LimitResultsCheckbox.getState()) {
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

	public static void createOptimizedIndex()
			throws ParseException, org.apache.lucene.queryparser.classic.ParseException {

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

			// 0. Specify the analyzer for tokenizing text.
			// The same analyzer should be used for indexing and searching
			StandardAnalyzer analyzer = new StandardAnalyzer();

			// 1. create the index
			boolean createNew = true;
			String SRC_FOLDER = "theLuceneIAAndex";

			if (createNew) {
				File directory = new File(SRC_FOLDER);
				if (!directory.exists()) {
					System.out.println("Directory does not exist.");
				} else {
					try {
						delete(directory);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
				}
				System.out.println("delete Done");
			}
			FSDirectory index = FSDirectory.open(Paths.get(SRC_FOLDER));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter w = new IndexWriter(index, config);

			// reader can only be closed when there
			// is no need to access the documents any more.

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
						temp.removeHtmlFormattingFromDescription();
						// resultsArray.add(temp);
						temp.description = temp.description.substring(0, Math.min(temp.description.length(), 10000));
						addDoc(w, temp);
						// goodPrint(temp.tags + "\n");

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
					w.close();
					goodPrint("--End of scanning--\n");

					goodPrint("--Total stories scanned: " + numberOfStoriesScanned + "--\n");
					if (viewsRadioButton.isSelected()) {
						goodPrint("--Sorting by views--\n");
					} else if (likesRadioButton.isSelected()) {
						goodPrint("--Sorting by likes--\n");
					} else if (wordsRadioButton.isSelected()) {
						goodPrint("--Sorting by words--\n");
					}

					goodPrint(
							"Creating optimizedIndex.json (takes about 6 seconds). The program will appear to freeze\n");

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

	public static void updateTextAreaFromLuceneIndex()
			throws IOException, org.apache.lucene.queryparser.classic.ParseException, java.text.ParseException {
		readTextBoxesIntoMemory();
		saveSettings();
		textArea.setText("");
		// 0. Specify the analyzer for tokenizing text.
		// The same analyzer should be used for indexing and searching
		StandardAnalyzer analyzer = new StandardAnalyzer();

		// 1. create the index
		String SRC_FOLDER = "theLuceneIAAndex";

		// Directory index = new RAMDirectory();
		FSDirectory index = FSDirectory.open(Paths.get(SRC_FOLDER));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter w = new IndexWriter(index, config);

		// 2. query
		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.
		QueryParser qp = new QueryParser("title", analyzer);
		// System.out.println("title contains: " + titleContainsTextBox.getText());
		// Query q = qp.parse("title:\"pony\""); //THIS IS HOW TO DO IT
		String theString = "";
		theString += "filler:abcde";
		if (titleContainsString.length() > 0) {
			titleContainsString = "\"" + titleContainsString + "\"";
			// theString += (" AND !title:" + titleContainsString); //how to exclude
			theString += (" AND title:" + titleContainsString);
		}
		if (descriptionContainsString.length() > 0) {
			descriptionContainsString = "\"" + descriptionContainsString + "\"";
			// theString += (" AND !title:" + titleContainsString); //how to exclude
			theString += (" AND description:" + descriptionContainsString);
		}
		String authorString = authorTextField.getText();
		if (authorTextField.getText().length() > 0) {
			authorString = "\"" + authorString + "\"";
			// theString += (" AND !title:" + titleContainsString); //how to exclude
			theString += (" AND author:" + authorString);
			System.out.println(authorTextField.getText());
			System.out.println(theString);
		}
		for (int i = 0; i < goodTags.size(); i++) {
			goodTags.set(i, "\"" + goodTags.get(i) + "\"");
			theString += (" AND tagsString:" + goodTags.get(i));
		}
		for (int i = 0; i < badTags.size(); i++) {
			badTags.set(i, "\"" + badTags.get(i) + "\"");
			theString += (" AND !tagsString:" + badTags.get(i));
		}

		Query minLikesQuery;
		Query minAndMaxWordsQuery;
		Query datePublishedQuery;
		Query percentRatingQuery;

		if (minLikesInt > -1) {
			minLikesQuery = IntPoint.newRangeQuery("likes", minLikesInt, Integer.MAX_VALUE);
		} else {
			minLikesQuery = IntPoint.newRangeQuery("likes", Integer.MIN_VALUE, Integer.MAX_VALUE);
		}

		if (minWordsInt > -1 && maxWordsInt > -1) {
			minAndMaxWordsQuery = IntPoint.newRangeQuery("words", minWordsInt, maxWordsInt);
		} else if (minWordsInt > -1) {
			minAndMaxWordsQuery = IntPoint.newRangeQuery("words", minWordsInt, Integer.MAX_VALUE);
		} else if (maxWordsInt > -1) {
			minAndMaxWordsQuery = IntPoint.newRangeQuery("words", Integer.MIN_VALUE, maxWordsInt);
		} else {
			minAndMaxWordsQuery = IntPoint.newRangeQuery("words", Integer.MIN_VALUE, Integer.MAX_VALUE);
		}
		int earliestAllowedPublishDateInt;
		String earliestAllowedPublishDateStringWithoutSlashes;
		int latestAllowedPublishDateInt;
		String latestAllowedPublishDateStringWithoutSlashes;

		if (earliestAllowedPublishDate.getText().length() > 0) {
			earliestAllowedPublishDateStringWithoutSlashes = earliestAllowedPublishDate.getText().replace("/", "");
			earliestAllowedPublishDateInt = Integer.parseInt(earliestAllowedPublishDateStringWithoutSlashes);
		} else {
			earliestAllowedPublishDateInt = 0;
		}

		if (latestAllowedPublishDate.getText().length() > 0) {
			latestAllowedPublishDateStringWithoutSlashes = latestAllowedPublishDate.getText().replace("/", "");
			latestAllowedPublishDateInt = Integer.parseInt(latestAllowedPublishDateStringWithoutSlashes);
		} else {
			latestAllowedPublishDateInt = Integer.MAX_VALUE;
		}

		datePublishedQuery = IntPoint.newRangeQuery("datePublishedInt", earliestAllowedPublishDateInt,
				latestAllowedPublishDateInt);

		if (minPercentageRatingFloat > 0) {
			percentRatingQuery = FloatPoint.newRangeQuery("percentRating", minPercentageRatingFloat, Integer.MAX_VALUE);
		} else {
			percentRatingQuery = FloatPoint.newRangeQuery("percentRating", -10, 200);

		}

		if (completedOnlyCheckbox.getState()) {
			theString += (" AND completionStatus:" + "complete");
		}

		// TO DO
		// only completed

		Query q = qp.parse(theString);

		BooleanQuery booleanQuery = new BooleanQuery.Builder().add(q, BooleanClause.Occur.MUST)
				.add(minLikesQuery, BooleanClause.Occur.MUST).add(minAndMaxWordsQuery, BooleanClause.Occur.MUST)
				.add(datePublishedQuery, BooleanClause.Occur.MUST).build();

		// Query combinedQuery = queryNumeric.Or(q);
		w.close();

		// 3. search
		int hitsPerPage = 999999;
		IndexReader reader1 = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader1);

		Sort sort = new Sort(new SortedNumericSortField("views", SortField.Type.LONG, true));

		if (viewsRadioButton.isSelected()) {
			// no need
		} else if (likesRadioButton.isSelected()) {
			sort = new Sort(new SortedNumericSortField("likes", SortField.Type.LONG, true));
		} else if (wordsRadioButton.isSelected()) {
			sort = new Sort(new SortedNumericSortField("words", SortField.Type.LONG, true));
		} else if (percentRatingRadioButton.isSelected()) {
			sort = new Sort(new SortedNumericSortField("percentRating", SortField.Type.LONG, true));
		} else if (oldestFirstRadioButton.isSelected()) {
			sort = new Sort(new SortedNumericSortField("datePublishedInt", SortField.Type.LONG, true));
		} else if (newestFirstRadioButton.isSelected()) {
			sort = new Sort(new SortedNumericSortField("datePublishedInt", SortField.Type.LONG, false));
		}
		TopDocs docs = searcher.search(booleanQuery, hitsPerPage, sort, true, true);
		// TopDocs docs = searcher.search(booleanQuery, hitsPerPage);
		ScoreDoc[] hits = docs.scoreDocs;

		// 4. display results

		DecimalFormat df2 = new DecimalFormat("#.##");
		float estimatedSecondsTheProgramWillFreezeFor = ((float) hits.length / (float) 20000);

		String endOfLine = System.lineSeparator();
		String endOfField = ", ";

		goodPrint("Found " + hits.length + " results" + endOfLine);
		goodPrint("Loading results into memory..." + endOfLine);
		goodPrint("The program will appear to freeze for about " + df2.format(estimatedSecondsTheProgramWillFreezeFor)
				+ " seconds" + endOfLine);

		// goodPrint("this is the query string: " + theString);
		goodPrint(endOfLine);
		goodPrint("Good tags entered: " + goodTags.toString() + endOfLine);
		goodPrint("Bad tags entered: " + badTags.toString() + endOfLine);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			StringBuilder stringBuilder = new StringBuilder("");

			@Override
			protected Void doInBackground() throws Exception {
				for (int i = 0; i < hits.length; ++i) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);

					stringBuilder.append("--Story Title: " + d.get("title") + endOfField);
					stringBuilder.append("Author: " + d.get("author") + endOfField);
					stringBuilder.append("ID: " + d.get("ID") + endOfLine);
					if (Integer.parseInt(d.get("prequelID")) != -1) {
						stringBuilder.append(
								"!! this story has a prequel. ID of prequel: " + d.get("prequelID") + endOfLine);
					}
					stringBuilder.append("Description: " + d.get("description") + endOfLine);
					stringBuilder.append("Date Published: " + d.get("datePublishedString") + endOfField);
					stringBuilder.append("Completion Status: " + d.get("completionStatus") + endOfLine);
					stringBuilder.append("Likes: " + d.get("likes") + endOfField);
					stringBuilder.append("Dislikes: " + d.get("dislikes") + endOfField);
					stringBuilder.append("Percent Rating: " + d.get("percentRating") + endOfField);
					stringBuilder.append("Views: " + d.get("views") + endOfField);
					stringBuilder.append("Word Count: " + d.get("words") + endOfLine);
					stringBuilder.append("Content Rating: " + d.get("contentRating") + endOfLine);
					stringBuilder.append("Tags: " + Arrays.asList(d.get("tagsString").split(",")) + endOfLine);

					stringBuilder.append(endOfLine);

					if (i == maxNumberOfResultsShown && LimitResultsCheckbox.getState()) {

						break;

					}
				}

				if (hits.length > 5000 && LimitResultsCheckbox.getState()) {
					// estimatedSecondsTheProgramWillFreezeFor = 1.2f;
				}
				goodPrint("Done finding results. Now printing. " + "The program will appear to freeze for another "
						+ df2.format(estimatedSecondsTheProgramWillFreezeFor * 1.5) + " seconds" + endOfLine
						+ endOfLine);

				stringBuilder.append("Total " + hits.length + " results. ");
				if (hits.length > maxNumberOfResultsShown && LimitResultsCheckbox.getState()) {
					stringBuilder.append("However, only showing " + maxNumberOfResultsShown + " results to avoid lag.");
				}

				goodPrint(stringBuilder.toString());

				reader1.close();
				return null;
			}
		};

		worker.execute();
	}

	public static boolean checkIfStoryMeetsRequirements() {
		goodPrint("checkIf\n");
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

	public static boolean checkRequirementsSmall(FimfictionStory storyToBeChecked) {

		if (maxWordsInt != -1) {
			if (storyToBeChecked.words > maxWordsInt) {
				return false;
			}
		}
		if (minWordsInt != -1) {
			if (storyToBeChecked.words < minWordsInt) {
				return false;
			}
		}
		if (minLikesInt != -1) {
			if (storyToBeChecked.likes < minLikesInt) {
				return false;
			}
		}
		if (minPercentageRatingFloat != -1) {
			if (storyToBeChecked.percentRating < minPercentageRatingFloat) {
				return false;
			}
		}

		return true;
	}

	public static void printResultsToTextArea(ScoreDoc[] hits, IndexSearcher searcher) {

	}

	public static void indent(int ind) {
		for (int i = 0; i < ind; i++) {
			System.out.print("    ");
		}
	}

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0) {
					file.delete();
				}
			}
		} else {
			file.delete();
		}
	}

	public static void addDoc(IndexWriter w, FimfictionStory theStoryToBeAddedToIndex) throws IOException {
		Document doc = new Document();
		doc.add(new org.apache.lucene.document.TextField("filler", "abcde", Field.Store.YES));
		doc.add(new org.apache.lucene.document.TextField("title", theStoryToBeAddedToIndex.title, Field.Store.YES));

		// use a string field for desc because we don't want it tokenized
		doc.add(new org.apache.lucene.document.TextField("description", theStoryToBeAddedToIndex.description,
				Field.Store.YES));
		String tagsString = theStoryToBeAddedToIndex.tags.toString();

		tagsString = tagsString.replace("[", "");
		tagsString = tagsString.replace("]", "");
		tagsString = tagsString.replace(", ", ",");
		doc.add(new org.apache.lucene.document.TextField("tagsString", tagsString, Field.Store.YES));

		doc.add(new org.apache.lucene.document.IntPoint("likes", theStoryToBeAddedToIndex.likes));
		doc.add(new StoredField("likes", theStoryToBeAddedToIndex.likes));
		doc.add(new SortedNumericDocValuesField("likes", theStoryToBeAddedToIndex.likes));

		doc.add(new org.apache.lucene.document.IntPoint("dislikes", theStoryToBeAddedToIndex.dislikes));
		doc.add(new StoredField("dislikes", theStoryToBeAddedToIndex.dislikes));

		theStoryToBeAddedToIndex.calculateRating();

		doc.add(new org.apache.lucene.document.FloatPoint("percentRating", theStoryToBeAddedToIndex.percentRating));
		doc.add(new StoredField("percentRating", theStoryToBeAddedToIndex.percentRating));
		doc.add(new SortedNumericDocValuesField("percentRating",
				(int) (theStoryToBeAddedToIndex.percentRating * 10000)));

		doc.add(new org.apache.lucene.document.IntPoint("views", theStoryToBeAddedToIndex.views));
		doc.add(new StoredField("views", theStoryToBeAddedToIndex.views));
		doc.add(new SortedNumericDocValuesField("views", theStoryToBeAddedToIndex.views));

		doc.add(new org.apache.lucene.document.TextField("author", theStoryToBeAddedToIndex.author, Field.Store.YES));

		doc.add(new org.apache.lucene.document.TextField("datePublishedString",
				theStoryToBeAddedToIndex.datePublishedString, Field.Store.YES));

		// YYYYMMDD
		int datePublishedInt = 19000101;
		if (theStoryToBeAddedToIndex.datePublishedString != "unknown date") {
			String datePublishedStringWithoutSlashes;
			datePublishedStringWithoutSlashes = theStoryToBeAddedToIndex.datePublishedString.replace("/", "");
			datePublishedInt = Integer.parseInt(datePublishedStringWithoutSlashes);
		}

		doc.add(new org.apache.lucene.document.IntPoint("datePublishedInt", datePublishedInt));
		doc.add(new StoredField("datePublishedInt", datePublishedInt));
		doc.add(new SortedNumericDocValuesField("datePublishedInt", theStoryToBeAddedToIndex.datePublishedInt));

		doc.add(new org.apache.lucene.document.TextField("completionStatus", theStoryToBeAddedToIndex.completionStatus,
				Field.Store.YES));

		doc.add(new org.apache.lucene.document.TextField("contentRating", theStoryToBeAddedToIndex.contentRating,
				Field.Store.YES));

		doc.add(new org.apache.lucene.document.IntPoint("words", theStoryToBeAddedToIndex.words));
		doc.add(new StoredField("words", theStoryToBeAddedToIndex.words));
		doc.add(new SortedNumericDocValuesField("words", theStoryToBeAddedToIndex.words));

		// ID is in caps

		doc.add(new org.apache.lucene.document.IntPoint("ID", theStoryToBeAddedToIndex.ID));
		doc.add(new StoredField("ID", theStoryToBeAddedToIndex.ID));

		doc.add(new org.apache.lucene.document.IntPoint("prequelID", theStoryToBeAddedToIndex.prequelID));
		doc.add(new StoredField("prequelID", theStoryToBeAddedToIndex.prequelID));

		w.addDocument(doc);
	}

	public static boolean isThisStringAnInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}
