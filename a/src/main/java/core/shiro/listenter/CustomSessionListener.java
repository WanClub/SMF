package core.shiro.listenter;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;


import core.shiro.cache.JedisShiroSessionRepository;
/**
 * 
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/  <p>
 * <p>
 * 
 * shiro 回话 监听
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年6月2日 　<br/>
 *
 * @author zhou-baicheng
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
public class CustomSessionListener implements SessionListener {

    private JedisShiroSessionRepository shiroSessionRepository;

    /**
     * 一个回话的生命周期开始
     */
    public void onStart(Session session) {
        //TODO
        System.out.println("一个回话的生命周期开始了");
    }
    /**
     * 一个回话的生命周期结束
     */
    public void onStop(Session session) {
        //TODO
        System.out.println("on stop");
    }

    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public JedisShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(JedisShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}

