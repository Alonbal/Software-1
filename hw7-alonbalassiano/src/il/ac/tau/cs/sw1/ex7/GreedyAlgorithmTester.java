package il.ac.tau.cs.sw1.ex7;


import java.util.*;

public class GreedyAlgorithmTester {
    public static void main(String[] args){
        coinsTest();
        fksTest();
        mstTest();
        circleTest();
        System.out.println("done!");
    }
    public static void coinsTest(){
        isCorrect((new Coins(1)).greedyAlgorithm().equals(Arrays.asList(1)), "coins 1");
        isCorrect((new Coins(19)).greedyAlgorithm().equals(Arrays.asList(10,5,2,2)), "coins 2");
        isCorrect((new Coins(30)).greedyAlgorithm().equals(Arrays.asList(10,10,10)), "coins 3");
        isCorrect((new Coins(36)).greedyAlgorithm().equals(Arrays.asList(10,10,10,5,1)), "coins 4");
        isCorrect((new Coins(0)).greedyAlgorithm().equals(Arrays.asList()), "coins 5");
        isCorrect((new Coins(-1)).greedyAlgorithm() == null, "coins 6");
    }
    public static void fksTest(){
        FractionalKnapSack.Item s1 = new FractionalKnapSack.Item(10,60);
        FractionalKnapSack.Item s2 = new FractionalKnapSack.Item(20,100);
        FractionalKnapSack.Item s3 = new FractionalKnapSack.Item(30,120);
        FractionalKnapSack s = new FractionalKnapSack(50, Arrays.asList(s3,s1,s2));
        isCorrect(s.greedyAlgorithm().toString().equals("[{weight=10.0, value=60.0}, {weight=20.0, value=100.0}, {weight=20.0, value=80.0}]"), "fks");
        FractionalKnapSack sac = new FractionalKnapSack(20, Arrays.asList());
        isCorrect(sac.greedyAlgorithm().toString().equals("[]"), "empty fks");
    }
    public static void mstTest(){
        Graph.Edge s1 = new Graph.Edge(0,1,1);
        Graph.Edge s2 = new Graph.Edge(1,2,4);
        Graph.Edge s3 = new Graph.Edge(2,3,1);
        Graph.Edge s4 = new Graph.Edge(2,4,3);
        Graph.Edge s5 = new Graph.Edge(3,4,3);
        Graph.Edge s6 = new Graph.Edge(0,4,4);
        Graph s = new Graph(4, Arrays.asList(s1,s2,s3,s4,s5,s6));
        List<Graph.Edge> lst = s.greedyAlgorithm();
        List<Graph.Edge> sol1 = Arrays.asList(s1,s3,s5,s6);
        List<Graph.Edge> sol2 = Arrays.asList(s1,s3,s4,s2);
        List<Graph.Edge> sol3 = Arrays.asList(s1,s3,s4,s6);
        List<Graph.Edge> sol4 = Arrays.asList(s1,s3,s5,s2);
        if (lst.equals(sol3));
        else if (lst.equals(sol1) || lst.equals(sol2) || lst.equals(sol4))
            System.out.println("false (This is an MST, but not in the right order)");
        else
            System.out.println("false");
    }
    
    public static void circleTest() {
    	Graph.Edge s1 = new Graph.Edge(0,1,1);
        Graph.Edge s2 = new Graph.Edge(1,2,4);
        Graph.Edge s3 = new Graph.Edge(2,3,1);
        Graph.Edge s4 = new Graph.Edge(2,4,3);
        Graph.Edge s5 = new Graph.Edge(3,4,3);
        Graph.Edge s6 = new Graph.Edge(0,4,4);
        Graph s = new Graph(4, Arrays.asList(s1,s2,s3,s4,s5,s6));
        isCorrect((!s.feasibility(Arrays.asList(s1,s2,s3,s5), s6)), "Circle 1");
        isCorrect((s.feasibility(Arrays.asList(s1,s2,s3), s5)), "Circle 2");
        isCorrect((!s.feasibility(Arrays.asList(s3,s4), s5)), "Circle 3");
        isCorrect((s.feasibility(Arrays.asList(s2,s6,s4), s5)), "Circle 4");
        isCorrect((!s.feasibility(Arrays.asList(s1,s2,s4), s6)), "Circle 5");
        for (Iterator<Graph.Edge> iter = s.selection(); iter.hasNext();) {
        	Graph.Edge next = iter.next();
        	isCorrect((!s.feasibility(Arrays.asList(next), next)), "Circle 6");
        }
        
    }
    
    public static void isCorrect(boolean bool, String question) {
    	if (!bool) System.out.println("Error at question " + question);
    }
}
