import java.io.*;
import java.util.*;

/**
 * @Author panxiong
 * @Date 2016/6/23 21:12
 * @Description 指定项目目录下, 统计被import最多的类，前十个是什么
 **/
public class CountMostImport {
    /**
     * @param args
     */
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        System.out.print("Input a java file directory: ");
        String filename = in.nextLine().trim();
        countMostImport(filename, 10);
    }

    /**
     * count the import for all the java file under path
     * @param path
     * @param counts
     */
    public static void countImport(String path, HashMap<String, Integer> counts){
        File root = new File(path);
        //root is directory
        if (root.isDirectory()){
            File[] files = root.listFiles();
            for (File file:files) {
                countImport(file.getAbsolutePath(), counts);
            }
        }
       else if(path.endsWith(".java")){ //root is a java file
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(root));
                while(true){
                    String line = reader.readLine();
                    //read the end of the file
                    if (line == null){
                        break;
                    }
                    line = line.trim();
                    //parse the import sentence
                    if (line.startsWith("import")){
                        String className = line.substring(line.lastIndexOf(".")+1);
                        className = className.substring(0, className.length()-1);

                        if (className.equals("*")){
                            continue;
                        }

                        //add the class name to hashmap
                        if (counts.containsKey(className)){
                            counts.put(className, counts.get(className)+1);
                        }
                        else{
                            counts.put(className, 1);
                        }
                    }
                    //if the code is not import sentence or package sentence, then end reading file
                    else if(line.length() != 0 && !line.startsWith("package")){
                        //break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * find most k import class under directory path
     * @param path
     * @param k
     */
    public static void countMostImport(String path, int k){
       /*
       * a record for the number of the import class and its name
       * */
        class Record{
            public Record(int cnt, String name){
                this.cnt = cnt;
                this.name = name;
            }
            public int cnt;
            public String name;
        }

        /*maintain a PriorityQueue which size is k */
        PriorityQueue<Record> queue = new PriorityQueue<Record>(k,
                new Comparator<Record>() {
                    @Override
                    public int compare(Record record, Record t1) {
                        return record.cnt - t1.cnt;
                    }
                });

        //count the import class
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        countImport(path, counts);

        /*traverse the hashmap and add the entries into PriorityQueue which is limited by size k */
        Iterator iter = counts.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            Record record = new Record((Integer) entry.getValue(), (String) entry.getKey());
            //the size of queue is less than k
            if (queue.size() < k){
                queue.add(record);
            }
            //the size of queue is k
            else{
                if (record.cnt > queue.peek().cnt){
                    queue.poll();
                    queue.add(record);
                }
            }
        }

        //display the result
        LinkedList<Record> records = new LinkedList<>();
        while(!queue.isEmpty()){
            records.add(queue.poll());
        }
        Collections.reverse(records);
        if (records.size() == 0){
            System.out.println("No import class!");
        }
        else{
            System.out.println("The most count of import class is "
                    + records.get(0).name + ", and counts " + records.get(0).cnt);
            System.out.println("Top " + k + " count: ");
            for (Record record:records) {
                System.out.println("class " + record.name + " counts " + record.cnt);
            }
        }
    }
}
