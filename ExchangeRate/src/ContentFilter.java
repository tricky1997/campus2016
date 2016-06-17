/**
 * Created by Administrator on 2016/6/4 0004.
 * 用于抽象字符串的过滤过程，由于作业中多次需要文件过滤，
 * 而每次进行过滤的规则不同，因此将文件过滤抽象为一个接口，
 * 每个不同的过滤规则需要实现此接口
 */
public interface ContentFilter {
    public String filter(String string);
}
