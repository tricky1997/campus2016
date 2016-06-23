import java.util.*;
import java.io.*;

public class EffectiveLines
{
	public static void main(String[] args)throws IOException
	{
	   System.out.println(read("test.java"));
	}
	public static String read(String filename) throws IOException
	{
       BufferedReader in = new BufferedReader(new FileReader(filename));
       String s;
       int result = 0;
       boolean comment = false;

       while((s = in.readLine()) != null)
       {
          s = s.trim();  
          if(s.matches("^[//s&&[^//n]]*$")) {  
             continue;  
          } 
          else if(s.startsWith("/*") && !s.endsWith("*/")) {   
              comment = true;  
          }else if (true == comment){   
              if (s.endsWith("*/")) {  
                  comment = false;  
                 }  
          }else if(s.startsWith("//")) {  
                  continue;  
          }else{  
               result++;  
          }  
       }
       return Integer.toString(result);
	}
}
