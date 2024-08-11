package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		int len = word.length();
		char[] array = new char[len];
		for (int i = 0; i < len; i++) {
			if (template[i])
				array[i] = HIDDEN_CHAR;
			else
				array[i] = word.charAt(i);
		}
		return array;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		if (word.length() != template.length) return false;
		int len = template.length;
		if (isAllFalse(template) || isAllTrue(template)) return false;
		for (int i = 0; i < len-1; i++) {
			for (int j = i+1; j < len; j ++) {
				if (word.charAt(i) == word.charAt(j) && template[i] != template[j])
					return false;
			}
		}
		return true;
	}
	
	public static boolean isAllFalse(boolean[] array) {
		int n = array.length;
		for (int i = 0; i < n; i++) {
			if (array[i]) return false;
		}
		return true;
	}
	
	public static boolean isAllTrue(boolean[] array) {
		int n = array.length;
		for (int i = 0; i < n; i++) {
			if (!array[i]) return false;
		}
		return true;
	}
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		int len = word.length();
		int i = 0;
		int ind = 0;
		boolean[][] array = new boolean[binomial(len, k)][len];
		while (i < (1 << len)-1) {
			String str = Integer.toBinaryString(i);
			int cnt = 0;
			for (int j = 0; j < str.length(); j++) {
				if (str.charAt(j) == '1') 
					cnt += 1;
			}
			if (cnt != k) {
				i++;
				continue;
			}
			int dif = len - str.length();
			boolean[] template = new boolean[len];
			for (int j = 0; j < str.length(); j++) {
				if (str.charAt(j) == '1')
					template[dif + j] = true;
			}
			if (checkLegalTemplate(word, template)) {
				array[ind] = template;
				ind++;
			}
			i++;
		}
		boolean[][] newArr = new boolean[ind][len];
		for (int j = 0; j < ind; j++)
			newArr[j] = array[j];
		return newArr;
	}
	
	
	private static int factorial(int n) {
		if (n == 0) return 1;
		if (n < 0) return 0;
		return n*factorial(n-1);
	}
	
	private static int binomial(int n, int k) {
		if (n<k || k<0) return 0;
		return factorial(n)/(factorial(k)*factorial(n-k));
	}
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int cnt = 0;
		int len = puzzle.length;
		for (int i = 0; i < len; i++) {
			if (puzzle[i] == guess) return 0;
			if (puzzle[i] == HIDDEN_CHAR) {
				if (word.charAt(i) == guess) {
					cnt++;
					puzzle[i] = guess;
				}
			}
		}
		return cnt;
	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		char[] hint = new char[2];
		int i = 0;
		Random random = new Random();
		while (i < 1) {
			int randInt = random.nextInt(26);
			char maybeThis = (char)('a' + randInt);
			if (!contains(puzzle, maybeThis) && !already_guessed[randInt]) {
				hint[0] = maybeThis;
				i++;
			}
		}
		boolean bool = (word.indexOf(hint[0]) != -1);
		while (i < 2) {
			int randInt = random.nextInt(26);
			char maybeThis = (char)('a' + randInt);
			if (!contains(puzzle, maybeThis) && !already_guessed[randInt] && ((word.indexOf(maybeThis) == -1) == bool)) {
				hint[1] = maybeThis;
				i++;
			}
		}
		return hint;
	}

	private static boolean contains(char[] array, char chr) {
		int n = array.length;
		for (int i = 0; i < n; i++) {
			if (array[i] == chr) return true;
		}
		return false;
	}
	

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		System.out.println("--- Settings stage ---");
		boolean[] template = new boolean[word.length()];
		System.out.println("Choose a (1) random or (2) manual template:");
		while (inputScanner.hasNext()) {
			int choice = inputScanner.nextInt();
			if (choice == 1) {
				System.out.println("Enter number of hidden characters:");
				int next = inputScanner.nextInt();
				boolean[][] allTemplates = getAllLegalTemplates(word, next);
				if (allTemplates.length != 0) {
					template = allTemplates[(new Random()).nextInt(allTemplates.length)];
					break;
				}
				System.out.println("Cannot generate puzzle, try again.");
				System.out.println("Choose a (1) random or (2) manual template:");
			}
			else {
				System.out.println("Enter your puzzle template:");
				String[] input = inputScanner.next().split(",");
				template = new boolean[input.length];
				for (int i = 0; i < template.length; i++) {
					if (input[i].equals("X"))
						template[i] = false;
					else template[i] = true;
				}
				if (checkLegalTemplate(word, template))
					break;
				System.out.println("Cannot generate puzzle, try again.");
				System.out.println("Choose a (1) random or (2) manual template:");
			}
		}
		
		return createPuzzleFromTemplate(word, template);
	}
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		System.out.println("--- Game stage ---");
		boolean[] already_guessed = new boolean[26];
		int cnt = 3;
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] == HIDDEN_CHAR) cnt++;
		}
		System.out.println(puzzle);
		System.out.println("Enter your guess:");
		while (inputScanner.hasNext()) {
			char guess = inputScanner.next().charAt(0);
			if (guess == 'H') {
				char[] hint = getHint(word, puzzle, already_guessed);
				System.out.println("Here's a hint for you: choose either " + 
				(char)Integer.min(hint[0], hint[1]) + " or " + (char)Integer.max(hint[0], hint[1]) + ".");
				System.out.println(puzzle);
				System.out.println("Enter your guess:");
			}
			else {
				already_guessed[guess - 'a'] = true;
				if (applyGuess(guess, word, puzzle) > 0) {
					if (!contains(puzzle, HIDDEN_CHAR)) {
						System.out.println("Congratulations! You solved the puzzle!");
						return;
					}
					cnt--;
					System.out.println("Correct guess, " + cnt + " guesses left.");
					if (cnt > 0) {
						System.out.println(puzzle);
						System.out.println("Enter your guess:");
						continue;
					}
					break;
				}
				else {
					cnt--;
					System.out.println("Wrong guess, " + cnt + " guesses left.");
					if (cnt > 0) {
						System.out.println(puzzle);
						System.out.println("Enter your guess:");
						continue;
					}
					break;
				}
			}
		}
		System.out.println("Game over!");	
	}
				
				


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
