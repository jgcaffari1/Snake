package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * the randomly generated food class.
 * 
 * @author jgcaf
 *
 */
public class Food {

	private Board board;
	private Cell location;
	private Random r;
	private int rows;
	private int cols;
	private ArrayList<String> loadedCoordinates = new ArrayList<>();

	/**
	 * initializes food in random locations on the playing board.
	 * 
	 * @param board - the playing board.
	 */
	public Food(Board board) {
		r = new Random();
		this.board = board;
		rows = board.getRows();
		cols = board.getCols();
		generateFood();
	}

	/**
	 * initializes a new food instance for replay mode
	 * 
	 * @param board             - the playing board
	 * @param loadedCoordinates - a list of predefined coordinates loaded from the
	 *                          save file.
	 */
	public Food(Board board, ArrayList<String> loadedCoordinates) {
		this.loadedCoordinates = loadedCoordinates;
		this.board = board;
		rows = board.getRows();
		cols = board.getCols();
		int[] coordiantes = parseIntFromLoadedCoordinates();
		generateOnSpace(coordiantes[0], coordiantes[1]);
	}

	/**
	 * parses the next set of coordinates from the save file into board coordinates
	 * to generate a new food object. This was created for use in replay mode.
	 * 
	 * @return the integer row, column coordinates of the new food being generated.
	 */
	public int[] parseIntFromLoadedCoordinates() {
		String nextCoord = this.loadedCoordinates.remove(0);
		return new int[] { Integer.parseInt(nextCoord.split(",")[0]), Integer.parseInt(nextCoord.split(",")[1]) };
	}

	/**
	 * gets the cell describing the location of the food on the board
	 * 
	 * @return the location of the food.
	 */
	public Cell getLocation() {
		return location;
	}

	/**
	 * randomly generates a new food object
	 */
	public void generateFood() {
		Cell space = getRandomCell();
		while (space.getLabel() == 'S') {
			space = getRandomCell();
		}
		space.setLabel('F');
		location = space;
	}

	/**
	 * randomly gets a cell from the board:
	 * 
	 * @return a random board space.
	 */
	public Cell getRandomCell() {
		int i = r.nextInt(cols + 1);
		int j = r.nextInt(rows + 1);
		return board.getSpace(i, j);
	}

	/**
	 * generates food on a specific space of cell- for recreating logged runs.
	 * 
	 * @param i - the row of the space being selected
	 * @param j - the column of the space being selected
	 */
	public void generateOnSpace(int i, int j) {
		Cell space = board.getSpace(i, j);
		space.setLabel('F');
		location = space;
	}

	/**
	 * generates the next food object from the loaded list of food coordinates.
	 * created for replay mode only.
	 */
	public void generateFromSave() {
		if (this.loadedCoordinates.size() > 0) {
			int[] coordiantes = parseIntFromLoadedCoordinates();
			generateOnSpace(coordiantes[0], coordiantes[1]);
		}
	}

	/**
	 * returns the coordinates in string form
	 */
	@Override
	public String toString() {
		return location.toString();
	}
}
