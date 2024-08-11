package il.ac.tau.cs.sw1.ex8.tfIdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	
	//Add members here
	private Map<String, HashMapHistogram<String>> index = new HashMap<>();
	private Map<String, List<String>> tfidfOrder = new HashMap<>();
	private int len = 0;
	
	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
		//This code iterates over all the files in the folder. add your code wherever is needed

		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				HashMapHistogram<String> histogram = new HashMapHistogram<>();
				try {
					histogram.addAll(FileUtils.readAllTokens(file));
					index.put(file.getName(), histogram);
					len++;
				} catch (IOException e) {
					e.printStackTrace();
				}
						
			}
		}
		for (String str : index.keySet()) {
			List<String> lst = new ArrayList<>();
			for (String word : index.get(str).getItemsSet()) {
				lst.add(word);
			}
			Collections.sort(lst, new Comparator<String>() {
				public int compare(String w1, String w2) {
					try {
						if (getTFIDF(w1, str) == getTFIDF(w2, str)) return w1.compareTo(w2);
						return Double.compare(getTFIDF(w2, str), getTFIDF(w1, str));
					} catch (FileIndexException e) {
						e.printStackTrace();
					}
					return 0;
				}
			});
			tfidfOrder.put(str, lst);
		}
		isInitialized = true;
	}
	
	
	
	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{ 
		if (!index.containsKey(fileName)) throw new FileIndexException("File" + fileName + "not found");
		return index.get(fileName).getCountForItem(word.toLowerCase());
	}
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{ 
		if (!index.containsKey(fileName)) throw new FileIndexException("File" + fileName + "not found");
		return index.get(fileName).size;
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return len;
	}

	
	/* @pre: isInitialized() */
	public double getTF(String word, String fileName) throws FileIndexException{ 
		if (!index.containsKey(fileName)) throw new FileIndexException("File" + fileName + "not found");
		return calcTF(index.get(fileName).getCountForItem(word.toLowerCase()), index.get(fileName).getCountsSum()); 
		}
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		int cnt = 0;
		for (HashMapHistogram<String> hist : index.values()) {
			if (hist.getCountForItem(word.toLowerCase()) > 0) cnt += 1;
		}
		return calcIDF(len, cnt); 
	}
	
	
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k) 
													throws FileIndexException{ //Q5
		if (!index.containsKey(fileName)) throw new FileIndexException("File" + fileName + "not found");
		List<Map.Entry<String, Double>> lst = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			String word = tfidfOrder.get(fileName).get(i);
			lst.add(Map.entry(word, getTFIDF(word, fileName)));
		}
		return lst; 
	}
	
	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		if (!index.containsKey(fileName1)) throw new FileIndexException("File" + fileName1 + "not found");
		if (!index.containsKey(fileName2)) throw new FileIndexException("File" + fileName2 + "not found");
		if (index.get(fileName1).size < index.get(fileName2).size) return getCosineSimilarity(fileName2, fileName1);
		double numerator = 0;
		double norm1 = 0;
		double norm2 = 0;
		Set<String> voc = new HashSet<>();
		for (String str : index.get(fileName1).getItemsSet()) voc.add(str);
		for (String str : index.get(fileName2).getItemsSet()) voc.add(str);
		for (String word : voc) {
			double tfidf1 = getTFIDF(word, fileName1);
			double tfidf2 = getTFIDF(word, fileName2);
			numerator += tfidf1*tfidf2;
			norm1 += tfidf1*tfidf1;
			norm2 += tfidf2*tfidf2;
		}
		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);
		return numerator/(norm1*norm2);
	}
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) 
			throws FileIndexException{ //Q6
		if (!index.containsKey(fileName)) throw new FileIndexException("File" + fileName + "not found");
		List<String> rest = new ArrayList<>();
		for (String file : index.keySet()) {
			if (!file.equals(fileName)) {
				rest.add(file);
			}
		}
		rest.sort((doc1, doc2) -> {
			try {
				return Double.compare(getCosineSimilarity(doc2, fileName), getCosineSimilarity(doc1, fileName));
			} catch (FileIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return doc2.compareTo(doc1);
			}
		});
		List<Map.Entry<String, Double>> res = new ArrayList<>();
		for (int i = 0 ; i < k ; i++) {
			res.add(Map.entry(rest.get(i), getCosineSimilarity(rest.get(i), fileName)));
		}
		return res; //replace this with the correct value
	}

	
	
	//add private methods here, if needed

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}
	
}
