package il.ac.tau.cs.sw1.hw6;

public class Polynomial {
	
	private double[] coefficients;
	
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
		this.coefficients = new double[1];
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		if (coefficients.length == 0) this.coefficients = new double[1];
		this.coefficients = coefficients;
	}
	
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		int deg1 = this.coefficients.length;
		int deg2 = polynomial.coefficients.length;
		int max = Integer.max(deg1, deg2);
		int min = Integer.min(deg1, deg2);
		double[] coefficient = new double[max];
		for (int i = 0; i < min; i++) {
			coefficient[i] = this.coefficients[i] + polynomial.coefficients[i];
		}
		if (deg1 > deg2) {
			for (int i = min; i < max; i ++)
				coefficient[i] = this.coefficients[i];
		}
		else if (deg2 > deg1) {
			for (int i = min; i < max; i ++)
				coefficient[i] = polynomial.coefficients[i];
		}
		return new Polynomial(coefficient);
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		double[] coefficient = new double[this.coefficients.length];
		for (int i = 0; i < coefficient.length; i++)
			coefficient[i] = a * this.coefficients[i];
		return new Polynomial(coefficient);
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		int deg = this.coefficients.length;
		for (int i = deg - 1; i >= 0; i--) {
			if (this.coefficients[i] != 0.0) {
				return i;
			}
		}
		return 0;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		if (n >= this.coefficients.length || n < 0) return 0.0;
		return this.coefficients[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		if (this.coefficients.length <= n) {
			double[] copy = new double[n+1];
			for (int i = 0; i < this.coefficients.length; i++) 
				copy[i] = this.coefficients[i];
			this.coefficients = copy;
		}
		this.coefficients[n] = c;
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		if (this.coefficients.length == 1) return new Polynomial();
		double[] derivative = new double[this.coefficients.length - 1];
		for (int i = 0; i < this.coefficients.length - 1; i++)
			derivative[i] = this.coefficients[i+1] * (double)(i+1);
		return new Polynomial(derivative);
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double sum = this.coefficients[0];
		for (int i = 1; i < this.coefficients.length; i++)
			sum += (this.coefficients[i] * pow(x, i));
		return sum;
	}
	
	public static double pow(double x, int n) {
		double res = 1.0;
		for (int i = 0; i < n; i++)
			res *= x;
		return res;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		Polynomial f = getFirstDerivation();
		return (f.computePolynomial(x) == 0) && (f.getFirstDerivation().computePolynomial(x) != 0);
	}
	
	
	
	

    
    

}
