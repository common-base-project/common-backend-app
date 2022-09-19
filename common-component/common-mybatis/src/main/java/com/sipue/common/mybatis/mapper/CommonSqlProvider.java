package com.sipue.common.mybatis.mapper;


import cn.hutool.core.util.StrUtil;
import com.sipue.common.core.model.Session;
import com.sipue.common.mybatis.enums.LogicDelete;
import com.sipue.common.mybatis.exception.SqlProviderException;
import com.sipue.common.core.model.BaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sipue.common.core.utils.StringEx;
import com.sipue.common.mybatis.config.MybatisPlusCustomizerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通用方法统一
 *
 * @Author mustang
 * @version 1.0
 * @date 2022年6月30日19:41:59
 */
@Slf4j
public class CommonSqlProvider {

    private static final String[] IGNORE_COLUMN_FIELDS = {"serialVersionUID", "deleted", "create_time", "modify_time"};
    /**
    *批量更新
    */
    /**
    *INSERT INTO t_schedule
     *  (schedule_id,case_id,schedule_type,schedule_status,sync_creditor,schedule_type_name,sort,creator_biz_type,modifier_biz_type,creator,modifier)
     *  VALUES
     *  (1371648184618700801,#{list[0].caseId},#{list[0].scheduleType},#{list[0].scheduleStatus},#{list[0].syncCreditor},#{list[0].scheduleTypeName},#{list[0].sort},'GLR','GLR',1248127113263255554,1248127113263255554),
     *  (1371648184618700802,#{list[1].caseId},#{list[1].scheduleType},#{list[1].scheduleStatus},#{list[1].syncCreditor},#{list[1].scheduleTypeName},#{list[1].sort},'GLR','GLR',1248127113263255554,1248127113263255554),
     *  (1371648184618700803,#{list[2].caseId},#{list[2].scheduleType},#{list[2].scheduleStatus},#{list[2].syncCreditor},#{list[2].scheduleTypeName},#{list[2].sort},'GLR','GLR',1248127113263255554,1248127113263255554),
     *  (1371648184618700804,#{list[3].caseId},#{list[3].scheduleType},#{list[3].scheduleStatus},#{list[3].syncCreditor},#{list[3].scheduleTypeName},#{list[3].sort},'GLR','GLR',1248127113263255554,1248127113263255554)
     *  ON DUPLICATE KEY UPDATE sort= VALUES(sort)
    */
    /**
     * @Desc: 对于表中存在多个唯一键，需要将所有唯一键属性都设置。
     * @Author mustang
     * @return
     **/
    public String batchUpdateOnDuplicateKey(Map<String,Object> param) throws IllegalAccessException {
        String insert = getSql(param);
        String needUpdate = (String) param.get("needUpdate");
        insert += " ON DUPLICATE KEY UPDATE "+needUpdate +"= VALUES("+needUpdate+")";
        return insert;
    }
    /**
     * @Desc: 对于表中存在多个唯一键，需要将所有唯一键属性都设置。
     * @Author mustang
     * @return
     **/
    public String batchUpdateOnDuplicateKeys(Map<String,Object> param) throws IllegalAccessException {
        String insert = getSql(param);
        StringBuilder sb = new StringBuilder(" ON DUPLICATE KEY UPDATE ");
        List<String> values = (List<String>) param.get("needUpdate");
        int size = values.size();
        int cnt = 0;
        for (String value : values) {
            sb.append(value).append(" = ").append("VALUES(").append(value).append(")");
            if (cnt < size - 1){
                sb.append(",");
            }
            cnt++;
        }
        return insert + sb.toString();
    }
    /**
     * 批量添加的方法
     *
     **/
    public String batchInsert(Map<String, Object> param) throws IllegalAccessException {
        List<?> entityList = (List<?>) param.get("list");
        int size = entityList.size();
        if (size < 1) {
            throw new IllegalArgumentException("批量添加方法的实体列表不能为空");
        }
        final IdType idType = MybatisPlusCustomizerConfig.getIdType();
        Class<?> clazz = entityList.get(0).getClass();
        StringBuilder columnBuilder = new StringBuilder();
        List<Field> objFields = getAllFields(clazz);
        //缓存该字段是否映射为数据库列名
        Map<Field, Boolean> fieldMap = new HashMap<>(objFields.size());
        for (Field field : objFields) {
            field.setAccessible(true);
            //是忽略字段
            boolean isIgnore = isIgnoreField(field);
            fieldMap.put(field, isIgnore);
            if (isIgnore) {
                continue;
            }
            //id自增则忽略主键字段
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                if (IdType.AUTO.equals(tableId.type()) || IdType.AUTO.equals(idType)) {
                    fieldMap.put(field, true);
                    continue;
                }
            }
            columnBuilder.append(",").append(getColumnName(field));
        }
        String idValue = null;
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            valueBuilder.append(", (");
            //保存值参数
            StringBuilder eachValueBuilder = new StringBuilder();
            for (Field field : objFields) {
                field.setAccessible(true);
                //是忽略字段
                if (fieldMap.get(field)) {
                    continue;
                }
                idValue = getIdValue(field.getAnnotation(TableId.class), idType);
                if (idValue != null) {
                    eachValueBuilder.append(",").append(idValue);
                    continue;
                }
                if ("creator".equals(field.getName()) || "modifier".equals(field.getName())) {
					eachValueBuilder.append(",").append(Session.getUserId());
                    //eachValueBuilder.append(",").append(0L);
                    continue;
                }
                if ("creatorBizType".equals(field.getName()) || "modifierBizType".equals(field.getName())) {
					eachValueBuilder.append(",'").append(Session.getBizType().toString()).append("'");//
                    //eachValueBuilder.append(",'").append("N/A").append("'");
                    continue;
                }
                eachValueBuilder.append(",");
                eachValueBuilder.append("#{list[").append(i).append("].");
                eachValueBuilder.append(field.getName());
                eachValueBuilder.append("}");
            }
            valueBuilder.append(eachValueBuilder.substring(1));
            valueBuilder.append(")");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(getTableName(clazz));
        sql.append(" (");
        sql.append(columnBuilder.substring(1));
        sql.append(") VALUES ");
        sql.append(valueBuilder.substring(1));
        return sql.toString();
    }

    /**
     * @Description: 批量新增方法2，不需要登录获取creator等参数
     * @Author: xiefangjiang
     * @date 2022/3/30 14:57
     */
    public String batchInsert2(Map<String, Object> param) throws IllegalAccessException {
        List<?> entityList = (List<?>) param.get("list");
        int size = entityList.size();
        if (size < 1) {
            throw new IllegalArgumentException("批量添加方法的实体列表不能为空");
        }
        final IdType idType = MybatisPlusCustomizerConfig.getIdType();
        Class<?> clazz = entityList.get(0).getClass();
        StringBuilder columnBuilder = new StringBuilder();
        List<Field> objFields = getAllFields(clazz);
        //缓存该字段是否映射为数据库列名
        Map<Field, Boolean> fieldMap = new HashMap<>(objFields.size());
        for (Field field : objFields) {
            field.setAccessible(true);
            //是忽略字段
            boolean isIgnore = isIgnoreField(field);
            fieldMap.put(field, isIgnore);
            if (isIgnore) {
                continue;
            }
            //id自增则忽略主键字段
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                if (IdType.AUTO.equals(tableId.type()) || IdType.AUTO.equals(idType)) {
                    fieldMap.put(field, true);
                    continue;
                }
            }
            columnBuilder.append(",").append(getColumnName(field));
        }
        String idValue = null;
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            valueBuilder.append(", (");
            //保存值参数
            StringBuilder eachValueBuilder = new StringBuilder();
            for (Field field : objFields) {
                field.setAccessible(true);
                //是忽略字段
                if (fieldMap.get(field)) {
                    continue;
                }
                idValue = getIdValue(field.getAnnotation(TableId.class), idType);
                if (idValue != null) {
                    eachValueBuilder.append(",").append(idValue);
                    continue;
                }
                eachValueBuilder.append(",");
                eachValueBuilder.append("#{list[").append(i).append("].");
                eachValueBuilder.append(field.getName());
                eachValueBuilder.append("}");
            }
            valueBuilder.append(eachValueBuilder.substring(1));
            valueBuilder.append(")");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(getTableName(clazz));
        sql.append(" (");
        sql.append(columnBuilder.substring(1));
        sql.append(") VALUES ");
        sql.append(valueBuilder.substring(1));
        return sql.toString();
    }

    /**
     * 批量添加的方法
     *
     **/
    public String batchInsertWithId(Map<String, Object> param) {
        List<?> entityList = (List<?>) param.get("list");
        int size = entityList.size();
        if (size < 1) {
            throw new IllegalArgumentException("批量添加方法的实体列表不能为空");
        }
//        IdType idType = MybatisPlusConfig.getIdType();
        Class<?> clazz = entityList.get(0).getClass();
        StringBuilder columnBuilder = new StringBuilder();
        List<Field> objFields = getAllFields(clazz);
        //缓存该字段是否映射为数据库列名
        Map<Field, Boolean> fieldMap = new HashMap<>(objFields.size());
        for (Field field : objFields) {
            field.setAccessible(true);
            //是忽略字段
            boolean isIgnore = isIgnoreField(field);
            fieldMap.put(field, isIgnore);
            if (isIgnore) {
                continue;
            }
            columnBuilder.append(",").append(getColumnName(field));
        }
        String idValue = null;
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            valueBuilder.append(", (");
            //保存值参数
            StringBuilder eachValueBuilder = new StringBuilder();
            for (Field field : objFields) {
                field.setAccessible(true);
                //是忽略字段
                if (fieldMap.get(field)) {
                    continue;
                }
                if ("creator".equals(field.getName()) || "modifier".equals(field.getName())) {
                    eachValueBuilder.append(",").append(Session.getUserId());
                    continue;
                }
                if ("creatorBizType".equals(field.getName()) || "modifierBizType".equals(field.getName())) {
                    eachValueBuilder.append(",'").append(Session.getBizType().toString()).append("'");
                    //eachValueBuilder.append(",'").append("N/A").append("'");
                    continue;
                }
                eachValueBuilder.append(",");
                eachValueBuilder.append("#{list[").append(i).append("].");
                eachValueBuilder.append(field.getName());
                eachValueBuilder.append("}");
            }
            valueBuilder.append(eachValueBuilder.substring(1));
            valueBuilder.append(")");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(getTableName(clazz));
        sql.append(" (");
        sql.append(columnBuilder.substring(1));
        sql.append(") VALUES ");
        sql.append(valueBuilder.substring(1));
        return sql.toString();
    }


    /**
     * 判断该字段是否不映射数据库表的列
     **/
    private boolean isIgnoreField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            return true;
        }
        String columnName = getColumnName(field);
        for (String column : IGNORE_COLUMN_FIELDS) {
            if (column.equals(columnName)) {
                return true;
            }
        }
        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField == null || tableField.exist()) {
            return false;
        }
        return true;
    }

    /**
     * 根据实体类字段获取数据库表映射的列名称
     **/
    private String getColumnName(Field field) {
        TableField tableField = field.getAnnotation(TableField.class);
        return getColumnName(tableField == null ? null : tableField.value(), field.getName());
    }

    /**
     * 根据字段名称和注解获取对应数据库列的名称
     **/
    private String getColumnName(String columnName, String fieldName) {
        if (StrUtil.isEmpty(columnName)) {
            return StringEx.toUnderlineName(fieldName);
        }
        return columnName;
    }

    /**
     * 根据实体类获取表名
     **/
    private String getTableName(Class<?> clazz) {
        TableName tn = clazz.getAnnotation(TableName.class);
        String tableName = (tn == null) ? null : tn.value();
        return StrUtil.isEmpty(tableName) ? StringEx.toUnderlineName(clazz.getName()) : tableName;
    }

    /**
     * 根据主键生成策略获取ID值
     **/
    private String getIdValue(TableId tableId, IdType idType) {
        if (tableId == null) {
            return null;
        }
        //优先使用TableId注解定义的主键类型
        if (!IdType.NONE.equals(tableId.type())) {
            idType = tableId.type();
        }
        if (IdType.ASSIGN_ID.equals(idType)) {
            return IdWorker.getIdStr();
        }
        if (IdType.ASSIGN_UUID.equals(idType)) {
            return IdWorker.get32UUID();
        }
        if(IdType.INPUT.equals(idType)){
            return null;
        }
        log.warn("无效的ID类型:"+tableId.type());
        return null;
    }

    /**
     * 获取某个实体类的所有字段，包括父类的私有字段
     **/
    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        boolean isExist = true;
        while (isExist) {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                fieldList.addAll(Arrays.asList(fields));
            }
            clazz = clazz.getSuperclass();
            if (clazz == null || (BaseModel.class.getName().equals(clazz.getName())) || (Object.class.getName().equals(clazz.getName()))) {
                isExist = false;
            }
        }
        return fieldList;
    }

    /**
     * 根据主键和是否删除字段逻辑更新，
     **/
    public String updateByIdContainNull(Object entity) {
        List<Field> objFields = getAllFields(entity.getClass());
        StringBuilder columnBuilder = new StringBuilder();
        String idColumnName = null;
        String idFieldName = null;
        String logicFieldName = null;
        for (Field field : objFields) {
            field.setAccessible(true);
            //是忽略字段
            boolean isIgnore = isIgnoreField(field);
            if (isIgnore) {
                continue;
            }
            String columnName = getColumnName(field);
            //不更新逻辑删除字段
            TableLogic tableLogic = field.getAnnotation(TableLogic.class);
            if (Objects.nonNull(tableLogic)) {
                logicFieldName = field.getName();
                continue;
            }
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                idFieldName = field.getName();
                idColumnName = getColumnName(tableId.value(), idFieldName);
                continue;
            }
            columnBuilder.append(",").
                    append(columnName).
                    append("=#{").append(field.getName()).append("}");
        }
        if (idColumnName == null) {
            throw new SqlProviderException("实体类缺少主键映射");
        }
        if (columnBuilder.length() < 1) {
            throw new SqlProviderException("缺少需要更新的字段");
        }
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(getTableName(entity.getClass()));
        sql.append(" SET ");
        sql.append(columnBuilder.substring(1));
        sql.append(" WHERE ");
        sql.append(idColumnName).append("=#{").append(idFieldName).append("}");
        if (Objects.nonNull(logicFieldName)) {

            sql.append(" AND ").append(logicFieldName).append("=").append(LogicDelete.NORMAL.getValue());
        }
        return sql.toString();
    }
    /**
    *付有杰
     * 手动赋值session，不从线程上下文读取
    */
    public String batchInsertWithSession(Map<String, Object> param) throws IllegalAccessException {
        List<?> entityList = (List<?>) param.get("list");
        int size = entityList.size();
        if (size < 1) {
            throw new IllegalArgumentException("批量添加方法的实体列表不能为空");
        }
        final IdType idType = MybatisPlusCustomizerConfig.getIdType();
        Class<?> clazz = entityList.get(0).getClass();
        StringBuilder columnBuilder = new StringBuilder();
        List<Field> objFields = getAllFields(clazz);
        //缓存该字段是否映射为数据库列名
        Map<Field, Boolean> fieldMap = new HashMap<>(objFields.size());
        for (Field field : objFields) {
            field.setAccessible(true);
            //是忽略字段
            boolean isIgnore = isIgnoreField(field);
            fieldMap.put(field, isIgnore);
            if (isIgnore) {
                continue;
            }
            //id自增则忽略主键字段
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                if (IdType.AUTO.equals(tableId.type()) || IdType.AUTO.equals(idType)) {
                    fieldMap.put(field, true);
                    continue;
                }
            }
            columnBuilder.append(",").append(getColumnName(field));
        }
        String idValue = null;
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            valueBuilder.append(", (");
            //保存值参数
            StringBuilder eachValueBuilder = new StringBuilder();
            for (Field field : objFields) {
                field.setAccessible(true);
                //是忽略字段
                if (fieldMap.get(field)) {
                    continue;
                }
                idValue = getIdValue(field.getAnnotation(TableId.class), idType);
                if (idValue != null) {
                    eachValueBuilder.append(",").append(idValue);
                    continue;
                }
                /**
                 * 异步的情况下，如果父线程提前返回了，属性得到销毁，此时仍然NPE。所以采取判断策略，有值不拿Session
                 */
                Object o = entityList.get(i);
                if ("creator".equals(field.getName()) || "modifier".equals(field.getName())) {
                    Long valObject = (Long) field.get(o);
                    if (Objects.nonNull(valObject)){
                        eachValueBuilder.append(",").append(valObject);
                        continue;
                    }
                    //eachValueBuilder.append(",").append(Session.getUserId());//todo 待处理
                    eachValueBuilder.append(",").append(0L);
                    continue;
                }
                if ("creatorBizType".equals(field.getName()) || "modifierBizType".equals(field.getName())) {
                    String valObject = (String) field.get(o);
                    if (!StringUtils.isEmpty(valObject)){
                        eachValueBuilder.append(",'").append(valObject).append("'");
                        continue;
                    }
                    //eachValueBuilder.append(",'").append(Session.getBizType().toString()).append("'");//todo 待处理
                    eachValueBuilder.append(",'").append("N/A").append("'");
                    continue;
                }
                eachValueBuilder.append(",");
                eachValueBuilder.append("#{list[").append(i).append("].");
                eachValueBuilder.append(field.getName());
                eachValueBuilder.append("}");
            }
            valueBuilder.append(eachValueBuilder.substring(1));
            valueBuilder.append(")");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(getTableName(clazz));
        sql.append(" (");
        sql.append(columnBuilder.substring(1));
        sql.append(") VALUES ");
        sql.append(valueBuilder.substring(1));
        return sql.toString();
    }
    /**
    * 所有属性不预先填充，避免影响SQL判断
    */
    public String getSql(Map<String,Object> param) throws IllegalAccessException{
        List<?> entityList = (List<?>) param.get("list");
        int size = entityList.size();
        if (size < 1) {
            throw new IllegalArgumentException("批量添加方法的实体列表不能为空");
        }
        final IdType idType = MybatisPlusCustomizerConfig.getIdType();
        Class<?> clazz = entityList.get(0).getClass();
        StringBuilder columnBuilder = new StringBuilder();
        List<Field> objFields = getAllFields(clazz);
        //缓存该字段是否映射为数据库列名
        Map<Field, Boolean> fieldMap = new HashMap<>(objFields.size());
        for (Field field : objFields) {
            field.setAccessible(true);
            //是忽略字段
            boolean isIgnore = isIgnoreField(field);
            fieldMap.put(field, isIgnore);
            if (isIgnore) {
                continue;
            }
            //id自增则忽略主键字段
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                if (IdType.AUTO.equals(tableId.type()) || IdType.AUTO.equals(idType)) {
                    fieldMap.put(field, true);
                    continue;
                }
            }
            columnBuilder.append(",").append(getColumnName(field));
        }
        String idValue = null;
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            valueBuilder.append(", (");
            //保存值参数
            StringBuilder eachValueBuilder = new StringBuilder();
            for (Field field : objFields) {
                field.setAccessible(true);
                //是忽略字段
                if (fieldMap.get(field)) {
                    continue;
                }
                eachValueBuilder.append(",");
                eachValueBuilder.append("#{list[").append(i).append("].");
                eachValueBuilder.append(field.getName());
                eachValueBuilder.append("}");
            }
            valueBuilder.append(eachValueBuilder.substring(1));
            valueBuilder.append(")");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(getTableName(clazz));
        sql.append(" (");
        sql.append(columnBuilder.substring(1));
        sql.append(") VALUES ");
        sql.append(valueBuilder.substring(1));
        return sql.toString();
    }
}
