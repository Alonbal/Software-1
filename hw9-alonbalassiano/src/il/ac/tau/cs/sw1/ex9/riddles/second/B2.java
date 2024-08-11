package il.ac.tau.cs.sw1.ex9.riddles.second;

public class B2 {
	
	
	public A2 getA(boolean bool) {
		if (bool) {
			class D2 extends A2 {
				@Override
				public String foo(String s) {
					return s.toUpperCase();
				}
			}
			return new D2();
		}
		return new A2();
	}
}
