package top.mybi.common.core.constants;


import java.util.Arrays;
import java.util.List;

/**
 * @Description 全局静态常量
 * @Author mustang
 * @Date 2022年6月26日13:49:10
 * @Version 1.0
 */
public interface GlobalConstants {
    /**
     * 默认日期时间格式
     */
    String DEFAULT_DATE_TIME_PATTEN = "yyyy-MM-dd HH:mm:ss";
    String DEFAULT_DATE_TIME_MINUTE_PATTEN = "yyyy-MM-dd HH:mm";

    /**
     * 默认日期格式
     */
    String DEFAULT_DATE_PATTEN = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    String DEFAULT_TIME_PATTEN = "HH:mm:ss";

    /**
     * 8位日期格式
     */
    String DATE_PATTEN_EIGHT = "yyyyMMdd";

    /**
     * 16位日期格式
     */
    String DATE_PATTEN_SIXTH = "yyyyMMddHHmmss";

    /**
     *
     */
    String CHARSET = "UTF-8";

    /**
     * 导出单次查询数据库条数
     */
    Integer EXPORT_SIZE = 200;

    /**
     * 案件任务阶段选择范围
     */
    List<Integer> TASK_SELECTION_RANGE = Arrays.asList(1, 2, 3, 9, 10, 99);

    /**
     * 进度：1:第一次债权人会议前阶段债权申报、审查，2:重整事务处理阶段，3：和解事务阶段处理阶段，4：资产变现，5：资产分配，6：终结程序
     * 案件进度（清算）
     */
    List<Integer> SCHEDULE_QS = Arrays.asList(1, 4, 5, 6);
    List<String> SCHEDULE_QS_NAME = Arrays.asList("第一次债权人会议前阶段债权申报、审查","资产变现","资产分配","终结程序");
    /**
     * 案件进度（重整）
     */
    List<Integer> SCHEDULE_CZ = Arrays.asList(1, 2, 4, 6);
    List<String> SCHEDULE_CZ_NAME = Arrays.asList("第一次债权人会议前阶段债权申报、审查","重整事务处理阶段",
            "资产变现","终结程序");
    /**
     * 案件进度（和解）
     */
    List<Integer> SCHEDULE_HJ = Arrays.asList(1, 3, 4,6);
    List<String> SCHEDULE_HJ_NAME = Arrays.asList("第一次债权人会议前阶段债权申报","和解事务阶段处理阶段","资产变现","终结程序");

    /**
     * 进度：1:第一次债权人会议前阶段债权申报、审查，2:重整事务处理阶段，3：和解事务阶段处理阶段，4：资产变现，5：资产分配，6：终结程序
     * 案件进度（强制清算）
     */
    List<Integer> SCHEDULE_QZQS = Arrays.asList(1, 4, 5, 6);
    List<String> SCHEDULE_QZQS_NAME = Arrays.asList("第一次债权人会议前阶段债权申报、审查","资产变现","资产分配","终结程序");
    /**
     * 案件进度（预重整）
     */
    List<Integer> SCHEDULE_YCZ = Arrays.asList(1, 2, 4, 6);
    List<String> SCHEDULE_YCZ_NAME = Arrays.asList("第一次债权人会议前阶段债权申报、审查","重整事务处理阶段",
            "资产变现","终结程序");

    /**
     * 债务人必交资料检查项模板
     */
    List<String> MATERIAL_CHECK_TEMPLATE = Arrays.asList("债务人公章", "债务人财务用章", "债务人各类合同协议","债务人的法人营业执照",
            "债务人的电脑数据和授权密码","债务人的财务账簿","法定代表人人名印章");

    /**
     * 消息类型
     */
    String SYSTEM_INFORMATION = "系统消息";

    String SCHEDULE_INFORMATION = "日程消息";
}
