package il.ac.tau.cs.sw1.ex9.riddles.forth;

import java.util.Iterator;

public class B4 implements Iterator<String> {
	
	public int i = 0;
	public String[] strings;
	public int k;
	
	public B4(String[] strings, int k) {
		this.strings = strings;
		this.k = k;
	}

	@Override
	public boolean hasNext() {
		return i < strings.length * k;
	}

	@Override
	public String next() {
		i++;
		return strings[(i-1)%strings.length];
	}
	
	
}
