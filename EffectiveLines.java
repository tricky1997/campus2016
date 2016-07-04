import java.io.*;

public class EffectiveLines {

    public static void main(String[] args) {
        int codeLine = 0;
        String path = "c://EffectiveLines.java";
        BufferedReader br = null;
        boolean comment = false;
        try {
            br = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            try {
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.trim().equals("") || line.startsWith("/*") || line.endsWith("*/") || line.startsWith("//")) {
                        continue;
                    }
                    codeLine++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    System.out.println("代码行数："+codeLine);
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    }