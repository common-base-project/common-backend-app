package top.mybi.common.core.model;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName BaseModel
 * @Description 基础模型 实现toString转换为json格式
 * @Author mustang
 * @Date 2022年6月22日18:00:42
 * @Version 1.0
 */
@Slf4j
public class BaseModel implements Serializable {

    /**
     * 将当前对象转换为Map
     * @return
     */
    public Map<String,Object> covertMap(){
        return covertMap(false,false);
    }

    /**
     * 将当前对象转换为Map
     * @param underLineKey 是否将KEY转换为小写
     * @return
     */
    public Map<String,Object> covertMap(boolean underLineKey){
        return covertMap(underLineKey,false);
    }

    /**
     * 将当前对象转换为Map
     * @param underLineKey 是否将KEY转换为小写
     * @param ignoreNullValue 是否忽略空值字段
     * @return
     */
    public Map<String,Object> covertMap(boolean underLineKey,boolean ignoreNullValue){
        return BeanUtil.beanToMap(this,underLineKey,ignoreNullValue);
    }

    /**
     * 将当前对象转换为另一对象
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T covertBean(Class<T> tClass){
        return  BeanUtil.toBean(this,tClass);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }

}
