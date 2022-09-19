package top.mybi.common.core.model;

/**
 * 错误消息接口
 */
public interface IErrorCode {
    /**
     * 获取错误码
     * @return
     */
    String getCode();

    /**
     * 获取错误消息
     * @return
     */
    String getMsg();
}
