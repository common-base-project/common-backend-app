package com.sipue.common.core.model;

import cn.hutool.core.util.StrUtil;
import com.sipue.common.core.constants.NumberConstants;
import com.sipue.common.core.enums.BizType;
import com.sipue.common.core.enums.Platform;
import com.sipue.common.core.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static com.sipue.common.core.model.CommonErrorCode.REQUEST_APPID_EMPTY_OR_INVALID;

/**
 * @Description 客户端信息
 * @Author wangjunyu
 * @Date 2022年6月05日15:34:18
 * @Version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo extends BaseModel {

    /**
     * 平台
     */
    private Platform platform;

    /**
     * 产品名称
     */
    private String product;

    /**
     * 业务类型
     */
    private BizType bizType;

    /**
     * 业务版本
     */
    private String version;

    /**
     * 构造器
     * @param appId eg： web_poyitong_business_1.0.0
     */
    public ClientInfo(String appId){
        if (StrUtil.isEmpty(appId)){
            throw new ServiceException(REQUEST_APPID_EMPTY_OR_INVALID);
        }
        appId = appId.toUpperCase();
        String[] appIdArray = appId.split("_");
        if (appIdArray.length != NumberConstants.FOUR) {
            throw new ServiceException(REQUEST_APPID_EMPTY_OR_INVALID);
        }

        platform = Platform.getPlatformByCode(appIdArray[0]);
        if(Objects.isNull(platform)){
            throw new ServiceException(REQUEST_APPID_EMPTY_OR_INVALID);
        }
        product = appIdArray[1];
        bizType = BizType.getBizTypeByCode(appIdArray[2]);
        if(Objects.isNull(bizType)){
            throw new ServiceException(REQUEST_APPID_EMPTY_OR_INVALID);
        }
        //版本号
        version = appIdArray[3];
    }
}
