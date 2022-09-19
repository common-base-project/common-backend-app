package top.mybi.common.mybatis.exception;

/**
 * @Description 实体类映射相关异常
 * @Author mustang
 * @Date 2022年6月28日11:54:22
 * @Version 1.0
 */
public class SqlProviderException extends RuntimeException {


    public SqlProviderException(String msg){
        super(msg);
    }

    public SqlProviderException(Throwable cause) {
        super(cause);
    }
}
