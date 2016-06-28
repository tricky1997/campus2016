import java.io.*;

/**
 * Created by cjq on 2016/6/14.
 */
public class EffectiveLines {
    private File file = null;

    public EffectiveLines(File file) {
        this.file = file;
    }

    public int getEffectiveLines() {
        String temp = null;
        int effectiveLines = 0;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(this.file));

            while(true) {
                temp = bf.readLine();
                if(temp == null)
                    break;
                if((!temp.trim().startsWith("/")) && (!temp.trim().equals("")))
                    effectiveLines++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return effectiveLines;
    }

    public static void main(String args[]) {
        File file = new File("C:/Users/cjq/Desktop/Candy.java");
        if(!file.getName().endsWith(".java")){
            System.out.println("输入文件有误，不是java文件");
        }
        else {
            EffectiveLines jFile = new EffectiveLines(file);
            System.out.println(jFile.getEffectiveLines());
        }
    }
}
