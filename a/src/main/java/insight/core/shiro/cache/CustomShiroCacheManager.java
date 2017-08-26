package insight.core.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;


/**
 * 
 * 开发公司：sojson.com<br/>
 * 版权：sojson.com<br/>
 * <p>
 * 
 * shiro Custom Cache
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年4月29日 　<br/>
 * <p>
 * *******
 * <p>
 * @author zhou-baicheng
 * @email  json@sojson.com
 * @version 1.0,2016年4月29日 <br/>
 * 
 */
public class CustomShiroCacheManager implements CacheManager, Destroyable {

    private JedisManager jedisManager;
    
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    public void destroy() {
    	//如果和其他系统，或者应用在一起就不能关闭
    	//getJedisManager().getJedis().shutdown();
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

}
