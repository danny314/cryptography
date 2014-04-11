package cryptography;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FrequencyAttack {

	public static void main(String[] args) {
		String cipher = "53qqt305))6*;4826)4q.)4q);806*;48t8I60))85;;]8*;:q*8t83(88)5*t;46(;88*96*?;8)*q(;485);5*t2:*q(;4956*2(5*-4)8I8*;4069285);)6t8)4qq;1(q9;48081;8:8q1;48t85;4)485t528806*81(q9;48;(88;4(q?34;48)4q;161;:188;q?;";
		char[] cipherArr = cipher.toCharArray();
		Map<String,Integer> uniqueMap = new HashMap<String,Integer>();
		
		for (char c : cipherArr) {
			String str = String.valueOf(c);
			if (uniqueMap.containsKey(str)) {
				uniqueMap.put(str, uniqueMap.get(str) + 1);
			} else {
				uniqueMap.put(str, 1);
			}
		}
		
		List<Map.Entry<String,Integer>> entries = new ArrayList<>(uniqueMap.entrySet());
		
		Collections.sort(entries, new Substiturion().new EntryComparator());

		System.out.println("Frequency distribution..");
		for (Map.Entry<String,Integer> entry : entries) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		
		Map<Double,String> englishMap = new HashMap<>();
		
		englishMap.put(8.167d,"a");
		englishMap.put(1.492d,"b");
		englishMap.put(2.782d,"c");
		englishMap.put(4.253d,"d");
		englishMap.put(12.702d,"e");
		englishMap.put(2.228d,"f");
		englishMap.put(2.015d,"g");
		englishMap.put(6.094d,"h");
		englishMap.put(6.996d,"i");
		englishMap.put(0.153d,"j");
		englishMap.put(0.772d,"k");
		englishMap.put(4.025d,"l");
		englishMap.put(2.406d,"m");
		englishMap.put(6.749d,"n");
		englishMap.put(7.507d,"o");
		englishMap.put(1.929d,"p");
		englishMap.put(0.095d,"q");
		englishMap.put(5.987d,"r");
		englishMap.put(6.327d,"s");
		englishMap.put(9.056d,"t");
		englishMap.put(2.758d,"u");
		englishMap.put(0.978d,"v");
		englishMap.put(2.360d,"w");
		englishMap.put(0.150d,"x");
		englishMap.put(1.974d,"y");
		englishMap.put(0.074d,"z");
		
		
		System.out.println(cipher);
		
		Map<String,String> cipherKey = new HashMap<>();
		
		for (int i=0; i < entries.size(); i++)  {
			Map.Entry<String,Integer> entry = entries.get(i); 
			String cipherStr = entry.getKey();
			System.out.print("\n"+cipherStr + "=" );
			int cipherStrFreq = uniqueMap.get(cipherStr);
			
			Set<Double> englishKeys = englishMap.keySet();
			List<Double> engFreqs = Arrays.asList(englishKeys.toArray(new Double[] {}));
			Collections.sort(engFreqs);
			Collections.reverse(engFreqs);
			
			
			cipherKey.put(cipherStr, englishMap.get(engFreqs.get(i)));
			System.out.print(englishMap.get(engFreqs.get(i)) + " ");
			
		}
		
		cipherKey.put("4", "h");
		cipherKey.put("q", "o");
		cipherKey.put("?", "u");
		cipherKey.put("3", "g");
		cipherKey.put("6", "i");
		
		//Corrections
		cipherKey.put("*", "n");
		cipherKey.put(")", "s");
		cipherKey.put("5", "a");
		cipherKey.put("0", "l");
		cipherKey.put("1", "f");
		cipherKey.put("I", "v");
		cipherKey.put("q", "o");
		cipherKey.put(":", "y");
		cipherKey.put("]", "w");
		cipherKey.put("2", "b");
		cipherKey.put("-", "c");


		
		System.out.println("Deciphered text...\n");
		String decipheredText = "";
		
		for (int k=0; k < cipher.length(); k++) {
			decipheredText = decipheredText + cipherKey.get(String.valueOf(cipher.charAt(k))); 
		}
		System.out.println(cipher);
		System.out.print(decipheredText);
		String[] splitStr = decipheredText.split("the");
		System.out.println("\n\n");
		for (String str : splitStr) {
			System.out.print(str + " the ");	
		}
		
		System.out.println("\n\n");
		
	}
	
	class EntryComparator implements Comparator<Map.Entry<String, Integer>> {

		@Override
		public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
			if (o1.getValue() < o2.getValue()) return 1;
			if (o1.getValue() > o2.getValue()) return -1;
			return 0;
		}
		
	}

	class EnglishComparator implements Comparator<Map.Entry<Double, String>> {

		@Override
		public int compare(Entry<Double, String> o1, Entry<Double, String> o2) {
			if (o1.getKey() < o2.getKey()) return 1;
			if (o1.getKey() > o2.getKey()) return -1;
			return 0;
		}
		
	}
}
