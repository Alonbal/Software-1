package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;
    

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
                
    }
    
    

    @Override
    public Iterator<Item> selection() {
    	lst.sort((item1, item2) -> Double.compare(item1.weight/item1.value, item2.weight/item2.value));
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
    	return currCap(candidates_lst) < (double) capacity;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
    	if (currCap(candidates_lst) > capacity) {
    		Item deleted = candidates_lst.get(candidates_lst.size() - 1);
    		candidates_lst.remove(candidates_lst.size() - 1);
    		double dif = (double) capacity - currCap(candidates_lst);
    		Item inserted = new Item(dif, dif*deleted.value/deleted.weight);
    		candidates_lst.add(inserted);
    	}
    	return (lst.size() == candidates_lst.size() || currCap(candidates_lst) == capacity);
    }
    
    
    public static double currCap(List<Item> candidates_lst) {
    	double cap = 0.0;
        for (int j = 0; j < candidates_lst.size(); j++)
        	cap += candidates_lst.get(j).weight;
        return cap;
    }
    
}
