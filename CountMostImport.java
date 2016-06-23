import javafx.util.Pair;
import java.io.*;
import java.util.*;

//1.不考虑import/*......*/ aaa.bbb.ccc /*......*/; 这种奇怪的注释情况
//2.将java.io.*这种情况算作单独一种
//3.不考虑形如
// /*
// import ..........;
// */
//这种情况
//4.不考虑 在类/接口 等声明之前有“{”存在的情况

public class CountMostImport {
    private static int N=10;
    private HashMap<String,Integer> map;


    public CountMostImport(){
        map=new HashMap<>();
    }

    private void read(String path){
        File file=new File(path);
        if(file.isDirectory()){
            for (String s : file.list()) {
                path=path.endsWith("/")?path:path+"/";
                read(path+s);
            }
        }
        else if(path.endsWith(".java")){
            try {

                FileReader fileReader=new FileReader(file);
                BufferedReader bufferedReader=new BufferedReader(fileReader);
                String s;
                while((s=bufferedReader.readLine())!=null&&!s.contains("{")){
                    s=s.trim();
                    if(s.startsWith("import")){
                        s=s.substring("import".length(),s.indexOf(";"));
                        int cnt=map.containsKey(s)?map.get(s):0;
                        cnt++;
                        map.put(s,cnt);
                    }
                }
                fileReader.close();
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private void show(){
        PriorityQueue<Map.Entry<String,Integer>> priorityQueue=new PriorityQueue<>(N + 1, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            priorityQueue.add(entry);
            if (priorityQueue.size() > N) {
                priorityQueue.poll();
            }
        }


        while(!priorityQueue.isEmpty()){
            Map.Entry<String, Integer> entry = priorityQueue.poll();
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }
    public void countMostImport(String path){
        map.clear();
        read(path);
        show();
    }

    public static void main(String[] args){
        CountMostImport countMostImport=new CountMostImport();
        countMostImport.countMostImport(args[0]);

    }
}
