package easyrpc.test;

/**
 * @Auther: hlj
 * @Date: 2019/1/16 14:24
 * @Description:
 */
public class TestImpl implements Test{
    @Override
    public String hello() {
        return "HELLO WORLD";
    }
}
