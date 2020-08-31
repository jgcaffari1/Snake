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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * a snake game in JavaFX
 * 
 * @author Joe Caffarini CS400 summer 2020
 * 
 *         Sources:
 *         https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm other
 *         oracle documentation.
 * 
 */
public class Main extends Application {

	private int gameWidth, gameHeight, gameCols, gameRows, frameLag;
	private Game game;
	private GUI gui;
	static protected FileLogger settings = new FileLogger();
	static protected final int highDensity = 40;
	static protected final int mediumDensity = 20;
	static protected final int lowDensity = 10;
	static protected final int fastFrameLag = 1;
	static protected final int normalFrameLag = 4;
	static protected final int slowFrameLag = 8;

	Color boardColor;
	Color snakeColor;
	Color foodColor;

	public static void main(String[] args) {

		launch(args);

	}

	/**
	 * starts the application loop
	 */
	@Override
	public void start(Stage pStage) throws Exception {
		gameWidth = 400;
		gameHeight = 400;
		loadBoardDensity();
		loadFrameLag();

		// set stage:
		pStage.setTitle("Snake");
		// create a group root
		BorderPane root = new BorderPane();

		// this is the overall scene containing everything, initialized once:
		Scene sc = new Scene(root, 700, 500, Color.rgb(10, 10, 10));

		// set scene display:
		pStage.setScene(sc);
		pStage.show();
		// initialize game instance:
		game = new Game(gameWidth, gameHeight, gameCols, gameRows, frameLag);
		gui = new GUI("quit", "reset", game);

		// load user saved color settings:
		loadColorSettings();

		// enter game loop:
		game.start(root, pStage, sc);
		gui.build(root, pStage, sc);

	}

	/**
	 * loads the user's board density from the saved file.
	 */
	private void loadBoardDensity() {
		int density;
		// first, attempt to load user settings, if the file is empty,
		// then load default settings
		if (settings.userSettingsEmpty()) {
			density = Integer.parseInt(settings.getDefaults(0));

		} else { // load user's saved settings
			density = Integer.parseInt(settings.getUserSettings(0));
		}
		setBoardDensity(density);
	}

	/**
	 * sets the board density according to user settings. Designed for initial
	 * setup.
	 * 
	 * @param densityCode - the code denoting the board density, 0 - low, 1- medium,
	 *                    2 - high
	 */
	private void setBoardDensity(int densityCode) {
		switch (densityCode) {
		case (0): {
			gameCols = lowDensity;
			gameRows = lowDensity;
			break;
		}
		case (1): {
			gameCols = mediumDensity;
			gameRows = mediumDensity;
			break;
		}
		case (2): {
			gameCols = highDensity;
			gameRows = highDensity;
			break;
		}
		default: {
			gameCols = mediumDensity;
			gameRows = mediumDensity;
			break;
		}
		}
	}

	/**
	 * loads the users frame lag from the saved file for initial setup.
	 */
	private void loadFrameLag() {
		int frameLagCode;
		// first, attempt to load user settings, if the file is empty,
		// then load default settings
		if (settings.userSettingsEmpty()) {
			frameLagCode = Integer.parseInt(settings.getDefaults(1));

		} else { // load user's saved settings
			frameLagCode = Integer.parseInt(settings.getUserSettings(1));
		}
		setFrameLag(frameLagCode);
	}

	/**
	 * sets the frame lag based off of the frame lag code. Designed for initial
	 * setup.
	 * 
	 * @param frameLagCode - code defining the frame lag: 0 - slow, 1 - normal, 2 -
	 *                     fast
	 */
	private void setFrameLag(int frameLagCode) {
		switch (frameLagCode) {
		case (0): {
			frameLag = slowFrameLag;
			break;
		}
		case (1): {
			frameLag = normalFrameLag;
			break;
		}
		case (2): {
			frameLag = fastFrameLag;
			break;
		}
		default: {
			frameLag = normalFrameLag;
			break;
		}
		}
	}

	/**
	 * loads the staring color settings from the user's save file:
	 */
	private void loadColorSettings() {

		if (settings.userSettingsEmpty()) {
			// if there are no user settings, use defaults
			boardColor = Color.web(settings.getDefaults(3));
			snakeColor = Color.web(settings.getDefaults(2));
			foodColor = Color.web(settings.getDefaults(4));

		} else {
			// load the users saved settings
			boardColor = Color.web(settings.getUserSettings(3));
			snakeColor = Color.web(settings.getUserSettings(2));
			foodColor = Color.web(settings.getUserSettings(4));
		}
		// change the game's colors:
		game.board.changeColors(boardColor, snakeColor, foodColor);

	}

}
