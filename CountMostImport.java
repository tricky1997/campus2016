import java.io.*;
import java.util.*;

/**
 * Created by meng fen zong.
 */
public class CountMostImport {
    private Map<String,Integer> imp = new HashMap<String,Integer>();
    private List<Map.Entry<String,Integer>> imp_temp ;
    public static void main(String[] args)throws Exception{
        CountMostImport countMostImport = new CountMostImport();
        System.out.println("请输入有效的项目目录(如：D:/IntelliJ IDEA/CountMostImport)：");
        Scanner in = new Scanner(System.in);
        String dir = in.next() ;
        File f = new File(dir);
        while(!f.exists()||!f.isDirectory()){
            System.out.println("输入目录错误，请输入有效的项目目录(如：D:/IntelliJ IDEA/CountMostImport)：");
            dir = in.next() ;
            f = new File(dir) ;
        }
        countMostImport.AnalysisFile(f);
        countMostImport.imp_temp= new ArrayList<Map.Entry<String, Integer>>(countMostImport.imp.entrySet());
        countMostImport.sortImport();
    }

    public void AnalysisFile(File f){
        //递归取得每一个文件夹下的文件
        if(f.isDirectory()){
            File[] file = f.listFiles();
            for(File child_file : file){
                AnalysisFile(child_file);
            }
        }
        //判断是否是文件
        if(f.isFile()){
            //判断是否是java结尾的文件
            if(f.getName().matches(".*\\.java"))
                CountImportForTen(f);
        }
    }
    /*
    把java文件中的import的类名取出来并计数
     */
    public void CountImportForTen(File f){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String str = "" ;
            String importname ;
            while((str = br.readLine())!=null){
                //去除头尾空格
                str = str.trim() ;
                //判断读取进来的一行字符串是否有import关键字
                if(str.matches("^import.*")){
                    //取出import后的类名
                    importname = str.substring(6,str.length()-1).trim();
                    //往集合中计算该类名的次数
                    if(imp.get(importname)==null) {
                        imp.put(importname, new Integer(1));
                    } else {
                        imp.put(importname, imp.get(importname) + 1);
                    }
                }
            }
            br.close();

        }catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    /*
    把HashMap中的数据按照value次数排序，当次数相等时，类名按照字典升序排序
     */
    public void sortImport(){

        Collections.sort(imp_temp, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //当次数相等时，类名按照字典升序排序
                if(o2.getValue().compareTo(o1.getValue())==0){
                    return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
                }
                //次数不相等，按照次数由大到小排序
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        int num = 0 ;
        //输出次数前10的类名
        for(Map.Entry<String,Integer> import_sort:imp_temp){
            System.out.println(import_sort.getKey()+":"+import_sort.getValue());
            num++ ;
            if(num ==10)
                break;
        }
    }
}
