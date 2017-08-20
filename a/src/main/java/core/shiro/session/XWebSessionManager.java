package core.shiro.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

public class XWebSessionManager extends DefaultWebSessionManager{

	
	
	
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        if (sessionId == null) {
            
            return null;
        }
        
        // ***************Add By Goma****************
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null) {
            Object s = request.getAttribute(sessionId.toString());
            if (s != null) {
                return (Session) s;
            }
        }
        // ***************Add By Goma****************


        
        Session s = retrieveSessionFromDataSource(sessionId);
        if (s == null) {
            //session ID was provided, meaning one is expected to be found, but we couldn't find one:
            String msg = "Could not find session with ID [" + sessionId + "]";
            throw new UnknownSessionException(msg);
        }
        
        // ***************Add By Goma****************
        if (request != null) {
            request.setAttribute(sessionId.toString(),s);
        }
        // ***************Add By Goma****************
        
        
        return s;
    }
	
	
	
}
