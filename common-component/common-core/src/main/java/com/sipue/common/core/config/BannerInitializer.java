package com.sipue.common.core.config;

import com.sipue.common.core.constants.CommonConstants;
import com.sipue.common.core.utils.CustomBanner;
import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.taobao.text.Color;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Banner初始化
 *
 */
public class BannerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            /*LogoBanner logoBanner = new LogoBanner(BannerInitializer.class, "/logo.txt", "重庆思普信息科技有限公司", 1, 6, new Color[3], true);
            CustomBanner.show(logoBanner, new Description(BannerConstant.VERSION + ":", CommonConstants.PROJECT_VERSION, 0, 1)
                    , new Description("", "", 0, 1)
            );*/
        }
    }
}
