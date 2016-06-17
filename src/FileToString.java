import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/6/4 0004.
 * 用以将文件内容读到对象的工具
 */
public class FileToString {
    //文件路径
    private String filePath;
    //打开的文件
    private File openFile;

    /**
     * getter方法
     * @return
     * 返回打开的文件
     */
    public File getFile(){
        return openFile;
    }

    /**
     * 通过文件路径来初始化的有参构造函数
     * @param filePath
     * 文件或者目录路径
     */
    public FileToString(String filePath){
        openFile=new File(filePath);
    }

    /**
     * 默认构造函数
     */
    public FileToString(){

    }
    /**
     * 将文件内容读入一个List，每行为其中一个元素
     * @return
     * 切分后的结果，每一个元素代表一个切分结果
     */
    public List<String> readToList(){
        //使用换行符切分
        String[] splitResult=readToString().split("\n");
        //存入List中
        List<String> textAsList=new ArrayList<>(Arrays.asList(splitResult));
        //split通常会在最前面形成一个空字符串，删除它
        if(textAsList.get(0).equals("")){
            textAsList.remove(0);
        }
        return textAsList;
    }
    /**
     * 将文件内容读入一个字符串中
     * @return
     * 返回文件中的全部内容
     *
     */
    public String readToString(){
        //为增加效率，使用初始大小为1000的StringBuffer
        if(!openFile.isFile()){
            System.out.println("此路径并非一个文件！");
            System.exit(0);
        }
        StringBuffer stringBuffer=new StringBuffer(1000);
        try{
            //以有缓冲区的方式打开输入流
            BufferedReader bufferedReader=new BufferedReader(new FileReader(openFile));
            try{
                String string;
                //以行为单位读入
                while((string=bufferedReader.readLine())!=null){
                    stringBuffer.append(string);
                    stringBuffer.append("\n");
                }
            }catch (IOException e){
                System.out.println("输入流打开异常！");
                System.exit(0);
            }finally {
                //关闭输入流
                bufferedReader.close();
            }
        }catch (FileNotFoundException e){
            System.out.println("文件打开异常！");
            System.exit(0);
        }catch (IOException e){
            System.out.println("输入流关闭异常！");
            System.exit(0);
        }
        return stringBuffer.toString();
    }

    /**
     * 带有参数的文件读入函数，主要是为了在多个嵌套目录时使用
     * @param file
     * 要读入的文件
     * @return
     * 文件的内容
     */
    public String readToString(File file){
        this.openFile=file;
        return readToString();
    }

}
