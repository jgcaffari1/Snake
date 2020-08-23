package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * class for initializing all the GUI components for controlling the
 * application.
 * 
 * @author jgcaf
 *
 */
public class GUI {

	MenuItem quit;
	MenuItem reset;
	MenuItem high;
	MenuItem medium;
	MenuItem low;

	MenuItem fast;
	MenuItem normal;
	MenuItem slow;

	VBox buttonBox;
	ColorPicker snakeColorPicker;
	ColorPicker boardColorPicker;
	ColorPicker foodColorPicker;
	TitledPane snakePane;
	TitledPane boardPane;
	TitledPane foodPane;
	MenuBar menubar;
	Menu menu;
	Menu boardDensity;
	Menu gameSpeed;
	Game game;
	ScrollPane scroll;
	VBox textBox;
	Label label;
	Button saveButton;
	Button loadButton;
	String saveFileName = "save_recorded.txt";
	File saveFile;
	FileChooser fileChooser;
	// initialize desktop for loading data files:

	public GUI(String exitText, String resetText, Game game) {
		buttonBox = new VBox();
		saveButton = new Button("Save Run");
		loadButton = new Button("Replay Run");
		label = new Label(saveFileName);

		menubar = new MenuBar();
		boardDensity = new Menu("Board");
		menu = new Menu("File");
		gameSpeed = new Menu("Speed");
		this.game = game;

		snakeColorPicker = new ColorPicker(Color.web(Main.settings.getUserSettings(2)));
		snakePane = new TitledPane("Snake Color", snakeColorPicker);
		snakePane.setExpanded(false);

		boardColorPicker = new ColorPicker(Color.web(Main.settings.getUserSettings(3)));
		boardPane = new TitledPane("Board Color", boardColorPicker);
		boardPane.setExpanded(false);

		foodColorPicker = new ColorPicker(Color.web(Main.settings.getUserSettings(4)));
		foodPane = new TitledPane("Food Color", foodColorPicker);
		foodPane.setExpanded(false);

		scroll = new ScrollPane();
		textBox = new VBox();

		// initialize log path
		Main.settings.setLogPath(saveFileName);

	}

	/**
	 * quits the current application and saves the user settings.
	 * 
	 * @param mainScene - the scene containing the application.
	 */
	private void quitAction(Scene mainScene) {
		Stage stage = (Stage) mainScene.getWindow();
		// save all user settings before quitting
		Main.settings.gatherUserSettings(game.nrows, game.frameLag, game.board.snakeRGB, game.board.boardRGB,
				game.board.foodRGB);
		try {
			Main.settings.saveUserSettings();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// do what you have to do
		stage.close();
	}

	/**
	 * reset game without changing any settings.
	 */
	private void resetAction() {
		// restart game with new size.
		game.reset();

		// refresh board with saved color settings:
		changeColors(game);

	}

	/**
	 * resets the board to have different cell sizes
	 * 
	 * @param game        - the game object being modified.
	 * @param rowsAndCols - the number of rows/columns on one side of a square
	 *                    board.
	 */
	private void resetGameSize(Game game, int rowsAndCols) {
		// change game settings
		game.nrows = rowsAndCols;
		game.ncols = rowsAndCols;

		// save settings
		Main.settings.gatherUserSettings(rowsAndCols, game.frameLag, game.board.snakeRGB, game.board.boardRGB,
				game.board.foodRGB);

		// reset game
		game.reset();

		// refresh board with saved color settings:
		changeColors(game);
	}

	/**
	 * changes the game speed
	 * 
	 * @param game     - game being updated
	 * @param frameLag - the new frame lag of the game.
	 */
	private void changeGameSpeed(Game game, int frameLag) {
		game.changeSpeed(frameLag);

	}

	/**
	 * method for replaying game from a specified file.
	 * 
	 * @param game - the game object.
	 */
	public void replaySavedGame(Game game) {
		// load log file
		Main.settings.loadLogFile(saveFileName);
		// create replay borderpane:
		BorderPane replayRoot = new BorderPane();
		// create replay scene:
		Scene replaySc = new Scene(replayRoot, game.canvasX, game.canvasY, Color.rgb(10, 10, 10));
		// create replay stage:
		Stage replayStage = new Stage();
		replayStage.setTitle("Replay");
		// set replay scene display:
		replayStage.setScene(replaySc);
		replayStage.show();

		// load replay settings and start replay loop:
		game.startReplay(replayRoot, replayStage, replaySc);
	}

	/**
	 * builds the gui
	 * 
	 * @param root   - border pane containing the gui
	 * @param pStage - stage of the applciation
	 * @param sc     - the scene containing the animation
	 * @param game   - the initialized game instance.
	 */
	public void build(BorderPane root, Stage pStage, Scene sc) {
		// set quit and reset defaults menu:
		quit = new MenuItem("Save & Quit");
		reset = new MenuItem("Restart");
		quit.setOnAction(e -> quitAction(sc));
		reset.setOnAction(e -> resetAction());
		menu.getItems().addAll(reset, quit);

		// set board density menu:
		high = new MenuItem("High Density");
		medium = new MenuItem("Medium Density");
		low = new MenuItem("Low Density");
		high.setOnAction(e -> resetGameSize(game, Main.highDensity));
		medium.setOnAction(e -> resetGameSize(game, Main.mediumDensity));
		low.setOnAction(e -> resetGameSize(game, Main.lowDensity));
		boardDensity.getItems().addAll(high, medium, low);

		// set board density menu:
		fast = new MenuItem("Fast");
		normal = new MenuItem("Normal");
		slow = new MenuItem("Slow");
		fast.setOnAction(e -> changeGameSpeed(game, Main.fastFrameLag));
		normal.setOnAction(e -> changeGameSpeed(game, Main.normalFrameLag));
		slow.setOnAction(e -> changeGameSpeed(game, Main.slowFrameLag));
		gameSpeed.getItems().addAll(fast, normal, slow);

		// assemble board density menu
		menubar.getMenus().addAll(menu, boardDensity, gameSpeed);
		buttonBox.getChildren().addAll(snakeColorPicker, boardColorPicker, foodColorPicker);

		// assemble color pickers:
		snakeColorPicker.setOnAction((ActionEvent t) -> {
			changeColors(game);
		});

		boardColorPicker.setOnAction((ActionEvent t) -> {
			changeColors(game);
		});

		foodColorPicker.setOnAction((ActionEvent t) -> {
			changeColors(game);
		});
		// assemble gui sub containers:
		root.setLeft(buttonBox);
		root.setTop(menubar);

		// add data log display:
		scroll.setContent(game.loggerText);
		textBox.getChildren().addAll(game.logLabel, scroll);
		root.setRight(textBox);
		// set main container style
		root.setStyle("-fx-background-color: BLACK");
		// set save button:
		saveButton.setOnAction(e -> showSaveDialog());

		// add all components to the button box:
		buttonBox.getChildren().addAll(snakePane, boardPane, foodPane, game.scoreValue, saveButton, label, loadButton);

		fileChooser = new FileChooser();

		loadButton.setOnAction(e -> launchFileChooser(pStage));

	}

	/**
	 * launches file chooser and allows user to select the path of the save file
	 * they want to load. see this tutorial for more information on using the file
	 * chooser: https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
	 * 
	 * @param stage
	 */
	public void launchFileChooser(Stage pStage) {
		fileChooser.setInitialDirectory(new File(FileLogger.gameSaveDir));
		File file = fileChooser.showOpenDialog(pStage);
		// do not let user select the file unless it ends with "_recorded.txt"
		while (!file.getName().contains("_recorded.txt")) {
			file = fileChooser.showOpenDialog(pStage);
		}

		if (file != null) {
			saveFileName = file.getPath();
			pStage.show();
			replaySavedGame(game);
		}
	}

	/**
	 * updates the colors of the game components.
	 * 
	 * @param game - the initialized game instance.
	 */
	public void changeColors(Game game) {
		game.board.changeColors(boardColorPicker.getValue(), snakeColorPicker.getValue(), foodColorPicker.getValue());
	}

	/**
	 * shows the user the text box dialog asking for their name. It then uses this
	 * as the save log filename.
	 */
	private void showSaveDialog() {

		TextInputDialog dialog = new TextInputDialog("Bucky");

		dialog.setTitle("Save Run");
		dialog.setHeaderText("Enter your name:");
		dialog.setContentText("Name:");

		Optional<String> result = dialog.showAndWait();
		saveFileName = result.toString().replace("Optional[", "");
		saveFileName.replace(']', '.');
		int index = saveFileName.indexOf("]");
		saveFileName = saveFileName.substring(0, index) + "_recorded.txt";

		// update path to log file:
		Main.settings.setLogPath(saveFileName);
		result.ifPresent(name -> {
			this.label.setText(saveFileName);
		});
	}

}
