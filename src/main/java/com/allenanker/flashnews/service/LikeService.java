package com.allenanker.flashnews.service;

import com.allenanker.flashnews.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private JedisService jedisService;

    /**
     * Determine if a given user like the news or not. Return 1 -- like, -1 -- dislike, 0 -- neutral.
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityId, entityType);
        if (jedisService.sismemeber(likeKey, String.valueOf(userId))) {
            return 1;
        } else if (jedisService.sismemeber(dislikeKey, String.valueOf(userId))) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Like a news for a user and return the newest like count.
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisService.sadd(likeKey, String.valueOf(userId));
        jedisService.srem(dislikeKey, String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    /**
     * Dislike a news for a user and return the newest dislike count.
     * @param userId
     * @param entityId
     * @param entityType
     * @return
     */
    public long dislike(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisService.srem(likeKey, String.valueOf(userId));
        jedisService.sadd(dislikeKey, String.valueOf(userId));

        return jedisService.scard(dislikeKey);
    }
}
