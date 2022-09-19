package top.mybi.user.constants;

import static top.mybi.common.core.constants.RedisConstants.USER_CENTER_KEY;

/**
 * @Author mustang
 * @date 2022/1/20 14:51
 */
public interface UserRedisConstants {
    /**
     * 用户信息redis key
     */
    String USER_INFO_KEY=USER_CENTER_KEY+"user:userId-%d";
    /**
     * 角色编码
     */
    String REDIS_KEY_ROLE_CODE = USER_CENTER_KEY+"role:roleCode-%s";
    /**
     * 机构信息
     */
    String REDIS_KEY_ORGID = USER_CENTER_KEY+"organization:orgId-%d";
}
