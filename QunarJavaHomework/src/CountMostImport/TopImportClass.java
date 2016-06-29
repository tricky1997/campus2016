package CountMostImport;

import java.io.*;
import java.util.*;

/**
 * Created by lz on 16-6-28.
 */
public class TopImportClass
{
    public String dirPath = "";
    public Map<String,Integer> count = new HashMap<String, Integer>();

    TopImportClass(String str)
    {
        this.dirPath = str;
        this.isDir(new File(dirPath));
    }

    public void isDir(File f)
    {
        if(!f.isDirectory())
        {
            countImport(f);
        }
        else
        {
            File [] fs = f.listFiles();
            for(File tmpFile: fs)
            {
                isDir(tmpFile);
            }
        }
    }

    public void countImport(File f)
    {
        BufferedReader reader;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = "";
            try
            {
                while((line = br.readLine()) != null)
                {
                    line = line.trim();
                    if(line.startsWith("public")||line.startsWith("class"))//首行没有import,直接跳出
                        break;

                    if(line.startsWith("import"))
                    {
                        String className = line.substring(6, line.length()-1).trim();
                        Integer value = count.get(className);
                        if(value==null)//第一次遇到
                        {
                            count.put(className, 1);
                        }
                        else
                        {
                            count.put(className, value+1);
                        }
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return;
        }
    }

    public void printTop10()//对map进行排序，打印前十个
    {
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(count.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        for (int i = 0; i < 10; i++)
        {
            String id = infoIds.get(i).toString();
            System.out.println(id);
        }
    }

    public static void main(String[] args)
    {
        TopImportClass tic = new TopImportClass("/home/lz/IdeaProjects/javaTest/src");
        tic.printTop10();
    }

}
