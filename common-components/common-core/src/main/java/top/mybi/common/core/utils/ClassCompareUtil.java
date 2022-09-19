package top.mybi.common.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * 类（对象）比较工具类
 * @Author mustang
 * @version 1.0
 */
@Slf4j
public class ClassCompareUtil {

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     *
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject) {
        Map<String, Map<String, Object>> resultMap = compareFields(oldObject, newObject);
        int size=resultMap.size();
        if(size>0) {
            log.info("对象1与对象2的属性值有差异,差异结果如下：");
            Iterator<String> it = resultMap.keySet().iterator();
            while(it.hasNext()) {
                String key=it.next();
               log.info("  "+key+"(oldValue:"+resultMap.get(key).get("oldValue")+",newValue:"+resultMap.get(key).get("newValue")+")");
            }
            return false;
        }else {
            return true;
        }
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果map
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Map<String,Object>> compareFields(Object oldObject, Object newObject) {
        Map<String, Map<String, Object>> map = null;

        try{
            /**
             * 只有两个对象都是同一类型的才有可比性
             */
            if (oldObject.getClass() == newObject.getClass()) {
                map = new HashMap<String, Map<String,Object>>();

                Class clazz = oldObject.getClass();
                //获取object的所有属性
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,Object.class).getPropertyDescriptors();

                for (PropertyDescriptor pd : pds) {
                    //遍历获取属性名
                    String name = pd.getName();

                    //获取属性的get方法
                    Method readMethod = pd.getReadMethod();

                    // 在oldObject上调用get方法等同于获得oldObject的属性值
                    Object oldValue = readMethod.invoke(oldObject);
                    // 在newObject上调用get方法等同于获得newObject的属性值
                    Object newValue = readMethod.invoke(newObject);

                    if(oldValue instanceof List){
                        continue;
                    }

                    if(newValue instanceof List){
                        continue;
                    }

                    if(oldValue instanceof Timestamp){
                        oldValue = new Date(((Timestamp) oldValue).getTime());
                    }

                    if(newValue instanceof Timestamp){
                        newValue = new Date(((Timestamp) newValue).getTime());
                    }

                    if(oldValue == null && newValue == null){
                        continue;
                    }else if(oldValue == null && newValue != null){
                        Map<String,Object> valueMap = new HashMap<String,Object>();
                        valueMap.put("oldValue",oldValue);
                        valueMap.put("newValue",newValue);

                        map.put(name, valueMap);

                        continue;
                    }

                    if (!oldValue.equals(newValue)) {// 比较这两个值是否相等,不等就可以放入map了
                        Map<String,Object> valueMap = new HashMap<String,Object>();
                        valueMap.put("oldValue",oldValue);
                        valueMap.put("newValue",newValue);

                        map.put(name, valueMap);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return map;
    }
}
