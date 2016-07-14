/**
 * Created by zhaozhenyu on 16-7-14.
 */


import java.util.ArrayList;
        import java.util.Map;

public class Main {
    private static final String path=CountMostImport.class.getResource("").getPath();
    public static void main(String args[]) {

        String filename =path+"/file";
        System.out.println(filename);
        CountMostImport countMostImport=new CountMostImport(filename);
        ArrayList<Map.Entry<String,Integer>> list =countMostImport.getTop10MostImportClass();
        for(int i=0;i<list.size()&&i<10;i++)
            System.out.println(list.get(i).getKey()+" : "+list.get(i).getValue());
    }
}