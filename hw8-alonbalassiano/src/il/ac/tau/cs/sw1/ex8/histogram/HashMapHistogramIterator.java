package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> 
							implements Iterator<Map.Entry<T, Integer>>{
	
	//add members here
	Map<T, Integer> map;
	List<T> L = new ArrayList<>();
	int index = 0;
	//add constructor here, if needed
	public HashMapHistogramIterator(Map<T, Integer> mapInput) {
		map = mapInput;
		for (T item : map.keySet()) L.add(item);
		L.sort(null);
	}
	
	@Override
	public boolean hasNext() {
		return index < L.size();
	}

	@Override
	public Map.Entry<T, Integer> next() {
		index++; 
		return Map.entry(L.get(index-1), map.get(L.get(index-1)));
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	
	//add private methods here, if needed
}
