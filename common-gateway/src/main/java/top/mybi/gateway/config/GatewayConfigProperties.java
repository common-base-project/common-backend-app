package top.mybi.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author mustang
 * <p>
 * 网关配置文件
 */
@Data
@RefreshScope
@ConfigurationProperties("mybi.gateway")
public class GatewayConfigProperties {
	/**
	 * 网关不需要校验的接口地址
	 */
	private List<String> ignoreUrls;
	/**
	 * 是否开启httpSign校验
	 */
	private boolean checkSign;
	/**
	 * 忽略httpSign校验的url
	 */
	private List<String> ignoreSignUrls;

}
