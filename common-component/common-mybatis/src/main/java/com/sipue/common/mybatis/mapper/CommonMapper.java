package com.sipue.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @Description 公共映射接口 预留Mybatis扩展
 * @Author wangjunyu
 * @Date 2022年6月28日12:08:40
 * @Version 1.0
 */
public interface CommonMapper<T> extends BaseMapper<T> {

    /**
     * 批量添加的方法，说明：
     * 1、对于数据库中的非空字段必须确保有值（即使数据库中有默认值）
     * 2、批量添加的条数应该控制在1000条以内
     * @param entityList 实体类列表
     **/
    @InsertProvider(type = CommonSqlProvider.class, method = "batchInsert")
    int batchInsert(@Param("list") List<?> entityList);

    /**
     * @Description: 批量新增方法2，不需要登录获取creator等参数
     * @Author: xiefangjiang
     * @date 2022/3/30 14:57
     */
    @InsertProvider(type = CommonSqlProvider.class, method = "batchInsert2")
    int batchInsert2(@Param("list") List<?> entityList);

    /**
     * 批量添加的方法(需手动设置Id)，说明：
     * 1、对于数据库中的非空字段必须确保有值（即使数据库中有默认值）
     * 2、批量添加的条数应该控制在1000条以内
     * @param entityList 实体类列表
     **/
    @InsertProvider(type = CommonSqlProvider.class, method = "batchInsertWithId")
    int batchInsertWithId(@Param("list") List<?> entityList);

    @UpdateProvider(type = CommonSqlProvider.class, method = "updateByIdContainNull")
    int updateByIdContainNull(T entity);
    /**
    * 调用此方法，有关Session的值默认你已经手动赋值了，不会从Session取值。
    */
    @InsertProvider(type = CommonSqlProvider.class, method = "batchInsertWithSession")
    int batchInsertWithSession(@Param("list") List<?> entityList);
    /**
     *批量更新的模板，将查出来的数据设置好要更新的值 比如event_user_id，然后调用就行。
     * 适用于给每条数据的同一个字段设置不一样的数据。
     * 缺点就是，数据特别多，会锁住很多行数据。所以要分段执行。
     * 支持多个字段（多个字段自己没试过有无BUG）。形容
     *toDoMapper.batchUpdateOnDuplicateKeys(entities,Arrays.asList("xxx,xxx"));
     *
     * 一般用法是：先查出来数据库的数据，再设置需要修改的值。对于空值也会覆盖，因为SQL语句是凭借的全字段。
     */
    @InsertProvider(type = CommonSqlProvider.class, method = "batchUpdateOnDuplicateKey")
    int batchUpdateOnDuplicateKey(@Param("list") List<?> entityList,@Param("needUpdate") String needUpdate);
    /**
    * 一般用法是：先查出来数据库的数据，再设置需要修改的值。对于空值也会覆盖，因为SQL语句是凭借的全字段。
    */
    @InsertProvider(type = CommonSqlProvider.class, method = "batchUpdateOnDuplicateKeys")
    int batchUpdateOnDuplicateKeys(@Param("list") List<?> entityList,@Param("needUpdate") List<String> sort);
}
