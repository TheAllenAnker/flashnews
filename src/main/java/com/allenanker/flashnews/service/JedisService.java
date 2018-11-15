package com.allenanker.flashnews.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisService.class);
    private JedisPool jedisPool;

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("Set element adding error: " + e.getMessage());
            return 1;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismemeber(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("Set element adding error: " + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return false;
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("Set element adding error: " + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.flushAll();

        // some basics
        jedis.set("hello", "jedis");
        System.out.println("hello: " + jedis.get("hello"));
        jedis.rename("hello", "I am learning");
        System.out.println(jedis.get("I am learning"));
        jedis.set("name", "Allen Anker");
        jedis.setex("15sname", 10, "FadingName");
        System.out.println(jedis.get("name"));

        // demo for list
        System.out.println("================================");
        String listName = "listA";
        for (int i = 0; i < 8; i++) {
            jedis.lpush(listName, "A" + String.valueOf(i));
        }
        System.out.println(jedis.lrange(listName, 0, 10));
        System.out.println(jedis.llen(listName));
        System.out.println(jedis.lpop(listName));

        System.out.println("================================");
        String userKey = "AllenProfile";
        jedis.hset(userKey, "name", "Allen Anker");
        jedis.hset(userKey, "gender", "Male");
        jedis.hset(userKey, "age", "19");
        System.out.println(jedis.hget(userKey, "name"));
        jedis.hdel(userKey, "age");
        System.out.println(jedis.hexists(userKey, "email"));
        System.out.println(jedis.hget(userKey, "email"));
        jedis.hsetnx(userKey, "name", "Fake Name");
        System.out.println(jedis.hgetAll(userKey));

        // demo for set
        System.out.println("================================");
        String setKey1 = "SetA";
        String setKey2 = "SetB";
        for (int i = 0; i < 8; i++) {
            jedis.sadd(setKey1, String.valueOf(i));
            jedis.sadd(setKey2, String.valueOf(i * 2));
        }
        System.out.println(jedis.sinter(setKey1, setKey2));

        // demo for sorted list

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool();
    }
}
