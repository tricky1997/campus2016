package CountMostImport;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计java文件中，不同类的import次数，并得到次数最多的10个
 */

public class CountImportNum {
	Map<String, Integer> m_CountImportMap = new HashMap<>();

	public List<Map.Entry<String, Integer>> getMaxImportClass(List<String> javaFilesPath) {
		CountNum(javaFilesPath);     //把所有import的类提取出来，放到m_CountImportMap
		
		 /* 对每个文件的import次数进行排序 */
        List<Map.Entry<String, Integer>> list
                = new ArrayList<>(m_CountImportMap.entrySet());
        // 创建比较器
                list.sort(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                        if(obj1.getValue()<obj2.getValue())
                            return 1;
                        else if(obj1.getValue()> obj2.getValue())
                            return -1;
                        return 0;
                    }
                });
return list;
	}

	/**
	 * 统计Java文件有效行数
	 * @param javaFilesPath  所有Java文件的路径
	 */
	public void CountNum(List<String> javaFilesPath) {
		//遍历每个java文件
		for (String path : javaFilesPath) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(path));     //读取当前java文件内容
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				while (br.ready()) {
					String line = br.readLine().trim(); // 读取一行字符

					if (line.startsWith("public") || line.startsWith("class")) // 出现类，停止读入
						break;

					if (line.startsWith("import") && line.charAt(6) != '*'
							&& !line.startsWith("import static")) // 存在import并排除import *和import static
					{
						String className = line.substring(7, line.length() - 1);    //提取类名
						if (m_CountImportMap.containsKey(className)) { // 类名已存在
							Integer val = m_CountImportMap.get(className);
							m_CountImportMap.put(className, val + 1);    //次数+1
						} else
							// 不存在主键
							m_CountImportMap.put(className, 1);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
