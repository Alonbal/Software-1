package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	// add members here
	public Map<T, Integer> map;
	public int size;
	
	//add constructor here, if needed

	
	public HashMapHistogram(){
		map = new HashMap<>();
		size = 0;
	}
	
	@Override
	public void addItem(T item) {
		if (map.containsKey(item)) map.put(item, 1 + map.get(item));
		else {
			map.put(item, 1);
			size += 1;
		}
	}
	
	@Override
	public boolean removeItem(T item)  {
		if (!map.containsKey(item)) return false;
		if (map.get(item) == 1) { 
			map.remove(item);
			size -= 1;
		}
		else map.put(item, map.get(item) - 1);
		return true;
		
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for (Iterator<T> it = items.iterator(); it.hasNext();)
			this.addItem(it.next());
	}

	@Override
	public int getCountForItem(T item) {
		if (!map.containsKey(item)) return 0;
		return map.get(item); 
	}

	@Override
	public void clear() {
		map = new HashMap<>();
		size = 0;
	}

	@Override
	public Set<T> getItemsSet() {
		return map.keySet();
	}
	
	@Override
	public int getCountsSum() {
		int sum = 0;
		for (Integer val : map.values()) sum += val;
		return sum;
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<T>(map);
	}
	
	//add private methods here, if needed
}
