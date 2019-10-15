package com.pouruan.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pouruan.common.exception.BadCredentialsException;
import com.pouruan.common.exception.UserPhoneNotFoundException;
import com.pouruan.common.ucpaasSMS.Rest;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.RequestUtils;
import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.component.ValidateSubmit;
import com.pouruan.web.entity.CheckCode;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.ConfigService;
import com.pouruan.web.service.IpLimitService;
import com.pouruan.web.service.CheckCodeService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.security.CommonService;
import com.pouruan.common.web.CookieUtils;
import com.pouruan.common.web.session.SessionProvider;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import static com.pouruan.common.web.Constants.PROCESS_URL;
import static com.pouruan.common.web.Constants.RETURN_URL;
import static com.pouruan.common.web.Constants.AUTH_KEY;
@Controller
public class LoginAction {

	@Autowired
	private UserService userService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private IpLimitService ipLimitService;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private CheckCodeService checkCodeService;
	@Autowired 
	private CommonService commonService;
	@Autowired
	private ValidateSubmit  vaildateSubmit;
	private Logger logger = LogManager.getRootLogger();
	/**
	 * 登录页面
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		User user=commonService.getCustomUser();
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);//主url
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);//来源url
		if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),0,false)>=
				configService.getConfig().getMaxErrorTimes()){//5次以上则需要验证码
			model.addAttribute("needCaptcha",1);
			logger.debug("需要验证码");
		}
		if(user!=null){//认证信息有效
				logger.debug("认证信息有效:"+commonService.getAuthenticationDetails().getSessionId());
				String view=getView(processUrl,returnUrl);
				if(view==null){
					return "login";
				}else{
					return view;
				}
		}else{
				logger.debug("认证信息无效:"+commonService.getAuthenticationDetails().getSessionId());
		}
		//不存在认证信息，显示正常登录页面
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		return "login";
	}
	
	
	
	/**
	 * 注册页面
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public String register(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		model.addAttribute("needCaptcha",1);//验证码是必须的
		return "register";
	}
	
	/**
	 * 手机验证码获取JSON接口
	 * @param phone
	 * @param captcha
	 * @param request
	 * @param response
	 * @return Json
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getCode.do", method = RequestMethod.POST)
	public void getCode(String phone, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		WebErrors errors = WebErrors.create(request);
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		try{
			if(errors.ifNotPhone(phone, "phone", 11, 11)){
				map.put("status", "-5");
				map.put("message", "手机号码格式非法");
			}else if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),1,true)>=
					configService.getConfig().getMaxSendMesTimes()){
				map.put("status", "-2");
				map.put("message", "ip注册过多禁止注册");
			}else if(userService.getByUserPhone(phone)!=null){
				//返回json，手机号码已经存在，禁止注册
				map.put("status", "-3");
				map.put("message", "手机号码已经存在，禁止注册");
			}else {
				String checkCode=String.valueOf((int)(Math.random()*9000+1000));
				CheckCode checkCodes=new CheckCode(phone,checkCode,TimeUtils.getCurrentDay(),(byte)0);
			
				if(checkCodeService.addCheckCode(checkCodes)){
					boolean status=Rest.sendMessage(phone,checkCode);
					if(status){
						map.put("status", "1");
						map.put("message", "手机验证码成功发送，注意查收");
					}else{
						map.put("status", "-4");
						map.put("message", "手机验证码发送失败");
					}
				}else{
					map.put("status", "0");
					map.put("message", "手机验证码发送时间间隔过短，请稍后再试");
				}
			}
		}catch(Exception e){
			map.put("status", "-5");
			map.put("message", "getCode未知错误"+e.getMessage());
		}
		
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 注册提交函数
	 * @param phone
	 * @param password
	 * @param checkCode
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String submitRegister(String phone,String password,String captcha,String checkCode,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);//主url
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);//来源url
		//判断用户和密码的合法性
		WebErrors errors =vaildateSubmit.validateSubmit(phone, password, captcha, request,response) ;
		
		if(userService.getByUserPhone(phone)!=null){
			errors.addError("你的手机号码已经注册过啦");
		}
		if(!checkCodeService.toCheckCode(phone, checkCode)){
			errors.addError("手机号码或者验证码出错");
		}
		
		
		if(!errors.hasErrors()){
			try{
				userService.addUser(phone, password, request);
				String view=getView(processUrl,returnUrl);
				if(view==null){
					return "index";
				}else{
					return view;
				}
			}catch(Exception e){
				logger.error("注册用户错误！"+e+phone+password);
				errors.addError("addUser注册失败"+e.getMessage());
			}
		}
		//注册失败
		errors.toModel(model);//输出错误信息
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		model.addAttribute("needCaptcha",1);//验证码是必须的
		return "register";
	}
	
	/**
	 * 获取存在认证之后的重定向地址
	 * @param processUrl
	 * @param returnUrl
	 * @return
	 */
	private String getView(String processUrl, String returnUrl){
		if (!StringUtils.isBlank(processUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(processUrl);
			if (!StringUtils.isBlank(returnUrl)) {
				sb.append("&").append(RETURN_URL).append("=").append(returnUrl);
			}
			return sb.toString();
		} else if (!StringUtils.isBlank(returnUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(returnUrl);
			return sb.toString();
		} else {
			logger.debug("存在认证，但是返回地址为空");
			return null;
		}
	}
	
	
	
	
	

	
	/**
	 * 测试函数
	 * @return
	 */
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public String index(){
		/**
		 *获得rootLogger：Logger rootLogger=Logger.getRootLogger();­
		 *获得自定义Logger：Logger myLogger =Logger.getLogger("log4j.logger.myLogger");­
		 */
		//Logger logger = LogManager.getRootLogger();
		Logger logger = LogManager.getLogger(LoginAction.class);
	    logger.debug( " debug eee" );
	    logger.error( " error lala " );
	    
	    
	    
	   
		//System.out.println(userService.getUserInfo().get(0).getPhone());
		return "index";
	}
	
	public static void main(String args[]){
		new LoginAction().index();
	}
	
//	ehcache不拦截action层的，所以这里使用缓存是无效果的
//	@RequestMapping(value = "/testCache.do", method = RequestMethod.GET)
//	public  String testCache(){
//		//System.out.println(System.currentTimeMillis());
//		t("123");
//		return "index";
//	}
//	@Cacheable(value="caches",key="#s")
//	private void t(String s){
//		//System.out.println(System.currentTimeMillis());
//	}
}
