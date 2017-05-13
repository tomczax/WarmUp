package csp;

import java.util.ArrayList;

public class Field {
	private char value;
	ArrayList<Character> domain = new ArrayList<Character>();

	public Field(char value) {
		for (int i = 0; i < 26; i++) {
			this.domain.add((char) (('a') + i));
		}
		
		this.value = value;
	}

	public char getValue() {
		return this.value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public ArrayList<Character> getDomain() {
		return this.domain;
	}
}
