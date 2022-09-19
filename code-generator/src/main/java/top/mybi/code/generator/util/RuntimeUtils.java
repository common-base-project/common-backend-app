/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package top.mybi.code.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description: 运行工具类
 *
 * @Author: mustang
 * @Date: 2021/12/31 9:36
 */
@Slf4j
public class RuntimeUtils {


    /**
     * 打开指定输出文件目录
     *
     * @param outDir 输出文件目录
     * @throws IOException
     */
    public static void openDir(String outDir) throws IOException {
        String osName = System.getProperty("os.name");
        if (osName != null) {
            if (osName.contains("Mac")) {
                Runtime.getRuntime().exec("open " + outDir);
            } else if (osName.contains("Windows")) {
                Runtime.getRuntime().exec("cmd /c start " + outDir);
            } else {
                log.debug("文件输出目录:{}", outDir);
            }
        } else {
            log.warn("读取操作系统失败");
        }
    }
}
