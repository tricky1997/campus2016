import java.io.*;

/**
 * Created by lqs on 2016/6/25.
 */
public class EffectiveLines {

    /**
     * @param path Java file path in string
     * @return Effective lines in the java file
     */
    public static long countEffectiveLines(String path) {
        File sourceFile = new File(path);
        return countEffectiveLines(sourceFile);
    }

    /**
     * @param file Java file path
     * @return Effective lines
     */
    public static long countEffectiveLines(File file) {
        long effectiveLines = 0L;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {  // Java7 try with resources
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                //process perline
                String trimedLine = line.trim();
                if(trimedLine.equals("") || trimedLine.startsWith("//"))
                    continue;
                else
                    effectiveLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return effectiveLines;
    }

    public static final void usage() {
        System.out.println("Usage:\n" +
                "EffectiveLines filename\n" +
                "Output: effective lines in the java file\n" +
                "A line is considered effective if it is not a blank line nor a comment line\n" +
                "Multi line comment is not in consideration");
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            usage();
            return;
        }
        System.out.println(EffectiveLines.countEffectiveLines(args[0]));
    }
}
