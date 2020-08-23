README

Course: cs400
Semester: Summer 2020
Project Name: Snake/SnakeFX
Student Name: Joe Caffarini

email: caffarini@wisc.edu

Other Notes: 

	This project is meant to provide a fundamental interface for AI interaction. Specifically, it is meant to save complete games, and parse the
	saved game data.  This code could be adapted in the future to run output generated from
	several ML trials playing the game.

	More information about the gui Design is provided in the GUI.pdf. 

	All game saves and settings files are stored in the folder "GameFiles".  This directory is required for the game to function.   
	The file default-settings.txt provide the default settings if there are no custom settings stored in user-settings.txt.  The other files that end in _recorded are the various 
	saved games.  The other files are there for testing purposes (FileLoggerTest.java).  

To run the .jar, type at the command line from the same directory as Snake.jar: 

	java -jar --module-path "C:\Users\userName\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml snake.jar 
		-the above path should just be the path to the javafx library on your computer.  

	 

Future Work: 

	Snake was desinged with machine learning in mind, so the next step will be a neural network for learning how to play.  The main candidate is a genetic algorithm because
	it is commonly used for this type of application. This means that the main method will need to be modified for the creation of thousands of instances recording in tandem,
	which might not be feasible with the current design, especially because it appends a string to save game data.  This project might also require a fitness score 
	metric in addition to the game score to encourage certain playstyles over others.  

	Other future directions would be to put this into its own application, and fix known bugs.  

Known bugs: 

	If you enter a save file name and restart the game, the program will save the parts of the game before the reset.  This means that after every reset, the file name 
	for logging the save needs to be re entered, or else the save file will contain the old game up to the reset.  

	The data is logged as a growing string, so it would be more efficient to instead append the lines directly to a txt file. This bug could be significant if there are
	many game instances running at once, like when implementing a gentic algorithm.  

	if the settings at the top of a saved file are corrupted, then the game will be unable to replay that log.  The top should include two one integer codes followed by 
	hex rgb color codes, ex: 2-1-0xcce6ffff-0x804d80ff-0xff0000ff.
	
