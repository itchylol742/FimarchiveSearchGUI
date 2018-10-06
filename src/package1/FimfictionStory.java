package package1;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.lang.model.element.VariableElement;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.util.Collections;
import java.util.Comparator;

public class FimfictionStory {

	public String title = "unknown title";
	public String author = "unknown author";
	public String description = "unknown description";

	public String datePublishedString = "unknown date";
	public Date datePublishedObject;

	public String contentRating = "unknown content rating";

	ArrayList<String> tags = new ArrayList<String>();

	public int likes = -1;
	public int dislikes = -1;
	public int views = -1;
	public float percentRating;

	public int words = -1;

	public int ID = -1;
	public int prequelID = -1;

	public String completionStatus = "default completion status";

	public FimfictionStory() {

	}

	public FimfictionStory(int newID) {
		this.ID = newID;
	}

	public FimfictionStory(FimfictionStory original) {// copy constructor
		title = original.title;
		author = original.author;
		description = original.description;
		datePublishedString = original.datePublishedString;
		datePublishedObject = original.datePublishedObject;// is this a deep copy? might cause problems!

		tags = new ArrayList<String>(original.tags);// copy original tags

		likes = original.likes;
		dislikes = original.dislikes;

		words = original.words;

		prequelID = original.prequelID;
	}

	public static void main(String[] args) throws ParseException, IOException {
		int d = 0;
		for (int i = 1; i <=20; i++) {
			System.out.println(i);
			d+=i;
			System.out.println(d);
		}
	}
	

	

	public static void indent(int ind) {
		for (int i = 0; i < ind; i++) {
			System.out.print("    ");
		}
	}

	/**
	 * Removed HTML formatting from description
	 * 
	 * @param none
	 * @return none
	 */
	public void removeHtmlFormattingFromDescription() {
		String html = description;
		EditorKit kit = new HTMLEditorKit();
		Document doc = kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		try {
			Reader reader = new StringReader(html);
			kit.read(reader, doc, 0);
			description = doc.getText(0, doc.getLength());
			description = description.replace("’", "'");
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * 
	 * Get percentage rating, with 1 fake like and dislike added to avoid perfect
	 * scores from stories with few ratings
	 * 
	 * @param none
	 * @return none
	 */
	public float getRating() {
		if (likes == -1 || dislikes == -1) {
			return -1;
		} else {
			return (float) 100 * (likes + 1) / (likes + dislikes + 2);
		}
	}

	public void calculateRating() {
		if (likes == -1 || dislikes == -1) {
			percentRating = -1;
		} else {
			percentRating = (float) 100 * (likes + 1) / (likes + dislikes + 2);
		}
	}
	
	public void calculateDatePublishedObject() {
		
	}

	public static Comparator<FimfictionStory> TitleComparator = new Comparator<FimfictionStory>() {
		public int compare(FimfictionStory s1, FimfictionStory s2) {
			// ascending order
			return s1.title.toUpperCase().compareTo(s2.title.toUpperCase());
			// descending order
			// return StudentName2.compareTo(StudentName1);
		}
	};
	public static Comparator<FimfictionStory> LikesComparator = new Comparator<FimfictionStory>() {
		public int compare(FimfictionStory s1, FimfictionStory s2) {
			// lowest likes first
			// return s1.likes-s2.likes;
			// highest likes first
			return s2.likes - s1.likes;
		}
	};
	public static Comparator<FimfictionStory> WordsComparator = new Comparator<FimfictionStory>() {
		public int compare(FimfictionStory s1, FimfictionStory s2) {
			// lowest likes first
			// return s1.likes-s2.likes;
			// highest likes first
			return s2.words - s1.words;
		}
	};
	public static Comparator<FimfictionStory> ViewsComparator = new Comparator<FimfictionStory>() {
		public int compare(FimfictionStory s1, FimfictionStory s2) {
			// lowest likes first
			// return s1.likes-s2.likes;
			// highest likes first
			return s2.views - s1.views;
		}
	};
	public static Comparator<FimfictionStory> PercentRatingComparator = new Comparator<FimfictionStory>() {
		public int compare(FimfictionStory s1, FimfictionStory s2) {
			// lowest likes first
			// return s1.likes-s2.likes;
			// highest likes first
			if (s2.percentRating > s1.percentRating) {
				return 1;
			}
			return -1;
		}
	};

	@Override
	public String toString() {
		return ("Author: " + author + ", Title: " + title + ", ID: " + ID);
	}

}
