import java.io.*;
import java.util.*;

/**
 * Created by daihy on 2016/6/19.
 * Statistical top 10 imported class in java files given a root path.
 */
public class CountMostImport {

    /*store all java files*/
    private List<File> fileList = new ArrayList<File>();
    /*map from class to imported count*/
    private HashMap<String, Integer> import2CNT = new HashMap<String, Integer>();

    public static void main(String[] args) {
        CountMostImport CMI = new CountMostImport();
        String src = "E:/exercise/";
        CMI.getAllJavaFiles(src);
        CMI.gatherImportCNT();
        CMI.printTop10();
    }

    /**
     * recursively get all java files
     * @param path root path
     */
    void getAllJavaFiles(String path){
        File root = new File(path);

        if (!root.isDirectory()) return;

        for (File file : root.listFiles()){
            if (file.isFile() && file.getName().endsWith(".java")){
                fileList.add(file);
            }
            else if (file.isDirectory()){
                getAllJavaFiles(file.getPath());
            }
        }
    }

    /**
     * Get each class imported count.
     */
    void gatherImportCNT() {
        for (File file : fileList) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    while (line.trim().contains("import")) {
                        int index1 = line.trim().indexOf("import");
                        int index2 = line.trim().indexOf(";");
                        if (index2 == -1) break;
                        String impCls = line.trim().substring(index1 + 7, index2).trim();
                        if (!impCls.contains("*")) {
                            if (import2CNT.containsKey(impCls)) {
                                import2CNT.put(impCls, import2CNT.get(impCls) + 1);
                            } else {
                                import2CNT.put(impCls, 1);
                            }
                        }
                        line = line.substring(index2 + 1);
                    }

                    if (line.startsWith("class") || line.startsWith("public")) {
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("file not fount!");
            } catch (IOException e) {
                System.out.println("read file failed!");
            }
        }
    }

    /**
     * Print top 10 class.
     */
    void printTop10(){
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(import2CNT.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if(o1.getValue() == o2.getValue())
                    return o1.getKey().compareTo(o2.getKey());
                return o2.getValue() - o1.getValue();
            }
        });

        int cnt = (list.size() > 10) ? 10 : list.size();
        for(int i = 0; i < cnt; i++){
            String cls = list.get(i).getKey();
            int num = list.get(i).getValue();
            System.out.println("Top " + (i + 1) + " class name: " + cls + "  count: " + num);
        }
    }
}
