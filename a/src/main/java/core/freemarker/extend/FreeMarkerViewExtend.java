package core.freemarker.extend;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.jagregory.shiro.freemarker.ShiroTags;

import bizs.demo.model.UUser;
import core.shiro.token.TokenManager;
import core.utilities.SpringContextUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
 
public class FreeMarkerViewExtend extends FreeMarkerView {
	
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request){
		
		try {
			super.exposeHelpers(model, request);
		} catch (Exception e) {
		}
		model.put("contextPath", request.getContextPath());
		//model.putAll(FreeMarkerViewExtend.initMap);
		UUser token = TokenManager.getToken();
		model.put("token", token);		//登录的token
		model.put("_time", new Date().getTime());
		model.put("NOW_YEAY", Calendar.getInstance().get(Calendar.YEAR));//今年
		
		model.put("_v", "1");			//版本号，重启的时间
		model.put("cdn", "www.aaa.com");//CDN域名
		
		
	}
	
 
}
