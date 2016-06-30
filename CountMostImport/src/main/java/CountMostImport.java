/**
 * Created by KL on 2016/6/29.
 */

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Ordering;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.primitives.Ints.min;
import static java.lang.Math.PI;

public class CountMostImport {
    //Match files with suffix java
    private static final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.java");
    //Most N imported classes
    private static final int mostN = 1000;
    private List<File> javaFiles = new ArrayList<File>();
    //Scan path for java files
    Path path = Paths.get("E:\\code\\work\\campus2016");


    private void getJavaFiles() {
        SimpleFileVisitor<Path> javaFinder = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(matcher.matches(file))
                    javaFiles.add(file.toFile());
                return super.visitFile(file, attrs);
            }
        };
        try {
            java.nio.file.Files.walkFileTree(path, javaFinder);
            System.out.println("files = " + javaFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map.Entry<String, Long>> countMostImportClass() {
        Map<String, Long> result = new HashMap<>();
        getJavaFiles();

        for (File f : javaFiles) {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {  // Java7 try with resources
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    //process perline
                    String trimedLine = line.trim();
                    if(!trimedLine.startsWith("import"))
                        continue;
                    //import statement
                    List<String> pieces = Splitter.on(CharMatcher.anyOf(" ;"))
                            .trimResults()
                            .omitEmptyStrings()
                            .splitToList(trimedLine);
                    if (pieces.get(pieces.size() - 1).endsWith("*"))
                        continue;
                    else if(pieces.size() == 3) {
                        //Static import
                        String methodName = pieces.get(pieces.size() - 1);
                        String classQualifiedName = methodName.substring(0, methodName.lastIndexOf('.'));
                        result.put(classQualifiedName, result.containsKey(classQualifiedName) ? result.get(classQualifiedName) + 1 : 1);
                    } else if(pieces.size() == 2) {
                        // None static
                        String classQualifiedName = pieces.get(pieces.size() - 1);
                        result.put(classQualifiedName, result.containsKey(classQualifiedName) ? result.get(classQualifiedName) + 1 : 1);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }// for end

        //Guava Ordering based on map.value
        Ordering<Map.Entry<String, Long>> valueOrdering = Ordering.natural().onResultOf(
                new Function<Map.Entry<String, Long>, Long>() {
                    @Override
                    public Long apply(Map.Entry<String, Long> entry) {
                        return entry.getValue();
                    }
                }).reverse();

        List< Map.Entry<String, Long>> l = valueOrdering.sortedCopy(result.entrySet());
        return l.subList(0, min(l.size(), mostN));
    }


    public static void main(String[] args) {
        System.out.println(PI);
        List<Map.Entry<String, Long>> l = new CountMostImport().countMostImportClass();
        System.out.println(l);
    }
}
