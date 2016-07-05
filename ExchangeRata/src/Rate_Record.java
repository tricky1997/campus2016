/**
 构造一个汇率记录类，保存当前日期和美元、欧元、港币对人民币的汇率，其中的函数通过ALT+Insert快捷键选择加入即可
 */
public class Rate_Record {
    private String  Hkdcny;  //港币对人民币汇率
    private String  Eurcny;  //欧元对人民币汇率
    private String  Usdcny;  //美元对人民币汇率
    private String  date;    //当前计算的时间

    public Rate_Record() {
       }
    public Rate_Record(String hkdcny, String eurcny, String usdcny, String date) {
        Hkdcny = hkdcny;
        Eurcny = eurcny;
        Usdcny = usdcny;
        this.date = date;
    }



    public String getEurcny() {
        return Eurcny;
    }

    public String getHkdcny() {
        return Hkdcny;
    }

    public String getUsdcny() {
        return Usdcny;
    }

    public String getDate() {
        return date;
    }

    public void setHkdcny(String hkdcny) {
        Hkdcny = hkdcny;
    }

    public void setEurcny(String eurcny) {
        Eurcny = eurcny;
    }

    public void setUsdcny(String usdcny) {
        Usdcny = usdcny;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rate_Record{" +
                "Hkdcny='" + Hkdcny + '\'' +
                ", Eurcny='" + Eurcny + '\'' +
                ", Usdcny='" + Usdcny + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
