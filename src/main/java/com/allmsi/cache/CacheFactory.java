package com.allmsi.cache;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.allmsi.cache.map.MapCache;
import com.allmsi.cache.redis.service.RedisCacheService;
import com.allmsi.sys.config.SpringContextRegister;
import com.allmsi.sys.util.StrUtil;

/**
 * cache Factory 切换缓存机制
 * 
 * @author sunnannan
 *
 */
@Configuration
public class CacheFactory {

	@Resource
	private SpringContextRegister springContextRegister;

	@Value("${im.cache:map}")
	private String imCache;

	@Value("${im.redis.cache:com.allmsi.cache.redis.RedisCache}")
	private String imRedisCache;

	/**
	 * im.cache 在 im-config.properties 中手动配置
	 */
	private final static String DEFAULT_REDIS_CACHE = "com.allmsi.cache.redis.RedisCache";
	private final static String REDIS = "redis";
	String redisCache;

	/**
	 * 读取配置文件中im.cache配置的缓存类型 为空时，系统缓存选用系统自带的map缓存
	 * 
	 * @return
	 */
	public Cache getCache() {
		String cache = imCache;
		redisCache = imRedisCache;
		if (StrUtil.isEmpty(cache)) {
			return getCache(CacheEnum.MAP);
		} else if (REDIS.equals(cache)) {
			return getCache(CacheEnum.REDIS);
		}
		return getCache(CacheEnum.MAP);
	}

	/**
	 * 程序中切换缓存
	 * 
	 * @param cacheEnum
	 * @return
	 */
	public Cache getCache(CacheEnum cacheEnum) {
		switch (cacheEnum) {
		case MAP:
			return MapCache.getInstance();
		case REDIS:
			if (StrUtil.isEmpty(redisCache)) {
				redisCache = DEFAULT_REDIS_CACHE;
			}
			return springContextRegister.getServiceImpl(RedisCacheService.class, redisCache);
		}
		return null;
	}
}
