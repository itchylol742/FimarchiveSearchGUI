# FimarchiveSearchGUI

![alt text](/FimarchiveSearchGUIScreenshot.PNG)

A program to search and filter Fimarchive, which is an archive of every story on Fimfiction.net.
To get Fimarchive - at the time of writing, a 5.5 GB file, [go here](https://www.fimfiction.net/user/116950/Fimfarchive/blog) and click on Direct: Official (if you want to try out the program without download a 5.5 GB file, instructions below)

The program was tested in Eclipse IDE on Windows, it might or might not work on other operating systems and IDEs

**__In addition, I have never shared this code before or used this on another PC so it might not work at all!__**

## To use without compiling (for non-programmers who just want to search stuff)

Download FimarchiveSearchGUI.jar and settings.txt

Download Fimarchive (the 5.5 GB file)

Extract index.json from Fimarchive (approximately 750 MB json file)

Put them all in the same folder and run FimarchiveSearchGUI.jar

Follow the built-in instructions in the text box

## To compile and run in Eclipse IDE

Clone the repo onto your PC

Put everything in a folder in your Eclipse workspace
/eclipseWorkspaceFolder/FimarchiveSearchGUI/all the files go here

Compile and run FimArchiveSearchGUI.java

## To compile and run in Eclipse IDE __without downloading a 5.5 GB file__

Same as above, but go into FimArchiveSearchGUI.java and set useSampleIndex to true. This will use a 1 MB sampleIndex.json which comes with the project, and only contains a small portion of the proper index.json.


