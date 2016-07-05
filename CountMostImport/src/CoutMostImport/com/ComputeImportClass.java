package CoutMostImport.com;

import java.io.*;
import java.util.*;


public  class ComputeImportClass {
     //��Ҫ����ʶ����ַ�������
     public static String Identify_String="import" ;
     public HashMap<String,Integer>  ImportClass_number;
    //   File[] sub_file ;
     ComputeImportClass()
     {
         ImportClass_number=new HashMap<String, Integer>();
     }
	 //ɨ���ļ�Ŀ¼�µ����к�׺ΪJava�ļ��������Ŀ¼�����Ŀ¼�����е��ļ���ɨ�裬��������ֱ�����յ����е��ļ�����
    public void Scanner_File(File file)
    {
        if(!file.exists())
            return;
		//����ļ���Ŀ¼
        else if(file.isDirectory()) {
			//�г�Ŀ¼�µ������ļ�
            File[] sub_file = file.listFiles();
            for (File f : sub_file)
                Scanner_File(f);
        }
        else
        {
			//����ļ�����Ŀ¼
            String File_Name=file.getName();
            if(File_Name.endsWith("java"))
                compute_ImprotClass(file);
        }
    }
	//����Java�ļ��е����������
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
			    //�洢����������ֺʹ���
                    String Class_Name= File_Line.substring(7,File_Line.length()-1);
                    Integer count=ImportClass_number.get(Class_Name);
					//��������µ��࣬�����Map
                    if(count==null)
                       ImportClass_number.put(Class_Name,1);
				   //�����ԭ�����ڵ��࣬��Ѵ���ĵ��������1
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
	//��Map���ҳ��������ǰ10���࣬�����뵼��Ĵ���
   public void  Count_TOP10_Class()
   {
       List<Map.Entry<String, Integer>> Class_ArrayList = new ArrayList<Map.Entry<String, Integer>>(
               ImportClass_number.entrySet());
	  //�½�һ���Ƚ��������ڶ�List�е�Ԫ�ؽ�������
       EntryComparator comparator = new EntryComparator();
	   //��List�е�Ԫ�ذ������صıȽϹ����������
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
	   //���������ĸ�������ʮ����ȫ������������������ǰʮ������������
       if(Class_ArrayList.size()<10)
       {
           for(int i=0;i<Class_ArrayList.size();i++)
           {
               Map.Entry<String,Integer> ent=Class_ArrayList.get(i);
               System.out.println("������"+ent.getKey()+"�������Ϊ"+ent.getValue());
           }
       }
       else {
           for(int i=0;i<10;i++)
                      {
                          Map.Entry<String,Integer> ent=Class_ArrayList.get(i);
                          System.out.println("������"+ent.getKey()+"�������Ϊ"+ent.getValue());
                      }
       }
   }
}
