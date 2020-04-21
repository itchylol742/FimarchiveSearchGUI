package package1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class FimArchiveSearchConsole {

	static ArrayList<FimfictionStory> goodStoriesArray = new ArrayList<FimfictionStory>();

	static ArrayList<String> arrayOfUselessProperties = new ArrayList<String>();

	static ArrayList<String> goodTags = new ArrayList<String>();
	static ArrayList<String> badTags = new ArrayList<String>();

	static int indents = 0;
	static int updateInterval = 5000;
	static int numberOfStoriesScanned = 0;

	static boolean stillGood = true;

	static String finalOutput = "";

	static FimfictionStory temp = new FimfictionStory();

	public static void main(String[] args) {

		arrayOfUselessProperties.clear();
		//arrayOfUselessProperties.add("archive");
		arrayOfUselessProperties.add("avatar");
		arrayOfUselessProperties.add("color");
		arrayOfUselessProperties.add("cover_image");
		arrayOfUselessProperties.add("chapters");

		try {
			String name = "default";

			goodTags.add("Comedy");
			badTags.add("Spike");

			JsonReader reader = new JsonReader(new FileReader("sampleIndex.json"));
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

						if (numberOfStoriesScanned % updateInterval == 0) {
							goodPrint("searching " + numberOfStoriesScanned + ", " + goodStoriesArray.size()
									+ " good stories\n");

						}
						// indent();
						// goodPrint("--End of story data--\n");
						// goodPrint("--Checking for good tags " + goodTags + "--\n");
						for (int i = 0; i < goodTags.size(); i++) {
							if (temp.tags.contains(goodTags.get(i))) {
								// still good
							} else {
								stillGood = false;
								break;
							}
						}
						if (stillGood) {
							for (int i = 0; i < badTags.size(); i++) {
								if (temp.tags.contains(badTags.get(i))) {
									stillGood = false;
									break;
								} else {
									// still good
								}
							}
						}

						if (stillGood) {
							goodStoriesArray.add(temp);
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
						//goodPrint("--skipping useless property " + name + "--\n");
						reader.skipValue();
					}
					
					break;
				case STRING:
					String s = reader.nextString();

					System.out.println("string variable " + name);
					if (indents == 4 && name.equals("name")) {
						temp.tags.add(s);
					} else if (indents == 2 && name.equals("title")) {
						temp.title = s;
					} else if (indents == 3 && name.equals("name")) {
						temp.author = s;
					}

					break;
				case NUMBER:
					int n = reader.nextInt();
					if (name.equals("num_likes")) {
						temp.likes = n;
					}
					else if (name.equals("num_dislikes")) {
						temp.dislikes = n;
					}
					else if (name.equals("num_views")) {
						temp.views = n;
					}
					else if (indents == 2 && name.equals("num_words")) {
						// goodPrint("word count " + n + "\n");
						temp.words = n;
					}
					else if (name.equals("id")) {
						System.out.println("got ID " + n + " indent " + indents);
						
					}
					
					break;
				case BOOLEAN:
					boolean b = reader.nextBoolean();
					break;
				case NULL:
					reader.nextNull();
					break;
				case END_DOCUMENT:
					Collections.sort(goodStoriesArray, FimfictionStory.WordsComparator);
					for (int i = 0; i < goodStoriesArray.size(); i++) {
						goodPrint("Title: " + goodStoriesArray.get(i).title + "\n");
						goodPrint("Author: " + goodStoriesArray.get(i).author + "\n");
						goodPrint("Word Count: " + goodStoriesArray.get(i).words + "\n");
						goodPrint("Likes: " + goodStoriesArray.get(i).likes);
						goodPrint(", Dislikes: " + goodStoriesArray.get(i).dislikes + "\n");
						goodPrint("Views: " + goodStoriesArray.get(i).views + "\n");
						goodPrint("Tags: " + goodStoriesArray.get(i).tags + "\n");
						goodPrint("\n");
					}
					goodPrint("--End of scanning--\n");
					goodPrint("--Total stories scanned: " + numberOfStoriesScanned + "--\n");
					goodPrint("--Good stories: " + goodStoriesArray.size() + "--\n");
					goodStoriesArray.clear();
					reader.close();
					return;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void goodPrint(String theString) {
		System.out.print(theString);
	}

	public static void indent() {
		for (int i = 0; i < indents; i++) {
			goodPrint("  ");
		}
	}

}
