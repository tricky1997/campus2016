import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by juqing on 2016/6/17.
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。
 */
public class CountMostImport {
    public static void main(String[] args)throws Exception{

        File file=new File("H:\\Test");
        statistDir(file);
        sort();
        for(int i=0;i<list.size();i++){
            if(i<10)
            System.out.println(list.get(i));
        }
    }
    private static Map<String,Integer> map=new HashMap<>();
    private static List<Map.Entry<String,Integer>> list=new ArrayList<>();

    /**
     * 统计每个文件import的类的数目
     * 只考虑单行注释和多行注释在开头和结尾的情况
     */
    public static void statistFile(File file){
        Pattern pattern=Pattern.compile("(.*?)import(.*?);");
        Matcher matcher;
        BufferedReader br;
        try {
            br=new BufferedReader(new FileReader(file));
            String line;
            while((line=br.readLine())!=null){
                line=line.trim();
                if(line.startsWith("public")||line.startsWith("class")){
                    break;
                }
                if(line.startsWith("//")) continue;
                if(line.startsWith("/*")){
                    if(line.endsWith("*/")) continue;
                    while(!((line=br.readLine()).trim().contains("*/"))) continue;
                    if(line.endsWith("*/")) continue;
                    line=line.substring(line.indexOf("*/")+2).trim();
                }
                matcher=pattern.matcher(line);
                while(matcher.find()){

                        String className=matcher.group(2).trim();
                        if(null==map.get(className)) map.put(className,1);
                        else map.put(className,map.get(className)+1);
                    }
                }
            }
        catch (Exception e) {
            e.printStackTrace();
            }
        }
    /**
     * 统计目录下所有import的类的数目
     */
    public static void statistDir(File file) throws Exception{
        File[] arrFile=file.listFiles();
        for(File f : arrFile){
            statistFile(f);
        }
    }

    /**
     * 按照map值由大到小排序，用到了ValueComparator这个类
     */
    public static void sort(){
        list.addAll(map.entrySet());
        ValueComparator vc=new ValueComparator();
        Collections.sort(list,vc);
    }
}
