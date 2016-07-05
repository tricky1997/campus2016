package CoutMostImport.com;

import java.io.*;
import java.util.*;


public  class ComputeImportClass {
     //需要进行识别的字符串名字
     public static String Identify_String="import" ;
     public HashMap<String,Integer>  ImportClass_number;
    //   File[] sub_file ;
     ComputeImportClass()
     {
         ImportClass_number=new HashMap<String, Integer>();
     }
	 //扫描文件目录下的所有后缀为Java文件，如果是目录则进行目录下所有的文件的扫描，迭代运算直至最终的所有的文件结束
    public void Scanner_File(File file)
    {
        if(!file.exists())
            return;
		//如果文件是目录
        else if(file.isDirectory()) {
			//列出目录下的所有文件
            File[] sub_file = file.listFiles();
            for (File f : sub_file)
                Scanner_File(f);
        }
        else
        {
			//如果文件不是目录
            String File_Name=file.getName();
            if(File_Name.endsWith("java"))
                compute_ImprotClass(file);
        }
    }
	//计算Java文件中导入类的数据
    public void  compute_ImprotClass(File file)
    {
        if(!file.exists())
                     return;
      //  FileReader s= null;
        try {
           // s = new FileReader(file);
            BufferedReader Java_File=new BufferedReader(new FileReader(file));
            String s1="";
            while((s1=Java_File.readLine())!=null)
            {
                String File_Line=s1.trim();
                if(File_Line.startsWith("import"))
                {
               //    int index=File_Line.indexOf('j');
			    //存储导入类的名字和次数
                    String Class_Name= File_Line.substring(7,File_Line.length()-1);
                    Integer count=ImportClass_number.get(Class_Name);
					//如果导入新的类，则加入Map
                    if(count==null)
                       ImportClass_number.put(Class_Name,1);
				   //如果是原来存在的类，则把此类的导入次数加1
                     else
                        ImportClass_number.put(Class_Name,++count);
                }
            }
          Java_File.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }


    }
	//从Map中找出导入次数前10的类，并输入导入的次数
   public void  Count_TOP10_Class()
   {
       List<Map.Entry<String, Integer>> Class_ArrayList = new ArrayList<Map.Entry<String, Integer>>(
               ImportClass_number.entrySet());
	  //新建一个比较器，用于对List中的元素进行排序
       EntryComparator comparator = new EntryComparator();
	   //对List中的元素按照重载的比较规则进行排序
       Collections.sort(Class_ArrayList, comparator);
      /* Collections.sort(Class_ArrayList, new Comparator<Map.Entry<String, Integer>>() {
       public int compare(Map.Entry<String, Integer> o1,
       Map.Entry<String, Integer> o2) {
           if(o1.getValue()==o2.getValue())
           {
               return o1.getKey().compareTo(o2.getKey());
           }
           else
               return o1.getValue()-o2.getValue();
       }
       });      */
	   //如果导入类的个数不足十个，全部进行输出，否则输出前十个次数最多的类
       if(Class_ArrayList.size()<10)
       {
           for(int i=0;i<Class_ArrayList.size();i++)
           {
               Map.Entry<String,Integer> ent=Class_ArrayList.get(i);
               System.out.println("导入类"+ent.getKey()+"，其次数为"+ent.getValue());
           }
       }
       else {
           for(int i=0;i<10;i++)
                      {
                          Map.Entry<String,Integer> ent=Class_ArrayList.get(i);
                          System.out.println("导入类"+ent.getKey()+"，其次数为"+ent.getValue());
                      }
       }
   }
}
