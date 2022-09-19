package top.mybi.common.redis.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis Repository
 * redis 基本操作 可扩展,基本够用了
 *
 */
@Slf4j
@RefreshScope
public class RedisRepository {
    /**
     * 缓存默认超时时间(秒)
     */
    @Value("${mybi.common.redis.cache-default-expire-second:600}")
    private long defaultExpireSecond;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * Spring Redis Template
     */
    private RedisTemplate<String, Object> redisTemplate;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取链接工厂
     */
    public RedisConnectionFactory getConnectionFactory() {
        return this.redisTemplate.getConnectionFactory();
    }

    /**
     * 获取 RedisTemplate对象
     */
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 清空DB
     *
     * @param node redis 节点
     */
    public void flushDB(RedisClusterNode node) {
        this.redisTemplate.opsForCluster().flushDb(node);
    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间(单位秒)
     */
    public void setExpire(final byte[] key, final byte[] value, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.setEx(key, time, value);
            return 1L;
        });
    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间
     * @param timeUnit  过期时间单位
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }
    public void setExpire(final String key, final Object value, final long time) {
        this.setExpire(key, value, time, TimeUnit.SECONDS);
    }
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = rawKey(key);
        byte[] rawValue = rawValue(value, valueSerializer);

        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                potentiallyUsePsetEx(connection);
                return null;
            }
            public void potentiallyUsePsetEx(RedisConnection connection) {
                if (!TimeUnit.MILLISECONDS.equals(timeUnit) || !failsafeInvokePsetEx(connection)) {
                    connection.setEx(rawKey, TimeoutUtils.toSeconds(time, timeUnit), rawValue);
                }
            }
            private boolean failsafeInvokePsetEx(RedisConnection connection) {
                boolean failed = false;
                try {
                    connection.pSetEx(rawKey, time, rawValue);
                } catch (UnsupportedOperationException e) {
                    failed = true;
                }
                return !failed;
            }
        }, true);
    }

    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     *
     * @param keys   redis主键数组
     * @param values 值数组
     * @param time   过期时间(单位秒)
     */
    public void setExpire(final String[] keys, final Object[] values, final long time) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i], time, TimeUnit.SECONDS);
        }
    }


    /**
     * 一次性添加数组到   过期时间的  缓存，不用多次连接，节省开销
     *
     * @param keys   the keys
     * @param values the values
     */
    public void set(final String[] keys, final Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i]);
        }
    }


    /**
     * 添加到缓存
     *
     * @param key   the key
     * @param value the value
     */
    public void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 查询在以keyPatten的所有  key
     *
     * @param keyPatten the key patten
     * @return the set
     */
    public Set<String> keys(final String keyPatten) {
        return redisTemplate.keys(keyPatten + "*");
    }

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the byte [ ]
     */
    public byte[] get(final byte[] key) {
        return redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key));
    }

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the string
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 根据key获取对象
     *
     * @param key the key
     * @param valueSerializer 序列化
     * @return the string
     */
    public Object get(final String key, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = rawKey(key);
        return redisTemplate.execute(connection -> deserializeValue(connection.get(rawKey), valueSerializer), true);
    }

    /**
     * Ops for hash hash operations.
     *
     * @return the hash operations
     */
    public HashOperations<String, String, Object> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 对HashMap操作
     *
     * @param key       the key
     * @param hashKey   the hash key
     * @param hashValue the hash value
     */
    public void putHashValue(String key, String hashKey, Object hashValue) {
        opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 获取单个field对应的值
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the hash values
     */
    public Object getHashValues(String key, String hashKey) {
        return opsForHash().get(key, hashKey);
    }

    /**
     * 根据key值删除
     *
     * @param key      the key
     * @param hashKeys the hash keys
     */
    public void delHashValues(String key, Object... hashKeys) {
        opsForHash().delete(key, hashKeys);
    }

    /**
     * key只匹配map
     *
     * @param key the key
     * @return the hash value
     */
    public Map<String, Object> getHashValue(String key) {
        return opsForHash().entries(key);
    }

    /**
     * 批量添加
     *
     * @param key the key
     * @param map the map
     */
    public void putHashValues(String key, Map<String, Object> map) {
        opsForHash().putAll(key, map);
    }

    /**
     * 集合数量
     *
     * @return the long
     */
    public long dbSize() {
        return redisTemplate.execute(RedisServerCommands::dbSize);
    }

    /**
     * 清空redis存储的数据
     *
     * @return the string
     */
    public String flushDB() {
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 删除key
     *
     * @param keys the keys
     * @return the long
     */
    public boolean del(final String... keys) {
        boolean result = false;
        for (String key : keys) {
            result = redisTemplate.delete(key);
        }
        return result;
    }

    /**
     * 对某个主键对应的值加一,value值必须是全数字的字符串
     *
     * @param key the key
     * @return the long
     */
    public long incr(final String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * redis List 引擎
     *
     * @return the list operations
     */
    public ListOperations<String, Object> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * redis List数据结构 : 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long leftPush(String key, Object value) {
        return opsForList().leftPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的头元素
     *
     * @param key the key
     * @return the string
     */
    public Object leftPop(String key) {
        return opsForList().leftPop(key);
    }

    /**
     * redis List数据结构 :将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long in(String key, Object value) {
        return opsForList().rightPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的末尾元素
     *
     * @param key the key
     * @return the string
     */
    public Object rightPop(String key) {
        return opsForList().rightPop(key);
    }


    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    public Long length(String key) {
        return opsForList().size(key);
    }


    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   the key
     * @param i     the
     * @param value the value
     */
    public void remove(String key, long i, Object value) {
        opsForList().remove(key, i, value);
    }

    /**
     * redis List数据结构 : 将列表 key 下标为 index 的元素的值设置为 value
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void set(String key, long index, Object value) {
        opsForList().set(key, index, value);
    }

    public void putList(String key, List value) {
        putList(key, value, defaultExpireSecond);
    }

    public void putList(String key, List value, long ttl) {
        putList(key, value, ttl, TimeUnit.SECONDS);
    }

    public void putList(String key, List value, long ttl, TimeUnit timeUnit) {
        putString(key, JSON.toJSONString(value), ttl, timeUnit);
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public List<Object> getList(String key, int start, int end) {
        return opsForList().range(key, start, end);
    }
    /**
     * 获取集合类
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key, Class<T> tClass) {
        String val = getString(key);
        if (StringUtils.isEmpty(val)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(val, tClass);
    }

    public <T> T getHash(String key,String hashKey,Class<T> tClass){
        Object obj=redisTemplate.opsForHash().get(key,hashKey);
        if (Objects.isNull(obj)) {
            return null;
        }
        return (T)obj;
    }

    public void putHash(String key,String hashKey,Object value){
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    public <T> List<T> getHashList(String key,String hashKey,Class<T> tClass){

        if(StringUtils.isEmpty(hashKey)) {
            List<Object> values = redisTemplate.opsForHash().values(key);
            return values.stream().map(e -> (T) e).collect(Collectors.toList());
        }
        BoundHashOperations bo = redisTemplate.boundHashOps(key);
        ScanOptions.ScanOptionsBuilder sacanBuider=  new ScanOptions.ScanOptionsBuilder();
        sacanBuider.match("*"+hashKey+"*");
        ScanOptions so =sacanBuider.build();
        System.out.println("so.toOptionString():"+so.toOptionString());
        org.springframework.data.redis.core.Cursor cur = bo.scan(sacanBuider.build());
        List<T> list=new ArrayList<>();
        try {
            while(cur.hasNext()){
                Map.Entry e= (Map.Entry) cur.next();
                list.add((T)e.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                cur.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @param valueSerializer 序列化
     * @return the list
     */
    public List<Object> getList(String key, int start, int end, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = rawKey(key);
        return redisTemplate.execute(connection -> deserializeValues(connection.lRange(rawKey, start, end), valueSerializer), true);
    }

    /**
     * redis List数据结构 : 批量存储
     *
     * @param key  the key
     * @param list the list
     * @return the long
     */
    public Long leftPushAll(String key, List<String> list) {
        return opsForList().leftPushAll(key, list);
    }

    /**
     * redis List数据结构 : 将值 value 插入到列表 key 当中，位于值 index 之前或之后,默认之后。
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void insert(String key, long index, Object value) {
        opsForList().set(key, index, value);
    }

    private byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");

        if (key instanceof byte[]) {
            return (byte[]) key;
        }
        RedisSerializer<Object> redisSerializer = (RedisSerializer<Object>)redisTemplate.getKeySerializer();
        return redisSerializer.serialize(key);
    }
    private byte[] rawValue(Object value, RedisSerializer valueSerializer) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }

        return valueSerializer.serialize(value);
    }

    private List deserializeValues(List<byte[]> rawValues, RedisSerializer<Object> valueSerializer) {
        if (valueSerializer == null) {
            return rawValues;
        }
        return SerializationUtils.deserialize(rawValues, valueSerializer);
    }

    private Object deserializeValue(byte[] value, RedisSerializer<Object> valueSerializer) {
        if (valueSerializer == null) {
            return value;
        }
        return valueSerializer.deserialize(value);
    }

    /**
     * 缓存string到redis 设置默认过期时间
     * @param key key
     * @param value 值
     */
    public void putString(String key, String value) {
        putString(key, value, defaultExpireSecond);
    }

    /**
     * 缓存string到redis 设置自定义过期时间(秒)
     * @param key key
     * @param value 值
     * @param ttl 过期时间
     */
    public void putString(String key, String value, long ttl) {
        putString(key, value, ttl, TimeUnit.SECONDS);
    }

    /**
     * 缓存string到redis 设置自定义过期时间(时间单位自定义)
     * @param key key
     * @param value 值
     * @param ttl 过期时间
     * @param timeUnit 时间单位
     */
    public void putString(String key, String value, long ttl, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, ttl, timeUnit);
    }

    /**
     * 缓存string到redis 永久不过期
     * @param key key
     * @param value 值
     */
    public void putStringNoTtl(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取String类型缓存值
     * @param key
     * @return
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /*--------------------------------Long-----------------------------------*/
    /**
     * 缓存Long到redis 设置默认过期时间
     * @param key key
     * @param value 值
     */
    public void putLong(String key, Long value) {
        putLong(key, value, defaultExpireSecond);
    }

    /**
     * 缓存Long到redis 设置自定义过期时间(秒)
     * @param key key
     * @param value 值
     * @param ttl 过期时间
     */
    public void putLong(String key, Long value, long ttl) {
        putLong(key, value, ttl, TimeUnit.SECONDS);
    }

    /**
     * 缓存Long到redis 设置自定义过期时间(时间单位自定义)
     * @param key key
     * @param value 值
     * @param ttl 过期时间
     * @param timeUnit 时间单位
     */
    public void putLong(String key, Long value, long ttl, TimeUnit timeUnit) {
        putString(key, String.valueOf(value), ttl, timeUnit);
    }
    /**
     * 缓存string到redis 永久不过期
     * @param key key
     * @param value 值
     */
    public void putLongNoTtl(String key, Long value) {
        putStringNoTtl(key, String.valueOf(value));
    }
    /**
     * 获取Long类型缓存值
     * @param key
     * @return long
     */
    public Long getLong(String key) {
        String val = getString(key);
        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            log.warn("获取Long类型缓存数据出错,key={};error={}",key,e.getMessage());
            return null;
        }
    }

    /*--------------------------------Object----------------------------------*/

    /**
     * 缓存 Object 到redis 设置默认过期时间
     * @param key key
     * @param value 值
     */
    public void putObject(String key, Object value) {
        putObject(key, value, defaultExpireSecond);
    }

    /**
     * 缓存 Object 到redis 设置自定义过期时间(秒)
     * @param key key
     * @param value 值
     * @param ttl 过期时间
     */
    public void putObject(String key, Object value, long ttl) {
        putObject(key, value, ttl, TimeUnit.SECONDS);
    }

    /**
     * 缓存 Object 到redis 设置自定义过期时间(时间单位自定义)
     * @param key key
     * @param value 值
     * @param ttl 过期时间
     * @param timeUnit 时间单位
     */
    public void putObject(String key, Object value, long ttl, TimeUnit timeUnit) {
        putString(key, JSON.toJSONString(value), ttl, timeUnit);
    }

    /**
     * 设置对象缓存(永不过期)
     * @param key   缓存Key
     * @param value 缓存值
     */
    public void putObjectNoTtl(String key, Object value) {
        putStringNoTtl(key, JSON.toJSONString(value));
    }

    /**
     * 获取 Object 缓存数据转成实体类对象返回
     * @param key redis key
     * @param tClass 类对象
     * @param <T> 类
     * @return T
     */
    public <T> T getObject(String key, Class<T> tClass) {
        return JSONObject.parseObject(getString(key), tClass);
    }

    /**
     * 获取 Object 缓存数据
     * @param key redis key
     * @return obj
     */
    public Object getObject(String key) {
        return JSONObject.parseObject(getString(key));
    }

    /**
     * 验证key是否存在
     * @param key redis key
     * @return boolean
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key redis key
     * @return 是否成功
     */
    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }
    /**
     * 获取缓存剩余时间（单位：秒）
     *
     * @param key 缓存key
     * @return
     */
    public long getExpire(String key){
        return getExpire(key, TimeUnit.SECONDS);
    }
    /**
     * 获取缓存剩余时间
     *
     * @param key      缓存key
     * @param timeUnit 时间单位
     * @return
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
}
