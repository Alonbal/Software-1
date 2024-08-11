
public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = Integer.parseInt(args[0]);
		String str = "";
		if (n != 0) {
			
		int [] arr = new int[n];
		int prev = 0;
		int curr = 1;
		for (int i = 0; i < n; i++) {
			arr[i] = curr;
			if (curr % 2 == 1)
				numOfOdd += 1;
			curr += prev;
			prev = curr - prev;
		}
		for (int i = 0; i < n-1; i++) {
			str = str + arr[i] + " ";
		}
		str += arr[n-1];
		}
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		// *** your code goes here below ***
		
		
		System.out.println(str);
		System.out.println("The number of odd numbers is: "+numOfOdd);

	}

}
