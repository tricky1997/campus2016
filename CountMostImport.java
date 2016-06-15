/**
 * 三、根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么 
 */

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
 * @author JiangHP
 */
public class CountMostImport {
	private static String charset = "UTF-8";
	private static String srcPath = (String)System.getProperties().get("user.dir");
	public static void main(String[] args) {
		if(args.length==2){
			srcPath = args[0];
			charset = args[1];
		}
		System.out.println("目录 "+srcPath+" 下，源文件中import次数前十的包如下：");
		File path = new File(srcPath);
		Map<String, Integer> res = countImportOfPath(path);
		Map<PackageCount,Object> map = new TreeMap<>(Collections.reverseOrder());
		Iterator<String> it = res.keySet().iterator();
		while(it.hasNext()){
			String pack = it.next();
			int cnt = res.get(pack);
			map.put(new PackageCount(pack,cnt),null);
		}
		Iterator<PackageCount> it2 = map.keySet().iterator();
		PackageCount[] pcs = new PackageCount[10];
		int i=0;
		while(it2.hasNext()){
			pcs[i++]=it2.next();
			if(i>9) {
				break;
			}
		}
		i=0;
		for(PackageCount pc:pcs){
			System.out.printf("第%2d位：  %-30s  %4d次 %n",++i,pc.pack,pc.count);
		}
	}
	
	public static Map<String,Integer> countImportOfPath(File f){
		Map<String,Integer> map = new HashMap<>();
		if(f.isFile()){
			List<String> list = countImportOfFile(f);
			for(String pStr:list){
				Integer cnt = map.get(pStr);
				if(cnt!=null){
					map.put(pStr, cnt+1);
				}else{
					map.put(pStr, 1);
				}
			}
		}else if(f.isDirectory()){
			File[] fs = f.listFiles();
			for(File aFile:fs){
				Map<String,Integer> subMap = countImportOfPath(aFile);
				Iterator<String> it = subMap.keySet().iterator();
				while(it.hasNext()){
					String pack = it.next();
					Integer cnt = map.get(pack);
					if(cnt!=null){
						map.put(pack, cnt+subMap.get(pack));
					}else{
						map.put(pack, subMap.get(pack));
					}
				}
			}
		}
		return map;
	}

	public static List<String> countImportOfFile(File f){
		List<String> list = new ArrayList<>();
		if(!f.getName().endsWith(".java")){
			return list;
		}
		Scanner in = null;
		try{
			in = new Scanner(f,charset);
			while(in.hasNext()){
				String line = in.nextLine().trim();
				if(line.startsWith("import")){
					String pack0 = line.split("\\s")[1];
					String pack = pack0.substring(0,pack0.length()-1);
					list.add(pack);
				}else if(line.startsWith("class")||line.startsWith("public")){
					break;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				in.close();
			}
		}
		return list;
	}
	
	private static class PackageCount implements Comparable<PackageCount>{
		String pack;
		int count;
		
		public PackageCount(String pack, int count) {
			super();
			this.pack = pack;
			this.count = count;
		}

		@Override
		public int compareTo(PackageCount o) {
            //首先按引用数排序
			if(count>o.count){
				return 1;
			}else if(count==o.count){
                //如果引用数相同，按包名排序
				return o.pack.compareTo(pack);
			}else {
				return -1;
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(pack);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj==this){
				return true;
			}
			if(obj==null){
				return false;
			}
			if(obj.getClass() != this.getClass()){
				return false;
			}
			PackageCount that =  (PackageCount) obj;
			return this.pack.equals(that.pack);
		}

		@Override
		public String toString() {
			return "PackageCount[pack="+pack+",count="+count+"]";
		}
	}
}
