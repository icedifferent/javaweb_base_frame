package com.pouruan.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.pouruan.common.upload.UploadUtils;
import com.pouruan.common.encode.PwdEncoder;
import com.pouruan.common.encode.aes.AesEncode;
import com.pouruan.common.exception.BadCredentialsException;
import com.pouruan.common.exception.UserPhoneNotFoundException;
import com.pouruan.common.image.ImageScaleImpl;
import com.pouruan.common.ucpaasSMS.Rest;
import com.pouruan.common.upload.FileRepository;
import com.pouruan.common.web.RequestUtils;
import com.pouruan.common.web.TimeUtils;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.tips.WebSuccess;
import com.pouruan.web.component.ValidateSubmit;
import com.pouruan.web.entity.CheckCode;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.AuthService;
import com.pouruan.web.service.ConfigService;
import com.pouruan.web.service.IpLimitService;
import com.pouruan.web.service.CheckCodeService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.security.CommonService;

@Controller
public class UserAction {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;
	@Autowired
	private IpLimitService ipLimitService;
	@Autowired 
	private CommonService commonService;
	@Autowired
	private PwdEncoder md5PwdEncoder;
	@Autowired
	private ValidateSubmit  vaildateSubmit;
	@Autowired
	private ConfigService configService;
	@Autowired
	private AesEncode aesEncode;
	@Autowired
	private CheckCodeService checkCodeService;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private ImageScaleImpl imageScale;
	private Logger logger = LogManager.getRootLogger();
	@Autowired
	private SessionRegistry  sessionRegistry;

	
	/**
	 * 个人信息页面
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/User/index.do", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		User user=commonService.getCustomUser();
		model.addAttribute("userInfo",user);
		return "User/index";
	}
	
	/**
	 * 编辑个人信息页面
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/User/editInfo.do", method = RequestMethod.GET)
	public String editInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		User user=commonService.getCustomUser();
		model.addAttribute("userInfo",user);
		return "User/editInfo";
	}
	
	
	/**
	 * 修改个人信息
	 * @param userName
	 * @param email
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/User/editInfo.do", method = RequestMethod.POST)
	public String editInfoDone(String userName,String email,HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		WebSuccess successes = WebSuccess.create(request);
		WebErrors errors; 
		errors=vaildateSubmit.validateEmailAndUserName(email, userName, request, response);
		User user=commonService.getCustomUser();
		model.addAttribute("userInfo",user);
		if(!errors.hasErrors()){
			try {
				if(userService.editUserInfo(user,StringEscapeUtils.escapeHtml4(userName),email)){
					successes.addSuccess("修改个人信息成功!");	
				}else{
					errors.addError("未知错误-邮箱已被他人绑定");
				}
			} catch (Exception e) {
				errors.addError("editUserInfo未知错误"+e);
			}
		}
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		return "User/editInfo";
	}
	
	/**
	 * 头像上传
	 * @param avatar_file
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/User/editPortrait.do", method = RequestMethod.POST)
	public void editInfoDone(MultipartFile avatar_file,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException{
		User user=commonService.getCustomUser();
		String photoUrl=null,miniUrl=null;
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		if(avatar_file!=null){
			if(!avatar_file.isEmpty()){
				String origName = avatar_file.getOriginalFilename();
				String ext = FilenameUtils.getExtension(origName).toLowerCase(
						Locale.ENGLISH);
				if(ext.equals("")){
					ext="png";
				}
				if (!"GIF,JPG,JPEG,PNG,BMP".contains(ext.toUpperCase())) {
					map.put("status", "-1");
					map.put("message", "文件格式有误，请上传图片(gif,jpg,jpeg,png,bmp)文件");
					//out.write("文件格式有误，请上传图片(gif,jpg,jpeg,png,bmp)文件");
				}else{
					try {
						photoUrl=fileRepository.storeByExt("/res/upload/portrait", ext, avatar_file);
						ServletContext  context=request.getSession().getServletContext();
						File fi = new File(context.getRealPath(photoUrl)); //大图文件  
						String miniPath=context.getRealPath("/res/upload/portrait");
						String miniName=UploadUtils.generateFilename("mini", ext);
						File fo = new File(miniPath,miniName); //将要转换出的小图文件
						miniUrl="/res/upload/portrait/"+miniName;
						imageScale.resizeFix(fi, fo,180,180);
						if(user.getPortrait_img()!=null){
							//删除用户之前的头像再上传
							File oldPic =  new File(miniPath,user.getPortrait_img());
							oldPic.delete();
						}
						//保存新的头像
						user.setPortrait_img(miniName);
						userService.editUser(user);
						map.put("status", "1");
						map.put("message", "上传成功");
						
					} catch (IOException e) {
						map.put("status", "-2");
						map.put("message", "上传失败");
					} catch (Exception e) {
						map.put("status", "-3");
						map.put("message", "上传失败"+e.getMessage());
					}
				}
			}else{
				map.put("status", "-4");
				map.put("message", "图片为空");
			}
		}else{
			map.put("status", "-5");
			map.put("message", "图片不存在");
		}
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
	}
		
	
	/**
	 * 获取邮箱激活链接 json接口
	 * @param checkVar
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException 
	 */
	@RequestMapping(value = "/User/sendActEmailCode.do", method = RequestMethod.POST)
	public  void sendActEmail(String email,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException{
		response.setCharacterEncoding("UTF-8");
		WebErrors errors = WebErrors.create(request);
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		User user=commonService.getCustomUser();
		if(errors.ifNotEmail(email, "email", 4, 64)){
			map.put("status", "-5");
			map.put("message", "email格式非法");
		} else
			try {
				if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),2,true)>=
						configService.getConfig().getMaxSendEmailTimes()){
					map.put("status", "-2");
					map.put("message", "同一ip短时间内发送邮件次数过多");
				}else if(!user.getEmail().equals(email)){
					map.put("status", "-3");
					map.put("message", "此email并不是绑定在你的账号之下");
				}else if(user.getIsCheckEmail()==1){
					map.put("status", "-6");
					map.put("message", "你已经激活了，不需要重新激活哦");
				}else {
					if(checkCodeService.sendActEmail(user,email,request)){
						map.put("status", "1");
						map.put("message", "email激活链接成功发送，注意查收");
					}else{
						map.put("status", "-4");
						map.put("message", "email激活链接发送失败");
					}
				}
			} catch (Exception e) {
				map.put("status", "-7");
				map.put("message", e.getMessage());
			}
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
	}
	/**
	 * 激活邮件
	 * @param actCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/User/actEmail.do", method = RequestMethod.GET)
	public  String activeEmail(String actCode,Integer userId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		WebErrors errors = WebErrors.create(request);
		User user;
		try {
			user = userService.getUserByUserId(userId);
		} catch (Exception e) {
			user=null;
		}
		if(user!=null){
			if(user.getIsCheckEmail()==0){
				//是否被别人绑定了
				User otherUser;
				try {
					otherUser = userService.getUserByEmail(user.getEmail());
				} catch (Exception e) {
					otherUser=null;
				}
				if(otherUser==null||otherUser.getIsCheckEmail()==1){
					logger.debug("email已经被其他用户激活");
					errors.addError("email已经被其他用户激活");
				}
				if(!errors.hasErrors()){
					if(user.getEmailActCode().equals(actCode)){
						user.setIsCheckEmail((byte)1);
						try {
							userService.editUser(user);
							userService.kickoutUser(user.getPhone());
							logger.debug("email激活成功");
						} catch (Exception e) {
							errors.addError("editUser错误"+e.getMessage());
							return "error/failure";
						}
						return "redirect:/login.do";
					}else{
						logger.debug("email激活码无效"+user.getEmailActCode()+"--"+actCode);
						errors.addError("email激活码无效");
					}
				}
			}else{
				errors.addError("你已经激活过了！请勿多次激活");
			}
		}else{
			logger.debug("email激活ID不存在失败");
			errors.addError("email激活ID不存在失败");
		}
		return "error/failure";
	}
	
	
	
	
	
	/**
	 * 找回密码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/User/FindPwd/findPwd.do", method = RequestMethod.GET)
	public String findPwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		model.addAttribute("needCaptcha",1);//验证码是必须的
		return "User/FindPwd/findPwd";
	}
	
	/**
	 * 找回密码
	 * @param phone
	 * @param checkCode
	 * @param newPwd
	 * @param captcha
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/User/FindPwd/findPwd.do", method = RequestMethod.POST)
	public String findPwdDone(String checkVar,String checkCode,String newPwd,String captcha,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors;
		WebSuccess successes = WebSuccess.create(request);
		if(!WebErrors.isEmail(checkVar))
			errors=vaildateSubmit.validateSubmit(checkVar, newPwd, captcha, request, response);
		else
			errors=vaildateSubmit.validateEmailSubmit(checkVar, newPwd, captcha, request, response);
		if(!errors.hasErrors()){
			User user=null;
			if(!WebErrors.isEmail(checkVar))
				user=userService.getByUserPhone(checkVar);
			else
				user=userService.getUserByEmail(checkVar);
			if(user!=null){
				try {
					if(checkCodeService.toCheckCode(checkVar, checkCode)){//验证通过
						user.setUserPwd(md5PwdEncoder.encodePassword(newPwd));
						userService.editUser(user);
						successes.addSuccess("修改密码成功");
						model.addAttribute("needCaptcha",1);//验证码是必须的
					}else{
						errors.addError("邮箱或者手机验证码错误");
					}
				} catch (Exception e) {
					errors.addError("editUser未知错误"+e);
				}
			}else{
				errors.addError("用户不存在");
			}
		}
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		model.addAttribute("needCaptcha",1);//验证码是必须的
		return "User/FindPwd/findPwd";
	}
	
	
	/**
	 * 获取邮件或者手机验证码 接口
	 * @param checkVar
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/User/FindPwd/getCode.do", method = RequestMethod.POST)
	public 	void getCode(String checkVar, HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		if(!WebErrors.isEmail(checkVar))
			this.getPhoneCode(checkVar, request, response);
		else
			this.getEmailCode(checkVar, request, response);
	}
	/**
	 * 发送邮件验证码 JSON接口
	 * @param email
	 * @param request
	 * @param response
	 * @throws IOException
	 */

	private void getEmailCode(String email, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		WebErrors errors = WebErrors.create(request);
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		User user;
		try {
			if(errors.ifNotEmail(email, "email", 4, 64)){
				map.put("status", "-5");
				map.put("message", "email格式非法");
			}else if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),2,true)>=
					configService.getConfig().getMaxSendEmailTimes()){
				map.put("status", "-2");
				map.put("message", "同一ip短时间内发送邮件次数过多");
			} else if(((user=userService.getUserByEmail(email))==null)||(user.getIsCheckEmail()==0)){
				map.put("status", "-3");
				map.put("message", "email不存在或者没激活");
			}else {
				String checkCode=String.valueOf((int)(Math.random()*9000+1000));
				CheckCode checkCodes=new CheckCode(email,checkCode,TimeUtils.getCurrentDay(),(byte)0);
				if(checkCodeService.addCheckCode(checkCodes)){
					if(checkCodeService.toSendEmailCode(email, checkCode)){
						map.put("status", "1");
						map.put("message", "email验证码成功发送，注意查收");
					}else{
						map.put("status", "-4");
						map.put("message", "email验证码发送失败");
					}
				}else{
					map.put("status", "0");
					map.put("message", "email发送时间间隔过短，请稍后再试");
				}
			}
		} catch (Exception e) {
			map.put("status", "-6");
			map.put("message", e.getMessage());
		}
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
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
	private void getPhoneCode(String phone, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		WebErrors errors = WebErrors.create(request);
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		try {
			if(errors.ifNotPhone(phone, "phone", 11, 11)){
				map.put("status", "-5");
				map.put("message", "手机号码格式非法");
			}else if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),1,true)>=
					configService.getConfig().getMaxSendMesTimes()){
				map.put("status", "-2");
				map.put("message", "ip短时间内获取验证码次数过多");
			} else if(userService.getByUserPhone(phone)==null){
				map.put("status", "-3");
				map.put("message", "手机号码不存在");
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
		} catch (Exception e) {
			map.put("status", "-6");
			map.put("message", e.getMessage());
		}
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
	}
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/User/resetPwd.do", method = RequestMethod.GET)
	public String resetPwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		model.addAttribute("needCaptcha",1);//验证码是必须的
		return "User/resetPwd";
					  
	}
	
	
	
	/**
	 * 重置密码
	 * @param oldPwd
	 * @param newPwd
	 * @param captcha
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/User/resetPwd.do", method = RequestMethod.POST)
	public String submitResetPwd(String oldPwd,String newPwd,String captcha,
			HttpServletRequest request,HttpServletResponse response, ModelMap model){
		WebErrors errors;
		WebSuccess successes = WebSuccess.create(request);
		String phone=commonService.getCustomUser().getPhone();
		errors=vaildateSubmit.validateSubmit(phone, newPwd, captcha, request, response);
		if(!errors.hasErrors()){
			try {
				User user=userService.login(phone, oldPwd);
				user.setUserPwd(md5PwdEncoder.encodePassword(newPwd));
				if(userService.editUser(user)){
					successes.addSuccess("修改密码成功");
				}else{
					errors.addError("未知错误");
				}
			} catch (UserPhoneNotFoundException e) {
				errors.addError("用户名不存在");
			} catch (BadCredentialsException e) {
				errors.addError("旧密码不正确");
			} catch (Exception e) {
				errors.addError("重置密码错误"+e.getMessage());
			}
		}
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		model.addAttribute("needCaptcha",1);//验证码是必须的
		return "User/resetPwd";
	}

	
	
	
	
	
}
