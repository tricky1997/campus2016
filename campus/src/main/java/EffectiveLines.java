import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by andrew on 2016/6/16.
 */
public class EffectiveLines {
    private static List<String> lines;

    public static int countValidityLines(String filePath, String encoding) throws IOException{
        int total = countTotalLines(filePath, encoding);
        int exclude = countBlankAndAnnotion();
        return total - exclude;
    }

    public static int countTotalLines(String filePath, String encoding) throws IOException {
        lines = FileUtils.readLines(new File(filePath), encoding);
        return lines.size();
    }

    private static int countBlankAndAnnotion(){
        int num = 0;
        for (String line : lines){
            if (StringUtils.isBlank(line)){
                num++;
                continue;
            }
            if (line.startsWith("//") || line.startsWith("/*")){
                num++;
                continue;
            }
        }
        return num;
    }
}
