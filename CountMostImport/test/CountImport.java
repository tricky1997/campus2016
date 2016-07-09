/**
 * Created by zhaoxin on 16-6-30.
 */
import java.util.*;
import java.util.Map.Entry;
import java.io.File;
import java.lang.Integer;

public class CountImport {
    private Hashtable<String,Integer> hashtable;
    public CountImport()
    {
        this.hashtable=new Hashtable<String,Integer>();

    }
    public void count(File java_file)
    {
        try
        {
            Scanner scan = new Scanner(java_file);
            String line;
            String[] tokens;
            while(scan.hasNextLine())
            {
                line=scan.nextLine();
                line.trim();
                if(line.startsWith("import"))
                {
                    tokens=line.split(" ");
                    if(hashtable.containsKey(tokens[1]))
                    {
                        hashtable.replace(tokens[1],hashtable.get(tokens[1])+1);
                    }
                    else
                    {
                        hashtable.put(tokens[1],1);
                    }
                }
            }
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("文件不存在");
            System.exit(0);
        }

    }
    public void getMostImport()
    {
        ArrayList<Entry<String,Integer>> sequence_line=new ArrayList<Entry<String,Integer>>();  //单调队列
        Iterator<Entry<String,Integer>> ite=hashtable.entrySet().iterator();
        while(ite.hasNext())
        {
            Entry<String,Integer> current_entry=ite.next();
            for(int i=0;i<10;i++)
            {
                try
                {
                    if (sequence_line.get(i).getValue().compareTo(current_entry.getValue())<0)
                    {
                        sequence_line.add(i,current_entry);
                        break;
                    }
                }
                catch (IndexOutOfBoundsException e)
                {
                    sequence_line.add(i,current_entry);
                    break;
                }
            }

        }
        System.out.println("import 最多的10个类是：");
        for (int i=0;i<10;i++)
        {
            try
            {
                System.out.println(sequence_line.get(i).getKey()+"被import"+sequence_line.get(i).getValue()+"次");
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("被引用的类不足10个");
                break;
            }
        }
    }
}
