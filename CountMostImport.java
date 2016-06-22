import javafx.util.Pair;

import java.io.*;
import java.util.*;

//1.不考虑import/*......*/ aaa.bbb.ccc /*......*/; 这种奇怪的注释情况
//2.将java.io.*这种情况算作单独一种
public class CountMostImport {
    HashMap<String,Integer> map;


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
                while((s=bufferedReader.readLine())!=null){
                    s=s.trim();
                    if(s.startsWith("import")&&s.endsWith(";")){
                        s=s.substring("import".length(),s.lastIndexOf(";"));
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

    class NPair extends Pair<String,Integer> implements Comparable<NPair> {
        public NPair(String s, Integer integer) {
            super(s, integer);
        }

        @Override
        public int compareTo(NPair o) {
            return -getValue().compareTo(o.getValue());
        }
    }


    private void show(){
        PriorityQueue<NPair> priorityQueue=new PriorityQueue<>();
        for (String s : map.keySet()) {
            priorityQueue.add(new NPair(s, map.get(s)));
        }
        for (int i = 0; i < 10&&!priorityQueue.isEmpty(); i++) {

            Pair pair=priorityQueue.poll();
            System.out.println(i+" " +pair.getKey()+" "+pair.getValue());
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
