/**
 * Created by xuxingbo on 2016/6/20.
 */

/**
 * 由于数据将被写入到execl中，所以全部用String存储免得转换
 */
public class Record {
    //日期
    private String date;
    //美元
    private String dollar;
    //欧元
    private String euro;
    //港币
    private String hk;

    public Record() {
    }

    public Record(String date, String dollar, String euro, String hk) {
        this.date = date;
        this.dollar = dollar;
        this.euro = euro;
        this.hk = hk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }

    public String getEuro() {
        return euro;
    }

    public void setEuro(String euro) {
        this.euro = euro;
    }

    public String getHk() {
        return hk;
    }

    public void setHk(String hk) {
        this.hk = hk;
    }

    @Override
    public String toString() {
        return "Record{" +
                "date='" + date + '\'' +
                ", dollar='" + dollar + '\'' +
                ", euro='" + euro + '\'' +
                ", hk='" + hk + '\'' +
                '}';
    }
}
