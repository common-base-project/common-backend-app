package com.sipue.common.mybatis.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sipue.common.core.model.Session;
import com.sipue.common.mybatis.enums.LogicDelete;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.apache.tomcat.jni.Local;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 自动填充配置
 * @Author wangjunyu
 * @date 2022/10/29 15:25
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis plus start insert fill ....");
        //LocalDateTime now = LocalDateTime.now();
        fillValIfNullByName("deleted", LogicDelete.NORMAL.getValue(), metaObject,false);
        fillValIfNullByName("createTime", LocalDateTime.now(), metaObject, false);
        fillValIfNullByName("modifyTime", LocalDateTime.now(), metaObject, false);
        if (metaObject.hasSetter("creator")&&Objects.isNull(metaObject.getValue("creator"))) {
            //fillValIfNullByName("creator", getUserId(), metaObject, false);
        }
        if (metaObject.hasSetter("modifier")&&Objects.isNull(metaObject.getValue("modifier"))) {
            //fillValIfNullByName("modifier", getUserId(), metaObject, false);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis plus start update fill ....");
        fillValIfNullByName("modifyTime", LocalDateTime.now(), metaObject, true);
        if (metaObject.hasSetter("modifier")&&Objects.isNull(metaObject.getValue("modifier"))) {
            fillValIfNullByName("modifier", getUserId(), metaObject, false);
        }
    }

    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject MetaObject
     * @param isCover 是否覆盖原有值,避免更新操作手动入参
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
        // 1. 没有 get 方法
        if (!metaObject.hasSetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StrUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }

    /**
     * 获取 spring security 当前的用户id
     */
    private Long getUserId() {
        return Session.getUserId();
    }

}