

public class Assignment1 {
	public static void main(String[] args){
		char chr = args[0].charAt(0);
		int offset = Integer.valueOf(args[1]);
		char newChar = (char)(chr + offset);
		System.out.println("New char is " + newChar + ".");
	}
}