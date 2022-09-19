package com.sipue.common.job;

import com.sipue.common.job.properties.XxlExecutorProperties;
import com.sipue.common.job.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * xxl-job自动装配
 *
 * @author wangjunyu
 * @date 2022/7/18
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

	/**
	 * 服务名称 包含 XXL_JOB_ADMIN 则说明是 Admin
	 */
	private static final String XXL_JOB_ADMIN = "xxl-job-admin";

	/**
	 * 配置xxl-job 执行器，提供自动发现 xxl-job-admin 能力
	 * @param xxlJobProperties xxl 配置
	 * @param environment 环境变量
	 * @return
	 */
	@Bean
	public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties, Environment environment) {
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		XxlExecutorProperties executor = xxlJobProperties.getExecutor();
		// 应用名默认为服务名
		String appName = executor.getAppname();
		if (!StringUtils.hasText(appName)) {
			appName = environment.getProperty("spring.application.name");
		}
		String accessToken = environment.getProperty("xxl.job.accessToken");
		if (!StringUtils.hasText(accessToken)) {
			accessToken = executor.getAccessToken();
		}

		xxlJobSpringExecutor.setAppname(appName);
		xxlJobSpringExecutor.setAddress(executor.getAddress()+":"+executor.getPort());
		xxlJobSpringExecutor.setIp(executor.getIp());
		xxlJobSpringExecutor.setPort(executor.getPort());
		xxlJobSpringExecutor.setAccessToken(accessToken);
		xxlJobSpringExecutor.setLogPath(executor.getLogPath());
		xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());
		xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());

		return xxlJobSpringExecutor;
	}

}
