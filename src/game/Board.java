package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * the game board - also contains methods that control how all game pieces are
 * displayed.
 * 
 * @author jgcaf
 *
 */
public class Board {

	private Cell[][] grid;
	private int rows;
	private int cols;
	private Integer score = 0;

	protected Color foodRGB;
	protected Color snakeRGB;
	protected Color boardRGB;

	/**
	 * initializes a new board instance.
	 * 
	 * @param nrows      - total number of rows
	 * @param ncols      - total number of columns
	 * @param windHeight - board height in px
	 * @param winWidth   - board width in px
	 */
	public Board(int nrows, int ncols, int windHeight, int winWidth) {
		this.rows = nrows;
		this.cols = ncols;

		int dX = winWidth / ncols;
		int dY = winWidth / nrows;

		score = 0;

		this.grid = new Cell[cols][rows];

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				// Initialize each object
				grid[i][j] = new Cell(i * dX, j * dY, dX, dY, i, j, foodRGB, snakeRGB, boardRGB);
			}
		}
	}

	/**
	 * displays the cells of the board
	 * 
	 * @param g - the graphics context for displaying the cells from the board.
	 */
	public void display(GraphicsContext g) {
		// The counter variables i and j are also the column and row numbers and
		// are used as arguments to the constructor for each object in the grid.
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				// Oscillate and display each object
				grid[i][j].display(g);
			}
		}
	}

	/**
	 * gets a space from the board
	 * 
	 * @param i - row index
	 * @param j - column index
	 * @return the specified space from the board.
	 */
	public Cell getSpace(int i, int j) {
		// wrap index around:
		i = i % cols;
		if (i < 0) {
			i = cols + i;
		}
		j = j % rows;
		if (j < 0) {
			j = rows + j;
		}
		return grid[i][j];
	}

	/**
	 * 
	 * @return the number of columns
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * 
	 * @return the number of rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * increases the score by one point.
	 */
	public void incrementScore() {
		score = score + 1;
	}

	/**
	 * 
	 * @return the score.
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * changes the colors of the game components
	 * 
	 * @param boardRGB - the new board color
	 * @param snakeRGB - the new snake color
	 * @param foodRGB  -the new food color
	 */
	public void changeColors(Color boardRGB, Color snakeRGB, Color foodRGB) {
		this.foodRGB = foodRGB;
		this.snakeRGB = snakeRGB;
		this.boardRGB = boardRGB;
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j].setColors(boardRGB, snakeRGB, foodRGB);
			}
		}
	}

}
