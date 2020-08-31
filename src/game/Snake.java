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

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

/**
 * the user controlled snake.
 * 
 * @author jgcaf
 *
 */
public class Snake {

	// method to move the head of the snake Down = 40, Right = 39, Up = 38, Left =
	// 37
	private KeyCode direction = KeyCode.W;
	private KeyCode previousDirection = KeyCode.S;
	private boolean dead = false;
	private int maxSize;
	private int tail = 0;
	private boolean replayMode;
	ArrayList<Cell> body = new ArrayList<Cell>();
	Board board;
	Food food;

	/**
	 * initialize snake
	 * 
	 * @param board   - board indicating the playing field
	 * @param food    - object containing food
	 * @param startI  - starting row of snake head
	 * @param startJ  - starting column
	 * @param maxSize - max size of snake
	 */
	public Snake(Board board, Food food, int startI, int startJ, int maxSize, boolean replayMode) {
		this.board = board;
		this.food = food;
		// initialize the snake's head
		body.add(board.getSpace(startI, startJ));
		// mark space as containing the snake
		body.get(0).setLabel('S');
		this.maxSize = maxSize;
		this.replayMode = replayMode;
	}

	/**
	 * updates state of snake - moves and draws snake.
	 * 
	 * @param keyCode - the key being pressed
	 */
	public void update(KeyCode keyCode) {
		// only change direction if it is not opposite the current direction
		// prevents snake from reversing over itself:
		if ((KeyCode.W == keyCode | KeyCode.S == keyCode | KeyCode.A == keyCode | KeyCode.D == keyCode)
				& keyCode != this.previousDirection) {
			// only allow a change in direction if the head does not move backwards -
			// prevent inverting.
			this.direction = keyCode;
		}
		// move in specified direction
		move();
		// updates the opposite direction:
		oppositeDirection();
	}

	/**
	 * maintains moving the snake in its current direction.
	 */
	public void update() {
		// move in specified direction
		move();
		// updates the opposite direction:
		oppositeDirection();
	}

	/**
	 * sets the direction opposite to the current direction.
	 */
	private void oppositeDirection() {
		switch (this.direction) {
		case W: {
			this.previousDirection = KeyCode.S;
			break;
		}
		case S: {
			this.previousDirection = KeyCode.W;
			break;
		}
		case A: {
			this.previousDirection = KeyCode.D;
			break;
		}
		case D: {
			this.previousDirection = KeyCode.A;
			break;
		}
		default:
			// do not change direction of no key is pressed.
			break;
		}
	}

	/**
	 * gets the direction of the snake
	 * 
	 * @return direction of the snake
	 */
	public KeyCode getDirection() {
		return this.direction;
	}

	/**
	 * moves the snake and logs the movements if needed.
	 */
	public void move() {
		// only move if snake is not dead:
		if (!dead & this.body.size() < this.maxSize) {
			int i = this.body.get(0).getCoordinates().getI();
			int j = this.body.get(0).getCoordinates().getJ();
			switch (this.direction) {
			case S: {
				slither(i, j + 1);
				break;
			}
			case D: {
				slither(i + 1, j);
				break;
			}
			case W: {
				slither(i, j - 1);
				break;
			}
			case A: {
				slither(i - 1, j);
				break;
			}
			default:
				// do not change direction if no key is pressed.
				break;
			}
		}
	}

	/**
	 * method for handling the snake's movement
	 * 
	 * @param i - the row of the space being moved to
	 * @param j - the column of the space being moved to.
	 */
	private void slither(int i, int j) {
		if (board.getSpace(i, j).getLabel() == 'F') {
			// if a food space, grow the queue
			this.body.add(0, board.getSpace(i, j));
			this.body.get(0).setLabel('S');
			if (!replayMode) {
				food.generateFood();
			} else {
				food.generateFromSave();
			}
			board.incrementScore();
		} else if (board.getSpace(i, j).getLabel() == 'S') {
			// stop moving if dead.
			dead = true;
		} else {
			// if the snake is not dead, then add space to front of queue, and remove the
			// last one:
			this.body.add(0, board.getSpace(i, j));
			this.body.get(0).setLabel('S');
			int end = this.body.size() - 1;
			this.body.get(end).setLabel('N');
			this.body.remove(end);
		}
		// update tail:
		tail = this.body.size() - 1;
	}

	/**
	 * returns the string representation of the snake's body
	 */
	@Override
	public String toString() {
		return (this.direction.toString() + "_" + food.toString() + "_" + body.get(0).toString() + "_"
				+ body.get(tail).toString() + "\n");
	}

	/**
	 * checks if the snake has died
	 * 
	 * @return true if the snake is dead, false otherwise.
	 */
	public boolean isDead() {
		return dead;

	}

}
