package insight.portal.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import insight.core.shiro.token.TokenManager;
import insight.core.utilities.StringUtils;
import insight.portal.model.UUser;
 
 

@Controller
@Scope(value="prototype")
@RequestMapping("portal")
public class PortalController  {
	
	
	protected Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

	
	/**
	 * 个人资料
	 * @return
	 */
	@RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView login(){
		
		
		UUser token = TokenManager.getToken();
		
		if(null != token ){ 
			return new ModelAndView("portal/frame");
        } else {
        	
        	return new ModelAndView("portal/login");
        }
		
		
	} 
		
	
	/**
	 * 个人资料
	 * @return
	 */
	@RequestMapping(value="index",method=RequestMethod.GET)
	public ModelAndView index(){
		
		return new ModelAndView("portal/frame");
	} 
	
	
	
	@RequestMapping(value="submitLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitLogin(UUser entity,Boolean rememberMe,HttpServletRequest request){
		
		try {
			entity = TokenManager.login(entity,rememberMe);
			resultMap.put("status", 200);
			resultMap.put("message", "登录成功");
			resultMap.put("date", System.currentTimeMillis());
			//获取用户未登录之前的地址
			String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
			//如果登录之前没有地址，那么就跳转到首页。
			if(StringUtils.isBlank(url)){
				url = "/portal/frame.shtml";
			}
			//跳转地址
			resultMap.put("back_url", url);
		/**
		 * 这里其实可以直接catch Exception，然后抛出 message即可，但是最好还是各种明细catch 好点。。
		 */
		} catch (DisabledAccountException e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号已经禁用。");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号或密码错误");
		}
			
		return resultMap;
	}		
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method =RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		try {
			TokenManager.logout();
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			e.printStackTrace();
		}
		return resultMap;
	}	
}
