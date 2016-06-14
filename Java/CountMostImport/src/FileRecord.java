import java.io.*;
import java.util.*;

/**
 * Created by TOSHIBA on 2016/6/12.
 */

public class FileRecord {

    public static Map<String, Integer> record; // 引入的包的次数信息

    // 从路径为filePath的Java文件中中读取引入包的情况
    public static void readPackageInfoFromFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (line.toLowerCase().startsWith("import ")) {
                //System.out.println(line);
                String[] importParts = line.split(" ");
                if (importParts.length == 2) {
                    if (!importParts[1].endsWith("*;")) {
                        String importClassName = importParts[1].substring(0, importParts[1].length() - 1);
                        Integer freq = record.get(importClassName);
                        record.put(importClassName, freq == null ? 1 : freq + 1);
                    } else {
                        System.out.println(filePath + "中包含 " + importParts[1] + " 中的所有类，不属于统计范围");
                    }
                } else {
                    System.out.println(filePath + " 中存在错误import语句 ：" + line);
                }
            }
        }
        br.close();
    }

    // 扫描指定路径path下的全部Java文件，查看包引入情况并按次数排序
    public static void scanJavaFile(String path) {
        FileReader fileReader = new FileReader();
        if (!fileReader.readEndnameFile(path, ".java")) {
            System.out.println("非法路径！");
            return;
        }
        record = new HashMap<String, Integer>();
        for (String targetPath : fileReader.targetFileList) {
            try {
                readPackageInfoFromFile(targetPath);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        //按照次数排序
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(record.entrySet());

        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        int rankNum = 1;
        int countNum = 0;
        int latestFreq = Integer.MAX_VALUE;
        System.out.print("排名(次数) : 类名1, 类名2, ..., 类名n(并列的情况)");
        //输出被import次数前10位的类（次数相同的并列输出）
        while (rankNum < 10 && countNum < infoIds.size()) {
            Map.Entry<String, Integer> e = infoIds.get(countNum);
            int freq = e.getValue();
            if (freq == latestFreq) {
                System.out.print(" \t" + e.getKey());
                countNum++;
            } else if (freq < latestFreq) {
                latestFreq = freq;
                if (countNum + 1 > 10)
                    break;;
                rankNum = countNum + 1;
                System.out.print("\n" + rankNum + "(" + freq + ")" + " : " + e.getKey());
                countNum++;
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Java文件夹路径：");
        try {
            String strPath = br.readLine();
            scanJavaFile(strPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
