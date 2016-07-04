public class Main {

    public static void main(String[] args) {
		//新建一个中间汇率类
        ExchangeData exchangeData=new ExchangeData();
		//解析其中符合要求的币种对人民币的汇率
        ExchangeData.Pars_HTML();
		//导出成xls进行输出
        ExchangeData.Export_Excel("Rate.xls");

    }
}
