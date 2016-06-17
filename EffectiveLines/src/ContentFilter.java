/**
 * Created by Administrator on 2016/6/4 0004.
 * 抽象出的过滤内容接口，在MostImportCount中也需要用到
 * 重写过滤方法需实现此接口
 */
public interface ContentFilter {
    public String filter(String string);
}
