
public class Assignment02Q01 {

	public static void main(String[] args) {
		for (String str : args) {
			char c = str.charAt(0);
			if (c%5 == 0) {
				System.out.println(c);
			}
		}
	}

}
