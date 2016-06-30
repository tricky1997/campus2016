

package me.helang.countMostImport;

import java.io.File;
import java.util.*;



public class CountMostImport{


	public static void main(String[] args){
		CountMostImport test= new CountMostImport();
		test.showFunction();
	}

	public void showFunction(){
		String rootDirName="/Users/lactic_h/Documents/Test/testDir";
		List<Map.Entry<String, Integer>> result= sortMap(countImportedClass(rootDirName));
		for(String s: getTopTen(result))
			System.out.println(s);	
	}

	public Map<String, Integer> countImportedClass(String rootDirName){

		//遍历根目录下的所有文件
		//输出 List<File> javaFileList
		DealWithRootDir dwrd= new DealWithRootDir();
		List<File> javaFileList= dwrd.getAllJaveFile(rootDirName);


		//遍历 List<File> javaFileList
		//输出 Map<String,Integer> classMap 和 Map<String,Integer> pkgMap

		Map<String, Integer> classMap= new HashMap<String, Integer>();
		Map<String, Integer> pkgMap= new HashMap<String, Integer>();
		
		DealWithSingleFile dwsf= new DealWithSingleFile(classMap, pkgMap);
		for(File f: javaFileList){
			dwsf.dealWithSigleFile(f);
		}



		//遍历 Map<String> pkgMap
		//输出 修改后的 Map<String> classMap

		DealWithPkg dwp= new DealWithPkg();
		for(String pkg: pkgMap.keySet()){

			Set<String> classesInThisPkg= dwp.getClassFromPkg(pkg);
			for(String s: classesInThisPkg){
				if(classMap.containsKey(s)){
					classMap.replace(s, classMap.get(s)+pkgMap.get(pkg));
				}else{
					classMap.put(s, pkgMap.get(pkg));
				}
			}
		}

		return classMap;
	}


	public List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> classMap){

		List<Map.Entry<String, Integer>> classEntryList= new LinkedList<Map.Entry<String, Integer>>(classMap.entrySet());
		Collections.sort(classEntryList, new CountComparator());
		return classEntryList;
	}

	public List<String> getTopTen(List<Map.Entry<String, Integer>> classEntryList){
		List<String> result= new LinkedList<String>();
		
		Iterator<Map.Entry<String, Integer>> it= classEntryList.iterator();
		for(int i=0; i<10; i++){
			if(it.hasNext()){
				result.add(it.next().getKey());
			}else
				result.add(null);
		}
		return result;
	}
}

class CountComparator implements Comparator<Map.Entry<String, Integer>>{
	@Override
	public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2){
		int result= e1.getValue()-e2.getValue();
		if (result!=0){
			return result;
		}else{
			return e1.getKey().compareTo(e2.getKey());
		}
	}

}