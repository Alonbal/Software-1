import java.util.Arrays;

public class ArrayUtils {

	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		if (array.length == 0 || !(direction == 'L' || direction == 'R'))
			return array;
		if (move < 0) {
			move = (-1) * move;
			if (direction == 'L')
				direction = 'R';
			else
				direction = 'L';
		}
		for (int i = 0; i < move % array.length; i++) {
			shiftOneCycle(array, direction);
		}
		return array; 

	}
	
	private static int[] shiftOneCycle(int[] array, char direction) {
		if (direction == 'R') {
			int last = array[array.length - 1];
			for (int i = array.length - 1; i > 0; i--) {
				array[i] = array[i-1];
			}
			array[0] = last;
			return array;
		}
		else {
			int first = array[0];
			for (int i = 0; i < array.length - 1; i++) {
				array[i] = array[i+1];
			}
			array[array.length - 1] = first;
			return array;
		}
	}


	public static int findShortestPath(int[][] m, int i, int j) {
		if (i == j)
			return 0;
		int dim = m.length;
		int cnt = Integer.MAX_VALUE;
		if (m[i].equals(new int[dim]))
			return -1;
		int[][] copy = new int[dim][dim];
		for (int k = 0; k < dim; k++) { // making a copy
			copy[k] = Arrays.copyOf(m[k], dim);
			copy[k][i] = 0;
		}
		for (int k = 0; k < dim; k++) {
			if (copy[i][k] == 1) {
				copy[i][k] = 0;
				int a = findShortestPath(copy, k, j);
				if (a >= 0)
					cnt = Integer.min(cnt, 1 + a);
				}
				
			}
		
		if (cnt > dim)
			cnt = -1;
		return cnt;

	}
	
	

}
