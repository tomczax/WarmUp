package csp;

public class Field {
	private char value;
	char[] domain = new char[26];

	public Field(char value) {
		for (int i = 0; i < 26; i++) {
			this.domain[i] = (char) (('a') + i);
		}
		
		this.value = value;
	}

	public char getValue() {
		return this.value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public char[] getDomain() {
		return this.domain;
	}
}
