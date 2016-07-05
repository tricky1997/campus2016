package com.qunar.exchangerate;

/**
 * Created by Charmy on 2016/6/30.
 */
public class ResultData {
    private String date;//日期,格式 YYYY-MM-DD
    private String dollar;//美元
    private String euro;//欧元
    private String hkd;//港元

    public void setDate(String date) {
        this.date = date;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }

    public void setEuro(String euro) {
        this.euro = euro;
    }

    public void setHkd(String hkd) {
        this.hkd = hkd;
    }

    public String getDate() {
        return date;
    }

    public String getDollar() {
        return dollar;
    }

    public String getEuro() {
        return euro;
    }

    public String getHkd() {
        return hkd;
    }


}
