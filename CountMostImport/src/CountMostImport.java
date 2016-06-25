import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wang on 2016/6/22.
 */
public class CountMostImport {
    public static Map<String, Integer> importLibCount =
            new HashMap<String, Integer>();
    private static final int N = 10;
    public static void main(String[] args) {
        CountMostImport countMostImport = new CountMostImport();
        System.out.print("请输入文件目录:");
        String folder = getInputPath();
        countMostImport.traverseFolderAndCountImport(folder);
        int[] biggestTenArray = countMostImport.getBiggistTenNumber(importLibCount);
        String[] mostTenImportLib = countMostImport.getMostTenImportLib(importLibCount, biggestTenArray);
        countMostImport.printResult(biggestTenArray, mostTenImportLib);

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

    private void traverseFolderAndCountImport(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolderAndCountImport(file2.getAbsolutePath());
                    } else {
                        if(!file2.getAbsolutePath().endsWith(".java"))
                            continue;
                        System.out.println("正在处理文件：" + file2.getAbsolutePath());
                        readFile(file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    private void readFile(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return;
        }
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //noinspection Since15
            while((tempString = reader.readLine()) != null && !tempString.isEmpty()) {
                countImport(tempString);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void countImport(String lineString) {
        //处理一行写1个、多个import的情况
        String[] imports = lineString.split(";");
        for(String importString: imports) {
            if(importString.trim().startsWith("import")) {
                String importedClass = importString.split(" ")[1];
                if(importLibCount.get(importedClass) == null)
                    importLibCount.put(importedClass, 1);
                else {
                    int count = importLibCount.get(importedClass);
                    importLibCount.put(importedClass, count + 1);
                }
            }
        }
    }

    private int[] getBiggistTenNumber(Map<String, Integer> map) {
        MinHeap minHeap = new MinHeap(N);
        int count=0;
        for(Integer i : map.values()) {
            if(minHeap.isNumberInHeap(i))
                continue;
            if(count < N) {
                minHeap.add(count, i);
                count++;
            } else {
                minHeap.addAndAdjustTopN(i);
            }
        }
        return minHeap.getArray();
    }

    private String[] getMostTenImportLib(Map<String, Integer> importLibCount, int[] biggestTen) {
        String[] result = new String[biggestTen.length];
        for(Map.Entry<String, Integer> entry : importLibCount.entrySet()) {
            int index = descendBinarySearch(biggestTen, entry.getValue());
            if(index < 0)
                continue;
            //处理有同样引用次数的类
            //noinspection Since15
            if(result[index] == null) {
                result[index] = entry.getKey();
            } else {
                result[index] = result[index] + ";" + entry.getKey();
            }
        }
        return  result;
    }

    public static int descendBinarySearch(int[] array, int number) {
        int low = 0, high = array.length - 1;
        while(low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = array[mid];
            if(midVal == number)
                return mid;
            else if(midVal < number)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -(low + 1);
    }

    private void printResult(int[] biggestTen, String[] mostTenImport) {
        if(biggestTen[0] == 0)
            return;
        for(int i=0; i<biggestTen.length; i++) {
            if(mostTenImport[i] == null)
                return;
            System.out.print((i+1) + ":" + biggestTen[i] + "  ");
            //处理有多个类的情况，依据字母表进行排序
            String[] importStrings = mostTenImport[i].split(";");
            Arrays.sort(importStrings);
            for(String s : importStrings) {
                System.out.print(s + " ;");
            }
            System.out.println();
        }
    }
}


