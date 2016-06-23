package com.qunar.com;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountMostImportClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("className.java");
		if (f.exists()) {
			System.out.println("className.java existed!");
		} else {
			System.out.println("className.java do not exist!");
		}
		getMostImportClass(f);
	}

	public static void getMostImportClass(File f) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		FileReader fr = null;
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					if (line.trim().startsWith("class")
							|| line.trim().startsWith("public")
							|| line.trim().startsWith("private")) {
						break;
					} else if (line.trim().startsWith("import")) {
						String className = line.trim().substring(7,
								line.trim().length() - 1);
						Integer val = map.get(className);
						if (val == null) {
							map.put(className, 1);
						} else {
							map.put(className, val + 1);
						}

					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ListMostImportClass(map);
		ListMostTenImportClass(map);

	}

	public static void ListMostImportClass(Map<String, Integer> map) {
		int max = Integer.MIN_VALUE;
		for (Map.Entry<String, Integer> entry0 : map.entrySet()) {
			int val0 = (int) entry0.getValue();
			if (val0 > max) {
				max = val0;
			}
		}
		Map<String, Integer> newMap = new HashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry0 : map.entrySet()) {
			String key0 = entry0.getKey();
			int val0 = (int) entry0.getValue();
			if (val0 == max) {
				newMap.put(key0, val0);
			}
		}
		String[] strs = swap(newMap);
		System.out.println("MostImportClass:");
		for (int i = 0; i < strs.length; i++) {
			System.out.println("frequency of occurrence:" + map.get(strs[i]) + "--" + strs[i]);
		}
	}

	public static void ListMostTenImportClass(Map<String, Integer> map) {
		int count = 0;
		Map<String, Integer> mapByValues = sortMapByValues(map);
		Map<String, Integer> bufMap0 = mapByValues;
		
		Map<String, Integer> bufMap1 = null;
		System.out.println("MostTenImportClass:");
		for (Map.Entry<String, Integer> entry0 : bufMap0.entrySet()) {
			String key0 = entry0.getKey();
			int val0 = (int) entry0.getValue();
			bufMap1 = new HashMap<String, Integer>();
			for (Map.Entry<String, Integer> entry1 : bufMap0.entrySet()) {
				String key1 = entry1.getKey();
				int val1 = (int) entry1.getValue();
				if (val0 == val1 && !key0.equals(key1) && val0 > 0 && val1 > 0) {
					bufMap1.put(key0, val0);
					bufMap1.put(key1, val1);
					if ((int) entry0.getValue() > 0) {
						entry0.setValue(-1);
					}
					entry1.setValue(-1);
				}
			}
			if (val0 > 0) {
				bufMap1.put(key0, val0);
				entry0.setValue(-1);
			}

			if (bufMap1.size() != 0) {
				String[] strs = swap(bufMap1);
				for (int i = 0; i < strs.length; i++) {
					System.out.println("frequency of occurrence:" + map.get(strs[i]) + "--"
							+ strs[i]);
					count++;
					if (10 == count)
						break;
				}
			}
			if (10 == count)
				break;
			bufMap1 = null;

		}
	}

	public static String[] swap(Map<String, Integer> map) {
		String str = "";
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			str += entry.getKey();
			str += ";";
		}
		str = str.substring(0, str.length() - 1);
		String[] strs = str.split(";");

		for (int i = 1; i < strs.length; i++) {
			for (int j = strs.length - 1; j >= i; j--) {
				if (strs[j].length() > strs[j - 1].length()) {
					int len = strs[j - 1].length();
					for (int k = 0; k < len; k++) {
						if (strs[j].charAt(k) < strs[j - 1].charAt(k)) {
							String temp = strs[j];
							strs[j] = strs[j - 1];
							strs[j - 1] = temp;
							break;
						}
					}
				} else {
					int len = strs[j].length();
					for (int k = 0; k < len; k++) {
						if (strs[j].charAt(k) < strs[j - 1].charAt(k)) {
							String temp = strs[j];
							strs[j] = strs[j - 1];
							strs[j - 1] = temp;
							break;
						}
					}
				}
			}

		}

		return strs;
	}

	public static Map<String, Integer> sortMapByValues(Map<String, Integer> map) {

		Set<Map.Entry<String, Integer>> mapEntries = map.entrySet();

		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				mapEntries);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> ele1,
					Map.Entry<String, Integer> ele2) {

				return -ele1.getValue().compareTo(ele2.getValue());
			}
		});
		Map<String, Integer> newMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : list) {
			newMap.put(entry.getKey(), entry.getValue());
		}
		return newMap;
	}

}
