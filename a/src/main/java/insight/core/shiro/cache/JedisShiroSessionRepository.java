package insight.core.shiro.cache;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

import insight.core.shiro.session.CustomSessionManager;
import insight.core.shiro.session.SessionStatus;
import insight.core.utilities.SerializeUtil;
 
/**
 * Session 管理
 * @author sojson.com
 *
 */
@SuppressWarnings("unchecked")
public class JedisShiroSessionRepository  {
    public static final String REDIS_SHIRO_SESSION = "shiro-session:";
    //这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
    public static final String REDIS_SHIRO_ALL = "*shiro-session:*";
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 1;

    private JedisManager jedisManager;

    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = buildRedisSessionKey(session.getId()).getBytes();//SerializeUtil.serialize(buildRedisSessionKey(session.getId()));
            
            
            //不存在才添加。
            if(null == session.getAttribute(CustomSessionManager.SESSION_STATUS)){
            	//Session 踢出自存存储。
            	SessionStatus sessionStatus = new SessionStatus();
            	session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }
            
            byte[] value = SerializeUtil.serialize(session);
            long sessionTimeOut = session.getTimeout() / 1000;
            Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
            getJedisManager().saveValueByKey(DB_INDEX, key, value, expireTime.intValue());
        } catch (Exception e) {
        }
    }

    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,buildRedisSessionKey(id).getBytes());
        } catch (Exception e) {
        }
    }

   
    public Session getSession(Serializable id) {
        if (id == null)
        	 throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, buildRedisSessionKey(id).getBytes());
            session = SerializeUtil.deserialize(value, Session.class);
        } catch (Exception e) {
        }
        return session;
    }

    public Collection<Session> getAllSessions() {
    	Collection<Session> sessions = null;
		try {
			sessions = getJedisManager().AllSession(DB_INDEX,REDIS_SHIRO_SESSION);
		} catch (Exception e) {
		}
       
        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
