/* Created by Jerry on 6/22/2016.*/


import java.util.Scanner;
/*Jerry*/
public class Test {
    //test
    public static void main(String [] args){
        System.out.println("ÇëÊäÈëË÷Òª¼ì²âµÄ×Ö·û´®:");
        Scanner s = new Scanner(System.in);
        String str = s.next();
        s.close();
        int start = 0;
        int end = str.length()-1;
        while(str.charAt(start)==str.charAt(end)){
            start ++;
            end--;
            if(start>end){
                System.out.println("Yes");
                break;
            }

        }
        if(start<end){
            System.out.println("No");
        }
    }
}

