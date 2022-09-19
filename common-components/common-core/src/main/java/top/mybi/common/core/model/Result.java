package top.mybi.common.core.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import top.mybi.common.core.exception.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果参数封装
 * @Author mustang
 */
@Data
@NoArgsConstructor
public class Result<T> extends BaseModel {

    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 接口响应时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 接口响应数据
     */
    private T data;

    /**
     * 默认构造方法
     * @param code 消息编码
     * @param msg 消息内容
     */
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 默认构造方法
     * @param code 消息编码
     * @param msg 消息内容
     */
    public Result(String code, String msg,String innerMsg) {
        this.code = code;
        this.msg = msg;
    }
    /**
     * 全参构造方法
     * @param data 结果集
     * @param code 消息编码
     * @param msg 消息内容
     */
    public Result(T data,String code, String msg,String innerMsg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
    /**
     * 错误码构造方法
     */
    public Result(IErrorCode message){
        this.code = message.getCode();
        this.msg = message.getMsg();
    }

    /**
     * 成功返回数据构造方法
     * @param data 结果集
     */
    public Result(T data){
        this.data = data;
        this.code = CommonErrorCode.SUCCESS.getCode();
        this.msg = CommonErrorCode.SUCCESS.getMsg();
    }

    /**
     * 返回结果组装方法（data，code，message,innerMessage）
     * @param data 结果集
     * @param code 消息编码
     * @param msg 消息内容
     * @param innerMsg 内部消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> of(T data,String code,String msg,String innerMsg) {
        return new Result<>(data,code,msg,innerMsg);
    }

    /**
     * 返回结果组装方法（data，code，message）
     * @param data 结果集
     * @param code 消息编码
     * @param msg 消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> of(T data,String code,String msg) {
        return of(data,code,msg,null);
    }
    /**
     * 返回结果组装方法（data，IErrorCode）
     * @param data 结果集
     * @param message ErrorCode
     * @return <T> 返回值
     */
    public static <T> Result<T> of(T data,IErrorCode message) {
        return of(data,message.getCode(), message.getMsg(), null);
    }
    /**
     * 返回结果组装方法（data，IErrorCode）
     * @param data 结果集
     * @param message ErrorCode
     * @param innerMsg 内部消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> of(T data,IErrorCode message,String innerMsg) {
        return of(data,message.getCode(), message.getMsg(), innerMsg);
    }

    /**
     * 成功默认响应
     */
    public static Result success(){
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 成功返回信息：自定义信息
     * @param data 结果集
     * @return <T> 返回值
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 成功返回信息：自定义信息
     * @param msg 消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> successMsg(String msg) {
        return of(null, CommonErrorCode.SUCCESS.getCode(), msg,null);
    }

    /**
     * 成功返回信息：返回data，自定义信息
     * @param data 结果集
     * @param msg 消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> success(T data, String msg) {
        return of(data, CommonErrorCode.SUCCESS.getCode(), msg,null);
    }
    /**
     * 成功返回信息：返回data，自定义IErrorCode错误信息
     * @param data 结果集
     * @param msg 消息内容
     * @param innerMsg 内部消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> success(T data,String msg,String innerMsg) {
        return of(data, CommonErrorCode.SUCCESS.getCode(), msg,innerMsg);
    }
    /*------------------------------------错误信息-------------------------------*/
    /**
     * 默认错误返回信息
     */
    public static Result fail() {
        return of(null, CommonErrorCode.ERROR);
    }

    /**
     * 错误返回信息：自定义错误信息
     * @param msg 消息内容
     */
    public static Result fail(String msg) {
        return of(null, CommonErrorCode.ERROR.getCode(), msg,null);
    }
    /**
     * 错误返回信息：自定义错误编码+自定义错误信息
     * @param code 消息编码
     * @param msg 消息内容
     */
    public static Result fail(String code, String msg) {
        return of(null, code, msg,null);
    }
    /**
     * 错误返回信息：自定义错误编码+自定义错误信息+内部错误消息
     * @param code 消息编码
     * @param msg 消息内容
     * @param innerMsg 内部消息内容
     * @return <T> 返回值
     */
    public static Result fail(String code, String msg,String innerMsg) {
        return of(null, code, msg,innerMsg);
    }
    /**
     * 错误返回信息：默认错误编码+返回值+自定义错误信息
     * @param data 结果集
     * @param msg 消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> fail(T data,String msg) {
        return of(data, CommonErrorCode.ERROR.getCode(), msg,null);
    }
    /**
     * 错误返回信息：自定义错误编码+返回值+自定义错误信息
     * @param data 结果集
     * @param code 消息编码
     * @param msg 消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> fail(T data,String code, String msg) {
        return of(data, code, msg,null);
    }

    /**
     * 错误返回信息：自定义错误编码+返回值+自定义错误信息+内部错误消息
     * @param data 结果集
     * @param code 消息编码
     * @param msg 消息内容
     * @param innerMsg 内部消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> fail(T data,String code, String msg,String innerMsg) {
        return of(data, code, msg,innerMsg);
    }

    /**
     * 错误返回信息：自定义IErrorCode错误信息
     * @param message ErrorCode
     */
    public static Result fail(IErrorCode message) {
        return of(null, message);
    }
    /**
     * 错误返回信息：自定义IErrorCode错误信息
     * @param message ErrorCode
     * @param innerMsg 内部消息内容
     */
    public static Result fail(IErrorCode message,String innerMsg) {
        return of(null, message,innerMsg);
    }
    /**
     * 错误返回信息：返回值+自定义IErrorCode错误信息
     * @param data 结果集
     * @param message ErrorCode
     * @return <T> 返回值
     */
    public static <T> Result<T> fail(T data, IErrorCode message) {
        return of(data, message);
    }
    /**
     * 错误返回信息：返回值+自定义IErrorCode错误信息
     * @param data 结果集
     * @param message ErrorCode
     * @param innerMsg 内部消息内容
     * @return <T> 返回值
     */
    public static <T> Result<T> fail(T data, IErrorCode message,String innerMsg) {
        return of(data, message,innerMsg);
    }

    /**
     * 错误返回信息：ServiceException
     * @param exception ServiceException
     */
    public static Result fail(ServiceException exception) {
        return of(null, exception.getCode(), exception.getMsg(), exception.getInnerMsg());
    }
    /**
     * 错误响应
     */
    public static Result fail(IErrorCode message, Object ... params){
        String msg = String.format(message.getMsg(),params);
        return of(null, message.getCode(),msg);
    }
    /**
     * 验证是否是成功返回
     * @return
     */
    @JsonIgnore
    public boolean isSuccess(){
        if(CommonErrorCode.SUCCESS.getCode().equals(this.code)){
            return true;
        }else{
            return false;
        }
    }
}
