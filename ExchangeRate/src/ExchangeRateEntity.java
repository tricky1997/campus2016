import java.util.Arrays;

/**
 * Created by kuangchunyan on 2016/7/3.
 * 定义人民币汇率中间价的尸体，包括公布汇率的日期以及
 * 人民币对美元、欧元以及港币的汇率
 */
public class ExchangeRateEntity {
    // 公布汇率的日期
    private String date;
    // 汇率中间价（包含美元、欧元、港币）
    private String rates[];

    public ExchangeRateEntity(String date, String[] rates) {
        this.date = date;
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "date='" + date + '\'' +
                ", rates=" + Arrays.toString(rates) +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getRates() {
        return rates;
    }

    public void setRates(String[] rates) {
        this.rates = rates;
    }
}

