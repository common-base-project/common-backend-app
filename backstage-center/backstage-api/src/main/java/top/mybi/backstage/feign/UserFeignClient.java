package top.mybi.backstage.feign;

import top.mybi.backstage.pojo.dto.user.UserPageDTO;
import top.mybi.backstage.pojo.vo.user.UserPageVO;
import top.mybi.common.core.model.BasePageVO;
import top.mybi.common.core.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: 用户管理feign
 *
 * @Author: mustang
 * @Date: 2022/7/22 11:38
 */
@FeignClient(name = "backstage-center",url = "${mybi.api.backstage}")
public interface UserFeignClient {

    @PostMapping("/user/page")
    Result<BasePageVO<UserPageVO>> getUser(@RequestBody @Validated UserPageDTO params);
}
