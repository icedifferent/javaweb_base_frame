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
	 * ������Ϣҳ��
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
	 * �༭������Ϣҳ��
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
	 * �޸ĸ�����Ϣ
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
					successes.addSuccess("�޸ĸ�����Ϣ�ɹ�!");	
				}else{
					errors.addError("δ֪����-�����ѱ����˰�");
				}
			} catch (Exception e) {
				errors.addError("editUserInfoδ֪����"+e);
			}
		}
		successes.toModel(model);
		errors.toModel(model);//���������Ϣ
		return "User/editInfo";
	}
	
	/**
	 * ͷ���ϴ�
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
					map.put("message", "�ļ���ʽ�������ϴ�ͼƬ(gif,jpg,jpeg,png,bmp)�ļ�");
					//out.write("�ļ���ʽ�������ϴ�ͼƬ(gif,jpg,jpeg,png,bmp)�ļ�");
				}else{
					try {
						photoUrl=fileRepository.storeByExt("/res/upload/portrait", ext, avatar_file);
						ServletContext  context=request.getSession().getServletContext();
						File fi = new File(context.getRealPath(photoUrl)); //��ͼ�ļ�  
						String miniPath=context.getRealPath("/res/upload/portrait");
						String miniName=UploadUtils.generateFilename("mini", ext);
						File fo = new File(miniPath,miniName); //��Ҫת������Сͼ�ļ�
						miniUrl="/res/upload/portrait/"+miniName;
						imageScale.resizeFix(fi, fo,180,180);
						if(user.getPortrait_img()!=null){
							//ɾ���û�֮ǰ��ͷ�����ϴ�
							File oldPic =  new File(miniPath,user.getPortrait_img());
							oldPic.delete();
						}
						//�����µ�ͷ��
						user.setPortrait_img(miniName);
						userService.editUser(user);
						map.put("status", "1");
						map.put("message", "�ϴ��ɹ�");
						
					} catch (IOException e) {
						map.put("status", "-2");
						map.put("message", "�ϴ�ʧ��");
					} catch (Exception e) {
						map.put("status", "-3");
						map.put("message", "�ϴ�ʧ��"+e.getMessage());
					}
				}
			}else{
				map.put("status", "-4");
				map.put("message", "ͼƬΪ��");
			}
		}else{
			map.put("status", "-5");
			map.put("message", "ͼƬ������");
		}
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
	}
		
	
	/**
	 * ��ȡ���伤������ json�ӿ�
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
			map.put("message", "email��ʽ�Ƿ�");
		} else
			try {
				if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),2,true)>=
						configService.getConfig().getMaxSendEmailTimes()){
					map.put("status", "-2");
					map.put("message", "ͬһip��ʱ���ڷ����ʼ���������");
				}else if(!user.getEmail().equals(email)){
					map.put("status", "-3");
					map.put("message", "��email�����ǰ�������˺�֮��");
				}else if(user.getIsCheckEmail()==1){
					map.put("status", "-6");
					map.put("message", "���Ѿ������ˣ�����Ҫ���¼���Ŷ");
				}else {
					if(checkCodeService.sendActEmail(user,email,request)){
						map.put("status", "1");
						map.put("message", "email�������ӳɹ����ͣ�ע�����");
					}else{
						map.put("status", "-4");
						map.put("message", "email�������ӷ���ʧ��");
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
	 * �����ʼ�
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
				//�Ƿ񱻱��˰���
				User otherUser;
				try {
					otherUser = userService.getUserByEmail(user.getEmail());
				} catch (Exception e) {
					otherUser=null;
				}
				if(otherUser==null||otherUser.getIsCheckEmail()==1){
					logger.debug("email�Ѿ��������û�����");
					errors.addError("email�Ѿ��������û�����");
				}
				if(!errors.hasErrors()){
					if(user.getEmailActCode().equals(actCode)){
						user.setIsCheckEmail((byte)1);
						try {
							userService.editUser(user);
							userService.kickoutUser(user.getPhone());
							logger.debug("email����ɹ�");
						} catch (Exception e) {
							errors.addError("editUser����"+e.getMessage());
							return "error/failure";
						}
						return "redirect:/login.do";
					}else{
						logger.debug("email��������Ч"+user.getEmailActCode()+"--"+actCode);
						errors.addError("email��������Ч");
					}
				}
			}else{
				errors.addError("���Ѿ�������ˣ������μ���");
			}
		}else{
			logger.debug("email����ID������ʧ��");
			errors.addError("email����ID������ʧ��");
		}
		return "error/failure";
	}
	
	
	
	
	
	/**
	 * �һ�����
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/User/FindPwd/findPwd.do", method = RequestMethod.GET)
	public String findPwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		model.addAttribute("needCaptcha",1);//��֤���Ǳ����
		return "User/FindPwd/findPwd";
	}
	
	/**
	 * �һ�����
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
					if(checkCodeService.toCheckCode(checkVar, checkCode)){//��֤ͨ��
						user.setUserPwd(md5PwdEncoder.encodePassword(newPwd));
						userService.editUser(user);
						successes.addSuccess("�޸�����ɹ�");
						model.addAttribute("needCaptcha",1);//��֤���Ǳ����
					}else{
						errors.addError("��������ֻ���֤�����");
					}
				} catch (Exception e) {
					errors.addError("editUserδ֪����"+e);
				}
			}else{
				errors.addError("�û�������");
			}
		}
		successes.toModel(model);
		errors.toModel(model);//���������Ϣ
		model.addAttribute("needCaptcha",1);//��֤���Ǳ����
		return "User/FindPwd/findPwd";
	}
	
	
	/**
	 * ��ȡ�ʼ������ֻ���֤�� �ӿ�
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
	 * �����ʼ���֤�� JSON�ӿ�
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
				map.put("message", "email��ʽ�Ƿ�");
			}else if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),2,true)>=
					configService.getConfig().getMaxSendEmailTimes()){
				map.put("status", "-2");
				map.put("message", "ͬһip��ʱ���ڷ����ʼ���������");
			} else if(((user=userService.getUserByEmail(email))==null)||(user.getIsCheckEmail()==0)){
				map.put("status", "-3");
				map.put("message", "email�����ڻ���û����");
			}else {
				String checkCode=String.valueOf((int)(Math.random()*9000+1000));
				CheckCode checkCodes=new CheckCode(email,checkCode,TimeUtils.getCurrentDay(),(byte)0);
				if(checkCodeService.addCheckCode(checkCodes)){
					if(checkCodeService.toSendEmailCode(email, checkCode)){
						map.put("status", "1");
						map.put("message", "email��֤��ɹ����ͣ�ע�����");
					}else{
						map.put("status", "-4");
						map.put("message", "email��֤�뷢��ʧ��");
					}
				}else{
					map.put("status", "0");
					map.put("message", "email����ʱ�������̣����Ժ�����");
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
	 * �ֻ���֤���ȡJSON�ӿ�
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
				map.put("message", "�ֻ������ʽ�Ƿ�");
			}else if(ipLimitService.getTimesByIp(RequestUtils.getIpAddr(request),1,true)>=
					configService.getConfig().getMaxSendMesTimes()){
				map.put("status", "-2");
				map.put("message", "ip��ʱ���ڻ�ȡ��֤���������");
			} else if(userService.getByUserPhone(phone)==null){
				map.put("status", "-3");
				map.put("message", "�ֻ����벻����");
			}else {
				String checkCode=String.valueOf((int)(Math.random()*9000+1000));
				CheckCode checkCodes=new CheckCode(phone,checkCode,TimeUtils.getCurrentDay(),(byte)0);
				if(checkCodeService.addCheckCode(checkCodes)){
					boolean status=Rest.sendMessage(phone,checkCode);
					if(status){
						map.put("status", "1");
						map.put("message", "�ֻ���֤��ɹ����ͣ�ע�����");
					}else{
						map.put("status", "-4");
						map.put("message", "�ֻ���֤�뷢��ʧ��");
					}
				}else{
					map.put("status", "0");
					map.put("message", "�ֻ���֤�뷢��ʱ�������̣����Ժ�����");
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
	 * ��������
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/User/resetPwd.do", method = RequestMethod.GET)
	public String resetPwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		model.addAttribute("needCaptcha",1);//��֤���Ǳ����
		return "User/resetPwd";
					  
	}
	
	
	
	/**
	 * ��������
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
					successes.addSuccess("�޸�����ɹ�");
				}else{
					errors.addError("δ֪����");
				}
			} catch (UserPhoneNotFoundException e) {
				errors.addError("�û���������");
			} catch (BadCredentialsException e) {
				errors.addError("�����벻��ȷ");
			} catch (Exception e) {
				errors.addError("�����������"+e.getMessage());
			}
		}
		successes.toModel(model);
		errors.toModel(model);//���������Ϣ
		model.addAttribute("needCaptcha",1);//��֤���Ǳ����
		return "User/resetPwd";
	}

	
	
	
	
	
}
