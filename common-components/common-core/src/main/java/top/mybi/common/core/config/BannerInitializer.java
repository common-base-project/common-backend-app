package top.mybi.common.core.config;

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
