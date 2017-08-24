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
		model.putAll(FreeMarkerViewExtend.initMap);
		UUser token = TokenManager.getToken();
		model.put("token", token);		//登录的token
		model.put("_time", new Date().getTime());
		model.put("NOW_YEAY", Calendar.getInstance().get(Calendar.YEAR));//今年
		
		model.put("_v", "1");			//版本号，重启的时间
		model.put("cdn", "www.aaa.com");//CDN域名
		
		
	}
	
	private static Configuration cfg = null;
	static Map<String,Object> initMap;
	
	static {
		initMap = new LinkedHashMap<String,Object>() ;
		/**项目静态地址*/
//		String jsPath = WYFConfig.get("js_path");
//		String csspath = WYFConfig.get("css_path");
//		String imgpath = WYFConfig.get("img_path");
//		String path    = WYFConfig.get("path");
//		initMap.put("jspath", jsPath);
//		initMap.put("csspath", csspath);
//		initMap.put("imgpath", imgpath);
//		initMap.put("path", path);
		/**Freemarker Config*/
		//1、创建Cfg
		cfg = new Configuration();
		//2、设置编码
		cfg.setLocale(Locale.getDefault()) ;
		cfg.setEncoding(Locale.getDefault(),"UTF-8") ;
		
		/**添加自定义标签*/
		//APITemplateModel api = SpringContextUtil.getBean("api",APITemplateModel.class);
		//cfg.setSharedVariable("api", api);
		
		FreeMarkerConfigurer ext = SpringContextUtil.getBean("freemarkerConfig",FreeMarkerConfigurer.class);
		
		Configuration vcfg = ext.getConfiguration();
		Set<String> keys = vcfg.getSharedVariableNames();
		for (String key : keys) {
			TemplateModel value = vcfg.getSharedVariable(key);
			cfg.setSharedVariable(key, value); 
		}
		cfg.setSharedVariable("shiro",new ShiroTags()); 
		cfg.setNumberFormat("#");//防止页面输出数字,变成2,000
		 
	}
}
