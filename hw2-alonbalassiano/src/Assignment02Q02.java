

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		int lim = Integer.parseInt(args[0]);
		// *** your code goes here below ***
		for (int i = 0 ; i < lim ; i++) {
			double sign = i%2 == 0 ? 1 : -1;
			piEstimation += (4.0/((double)(2*i+1)))*sign;
		}
		
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}
