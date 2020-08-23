package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Encapsulates the properties of the individual grid spaces on the board.
 * 
 * @author Joe Caffarini
 *
 */
public class Cell {
	// A cell object knows about its location in the grid
	public Coordinate coordinate;
	private double w, h; // width and height
	private char label = 'N';
	private Color foodRGB;
	private Color snakeRGB;
	private Color boardRGB;

	/**
	 * initializes cell for general use.
	 * 
	 * @param tempX - the x coordinate of the cell (upper left corner)
	 * @param tempY - the y coordinate of the cell (upper left corner)
	 * @param tempW - the width of the cell
	 * @param tempH - the height of the cell
	 */
	public Cell(int tempX, int tempY, int tempW, int tempH) {
		// x = tempX;
		// y = tempY;
		coordinate = new Coordinate(tempX, tempY);
		w = tempW;
		h = tempH;

	}

	/**
	 * initializes cell for use in a board/ array
	 * 
	 * @param tempX    - the x coordinate of the cell (upper left corner)
	 * @param tempY    - the y coordinate of the cell (upper left corner)
	 * @param tempW    - the width of the cell
	 * @param tempH    - the height of the cell
	 * @param foodRGB  - the food color code
	 * @param snakeRGB - the snake color code
	 * @param boardRGB -the board color code
	 */
	public Cell(int tempX, int tempY, int tempW, int tempH, Color foodRGB, Color snakeRGB, Color boardRGB) {
		this.foodRGB = foodRGB;
		this.snakeRGB = snakeRGB;
		this.boardRGB = boardRGB;

		coordinate = new Coordinate(tempX, tempY);
		w = tempW;
		h = tempH;

	}

	/**
	 * initializes cell for use in a board/ array
	 * 
	 * @param tempX    - the x coordinate of the cell (upper left corner)
	 * @param tempY    - the y coordinate of the cell (upper left corner)
	 * @param tempW    - the width of the cell
	 * @param tempH    - the height of the cell
	 * @param i        - the cell's row index in an array
	 * @param j        - the cells column index in an array
	 * @param foodRGB  - the food color code
	 * @param snakeRGB - the snake color code
	 * @param boardRGB -the board color code
	 */
	public Cell(int tempX, int tempY, int tempW, int tempH, int i, int j, Color foodRGB, Color snakeRGB,
			Color boardRGB) {
		this.foodRGB = foodRGB;
		this.snakeRGB = snakeRGB;
		this.boardRGB = boardRGB;

		coordinate = new Coordinate(tempX, tempY, i, j);
		w = tempW;
		h = tempH;
	}

	// TODO add in a slide bar that edits the colors of these components
	// create an array that contains all these colors, then create a method that
	// allows for them to be changed.
	/**
	 * display the individual cell
	 * 
	 * @param g - the graphics context object for displaying the cell.
	 */
	public void display(GraphicsContext g) {
		// sets gridline color:
		g.setFill(boardRGB);
		g.setStroke(boardRGB);
		if (this.label == 'N') {
			g.setFill(boardRGB);
			g.setStroke(boardRGB);
		} else if (this.label == 'S') {
			g.setFill(snakeRGB);
			g.setStroke(snakeRGB);
		} else if (this.label == 'F') {
			g.setFill(foodRGB);
			g.setStroke(foodRGB);
		}
		g.fillRect(coordinate.getX(), coordinate.getY(), w, h);
	}

	/**
	 * 
	 * @return the coordinates of the cell.
	 */
	public Coordinate getCoordinates() {
		return coordinate;
	}

	/**
	 * checks if the current cells have the same coordinates.
	 * 
	 * @param cell - cell being compared
	 * @return true if they occupy the same coordinates.
	 */
	public boolean equals(Cell cell) {
		return this.getCoordinates().equals(cell.getCoordinates());
	}

	/**
	 * 
	 * @return the label of the cell - meant for use with snake game
	 */
	public char getLabel() {
		return this.label;
	}

	/**
	 * sets the label of the cell
	 * 
	 * @param newLabel - the new label of the cell
	 */
	public void setLabel(char newLabel) {
		this.label = newLabel;
	}

	/**
	 * returns the string representation of this cell
	 */
	@Override
	public String toString() {
		return coordinate.toString();
	}

	public void setColors(Color board, Color snake, Color food) {
		boardRGB = board;
		snakeRGB = snake;
		foodRGB = food;
	}

}