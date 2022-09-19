package com.sipue.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.sipue.common.mybatis.enums.LogicDelete;

/**
 * @Author mustang
 */
public class MybatisPlusCustomizerConfig implements MybatisPlusPropertiesCustomizer {
    private static IdType idType = IdType.ASSIGN_ID;
    public static IdType getIdType() {
        return idType;
    }
    /**
     * Customize the given a {@link MybatisPlusProperties} object.
     *
     * @param properties the MybatisPlusProperties object to customize
     */
    @Override
    public void customize(MybatisPlusProperties properties) {
        GlobalConfig globalConfig = properties.getGlobalConfig();
        globalConfig.setBanner(false);
        GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
        dbConfig.setLogicDeleteValue(String.valueOf(LogicDelete.DELETE.getValue()));
        dbConfig.setLogicNotDeleteValue(String.valueOf(LogicDelete.NORMAL.getValue()));
        dbConfig.setIdType(idType);
    }
}
