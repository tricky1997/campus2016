import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by xuxingbo on 2016/6/20.
 */
public class CountMostImport {
    private static String IMPORT_STRING = "import";
    private HashMap<String,Integer> countImport;
    public CountMostImport() {
        countImport = new HashMap<String,Integer>();
    }
    /**
     * 递归的遍历文件，找出其中的import项
     * @param file
     */
    public void start(File file){
        //如果file为单个java文件，则找出其中的import项
        if(!file.isDirectory()){
            //获取文件的后缀名，如果是java文件，则进行分析
            String fileName = file.getName();
            String prefix = fileName.substring(fileName.lastIndexOf('.')+1);
            if(prefix.equals("java"))
                countFile(file);
        }else{
            File[] files = file.listFiles();
            for(File f: files){
                start(f);
            }
        }
    }

    /**
     * 统计文件中import的情况，将import java.util.*当成一种特殊的import
     * 来处理，而不是将java.util下的所有类的import加1
     * @param file
     */
    public void countFile(File file){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String str=null;
            while((str=br.readLine())!=null){
                str = str.trim();
                if(str.startsWith(IMPORT_STRING)){
                    String className = str.substring(IMPORT_STRING.length(),str.length()-1).trim();
                    Integer value = countImport.get(className);
                    if(value==null){
                        //如果里面不存在import记录，则将其加入到map中
                        countImport.put(className,1);
                    }else{
                        //否则将其加1
                        countImport.put(className,value+1);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //获取import次数最多的前n个
    public List<Record> getTopNImport(int n){
        if(countImport.size() == 0)
            return null;
        List<Record> list = new ArrayList<>();
        for(Entry<String,Integer> item:countImport.entrySet()){
            Record rec = new Record();
            rec.setCount(item.getValue());
            rec.setClassName(item.getKey());
            list.add(rec);
        }
        Collections.sort(list);
        if(list.size() > n){
            list = list.subList(0,n);
        }
        return list;
    }

    /**
     * @param args arg[0]为要遍历的目录
     */
    public static void main(String[] args){
        CountMostImport countMostImport = new CountMostImport();
        File file = new File(args[0]);
        countMostImport.start(file);
        //获取import最多的前10个
        List<Record> list = countMostImport.getTopNImport(10);
        if(!list.isEmpty()){
            for(Record tmp:list){
                System.out.println(tmp);
            }
        }
    }
}
