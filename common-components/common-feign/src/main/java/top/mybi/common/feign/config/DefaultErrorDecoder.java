package top.mybi.common.feign.config;

import top.mybi.common.core.exception.ServiceException;
import top.mybi.common.core.model.CommonErrorCode;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * @Author mustang
 * @date 2022/1/20 9:36
 */
public class DefaultErrorDecoder extends ErrorDecoder.Default {
    @Override
    public Exception decode(String s, Response response)  {
        FeignException exception = (FeignException)super.decode(s, response);
        String body = exception.contentUTF8();
        /*if(StrUtil.isNotEmpty(body)){
            JSONObject res = JSON.parseObject(body);
            if(res.containsKey("code")&&res.containsKey("msg")){
                throw new ServiceException(res.getString("code"),res.getString("msg"),res.getString("innerMsg"));
            }
        }*/
        if (response.status() == 500){
            throw new ServiceException(CommonErrorCode.SERVER_CUSTOM_ERROR);
        }else{
            throw new ServiceException(CommonErrorCode.FEIGN_CLIENT_ERROR);
        }
    }

}
