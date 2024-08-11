
public class StringUtils {

	public static String findSortedSequence(String str) {
		String[] array = str.split(" ");
		if (array.length <= 1)
			return str;
		int cnt = 0;
		int max = 0;
		int ind = 0;
		for (int i = 0; i < array.length - 1; i++) {
			if (lexiOrder(array[i], array[i+1])) 
				cnt += 1;
			else {
				if (cnt >= max) { 
					max = cnt;
					ind = i;
				}
				cnt = 0;
			}
		}
		if (max <= cnt) {
			max = cnt;
			ind = array.length - 1;
		}
		cnt = ind - max;
		String newStr = array[cnt];
		while (cnt < ind) {
			cnt += 1;
			newStr = newStr + " " + array[cnt];
		}
		return newStr; //Replace this with the correct returned value

	}
	
	private static boolean lexiOrder(String str1, String str2) {
		if (str1.equals("") || str2.equals(""))
			return str1.equals("");
		int len1 = str1.length();
		int len2 = str2.length();
		int i = 0;
		while (i < len1 && i < len2) {
			if (str1.charAt(i) == str2.charAt(i))
				i += 1;
			else return str1.charAt(i) < str2.charAt(i);
		}
		return len1 <= len2;
	}
	
	public static boolean isEditDistanceOne(String a, String b){
		int dif = a.length() - b.length();
		if (dif*dif > 2)
			return false;
		if (dif == 0) {
			int i = 0;
			while (i < a.length()) {
				if (a.charAt(i) == b.charAt(i)) {
					i += 1;
				}
				else {
					i += 1;
					dif += 1;
					if (dif == 2) return false;
				}
			}
			return true;
		}
		if (dif == 1) {
			int i = 0;
			while (i < b.length()) {
				if (a.charAt(i) == b.charAt(i)) 
					i += 1;
				else break;
			}
			while (i < b.length()) {
				if (a.charAt(i+1) == b.charAt(i))
					i += 1;
				else return false;
			}
			return true;
		}
			
		
		else {
			int i = 0;
			while (i < a.length()) {
				if (b.charAt(i) == a.charAt(i)) 
					i += 1;
				else break;
			}
			while (i < a.length()) {
				if (b.charAt(i+1) == a.charAt(i))
					i += 1;
				else return false;
			}
			return true;
		} //Replace this with the correct returned value
	
	
	}
	
	
}