package com.github.wangdasong.wx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RedisBusiness {
    private static final Logger logger = LoggerFactory.getLogger(RedisBusiness.class);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public RedisConnection getConnection(){
        return redisConnectionFactory.getConnection();
    }

    public void setEx(RedisConnection connection, String key, String value , int expire) throws Exception{
            connection.setEx(key.getBytes(), expire, value.getBytes());
    }
    public void setEx(String key, String value, int expire) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            setEx(connection, key, value, expire);
        }catch (Exception e){
            logger.warn("redis 保存数据出现情况，现发起一次重复连接", e);
            if (connection != null)
                connection.close();
            connection = redisConnectionFactory.getConnection();
            connection.setEx(key.getBytes(), expire, value.getBytes());
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    public void del(RedisConnection connection, String key) throws Exception{
        connection.del(key.getBytes());
    }
    public void del(String key) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            del(connection, key);
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    public void watch(RedisConnection connection, String key) throws Exception{
        connection.watch(key.getBytes());
    }
    public void watch(String key) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            watch(connection, key);
        } finally {
            if (connection != null)
                connection.close();
        }
    }

    public String get(RedisConnection connection, String key) throws Exception{
        byte[] bytes = null;
        bytes = connection.get(key.getBytes());
        if (bytes == null) {
            logger.warn("redis缓存无此key，可能是超时失效了");
            throw new Exception("redis 失效");
        }
        return  new String(bytes);
    }
    public String get(String key) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        String reValue;
        try {
            reValue = get(connection, key);
        }finally {
            if (connection != null)
                connection.close();
        }
        return reValue;
    }

    public void set(RedisConnection connection, String key,String value) throws Exception{
            connection.set(key.getBytes(), value.getBytes());
    }

    public void set(String key,String value) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            set(connection, key, value);
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    public void setNX(RedisConnection connection, String key,String value) throws Exception{
        connection.setNX(key.getBytes(), value.getBytes());
    }
    public void setNX(String key,String value) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            setNX(connection, key, value);
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    public void multi(RedisConnection connection) throws Exception{
             connection.multi();
    }

    public void multi() throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            multi(connection);
        }finally {
            if (connection != null)
                connection.close();
        }
    }


    public Long incrBy(RedisConnection connection, String key, Long value) throws Exception{
        return connection.incrBy(key.getBytes(), value);
    }
    public Long incrBy(String key, Long value) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        Long reIncr = null;
        try {
            return incrBy(connection, key, value);
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    public List<Object> exec(RedisConnection connection) throws Exception{
        return connection.exec();
    }
    public List<Object> exec() throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            return exec(connection);
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    public boolean exists(RedisConnection connection, String key) throws Exception{
        return connection.exists(key.getBytes());

    }
    public boolean exists(String key) throws Exception{
        RedisConnection connection = redisConnectionFactory.getConnection();
        try {
            return exists(connection, key);
        }finally {
            if (connection != null)
                connection.close();
        }
    }

}
