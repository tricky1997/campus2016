package CoutMostImport.com;

import java.io.File;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2016-7-2
 * Time: 22:23:20
 * To change this template use File | Settings | File Templates.
 */
public class CoutMostImprot_Main {

    public static void main(String[] args) {
        ComputeImportClass computeimportclass=new ComputeImportClass();
       // Scanner in=new Scanner(System.in);
       // String input_s="";
        //while(true)
      //  {
       //     String str = in.nextLine();
      //      if(str.equals("exit"))
     //           break;
     //       input_s=str;
      //  }
       // File Java_file=new File(input_s);
        File Java_file=new File("F:\\CountMostImport\\File");
        computeimportclass.Scanner_File(Java_file);
        computeimportclass.Count_TOP10_Class();

}

}
