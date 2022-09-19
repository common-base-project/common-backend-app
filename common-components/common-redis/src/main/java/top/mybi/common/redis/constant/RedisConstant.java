package top.mybi.common.redis.constant;

/**
 * redis 工具常量
 */
public class RedisConstant {
    private RedisConstant() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
