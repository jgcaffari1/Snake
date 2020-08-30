Snake
=====

A simple snake game created in javaFX.  

To run the .jar, type at the command line from the same directory as Snake.jar: 

	java -jar --module-path "C:\Users\userName\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml snake.jar 
	
    -the above path should be the path to the javafx library on your computer.  
    
User Guide:
-----

The snake is moved using the WSAD keys. The rest of the controls are indicated by buttons on
the GUI.

<img alt="Image" title="icon" src="..\readme_images\snake1.png" />
Figure 1: Basic GUI. The File menu allows the user to reset or quit the game- where save operations only work if the user
selects the “quit and save” option. The board menu allows the user to select between 3 different board sizes. The Speed menu
allows the user to select between three different speeds. The various Color bars open color choosers to change the colors of
the snake, board, or food. The Save Run button allows the user to name the save file for this run. The score is displayed above
the Save Run button. The settings are saved in the data log, and in another file specifically for user settings. The Replay Run
button allows the user to select and replay previously saved logs. The “Data Log” window displays all of the data that will be
saved in the save_recorded.txt file after the game is over.

The top menu choices allow the user to quit, reset, modify board size, and game speed:

<img alt="Image" title="icon" src="..\readme_images\snake2.png" />
Figure 2: Example File menu.

<img alt="Image" title="icon" src="..\readme_images\snake3.png" />
Figure 3: Example Board Menu. High density means that the cells on the board are smaller, making the board appear larger.
Low Density makes the cells on the board bigger, making the board appear smaller. 

<img alt="Image" title="icon" src="..\readme_images\snake4.png" />
Figure 4: Example Speed Menu.

The various color menus open color choosers like the ones shown in Figure 5, which change that
particular game setting. The last used colors and game settings are saved if the user quits with the Save
& Quit button, and are automatically loaded the next time the player starts the game. The settings are
also included at the top of the recorded game log.

<img alt="Image" title="icon" src="..\readme_images\snake5.png" />
Figure 5: Example of Color chooser. Identical menus open for each game component.

Clicking the Save Run button prompts the user to enter the filename, as shown in figure 6. Logs
are appended with “_recorded.txt” to allow the save logs to be easily distinguished from other test files/
settings files in the current directory. The total name of the data log is displayed under the Save Run
Button. 


<img alt="Image" title="icon" src="..\readme_images\snake6.png" />
Figure 6: Example User Prompt for Save Run. The Save Run window opens after clicking the “Save Run” button. 

<img alt="Image" title="icon" src="..\readme_images\snake7.png" />
Figure 7: Example of save file display text. The name entered will be displayed with _recorded.txt appended to it, this will be the
name of the file containing the saved game data. If no name is entered, then the game will be saved as Save_recorded.txt. 

The Data in the “Data Log” Window is of the format: _Score_MovementCommand_foodCorrdinates_snake’sHeadCoordinates_Snake’sTailCoordiantes_. All
coordinates are (row, col) of the occupied space on the board. The game will be recreated using the
movement commands and food coordinates, the rest of the data is present for potential machine
learning algorithms to learn the game. The “Data Log” title turns to “Dead!” when the user Dies, as
shown in figure 8. 

<img alt="Image" title="icon" src="..\readme_images\snake8.png" />
Figure 8: Example end of game screen. The data is only saved if the user selects "Save & Quit" from the File menu. The saved
game data appears under the “DEAD!” title. 

To load save game files, click the “Replay Run” Button to open a file chooser. Then select a file ending in
“_recorded.txt” to open another window and replay the saved game. 

<img alt="Image" title="icon" src="..\readme_images\snake9.png" />
Figure 9. Replay Run Button. Click the "Replay Run button to open the GameFiles directory.

<img alt="Image" title="icon" src="..\readme_images\snake10.png" />
Figure 10: FileChooser opened by the Replay Run button. The default directory should the the GameFiles directory inside of the
snake.jar’s pwd. 

<img alt="Image" title="icon" src="..\readme_images\snake11.png" />
Figure 11: Replaying Old Recordings. Once the file is opened, a new canvas starts and displays all the moves recorded in the
game file.

