package game;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * the game launcher - initializes all components for the game animation.
 * 
 * @author jgcaf
 *
 */
public class Game {
	protected ArrayList<KeyCode> input;
	protected ArrayList<KeyCode> replayInput;
	protected ArrayList<KeyCode> loadedInputs;
	protected Canvas canvas;
	protected Board board;
	protected Food food;
	protected Snake snake;
	protected AnimationTimer replayTimer;
	protected Board replayBoard;
	protected Food replayFood;
	protected Snake replaySnake;
	protected Canvas replayCanvas;
	protected GraphicsContext replayGc;
	protected GraphicsContext gc;
	protected AnimationTimer timer;

	protected int frameLag;
	protected int nrows;
	protected int ncols;
	protected Label scoreValue = new Label();
	protected String recordedData = "\n";
	protected Text loggerText;
	protected Label logLabel;

	private int startRow;
	private int startCol;
	private Integer score;
	protected int canvasX;
	protected int canvasY;
	private int maxSnakeSize;
	private boolean saved = false;

	/**
	 * initializes the game window's size, density, and speed
	 * 
	 * @param canvasX  -max number of rows in the game window
	 * @param canvasY  - max number of columns in the game window
	 * @param nrows    - number of rows in the game
	 * @param ncols    - number of columns in the game
	 * @param frameLag - the game speed - the number of frames before the display is
	 *                 updated.
	 */
	public Game(int canvasX, int canvasY, int nrows, int ncols, Integer frameLag) {
		startRow = nrows / 2;
		startCol = ncols / 2;
		maxSnakeSize = nrows * ncols;

		// initialize command queue:
		input = new ArrayList<KeyCode>();
		// this creates the playable canvas area
		canvas = new Canvas(canvasX, canvasY);
		// set copy of initial parameters:
		this.canvasX = canvasX;
		this.canvasY = canvasY;
		this.nrows = nrows;
		this.ncols = ncols;
		this.frameLag = frameLag;

		// sizes:
		// initialize all players:
		board = new Board(this.nrows, this.ncols, canvasX, canvasY);
		food = new Food(board);
		snake = new Snake(board, food, startRow, startCol, maxSnakeSize, false);
		// set games graphics
		gc = canvas.getGraphicsContext2D();
		score = 0;

		scoreValue.setTextFill(Color.SILVER);
		scoreValue.setFont(new Font("Courier New", 30));

		// initialize data logger:
		loggerText = new Text(recordedData);
		logLabel = new Label("Data Log");

		logLabel.setFont(new Font("Courier New", 30));
		logLabel.setTextFill(Color.SILVER);
		loggerText.setFont(new Font("Courier New", 8));
		loggerText.setFill(Color.BLACK);
		loggerText.setText("Data will be displayed at \n the end of the game.");

	}

	/**
	 * starts the game within the gui
	 * 
	 * @param root   - the root of the gui
	 * @param pStage - the gui stage
	 * @param sc     - the scene containing the game
	 */
	public void start(BorderPane root, Stage pStage, Scene sc) {
		root.setCenter(canvas);
		score = 0;

		// initialize the control key event handler:
		sc.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				// do not add duplicate codes - only need one key code to change directions.
				if (!input.contains(code))
					input.add(code);
			}
		});

		// initialize timer with default settings:
		timer = new AnimationTimer() {

			int frames = 0;

			@Override
			public void handle(long currentNanoTime) {
				// only move the snake once every number of frames:
				if (frames % frameLag == 0) {
					if (input.size() > 0) {
						while (input.size() > 0) {
							snake.update(input.remove(0));
							updateLogString();
						}
					} else {
						snake.update();
						updateLogString();
					}
					// update the score:
					score = board.getScore();
					scoreValue.setText("   " + score.toString());
					if(snake.isDead()) {
						loggerText.setText(recordedData);
					}

				}
				board.display(gc);

				frames++;
			}

		};
		// start timer:
		timer.start();
	}

	/**
	 * updates the run log with more data.
	 */
	private void updateLogString() {
		// only log movements if snake is not dead:
		if (!snake.isDead()) {
			recordedData = recordedData + score.toString() + "_" + snake.toString();
		} else {
			logLabel.setText("DEAD!");
			if (!saved) {
				// if the snake is dead and the game has not been saved, save all the
				// recorded data
				Main.settings.logUserRun(recordedData);
				saved = true;
			}
		}
	}

	/**
	 * changes the frame rate of the game
	 * 
	 * @param inverseSpeed - the number of frames it takes to move the snake.
	 */
	public void changeSpeed(Integer inverseSpeed) {

		// stop timer:
		timer.stop();

		// update the class's frame rate variable.
		this.frameLag = inverseSpeed;

		// re initialize animation timer:
		timer = new AnimationTimer() {

			int frames = 0;

			@Override
			public void handle(long currentNanoTime) {
				// only move the snake once every number of frames:
				if (frames % frameLag == 0) {
					if (input.size() > 0) {
						while (input.size() > 0) {
							snake.update(input.remove(0));
							updateLogString();
						}
					} else {
						snake.update();
						updateLogString();
					}
					// update the score:
					score = board.getScore();
					if(snake.isDead()) {
						loggerText.setText(recordedData);
					}
					scoreValue.setText("   " + score.toString());
				}
				board.display(gc);

				frames++;
			}

		};
		// restart timer:
		timer.start();
	}

	/**
	 * gets the canvas displaying the game pieces
	 * 
	 * @return - the canvas containing the game pieces.
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * gets the score of the game.
	 * 
	 * @return the current score.
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * resets the game.
	 */
	protected void reset() {
		board = new Board(nrows, ncols, canvasX, canvasY);
		food = new Food(board);
		snake = new Snake(board, food, startRow, startCol, maxSnakeSize, false);
		// reset display string if the game is reset:
		changeSpeed(frameLag);
		recordedData = "\n";
	}

	/**
	 * resets the game with a new board size/ space density
	 * 
	 * @param nrows - new number of rows
	 * @param ncols - new number of columns.
	 */
	protected void reset(int nrows, int ncols) {
		board = new Board(nrows, ncols, canvasX, canvasY);
		food = new Food(board);
		snake = new Snake(board, food, startRow, startCol, maxSnakeSize, false);
		// reset display string if the game is reset:
		recordedData = "\n";
	}

	/**
	 * resets and replays saved games.
	 */
	protected void startReplay(BorderPane replayRoot, Stage replayStage, Scene replaySc) {

		int boardSize = Main.settings.getBoardSize();
		// initialize replay objects:
		replayBoard = new Board(boardSize, boardSize, canvasX, canvasY);
		replayFood = new Food(replayBoard, Main.settings.getLoadedFoodCoordinates());
		replaySnake = new Snake(replayBoard, replayFood, startRow, startCol, maxSnakeSize, true);

		// add colors:
		replayBoard.changeColors(Color.web(Main.settings.getUserSettings(3)),
				Color.web(Main.settings.getUserSettings(2)), Color.web(Main.settings.getUserSettings(4)));

		// load movement commands:
		loadedInputs = new ArrayList<>();
		ArrayList<String> directions = Main.settings.getLoadedDirections();
		// convert them into keycodes:
		for (int i = 0; i < directions.size(); i++) {
			loadedInputs.add(FileLogger.translateDirections((directions.get(i))));
		}
		// create new replay input list:
		replayInput = new ArrayList<KeyCode>();
		// create replay graphics content:
		replayCanvas = new Canvas(canvasX, canvasY);
		replayGc = replayCanvas.getGraphicsContext2D();

		startReplayTimer(replayRoot, replayStage, replaySc);
	}

	/**
	 * sets up game clock to use loaded commands rather than user keyboard
	 * 
	 * @param inverseSpeed - the new frame lag of the game.
	 */
	public void startReplayTimer(BorderPane replayRoot, Stage replayStage, Scene replaySc) {
		// reload log's frame rate, and start new replay timer with it:
		replayRoot.setCenter(replayCanvas);
		// pause main game timer:
		timer.stop();
		// initialize replay timer:
		replayTimer = new AnimationTimer() {

			int frames = 0;

			@Override
			public void handle(long currentNanoTime) {
				// only move the snake once every number of frames:

				if (frames % frameLag == 0) {
					if (loadedInputs.size() == 0) {
						// at the end of the replay, close window
						replayStage.close();
						// restart main game timer
						timer.start();
						// stop replay timer:
						replayTimer.stop();
					} else {
						// otherwise, pull a movement command off the saved list of commands:
						replaySnake.update(loadedInputs.remove(0));
					}
				}
				replayBoard.display(replayGc);
				frames++;
			}
		};
		replayTimer.start();
	}

}
