import java.io.*;

/**
 * Created by daihy on 2016/6/19.
 * Statistical effective lines of a java file
 */
public class EffectiveLines {
    public static void main(String[] args) {
        String file = "EffectiveLines.java";
        String msg = "File " + file + " has " + EffectiveLines.readFile(file) + " effective lines!";
        System.out.println(msg);
    }

    public static int readFile(String fileName) {
        int eff_line = 0;
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                /*filter blank line*/
                if (line.trim().equals("")) continue;
                /*filter annotations*/
                if (line.trim().startsWith("/*") || line.trim().startsWith("*")) continue;
                if (line.trim().startsWith("//")) continue;
                /*effective line increase 1*/
                eff_line += 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not exists!");
        } catch (IOException e) {
            System.out.println("read file problem!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("close file problem");
                }
            }
        }
        return eff_line;
    }
}
