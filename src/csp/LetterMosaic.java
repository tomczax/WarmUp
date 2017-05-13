package csp;

public class LetterMosaic {
	public static void main(String[] args) {
		Board b = new Board(10, 10);
//		b.forwardChecking();
		
		long startTime = System.nanoTime();
		b.recursiveBacktracking();
		long endTime = System.nanoTime();

		long duration = (endTime - startTime)/100000; //to get milliseconds.
		
		b.displayBoard();
		System.out.println(duration);
	}
}


