package com.allmsi.cache;


import org.springframework.stereotype.Component;

import com.allmsi.cache.Cache;
import com.allmsi.cache.CacheFactory;

@Component(value = "CacheInstenceBase")
public class CacheInstence {

	private final CacheFactory cacheFactory;
	
	public CacheInstence(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

	public Cache getCache() {
		return cacheFactory.getCache();
	}

	public boolean del(String key) {
		return getCache().del(key);
	}

	public void setObject(String key, Object value) {
		getCache().setObject(key, value);
	}

	public void setObject(String key, Object value, int iSessionOut) {
		getCache().setObject(key, value, iSessionOut);
	}

	public Object getObject(String key) {
		return getCache().getObject(key);
	}

	public boolean isExists(String key) {
		return getCache().isExists(key);
	}
}
