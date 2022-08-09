package com.sipue.common.core.utils;


import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.common.core.model.Result;

/**
 * @Desc
 * @Author wangjunyu
 */
public class ResultCodeUtil {
    /**
     * 判断返回的是不是成功码
     * @param result
     * @return bool
     */
    public static <T> boolean resultCodeSuccess(Result<T> result){
        if (result.getCode().equals(CommonErrorCode.SUCCESS.getCode())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 直接判断＋返回 适用于有返回值的情况
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Result<T> returnResult(Result<T> result){
        if (result.getCode().equals(CommonErrorCode.SUCCESS.getCode())){
            return result;
        }else {
            throw new ServiceException(result.getCode(),result.getMsg());
        }
    }
    /**
    *判断是否有异常，并返回
    */
    public static <T> T resultInnerData(Result<T> result){
        return returnResult(result).getData();
    }
    /**
     * 检查返回值，适用于不返回的方法中
     */
    public static <T> void checkResult(Result<T> result){
        if ( ! resultCodeSuccess(result)){
            throw new ServiceException(result.getCode(),result.getMsg());
        }
    }
}