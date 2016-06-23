import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountMostImport {
	private static Map<String, Integer> out = new HashMap<String, Integer>();
	public static void main(){
		String path = "";
		File new_file = new File(path);
		DepthSearch(new_file, out);
		CountMostImport10(out);
	}
	
	
	
	private static void DepthSearch(File s, Map<String, Integer> out){
		if(s.isDirectory()){
			File[] files = s.listFiles();
			if(files.length != 0){
				for(File file:files){
					DepthSearch(file,out);
				}
			}
		}
		else{
			try{
				CalImport(s, out);
			}catch(Exception ex){
				System.out.println("error reading files!");
			}
			
		}
		
	}
	
	private static void CalImport(File file, Map<String, Integer> out) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(file));
		String read_string;
		while((read_string = in.readLine()) != null){
			if(read_string.startsWith("package")){
				continue;
			}
			if(read_string.startsWith("import")){
				String m_s = read_string.replaceAll("\\.", " ");
				String regex = "import .+?(\\S+);";
				Matcher matcher = Pattern.compile(regex).matcher(m_s);
				matcher.find();
				Integer count = out.get(matcher.group(1));
				if(count == null){
					out.put(matcher.group(1), 1);
				}else{
					out.put(matcher.group(1), count++);
				}
			}
			else break;
		}
		
	}
	
	private static void CountMostImport10(Map<String, Integer> out){
		int count = 0 ;
		for(String i: out.keySet())
		{
			if(count == 10)
				break;
			System.out.println(i + ":" + out.get(i));
			count++;	
		}
		
	}

}
