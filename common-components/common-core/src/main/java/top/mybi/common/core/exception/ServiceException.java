package top.mybi.common.core.exception;

import top.mybi.common.core.model.CommonErrorCode;
import top.mybi.common.core.model.IErrorCode;
import top.mybi.common.core.model.Result;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 自定义业务服务错误
 * @Author mustang
 * @Date 2022年6月22日17:32:40
 * @Version 1.0
 */
@Setter
@Getter
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String msg;
    /**
     * 内部错误信息
     */
    private String innerMsg;
    /**
     * 构造器
     * @param code
     * @param msg
     */
    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    /**
     * 构造器
     * @param code
     * @param msg
     */
    public ServiceException(String code, String msg,String innerMsg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.innerMsg=innerMsg;
    }

    /**
     * 基于响应信息错误码构造自定义异常
     * @param result
     */
    public ServiceException(Result result) {
        super(result.getMsg());
        this.code = result.getCode();
        this.msg = result.getMsg();
    }
    /**
     * 基于标注定义的错误码构造自定义业务异常
     * @param message
     */
    public ServiceException(IErrorCode message){
        this(message.getCode(),message.getMsg());
    }

    /**
     * 基于标注定义的错误码和自定义的错误信息构造自定义业务异常
     * @param message
     */
    public ServiceException(IErrorCode message, String msg){
        this(message.getCode(),msg);
    }

    /**
     * 指定错误描述构造自定义业务异常
     * @param msg
     */
    public ServiceException(String msg){
        this(CommonErrorCode.SERVER_CUSTOM_ERROR,msg);
    }
}