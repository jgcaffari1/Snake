/**
 *  A simple snake game and gui created with javaFX.
 *  
    Copyright (C) 2020  Joe Caffarini jgcaffari1@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    
    I ask that you cite / reference my github repo if you use this code as a reference.  
 */

package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * class for loading all settings, and storing all positional data. It also
 * contains general methods for translating the save data into other formats.
 * 
 * @author jgcaf
 *
 */
public class FileLogger {
	static protected final int highDensity = 40;
	static protected final int mediumDensity = 20;
	static protected final int lowDensity = 10;
	static protected final int fastFrameLag = 1;
	static protected final int normalFrameLag = 4;
	static protected final int slowFrameLag = 8;
	static protected String gameSaveDir = System.getProperty("user.dir") + "//GameFiles//";
	final private String defaultsPath = "default-settings.txt";
	final private String userSettingsFile = "user-settings.txt";
	File saveFile;
	private File defaultFile;
	private Scanner sc;
	private ArrayList<String> defaults = new ArrayList<>();
	private ArrayList<String> userSettings = new ArrayList<>();

	// variables for loading save states:
	private List<String> replayLines;
	protected ArrayList<String> loadedFoodCoordinates = new ArrayList<>();
	protected ArrayList<String> loadedDirections = new ArrayList<>();

	FileWriter logFileWriter;
	PrintWriter printToLog;
	private String logPath;

	protected ArrayList<String> dataLog;

	/**
	 * no argument constructor.
	 */
	public FileLogger() {
		try {
			saveFile = new File(gameSaveDir + userSettingsFile);
			defaultFile = new File(gameSaveDir + defaultsPath);
			sc = new Scanner(defaultFile);
			// load settings:
			loadDefaults();
			loadUserSettings();
			dataLog = new ArrayList<String>();

		} catch (FileNotFoundException e) {
			// log error
			Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * prints the contents of the file
	 * 
	 * @throws FileNotFoundException - if the file does not exist
	 */
	public void printDefaults() throws FileNotFoundException {

		while (sc.hasNextLine()) {
			System.out.println(sc.nextLine());
		}
	}

	/**
	 * loads the defaults from the file.
	 */
	public void loadDefaults() {
		while (sc.hasNextLine()) {
			defaults.add(sc.nextLine());
		}
	}

	/**
	 * loads all the data from the save file and sets them into user settings.  It also stores food coordinates and movement commands into their 
	 * respective buffers.  
	 */
	public void loadLogFile(String savePath) {
		try {
			replayLines = Files.readAllLines(Paths.get(savePath));
			// gets the settings from the first line of the dataset:
			String[] replaySettings = replayLines.remove(0).split("-");

			// sets the user settings to be the same as the replay
			if (replaySettings.length == 5) {
				clearUserSettings();
			}
			for (int i = 0; i < replaySettings.length; i++) {
				userSettings.add(replaySettings[i]);
			}

			if (replayLines.size() > 1) {
				// remove empty line between settings and recorded data if there is more than
				// one extra line present - avoids an error if only settings are stored in
				// the file.
				replayLines.remove(0);
				// load all the food coordinates and movement commands:
				String coordiantes;
				for (int i = 0; i < replayLines.size(); i++) {
					loadedDirections.add(replayLines.get(i).split("_")[1]);
					coordiantes = replayLines.get(i).split("_")[2];
					// only add unique coordinates to the list:
					if (!loadedFoodCoordinates.contains(coordiantes)) {
						loadedFoodCoordinates.add(coordiantes);
					}

				}
			}
		} catch (IOException e) {
			// log error:
			Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, e);

		}

	}

	/**
	 * gets the recorded food coordinates from the save file.
	 * 
	 * @return - list of predefined food coordinates.
	 */
	public ArrayList<String> getLoadedFoodCoordinates() {
		return loadedFoodCoordinates;
	}

	/**
	 * gets the directions loaded from the save files.
	 * 
	 * @return the list of directions from a saved file.
	 */
	public ArrayList<String> getLoadedDirections() {
		return loadedDirections;
	}

	/**
	 * gets the data loaded from the save file.
	 * 
	 * @return the list of loaded replay data.
	 */
	public List<String> getReplayLines() {
		return replayLines;
	}

	/**
	 * prints the default settings once they are loaded
	 */
	public void printLoadedDefaults() {
		for (int i = 0; i < defaults.size(); i++) {
			System.out.println(defaults.get(i));
		}
	}

	/**
	 * gets the specified input value. If the input parameter i is too big, then get
	 * the last element of the array
	 * 
	 * @param i - the line of the value being returned
	 * @return the line of the default settings
	 */
	protected String getDefaults(int i) {
		if (i >= defaults.size()) {
			i = defaults.size() - 1;
		}
		return defaults.get(i);
	}

	/**
	 * collects the user settings from the application and stores them locally.
	 * 
	 * @param boardDensity - the number 0-2 indicating the board density setting
	 * @param gameSpeed    - the number 0-2 indicating the board speed setting.
	 * @param snakeColor   - the color of the snake
	 * @param boardColor   - the color of the board
	 * @param foodColor    - the color of the food
	 */
	protected void gatherUserSettings(Integer boardDensity, Integer frameLag, Color snakeColor, Color boardColor,
			Color foodColor) {
		clearUserSettings();
		userSettings.add(translateBoardDensity(boardDensity));
		userSettings.add(translateFrameLag(frameLag));
		userSettings.add(snakeColor.toString());
		userSettings.add(boardColor.toString());
		userSettings.add(foodColor.toString());
	}

	/**
	 * updates board density and
	 * 
	 * @param boardDensity - the number 0-2 indicating the board density setting
	 * @param gameSpeed    - the number 0-2 indicating the board speed setting.
	 */
	protected void setUserSettings(int boardDensity, int frameLag) {
		userSettings.set(0, translateBoardDensity(boardDensity));
		userSettings.set(1, translateFrameLag(frameLag));
	}

	/**
	 * changes the path to the data log directory.
	 * 
	 * @param logPath - the name of the filepath to the data log files.
	 */
	protected void setLogPath(String logPath) {
		// update log path
		// if the path to the save directory is already in the log path,
		// then do not prepend with relative path
		if (logPath.contains(gameSaveDir)) {
			this.logPath = logPath;
		} else {
			this.logPath = gameSaveDir + logPath;
		}
	}

	/**
	 * saves the user settings to the motion Log File
	 */
	protected void logUserSettings(String logPath) {
		PrintWriter save;
		setLogPath(logPath);
		String lineToSave = convertUserSettingsToString();

		try {
			if (!lineToSave.equals("")) {
				save = new PrintWriter(logPath);
				save.write(lineToSave + "\n");
				save.flush();
				save.close();
			}
		} catch (FileNotFoundException e) {
			// log error:
			Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	/**
	 * translates an Integer board density value to the single number board density
	 * code
	 * 
	 * @param boardDensity - the given board density.
	 * @return the single letter density code: 0-low, 1-medium, 2- high
	 */
	private String translateBoardDensity(Integer boardDensity) {
		int densityCode;
		switch (boardDensity) {
		case (lowDensity): {
			densityCode = 0;
			break;
		}
		case (mediumDensity): {
			densityCode = 1;
			break;
		}
		case (highDensity): {
			densityCode = 2;
			break;
		}
		default: {
			densityCode = 1;
			break;
		}
		}
		return Integer.toString(densityCode);
	}

	/**
	 * converts the frame lag into its code for storage.
	 * 
	 * @param frameLag - the given frame lag
	 * @return the frame lag code: 0-slow, 1-normal, 2- fast.
	 */
	private String translateFrameLag(Integer frameLag) {
		int frameLagCode;
		switch (frameLag) {
		case (slowFrameLag): {
			frameLagCode = 0;
			break;
		}
		case (normalFrameLag): {
			frameLagCode = 1;
			break;
		}
		case (fastFrameLag): {
			frameLagCode = 2;
			break;
		}
		default: {
			frameLagCode = 1;
			break;
		}
		}
		return Integer.toString(frameLagCode);
	}

	/**
	 * prints the imported user settings.
	 */
	protected void printUserSettings() {
		for (int i = 0; i < userSettings.size(); i++) {
			System.out.println(userSettings.get(i));
		}
	}

	/**
	 * gets the user settings
	 * 
	 * @param i - the user setting number
	 * @return the specified user setting.
	 */
	protected String getUserSettings(int i) {
		if (userSettings.size() == 0) {
			// if no user settings are loaded, then use defaults
			return getDefaults(i);
		}
		if (i >= userSettings.size()) {
			i = userSettings.size() - 1;
		}

		return userSettings.get(i);
	}

	/**
	 * saves the imported user settings to the user settings file.
	 * 
	 * @throws FileNotFoundException - if the default save file does not exist
	 */
	protected void saveUserSettings() throws FileNotFoundException {
		PrintWriter save = new PrintWriter(saveFile);
		for (int i = 0; i < userSettings.size(); i++) {
			save.write(userSettings.get(i) + "\n");
		}
		save.flush();
		save.close();
	}

	/**
	 * loads the defaults from the file.
	 * 
	 * @throws FileNotFoundException
	 */
	protected void loadUserSettings() throws FileNotFoundException {
		sc = new Scanner(saveFile);
		while (sc.hasNextLine()) {
			userSettings.add(sc.nextLine());
		}
	}

	/**
	 * converts the user settings to a single line for saving.
	 * 
	 * @return
	 */
	protected String convertUserSettingsToString() {

		String lineToSave = "";
		for (int i = 0; i < userSettings.size(); i++) {
			lineToSave = lineToSave + userSettings.get(i);
			if (i < userSettings.size() - 1) {
				lineToSave = lineToSave + "-";
			}
		}

		return lineToSave;

	}

	/**
	 * resets the user settings array
	 */
	protected void clearUserSettings() {
		userSettings = new ArrayList<>();
	}

	/**
	 * checks if the user settings are empty
	 * 
	 * @return true of the user settings are empty.
	 */
	protected boolean userSettingsEmpty() {

		return userSettings.size() == 0;

	}

	/**
	 * saves the complete log of the user file.
	 * 
	 * @param filename     - the name of the file being saved
	 * @param fileContents - the contents of the file being saved
	 */
	public void logUserRun(String fileContents) {
		// initialize the save file with one line containing user settings:
		logUserSettings(logPath);
		try {
			// append the user recorded data to the file containing the one line user
			// settings
			PrintWriter save = new PrintWriter(new FileOutputStream(new File(logPath), true));
			save.print(fileContents);
			save.flush();
			save.close();
		} catch (FileNotFoundException e) {
			// log error:
			Logger.getLogger(FileLogger.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * translates an input direction string loaded from a data log into a KeyCode
	 * 
	 * @param direction - String representation of a user direction WASD
	 * @return the corresponding KeyCode direction WASD, if the input is invalid,
	 *         then it returns -
	 */
	static public KeyCode translateDirections(String direction) {
		// if the input is empty or null, return - sign.
		if (direction == null || direction.equals("")) {
			return KeyCode.SUBTRACT;
		}
		if (direction.equals("W")) {
			return KeyCode.W;
		}
		if (direction.equals("A")) {
			return KeyCode.A;
		}
		if (direction.equals("S")) {
			return KeyCode.S;
		}
		if (direction.equals("D")) {
			return KeyCode.D;
		} else {
			// if key is unexpected, then return - sign.
			return KeyCode.SUBTRACT;
		}
	}

	/**
	 * gets the current board density from the imported code
	 * 
	 * @return the decoded board density setting.
	 */
	public int getBoardSize() {
		int boardDensity;
		switch (userSettings.get(0)) {
		case ("0"): {
			boardDensity = lowDensity;
			break;
		}
		case ("1"): {
			boardDensity = mediumDensity;
			break;
		}
		case ("2"): {
			boardDensity = highDensity;
			break;
		}
		default: {
			boardDensity = mediumDensity;
			break;
		}
		}
		return boardDensity;
	}

	/**
	 * gets the frame lag setting from the imported save file.
	 * 
	 * @return - the decoded frame lag setting.
	 */
	public int getFrameLag() {
		int frameLag;
		switch (userSettings.get(1)) {
		case ("0"): {
			frameLag = slowFrameLag;
			break;
		}
		case ("1"): {
			frameLag = normalFrameLag;
			break;
		}
		case ("2"): {
			frameLag = fastFrameLag;
			break;
		}
		default: {
			frameLag = normalFrameLag;
			break;
		}
		}
		return frameLag;
	}

}
