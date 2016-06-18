import java.util.Arrays;

/**
 * Created by andrew on 16-6-18.
 */
public class ExchangeRate {
    // 公布汇率的日期
    private String date;
    // 汇率中间价（包含美元、欧元、港币）
    private String rates[];

    public ExchangeRate(String date, String[] rates) {
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
