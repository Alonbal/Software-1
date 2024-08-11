package il.ac.tau.cs.sw1.ex7;
import java.util.*;


public class Graph implements Greedy<Graph.Edge>{
    List<Edge> lst; //Graph is represented in Edge-List. It is undirected. Assumed to be connected.
    int n; //nodes are in [0,..., n]

    Graph(int n1, List<Edge> lst1){
        lst = lst1;
        n = n1;
    }

    public static class Edge implements Comparable<Edge> {
        int node1, node2;
        double weight;

        Edge(int n1, int n2, double w) {
            node1 = n1;
            node2 = n2;
            weight = w;
        }

        @Override
        public String toString() {
            return "{" + "(" + node1 + "," + node2 + "), weight=" + weight + '}';
        }
        
        @Override
        public int compareTo(Edge other) {
        	if (weight != other.weight) return Double.compare(weight, other.weight);
        	if (node1 != other.node1) return Double.compare((double)node1, (double)other.node1);
        	return Double.compare((double) node2, (double) other.node2);
        }
        
    }
    
    
    @Override
    public Iterator<Edge> selection() {
    	lst.sort(null);
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Edge> candidates_lst, Edge element) {
        return !circle(candidates_lst, element.node1, element.node2);
    }

    @Override
    public void assign(List<Edge> candidates_lst, Edge element) {
        candidates_lst.add(element);
    }

    @Override
    public boolean solution(List<Edge> candidates_lst) {
        return candidates_lst.size() == n;
    }
    
    public static boolean circle(List<Edge> L, int start, int end) {
    	if (start == end) return true;
    	for (int i = 0; i < L.size(); i ++) {
    		List<Edge> copy = new ArrayList<Edge>();
    		copy.addAll(L);
    		copy.remove(i);
    		if (end == L.get(i).node1) {
    			if (circle(copy, start, L.get(i).node2))
    				return true;
    		}
    		if (end == L.get(i).node2) {
    			if (circle(copy, start, L.get(i).node1))
    				return true;
    		}
    	}
    	return false;
    }
    
}
