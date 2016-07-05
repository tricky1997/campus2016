import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sun.rowset.internal.Row;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 ��Ҫͨ��Jsoup������ҳ�����ݵĽ���
 */
public class ExchangeData {
	//���ʼ�¼�б�
    private static List<Rate_Record> Rate_List;

    public ExchangeData() {
        Rate_List = new ArrayList<Rate_Record>();
    }
//������ַ��Ӧ��Jsoup���н������ҳ�30���ڲ�ͬ����Ļ��Ҷ�����Ļ���
    public static Document getDataByJsoup(String url) {
        Document doc2 = null;
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        //��ȡ��ǰ����
        Calendar C_Time = Calendar.getInstance();
        //��ȡ��ǰ����ǰ30�������
        Calendar P_Time = Calendar.getInstance();
        P_Time.add(P_Time.DATE, -30);
        try {
			//��ȡһ���µ����ӣ���get()ȡ�úͽ���һ��HTML
            doc2 = Jsoup.connect(url).timeout(6000)
                    .data("projectBean.startDate", Date_Format.format(P_Time.getTime()))
                    .data("projectBean.endDate", Date_Format.format(C_Time.getTime()))
                    .get();
            String title = doc2.body().toString();
        } catch (SocketTimeoutException e) {
            System.out.println("Socket���ӳ�ʱ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc2;
    }
   //��Jsoup����������ҳ���з���
    public static void Pars_HTML() {
       //���������xls����е�һ������
        Rate_Record Zero_record = new Rate_Record("�۱�", "ŷԪ", "��Ԫ", "����");
        Rate_List.add(Zero_record);
		//��ץȡ����ҳ����
        Document doc = ExchangeData.getDataByJsoup
                ("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action");
        String title = doc.body().toString();
		//����ҳ���ݿ��Է������õ����еĻ������ݶ�����first�����棬����ֻ���class=��first���µĽڵ���н���
        Elements elements = doc.getElementsByClass("first");
        for (Element element : elements) {
			//����ҳ���ݿ��Է��֣�������������ݵ�ʱ�䡢�۱Ҷ�����ҡ�ŷԪ������ҡ���Ԫ������ҷֱ�λ��0��4��1��2�ӽڵ��С�
            Rate_Record temp_record = new Rate_Record();
            temp_record.setDate(element.child(0).text());
            temp_record.setEurcny(element.child(2).text());
            temp_record.setHkdcny(element.child(4).text());
            temp_record.setUsdcny(element.child(1).text());
			//��һ����¼������ʼ�¼��
            Rate_List.add(temp_record);

        }
    }
    public static  void Export_Excel(String Excel_Name) {
        File Excel_File = new File(Excel_Name);
        String worksheet = "����Ҷ���ŷ��Ԫ����";//�����excel�ļ���������
        WritableWorkbook workbook;
        try {
            //������д���Excel������,�������ɵ��ļ���tomcat/bin��
            //workbook = Workbook.createWorkbook(new File("output.xls"))
            workbook = Workbook.createWorkbook(Excel_File);
            WritableSheet sheet = workbook.createSheet(worksheet, 0); //��ӵ�һ��������
            for (int i = 0; i < Rate_List.size(); i++) {
				//��Ӷ�Ӧ��ǩ�µ����ݵ�xls��Ӧ�����
                jxl.write.Label Date_label = new jxl.write.Label(0, i, Rate_List.get(i).getDate());  //��ǰ��������
                jxl.write.Label HK_label = new jxl.write.Label(1, i, Rate_List.get(i).getHkdcny());  //�۱Ҷ�����ҵĻ�������
                jxl.write.Label EU_label = new jxl.write.Label(2, i, Rate_List.get(i).getEurcny());  //ŷԪ������ҵĻ�������
                jxl.write.Label US_label = new jxl.write.Label(3, i, Rate_List.get(i).getUsdcny());  //��Ԫ������ҵĻ�������
                sheet.addCell(Date_label);
                sheet.addCell(HK_label);
                sheet.addCell(EU_label);
                sheet.addCell(US_label);
            }
            workbook.write();
            workbook.close();

        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}