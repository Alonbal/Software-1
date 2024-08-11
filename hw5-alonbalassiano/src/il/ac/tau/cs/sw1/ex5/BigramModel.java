package il.ac.tau.cs.sw1.ex5;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] array = new String[MAX_VOCABULARY_SIZE];
		int index = 0;
		File file = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String newLine;
		while ((newLine = reader.readLine()) != null && index < MAX_VOCABULARY_SIZE) {
			String[] line = newLine.split(" ");
			int len = line.length;
			for (int i = 0; i < len; i++) {
				if (index == MAX_VOCABULARY_SIZE) break;
				String str = line[i];
				if (isNum(str)) {
					if (isInArray(array, SOME_NUM, index) == -1) {
						array[index] = SOME_NUM;
						index++;
					}
				}
				else if (isWord(str)) {
					if (isInArray(array, str.toLowerCase(), index) == -1) {
						array[index] = str.toLowerCase();
						index++;
					}
				}	
			}	
		}
		reader.close();
		if (index == MAX_VOCABULARY_SIZE) return array;
		String[] new_array = new String[index];
		for (int i = 0; i < index; i++)						//shrinking the array
			new_array[i] = array[i];
		return new_array;
	}
	
	/*
	 * @pre: index < array.length
	 * @pre: str is legal
	 * @post: $ret = -1 if str is not in array, else array[$ret] = str
	 */
	private static int isInArray(String[] array, String str, int index) {
		for (int i = 0; i < index; i++) {
			if (str.equals(array[i])) return i;
		}
		return ELEMENT_NOT_FOUND;
	}
	
	public static boolean isWord(String str) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (Character.isAlphabetic(str.charAt(i))) return true;
		}
		return false;
	}
	
	public static boolean isNum(String str) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (!Character.isDigit(str.charAt(i))) 
				return false;
		}
		return true;
	}
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		File file = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int len = vocabulary.length;
		int[][] counts = new int[len][len];
		String newLine;
		while ((newLine = reader.readLine()) != null) {
			String[] line = newLine.split(" ");
			int sentLen = line.length;		
			for (int i = 0; i < sentLen - 1; i++) {
				String a = null;
				String b = null;
				if (isNum(line[i])) a = SOME_NUM;
				else if (isWord(line[i])) a = line[i].toLowerCase();
				if (isNum(line[i+1])) b = SOME_NUM;
				else if (isWord(line[i+1])) b = line[i+1].toLowerCase();
				if (a == null || b == null) continue;
				int j = isInArray(vocabulary, a, len);
				int k = isInArray(vocabulary, b, len);
				if (j != -1 && k != -1)
					counts[j][k]++;
			}
		}
		reader.close();
		return counts;

	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		File countsFile = new File(fileName + COUNTS_FILE_SUFFIX);
		BufferedWriter vocWriter = new BufferedWriter(new FileWriter(vocFile));
		int vocLen = mVocabulary.length;
		vocWriter.write(vocLen + " words" + System.lineSeparator());
		for (int i = 0; i < vocLen; i++) {
			vocWriter.write(i + "," + mVocabulary[i] + System.lineSeparator());
		}
		vocWriter.close();
		BufferedWriter countsWriter = new BufferedWriter(new FileWriter(countsFile));
		for (int i = 0; i < vocLen; i++) {
			for (int j = 0; j < vocLen; j++) {
				int count = mBigramCounts[i][j];
				if (count > 0) {
					countsWriter.write(i + "," + j + ":" + count + System.lineSeparator());
				}
			}
		}
		countsWriter.close();
	}
	
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		File countsFile = new File(fileName + COUNTS_FILE_SUFFIX);
		BufferedReader vocReader = new BufferedReader(new FileReader(vocFile));
		String[] firstline = vocReader.readLine().split(" ");
		String[] voc = new String[Integer.parseInt(firstline[0])];
		int len = voc.length;
		for (int i = 0; i < len; i++) {
			String[] curr = vocReader.readLine().split(",");
			voc[i] = curr[1];
		}
		vocReader.close();
		mVocabulary = voc;
		int[][] counts = new int[len][len];
		BufferedReader countsReader = new BufferedReader(new FileReader(countsFile));
		String line;
		while ((line = countsReader.readLine()) != null) {
			String[] tokens = line.split(",|\\:");
			counts[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])] = Integer.parseInt(tokens[2]);
		}
		countsReader.close();
		mBigramCounts = counts;
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		return isInArray(mVocabulary, word, mVocabulary.length);
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int ind1 = getWordIndex(word1);
		int ind2 = getWordIndex(word2);
		if (ind1 != -1 && ind2 != -1)
			return mBigramCounts[ind1][ind2];
		return 0;
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int max = 0;
		String curr = null;
		int bound = mVocabulary.length;
		for (int i = 0; i < bound; i++) {
			if (getBigramCount(word, mVocabulary[i]) > max) {
				curr = mVocabulary[i];
				max = getBigramCount(word, curr);
			}
		}
		return curr;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String[] array = sentence.split(" ");
		int len = array.length;
		if (len == 0) return true;
		if (len == 1) return getWordIndex(array[0]) != -1;
		for (int i = 0; i < len-1; i++) {
			if (getBigramCount(array[i], array[i+1]) == 0) return false;
		}
		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		int len = arr1.length;
		int sum1 = 0;
		int sum2 = 0;
		for (int i = 0; i < len; i++)
			sum1 += arr1[i]*arr1[i];
		if (sum1 == 0) return (double) -1;
		double norm1 = Math.sqrt((double)sum1);
		for (int i = 0; i < len; i++)
			sum2 += arr2[i]*arr2[i];
		if (sum2 == 0) return (double) -1;
		double norm2 = Math.sqrt((double) sum2);
		int numerator = 0;
		for (int i = 0; i < len; i++) 
			numerator += arr1[i]*arr2[i];
		return (double)numerator/(norm1*norm2);
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		int len = mVocabulary.length;
		if (len == 1) return word;
		int[] wordVector = mBigramCounts[getWordIndex(word)];
		String curr = null;
		double max = 0.0;
		for (int i = 0; i < len; i++) {
			if (getWordIndex(word) != i) {
				double similarity = calcCosineSim(wordVector, mBigramCounts[i]);
				if (similarity > max) {
					curr = mVocabulary[i];
					max = similarity;
				}
			}
		}
		return curr;
	}

	
}
