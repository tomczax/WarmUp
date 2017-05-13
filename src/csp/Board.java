package csp;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class Board {
	private Field[][] board;
	private int width;
	private int height;
	private char defaultValue = '\0';

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		board = new Field[height][width];
		// fill with default values: 0
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++)
				board[r][c] = new Field(defaultValue);
		}
	}

	private int[] chooseUnasignedField() {
		int[] coordinates = { -1, -1 };
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++)
				if (board[r][c].getValue() == defaultValue) {
					coordinates[0] = r;
					coordinates[1] = c;
					return coordinates;
				}
		}
		return coordinates;
	}

	public boolean forwardChecking() {
		int[] cellIndex = chooseUnasignedField();
		Field[][] knownVariables;
		// if assignment is complete
		if (cellIndex[0] == -1)
			return true;
//		else return knownVariables;
		
		int row = cellIndex[0];
		int column = cellIndex[1];
		Field currentVariable = this.board[cellIndex[0]][cellIndex[1]];
		ArrayList<Character> currentDomain = currentVariable.getDomain();
//		int row = cellIndex[0];
//		int column = cellIndex[1];
		// for each value in domain
		for (int i = 0; i < currentVariable.getDomain().size(); i++) {
			// is consistent
			if (checkConstraints(currentVariable.getDomain().get(i), cellIndex)) {
				// assign a value to the variable
				currentVariable.setValue(currentVariable.getDomain().get(i));
				// if result is not a failure
				
				if (recursiveBacktracking()) {
					return true;
				}
				currentVariable.setValue(defaultValue);
			} else {continue; } //not all constraints are satisfied -> test another value
		}
		System.out.println("The assignment is not possible");
		return false;
//		// for each variable X
//		for (int row = 0; row < height; row++) {
//			// for each variable connected to X
//			for (int column = 0; column < width; column++) {
//				char value = board[row][column].getValue();
//				checkConstraintsAndModifyDomain(row, column, value);
//			}
//		}
//		return false;

	}

	public boolean recursiveBacktracking() {

		int[] cellIndex = chooseUnasignedField();
		// if assignment is complete
		if (cellIndex[0] == -1)
			return true;
		
		Field variable = this.board[cellIndex[0]][cellIndex[1]];
//		int row = cellIndex[0];
//		int column = cellIndex[1];
		// for each value in domain
		for (int i = 0; i < variable.getDomain().length; i++) {
			// is consistent
			if (checkConstraints(variable.getDomain()[i], cellIndex)) {
				// assign a value to the variable
				variable.setValue(variable.getDomain()[i]);
				// if result is not a failure
				
				if (recursiveBacktracking()) {
					return true;
				}
				variable.setValue(defaultValue);
			}
		}
		System.out.println("The assignment is not possible");
		return false;

	}

	private void checkConstraintsAndModifyDomain(int row, int column, char value) {
		if (row == 0) {
			this.board[row + 1][column].domain = ArrayUtils.removeElement(this.board[row + 1][column].domain, value);

			if (column == 0) {
				this.board[row][column + 1].domain = ArrayUtils.removeElement(this.board[row][column + 1].domain,
						value);
			} else if (column == width - 1) {
				this.board[row][column - 1].domain = ArrayUtils.removeElement(this.board[row][column - 1].domain,
						value);
			} else {
				this.board[row][column - 1].domain = ArrayUtils.removeElement(this.board[row][column - 1].domain,
						value);
				this.board[row][column + 1].domain = ArrayUtils.removeElement(this.board[row][column + 1].domain,
						value);
			}
		} else if (row == height - 1) {
			this.board[row - 1][column].domain = ArrayUtils.removeElement(this.board[row - 1][column].domain, value);

			if (column == 0) {
				this.board[row][column + 1].domain = ArrayUtils.removeElement(this.board[row][column + 1].domain,
						value);
			} else if (column == width - 1) {
				this.board[row][column - 1].domain = ArrayUtils.removeElement(this.board[row][column - 1].domain,
						value);
			} else {
				this.board[row][column - 1].domain = ArrayUtils.removeElement(this.board[row][column - 1].domain,
						value);
				this.board[row][column + 1].domain = ArrayUtils.removeElement(this.board[row][column + 1].domain,
						value);
			}
		} else if (column == 0) {
			this.board[row - 1][column].domain = ArrayUtils.removeElement(this.board[row - 1][column].domain, value);
			this.board[row + 1][column].domain = ArrayUtils.removeElement(this.board[row + 1][column].domain, value);
			this.board[row][column + 1].domain = ArrayUtils.removeElement(this.board[row][column + 1].domain, value);
		} else if (column == width - 1) {
			this.board[row][column - 1].domain = ArrayUtils.removeElement(this.board[row][column - 1].domain, value);
			this.board[row - 1][column].domain = ArrayUtils.removeElement(this.board[row - 1][column].domain, value);
			this.board[row + 1][column].domain = ArrayUtils.removeElement(this.board[row + 1][column].domain, value);
		} else {
			this.board[row - 1][column].domain = ArrayUtils.removeElement(this.board[row - 1][column].domain, value);
			this.board[row + 1][column].domain = ArrayUtils.removeElement(this.board[row + 1][column].domain, value);
			this.board[row][column - 1].domain = ArrayUtils.removeElement(this.board[row][column - 1].domain, value);
			this.board[row][column + 1].domain = ArrayUtils.removeElement(this.board[row][column + 1].domain, value);

		}
	}

	private boolean checkConstraints(char value, int[] index) {
		int row = index[0];
		int column = index[1];
		if (row == 0) {
			if (column == 0) {
				if (this.board[row + 1][column].getValue() == value
						|| this.board[row][column + 1].getValue() == value) {
					return false;
				}
			} else if (column == width - 1) {
				if (this.board[row + 1][column].getValue() == value
						|| this.board[row][column - 1].getValue() == value) {
					return false;
				}
			} else {
				if (this.board[row + 1][column].getValue() == value || this.board[row][column - 1].getValue() == value
						|| this.board[row][column + 1].getValue() == value) {
					return false;
				}
			}

		} else if (row == height - 1) {
			if (column == 0) {
				if (this.board[row - 1][column].getValue() == value
						|| this.board[row][column + 1].getValue() == value) {
					return false;
				}
			} else if (column == width - 1) {
				if (this.board[row - 1][column].getValue() == value
						|| this.board[row][column - 1].getValue() == value) {
					return false;
				}
			} else {
				if (this.board[row - 1][column].getValue() == value || this.board[row][column - 1].getValue() == value
						|| this.board[row][column + 1].getValue() == value) {
					return false;
				}
			}
		} else if (column == 0) {
			if (this.board[row - 1][column].getValue() == value || this.board[row + 1][column].getValue() == value
					|| this.board[row][column + 1].getValue() == value) {
				return false;
			}
		} else if (column == width - 1) {
			if (this.board[row - 1][column].getValue() == value || this.board[row + 1][column].getValue() == value
					|| this.board[row][column - 1].getValue() == value) {
				return false;
			}
		} else {
			if (this.board[row - 1][column].getValue() == value || this.board[row + 1][column].getValue() == value
					|| this.board[row][column - 1].getValue() == value
					|| this.board[row][column + 1].getValue() == value) {
				return false;
			}
		}
		return true;
	}

	public void displayBoard() {
		for (int r = 0; r < this.height; r++) {
			for (int c = 0; c < this.width; c++) {
				System.out.print(board[r][c].getValue());
			}
			System.out.println();
		}
	}
}
