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

/**
 * class describing position information
 * 
 * @author jgcaf
 *
 */
public class Coordinate {

	private final int x;
	private final int y;
	private final int i;
	private final int j;

	/**
	 * initializes coordinates with only pixel positions
	 * 
	 * @param x - x pixel coordinate
	 * @param y - y pixel coordinate
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		i = 0;
		j = 0;
	}

	/**
	 * initializes coordinate relative to its position in a matrix.
	 * 
	 * @param x - x pixel coordinate
	 * @param y - y pixel coordinate
	 * @param i - array row coordinate
	 * @param j - array column coordinate
	 */
	public Coordinate(int x, int y, int i, int j) {
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x coordinate of the cell
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return the y coordinate of the cell
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return - the row index of the current cell within a larger array structure
	 */
	public int getI() {
		return i;
	}

	/**
	 * @return - the column index of the cell in a larger array structure.
	 */
	public int getJ() {
		return j;
	}

	/**
	 * returns the string representation of the coordinates.
	 */
	@Override
	public String toString() {
		return (Integer.toString(i) + "," + Integer.toString(j));
	}

	/**
	 * checks if the coordinates are equal/ occupy the same space
	 * 
	 * @param coordinate - the coordinate being compared.
	 * @return true if the coordinates are equal.
	 */
	public boolean equals(Coordinate coordinate) {
		return (this.i == coordinate.getI())
				& (this.j == coordinate.getJ() & (this.x == coordinate.getX()) & (this.y == coordinate.getY()));
	}
}
