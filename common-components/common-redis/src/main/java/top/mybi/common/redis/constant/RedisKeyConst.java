package top.mybi.common.redis.constant;

/**
 * Redis Key 前缀常量
 * @Author mustang
 * @date 2022年6月12日16:18:27
 * @version 1.0
 */
public interface RedisKeyConst {

    /**
     * 公共前缀
     */
    String PUBLIC_PREFIX = "swartz:poyitong:";

    /**
     * 后台系统前缀
     */
    String BACKSTAGE_PREFIX = PUBLIC_PREFIX + "backstage:";

    /**
     * 账户服务系统前缀
     */
    String ACCOUNT_PREFIX = PUBLIC_PREFIX + "account:";

    /**
     * 案件服务系统
     */
    String CASE_PREFIX = PUBLIC_PREFIX + "case:";

    /**
     * 债权服务
     */
    String CREDITOR_PREFIX = PUBLIC_PREFIX + "creditor:";


    /**
     * 工具服务
     */
    String TOOL_PREFIX = PUBLIC_PREFIX + "tool:";

    /**
     * 导出服务
     */
    String EXPORT_PREFIX = PUBLIC_PREFIX + "export:";

    /**
     * 网关服务
     */
    String GATEWAY_PREFIX = PUBLIC_PREFIX + "gateway:";

    /**
     * 锁
     */
    String LOCK_PREFIX = PUBLIC_PREFIX + "lock";

    /**
     * 转换缓存Key
     * @param prefix 前缀
     * @param key 键
     * @return 最终键值
     */
    static String serializeKey(String prefix, String key){
        if (prefix.endsWith(":")){
            return prefix + key;
        }else {
            return prefix + ":" + key;
        }
    }
}
