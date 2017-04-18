/**
 * Created by Administrator on 2016/5/4.
 */
public class o {
    private static o ourInstance = new o();

    public static o getInstance() {
        return ourInstance;
    }

    private o() {
    }
}
