package com.kaola.edata;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

public class JredisUtil {

	private static final Logger		logger				= LoggerFactory.getLogger(JredisUtil.class);
	private static JedisPool		pool				= null;

	private static final int		poolMaxActive		= 200;
	private static final int		poolMaxIdle			= 50;
	private static final int		poolMaxWait			= 5000;
	private static final boolean	poolTestOnBorrow	= true;

	public static JedisPool getPool() {
		if (pool == null) {
			synchronized (JedisPool.class) {
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxActive(poolMaxActive);
					config.setMaxIdle(poolMaxIdle);
					config.setMaxWait(poolMaxWait);
					config.setTestOnReturn(true);
					config.setTestWhileIdle(true);
					config.setTestOnBorrow(poolTestOnBorrow);
					config.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
					pool = new JedisPool(config,
					//	"10.5.28.11",6379);
						"127.0.0.1",6379);

				}
			}
		}
		return pool;
	}

	/**
	 * @see 根据key删除缓存
	 * @param key
	 */
	public static void hdel(String key) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.del(SafeEncoder.encode(key));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key和field删除缓存
	 * @param key
	 * @param field
	 */
	public static void hdel(String key, Integer field) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hdel(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(field)));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key和field删除缓存
	 * @param key
	 * @param field
	 */
	public static void hdel(String key, Long field) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hdel(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(field)));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key和field删除缓存
	 * @param key
	 * @param field
	 */
	public static void hdel(String key, String field) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hdel(SafeEncoder.encode(key), SafeEncoder.encode(field));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key和field得到缓存
	 * @param key
	 * @param field
	 * @return
	 */
//	public static Object getObject(String key, Object field) {
//		Jedis jedis = getPool().getResource();
//		try {
//			byte bytes[] = jedis.hget(SafeEncoder.encode(key),
//				SafeEncoder.encode(String.valueOf(field)));
//			if (bytes != null) {
//				return SerializerUtil.byteToObject(bytes);
//			} else {
//				return null;
//			}
//		} finally {
//			try {
//				getPool().returnResource(jedis);
//			} catch (Throwable e) {
//				logger.error("return resource exception", e);
//			}
//		}
//	}

	/**
	 * @see 根据key和field得到缓存
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hget(String key, Long field) {
		Jedis jedis = getPool().getResource();
		try {
			byte bytes[] = jedis.hget(SafeEncoder.encode(key),
				SafeEncoder.encode(String.valueOf(field)));
			if (bytes != null) {
				return new String(bytes);
			} else {
				return null;
			}
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key和field得到缓存
	 * @param key
	 * @param field
	 * @return
	 */
	public static Integer hget(String key, String field) {
		Jedis jedis = getPool().getResource();
		try {
			String value = jedis.hget(key, field);
			if (value != null && !StringUtils.isBlank(value)) {
				return Integer.valueOf(value);
			} else {
				return null;
			}
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}
	/**
	 * @see 根据key和field得到缓存
	 * @param key
	 * @param field
	 * @return
	 */
	public static String hgetS(String key, String field) {
		Jedis jedis = getPool().getResource();
		try {
			String value = jedis.hget(key, field);
			if (value != null && !StringUtils.isBlank(value)) {
				return value;
			} else {
				return null;
			}
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key得到所有的缓存
	 * @param key
	 * @param field
	 * @return
	 */
	public static Map<byte[], byte[]> hgetAll(String key) {
		Jedis jedis = getPool().getResource();
		try {
			return jedis.hgetAll(SafeEncoder.encode(key));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 根据key值计算缓存对象的个数
	 * @param key
	 * @return
	 */
	public static Long hlen(String key) {
		Jedis jedis = getPool().getResource();
		try {
			return jedis.hlen(SafeEncoder.encode(key));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	/**
	 * @see 添加缓存对象
	 * @param key 
	 * 			两种理解(1:key可以是类别名称2:key可以是对象主键)
	 * @param field
	 * 			两种理解(1:field可以是主键2:filed可以是对象属性的主键)
	 * @param value
	 * 			两种理解(1:value可以是对象2:value可以是对象属性的值)
	 */
	public static void hset(String key, Integer field, Object value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(field)),
				SerializerUtil.objectToByte(value));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}
	public static void hsetS(String key, String field, Object value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(field)),
				SerializerUtil.objectToByte(value));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}
	public static void hset(String key, Long field, Object value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(field)),
				SerializerUtil.objectToByte(value));

		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void hset(String key, String field, Object value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(field)),
				SerializerUtil.objectToByte(value));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void hset(String key, String field, Integer value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field),
				SafeEncoder.encode(String.valueOf(value)));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void hset(String key, String field, String value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field),
				SafeEncoder.encode(String.valueOf(value)));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void hset(String key, String field, Long value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field),
				SafeEncoder.encode(String.valueOf(value)));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void set(String key, String value) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.set(SafeEncoder.encode(key), SafeEncoder.encode(String.valueOf(value)));
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static String get(String key) {
		Jedis jedis = getPool().getResource();
		try {
			byte bytes[] = jedis.get(SafeEncoder.encode(key));

			if (bytes != null) {
				return new String(bytes);
			} else {
				return null;
			}
		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void expire(String key, int time) {
		Jedis jedis = getPool().getResource();
		try {
			jedis.expire(SafeEncoder.encode(key), time);

		} finally {
			try {
				getPool().returnResource(jedis);
			} catch (Throwable e) {
				logger.error("return resource exception", e);
			}
		}
	}

	public static void main(String[] args) {
		//PropertyReader.setResourceBundle("app");
		hset("test", "ddd1", 50);
		System.out.println(hget("test", "ddd1"));
		//set("my", "ddd1");
		expire("my", 1);
		System.out.println(get("my"));
		expire("dd", 1);

	}
}
