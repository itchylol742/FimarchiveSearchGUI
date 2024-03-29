# FimarchiveSearchGUI

![alt text](/FimarchiveGUIdemo.gif)

(the gif is outdated, the prequel/sequel finder is complete now)

A program to search and filter Fimarchive, which is an offline archive of every story on Fimfiction.net.
To get Fimarchive - at the time of writing, a 7.5 GB file, [go here](https://www.fimfiction.net/user/116950/Fimfarchive/blog) and click on Direct: Official (if you want to try out the program without download a 7.5 GB file, instructions below)

The program was tested on Windows, it might or might not work on other operating systems

## To use without compiling (for non-programmers who just want to search stuff)

Download FimarchiveSearchGUI(version).jar

Download Fimarchive

Extract index.json from Fimarchive (approximately 800 MB json file)

Extract the epub folder from Fimarchive (if Windows file explorer crashes, use 7zip)

Put FimarchiveSearchGui(version).jar, index.json and the epub folder in the same directory and run FimarchiveSearchGUI(version).jar

Follow the built-in instructions in the text box

![alt text](/before.png)

This is what the directory should look like before you run the program the first time

![alt text](/after.PNG)

This is what it should look like after running it the first time

## To compile and run in Eclipse IDE (Haven't tested this on a different PC, don't know if it works)

Clone the repo onto your PC

Put everything in a folder in your Eclipse workspace
/eclipseWorkspaceFolder/FimarchiveSearchGUI/all the files go here

Compile and run FimArchiveSearchGUI.java

![alt text](developerDirectory.PNG)

What the directory should look like if you're doing this

## To compile and run in Eclipse IDE __without downloading a 5.9 GB file__

Same as above, but go into FimArchiveSearchGUI.java and set useSampleIndex to true. This will use a 1 MB sampleIndex.json which comes with the project, and only contains a small portion of the proper index.json.

## About/other

I created this in 2018 after finding an offline archive of fimfiction.net, Fimfarchive. The archive had no way of searching through the stories so I asked if there was a way, and the author said there was no way unless I happened to be a programmer. And I was, so I decided to do something about it. I wanted to replicate most of the functionality of the fimfiction.net search, with parameters like including and excluding tags, min and max word count, rating, publish date, etc. The first version was finished in 2018, then I updated it in 2020 to use a third party library to make the search much faster, open a story by its ID, as well as find prequels/sequels to a story by its ID. I've been on the internet for some time and I know that no website lasts forever. One day fimfiction.net will go down forever and the only way to read fanfictions that were hosted there will be to download the offline archive. When that happens, I want the few remaining MLP fanfiction enthusiasts to have a way to search through the archive. I'm writing this in 2023, hello future MLP fanfiction enthusiasts!

