import java.io.*;
import java.util.*;

/**
 * Created by kuangchunyan on 2016/7/2.
 * 统计指定目录中被import最多的类，输出前十个；
 * 若import次数相同，类名按字典顺序排序，输出前面的类
 */
public class CountMostImport {
    public static void main(String[] args)throws FileNotFoundException{
        CountMostImport countMI = new CountMostImport();
        String path = "TestResource/io";
        ArrayList<File> dirFile = countMI.fileFilter(new File(path),".java");
        Map<String,Integer> results = countMI.ImportCount(dirFile);
        List<Map.Entry<String, Integer>> infolds = countMI.sortImport(results);
        countMI.display(infolds,10);


    }
    /**
     * 统计指定目录下所有指定后缀的文件
     * @param rootFile 指定目录的文件，suffix 指定后缀
     * @return 返回指定后缀的文件列表
     */
    public ArrayList<File> fileFilter(File rootFile,String suffix) throws FileNotFoundException{
        File[] files = rootFile.listFiles();
        ArrayList<File> dirFile = new ArrayList<File>();
            for (File file : files) {
                if (file.isDirectory()) {
                    fileFilter(file, suffix);
                } else {
                    if (file.getName().endsWith(suffix)) {
                        dirFile.add(file);
                    }
                }
            }


        return dirFile;
    }
    /**
     * 统计指定目录下指定后缀文件中import不同类的次数
     * @param fileList 指定后缀的文件列表
     * @return 返回保存import的类的名字及次数组成的Map
     */
    public Map<String,Integer>  ImportCount(ArrayList<File> fileList ){
        Map<String,Integer> countResult = new HashMap<String,Integer>();
        int count = 0;
        BufferedReader br = null;
        Iterator it = fileList.iterator();
        try{
            while(it.hasNext()){
                br = new BufferedReader(new FileReader((File)it.next()));
                String line = null;
                String str = null;
                while((line = br.readLine())!=null){
                    str = line.trim();
                    if(str.startsWith("import")){
                        //获取导入类名
                        str = str.substring(7,str.length()-1);
                        if(countResult.containsKey(str)){
                            count = countResult.get(str);
                            countResult.put(str,++count);
                        }else{
                            countResult.put(str,1);
                        }
                    }
                    //当遇到起始开头是public或者class就结束循环
                    if(str.startsWith("public")|| str.startsWith("class")){
                        break;
                    }
                }
            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(br!=null){
                    br.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return countResult;
    }

    /**
     * 对HashMap中的文件从大到小排序，遇到数目相同的，按名字的字典顺序排列
     * @param countResult 输入的HashMap结构
     * @return 返回排好序的列表结构
     */
    public List<Map.Entry<String, Integer>> sortImport(Map<String,Integer> countResult){
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(countResult.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                // return o2.getValue()==o1.getValue()?o1.getKey().compareTo(o2.getKey()):o2.getValue() - o1.getValue();

                if(o2.getValue() - o1.getValue()>0){
                    return 1;
                }else if(o2.getValue() - o1.getValue()==0){
                    /*if(o1.getKey().compareTo(o2.getKey())>0)    {
                        return 1;
                    }
                    if(o1.getKey().compareTo(o2.getKey())<0){
                        return -1;
                    }
                    return 0;   */
                    return o1.getKey().compareTo(o2.getKey());
                }else{
                    return -1;
                }

            }
        });
        return infoIds;
    }
    /**
     * 输出最多的10个类
     * @param infoIds 输入的列表结构
     */
    public void display(List<Map.Entry<String, Integer>> infoIds,int number){
        for(int i=0;i<number;i++){
            System.out.println(infoIds.get(i).getKey() + "被import的次数为" + infoIds.get(i).getValue());
        }

    }
}
