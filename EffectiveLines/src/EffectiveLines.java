import java.io.*;

/**
 * Created by Wang on 2016/6/18.
 */
public class EffectiveLines {
    private static String path;
    public static void main(String[] args) {
        startCountLines();
    }

    private static void startCountLines() {
        System.out.print("please input the Java file's path: ");
        path = getInputPath();
        System.out.println();
        boolean isFileExist = fileExist(path);
        if(!isFileExist) {
            System.out.println("the file(" + path + ") does not exist");
            return;
        }
        int lines = countLines(path);
        System.out.println("this java file's line is " + lines);
    }

    private static String getInputPath() {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        try {
            return buf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no input";
    }

    private static boolean fileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    private static int countLines(String path) {
        int lines = 0;
        File file = new File(path);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while((tempString = reader.readLine()) != null) {
                String trimString = tempString.trim();
                //only consider line starting with "//" and empty line
                if(!trimString.startsWith("//") && trimString.length() > 0) {
                    lines++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
