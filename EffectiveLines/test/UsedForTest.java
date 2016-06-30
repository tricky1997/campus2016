
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class LearnFileInputStream {
    public static void main(String[] args){
        String filename= "/Users/lactic_h/Documents/testInput";
        InputStream input=null;
        try{
            input= new FileInputStream(filename);
            int cc= input.read();
            while (cc!=-1){
                System.out.println((char)cc);
            }
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException te){
            te.printStackTrace();
        }finally {
            if(input!=null){
                try {
                    input.close();
                }catch (IOException ce){
                    ce.printStackTrace();
                }
            }
        }
    }
}
