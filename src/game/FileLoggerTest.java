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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * test class for the FileLogger class - tests the saving an loading operations.
 * 
 * @author jgcaf
 *
 */
class FileLoggerTest {
	FileLogger ds;

	protected FileLogger createInstance() {
		return new FileLogger();
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ds = createInstance();
	}

	@AfterEach
	void tearDown() throws Exception {

		// clear user settings
		ds.clearUserSettings();
		ds.saveUserSettings();
		// reset object:
		ds = null;
	}

	/**
	 * checks if the FileLogger class can correctly load data from the default
	 * settings file
	 */
	@Test
	void canLoadDefaultFileContents() {
		ArrayList<String> expected = new ArrayList<>();
		expected.add("1");
		expected.add("1");
		expected.add("000000");
		expected.add("FFFFFF");
		expected.add("FF0000");
		ds.loadDefaults();
		for (int i = 0; i < expected.size(); i++) {
			if (!expected.get(i).equals(ds.getDefaults(i))) {
				fail("default values are not as expected!");
			}
		}
	}

	/**
	 * tests that the user specified settings are being set in the data structure,
	 * and that these strings can be converted back into Colors.
	 */
	@Test
	void storesUserSettingsInDataStructure() {
		ArrayList<String> expected = new ArrayList<>();
		expected.add("1");
		expected.add("1");
		expected.add("000000");
		expected.add("FFFFFF");
		expected.add("FF0000");
		Color snakeColor = Color.web("000000");
		Color boardColor = Color.web("FFFFFF");
		Color foodColor = Color.web("FF0000");
		ds.gatherUserSettings(20, 4, snakeColor, boardColor, foodColor);
		for (int i = 0; i < 2; i++) {
			if (!expected.get(i).equals(ds.getUserSettings(i))) {
				fail("game density and speed settings are not being set as expected");
			}
		}
		for (int i = 2; i < expected.size(); i++) {
			if (!Color.web(expected.get(i)).equals(Color.web(ds.getUserSettings(i)))) {
				fail("Color strings cannot be converted back into colors ");
			}
		}
	}

	/**
	 * test to see if data logger can load the saved user settings from the
	 * designated file
	 */
	@Test
	void savesUserSettings() {
		ArrayList<String> expected = new ArrayList<>();
		expected.add("1");
		expected.add("1");
		expected.add("000000");
		expected.add("FFFFFF");
		expected.add("FF0000");
		Color snakeColor = Color.web("000000");
		Color boardColor = Color.web("FFFFFF");
		Color foodColor = Color.web("FF0000");
		ds.gatherUserSettings(20, 4, snakeColor, boardColor, foodColor);
		try {
			// save user settings to the file
			ds.saveUserSettings();
			// clear user settings
			ds.clearUserSettings();
			// re load saved user settings
			ds.loadUserSettings();
			// check if the board density and clock speed are loaded properly:
			for (int i = 0; i < 2; i++) {
				if (!expected.get(i).equals(ds.getUserSettings(i))) {
					fail("game density and speed settings are not being loaded from file");
				}
			}
			// check if colors are loaded properly
			for (int i = 2; i < expected.size(); i++) {
				if (!Color.web(expected.get(i)).equals(Color.web(ds.getUserSettings(i)))) {
					fail("Color strings cannot be loaded from file");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// test fails of file cannot be found:
			fail("user-settings.txt not present");
		}
	}

	/**
	 * test to see if data logger can load the saved user settings from the
	 * designated file
	 */
	@Test
	void loadSettingsFromLoggedRun() {
		ArrayList<String> expected = new ArrayList<>();
		expected.add("1");
		expected.add("1");
		expected.add("000000");
		expected.add("FFFFFF");
		expected.add("FF0000");
		Color snakeColor = Color.web("000000");
		Color boardColor = Color.web("FFFFFF");
		Color foodColor = Color.web("FF0000");
		// gather the default settings
		ds.gatherUserSettings(20, 4, snakeColor, boardColor, foodColor);
		// save user settings to log file
		ds.logUserSettings("test.txt");
		// clear user settings
		ds.clearUserSettings();
		// re load saved user settings
		ds.loadLogFile("test.txt");
		// check if the board density and clock speed are loaded properly:
		for (int i = 0; i < 2; i++) {
			if (!expected.get(i).equals(ds.getUserSettings(i))) {
				fail("game density and speed settings are not being loaded from file");
			}
		}
		// check if colors are loaded properly
		for (int i = 2; i < expected.size(); i++) {
			if (!Color.web(expected.get(i)).equals(Color.web(ds.getUserSettings(i)))) {
				fail("Color strings cannot be loaded from file");
			}
		}
	}

	/**
	 * test to see if the settings can be drawn from one line of the data log.
	 */
	@Test
	void loadInitializingDataLog() {
		ArrayList<String> expected = new ArrayList<>();
		expected.add("1");
		expected.add("1");
		expected.add("000000");
		expected.add("FFFFFF");
		expected.add("FF0000");
		Color snakeColor = Color.web("000000");
		Color boardColor = Color.web("FFFFFF");
		Color foodColor = Color.web("FF0000");
		// gather the default settings
		ds.gatherUserSettings(20, 4, snakeColor, boardColor, foodColor);
		// save user settings to log file
		ds.logUserSettings("test.txt");
		// clear user settings
		ds.clearUserSettings();
		// re load saved user settings
		ds.loadLogFile("test.txt");
		// check if the board density and clock speed are loaded properly:
		for (int i = 0; i < 2; i++) {
			if (!expected.get(i).equals(ds.getUserSettings(i))) {
				fail("game density and speed settings are not being loaded from file");
			}
		}
		// check if colors are loaded properly
		for (int i = 2; i < expected.size(); i++) {
			if (!Color.web(expected.get(i)).equals(Color.web(ds.getUserSettings(i)))) {
				fail("Color strings cannot be loaded from file");
			}
		}
	}

	/**
	 * development for loading and parsing a saved file:
	 */
	@Test
	void loadSaveLog() {

		// re load saved user settings
		ds.loadLogFile("Joe.txt");

		ArrayList<String> foodCoordinates = ds.getLoadedFoodCoordinates();
		ArrayList<String> directions = ds.getLoadedDirections();
		for (int i = 0; i < foodCoordinates.size(); i++) {
			System.out.println(foodCoordinates.get(i));
		}
		for (int i = 0; i < directions.size(); i++) {
			KeyCode code = FileLogger.translateDirections((directions.get(i)));
			if (!(code instanceof KeyCode)) {
				fail("Failed to convert strings to KeyCodes");
			}
			if (!code.toString().equals(directions.get(i))) {
				fail("Incorrect Translation");
			}
			System.out.println(code.toString());

		}
	}

}
