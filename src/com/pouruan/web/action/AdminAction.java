package com.pouruan.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.tips.WebSuccess;
import com.pouruan.web.component.ValidateSubmit;
import com.pouruan.web.entity.Config;
import com.pouruan.web.entity.Log;
import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.Role;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.AdminService;
import com.pouruan.web.service.ConfigService;
import com.pouruan.web.service.LogService;
import com.pouruan.web.service.MessageService;
import com.pouruan.web.service.PermissionService;
import com.pouruan.web.service.RoleService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.security.CommonService;

@Controller
public class AdminAction {
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;
	@Autowired 
	private CommonService commonService;
	@Autowired 
	private ConfigService configService;
	@Autowired
	private ValidateSubmit  vaildateSubmit;
	@Autowired
	private MessageService messageService;
	@Autowired
	private LogService logService;
	private Logger logger = LogManager.getRootLogger();
	/**
	 * ���ӹ���Աҳ��
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Role/addRole.do", method = RequestMethod.GET)
	public String addRole(
			HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		//�г�����Ȩ��
		model.addAttribute("permissionList",permissionService.showAllPermission());
		logger.debug("����Ȩ��:"+permissionService.showAllPermission());
		return "Admin/Role/addRole";
	}
	
	/**
	 * ���ӹ���ĳ��Ȩ�ޣ��ǹ���Ա���Զ���ӳ�Ϊ����Ա��
	 * @param userId
	 * @param permissionId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Role/addRole.do", method = RequestMethod.POST)
	public String addRoleDone(Integer userId,Integer permissionId,
			HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		errors.ifBlank(userId.toString(), "userId", 10);
		errors.ifBlank(permissionId.toString(), "permissionId", 10);
		try {
			if(!errors.hasErrors()){
				//�ж��û�Id�Ƿ����
				User user = userService.getUserByUserId(userId);
				
				if(user==null){
					errors.addError("�û�id������");
				}
				
				//�ж�Ȩ��Id�Ƿ����
				Permission permission =permissionService.getPermissionById(permissionId);
				if(permission==null){
					errors.addError("Ȩ�޲�����");
				}
				//�жϴ��û��Ƿ��Ѿ�ӵ�д�Ȩ��
				if(!errors.hasErrors()){
					if( user.getAdmin()!=null){
						Set<Role> set = user.getAdmin().getRoles();
						Iterator<Role> it = set.iterator();  
						boolean flag=false;
						while (it.hasNext()) {  
							 Role role = it.next();
							 if(role.getPermission().getId().equals(permissionId)){
								 flag=true;
								 break;
							 }
						}
						if(flag==true){
							errors.addError("���û��Ѿ�ӵ�д�Ȩ�ޣ������������");
						}
					}
				}
				if(!errors.hasErrors()){
					adminService.addRole(user, permission);
					successes.addSuccess("��ӳɹ�");
				}
			}
		} catch (Exception e) {
			errors.addError("addRoleDone"+e.getMessage());
		}
		//�г�����Ȩ��
		model.addAttribute("permissionList",permissionService.showAllPermission());
		successes.toModel(model);
		errors.toModel(model);//���������Ϣ
		return "Admin/Role/addRole";
	}
	
	/**
	 * ɾ��ĳ������Ա
	 * @param userId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Role/delAdmin.do", method = RequestMethod.POST)
	public String delAdmin(Integer userId,
			HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		errors.ifBlank(userId.toString(), "userId", 10);
		User users=commonService.getCustomUser();
		if(users.getUserId()==userId){
			errors.addError("���ܲ����Լ�");	
		}
		try{
			if(!errors.hasErrors()){
				//�ж��û�Id�Ƿ����
				User user=userService.getUserByUserId(userId);
				if(user==null){
					errors.addError("���û�������");	
				}else{
					if(user.getAdmin()==null){
						errors.addError("���û����ǹ���Ա");	
					}
				}
				if(!errors.hasErrors()){
					adminService.delAdmin(user.getAdmin());//������ɾ�����ж�Ӧ��Ȩ��
					model.addAttribute("adminList",adminService.getAllAdmin());
					successes.addSuccess("�ɹ�ɾ���˹���Ա");
				}
			}	
		}catch (Exception e) {
			errors.addError(e.getMessage());
		}
		successes.toModel(model);
		errors.toModel(model);//���������Ϣ
		model.addAttribute("adminList",adminService.getAllAdmin());
		return "Admin/Role/listAdmin";
	}
	
	/**
	 * ɾ���û���ĳ��Ȩ��
	 * @param userId
	 * @param permissionId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Role/delRole.do", method = RequestMethod.POST)
	public String delRole(Integer userId,Integer permissionId,
			HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		errors.ifBlank(userId.toString(), "userId", 10);
		errors.ifBlank(permissionId.toString(), "permissionId", 10);
		User users=commonService.getCustomUser();
		if(users.getUserId()==userId){
			errors.addError("���ܲ����Լ�");	
		}
		try{
			if(!errors.hasErrors()){
				//�ж��û�Id�Ƿ����
				User user=userService.getUserByUserId(userId);
				if(user==null){
					errors.addError("���û�������");	
				}else{
					if(user.getAdmin()==null){
						errors.addError("���û����ǹ���Ա");	
					}
				}
				//�ж�Ȩ��Id�Ƿ����
				Permission permission =permissionService.getPermissionById(permissionId);
				if(permission==null){
					errors.addError("Ȩ�޲�����");
				}
				if(!errors.hasErrors()){
					Set<Role> set = user.getAdmin().getRoles();
					Iterator<Role> it = set.iterator();  
					boolean flag=false;
					while (it.hasNext()) {  
						 Role role = it.next();
						 if(role.getPermission().getId().equals(permissionId)){
							 flag=true;
							 roleService.delRole(role);
							 break;
						 }
					}
					if(flag==false){
						errors.addError("���û���ӵ�д�Ȩ��");
					}
				}
			
				if(!errors.hasErrors()){
					successes.addSuccess("�ɹ�ɾ��Ȩ��");
					logger.info("ɾ���ɹ�");
					model.addAttribute("adminList",adminService.getAllAdmin());
				}
			}
		}catch (Exception e) {
			errors.addError(e.getMessage());
		}
		logger.info("ɾ��ʧ��");
		successes.toModel(model);
		errors.toModel(model);//���������Ϣ
		model.addAttribute("adminList",adminService.getAllAdmin());
		return "Admin/Role/listAdmin";
	}
	/**
	 * �г����й���Ա�Լ�Ȩ��
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Role/listAdmin.do", method = RequestMethod.GET)
	public String showAdmin(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		model.addAttribute("adminList",adminService.getAllAdmin());
		return "Admin/Role/listAdmin";
	}
	
	
	
	/**
	 * �������г��û�
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/User/listUser.do" )
	public String showUser(User user,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		if(pageNo==null||pageNo<0){pageNo=1;}
		Map RequestParameters=new HashMap();
		WebSuccess successes = WebSuccess.create(request);
		List list=userService.getUserByCondition(user,pageNo,10);
		int userSize=userService.getUserCountByCondition(user);
		model.addAttribute("userList",list);
		model.addAttribute("userSize",userSize);
		model.addAttribute("currentPage",pageNo);
		String condition="";
		if(user.getUserId()!=null){
			RequestParameters.put("userId", user.getUserId());
			condition+="userId:"+user.getUserId();
		}
		if(user.getUserName()!=null){
			RequestParameters.put("userName", user.getUserName());
			condition+="userName:"+user.getPhone();
		}
		if(user.getPhone()!=null){
			RequestParameters.put("phone", user.getPhone());
			condition+="phone:"+user.getPhone();
		}
		if(user.getEmail()!=null){
			RequestParameters.put("email", user.getEmail());
			condition+="email:"+user.getEmail();
		}
		successes.addSuccess(condition+"����"+userSize+"������");
		successes.toModel(model);
		model.addAttribute("RequestParameters",RequestParameters);
		return "Admin/User/listUser";
	}
	
	
	
	/**
	 * ����û�  JSON
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/User/editUser.do", method = RequestMethod.POST)
	public void editUser(Integer userId,Integer status,Integer pageNo,Integer pageSize,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		response.setCharacterEncoding("UTF-8");
		WebErrors errors = WebErrors.create(request);
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		errors.ifBlank(userId.toString(), "userId", 10);
		User users=commonService.getCustomUser();
		//�ж��û�Id�Ƿ����
		User user=null;
		if(!errors.hasErrors())
			user=userService.getUserByUserId(userId);
		if(errors.hasErrors()){
			map.put("status", "-4");
			map.put("message", "userId����Ϊ��");
		}else if(status!=0&&status!=1){
			map.put("status", "0");
			map.put("message", "״̬������");
		}else if(users.getUserId()==userId){
			map.put("status", "-1");
			map.put("message", "���ܶ��Լ����в���");
		}else if(user==null){
			map.put("status", "-2");
			map.put("message", "�û�������");
		}else{
			if(user.getStatus()!=(byte)status.intValue()){
				user.setStatus((byte)status.intValue());
				try {
					userService.editUser(user);
					map.put("status", "1");
					map.put("message", "�ɹ�");		
				} catch (Exception e) {
					map.put("status", "-3");
					map.put("message",e.getMessage());
				}
			}
		}
		out.write(JSONObject.fromObject(map).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * ���ϵͳ����ҳ��
	 * @param config
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Config/editConfig.do", method = RequestMethod.GET)
	public String  editConfigV(Config config,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		model.addAttribute("config",configService.getConfig());
		return "Admin/Config/viewConfig";
	}
	
	/**
	 * �޸�ϵͳ����
	 * @param config
	 * @param request
	 * @param response
	 * @param model 
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Config/editConfig.do", method = RequestMethod.POST)
	public String  editConfig(Config config,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		WebErrors errors;
		WebSuccess successes = WebSuccess.create(request);
		errors=vaildateSubmit.validateConfig(config, request, response);
		if(!errors.hasErrors()){
		Config configs=	configService.getConfig();
		configs.editConfig(config.getDomain(),config.getEmailActContent(),config.getEmailHost(),config.getEmailPwd(),
				config.getEmailPwdContent(),config.getEmailTitle(),config.getEmailUserName(),config.getMaxErrorTimes(),
				config.getMaxSendEmailTimes(),config.getMaxSendMesTimes(),config.getWebDescription(),config.getWebKeyWord(),config.getWebTitle());
			try {
				configService.editConfig(configs);
				model.addAttribute("config",configs);
				successes.addSuccess("�޸ĳɹ�");
				successes.toModel(model);
				
			} catch (Exception e) {
				errors.addError("editConfig�޸�ʧ��"+e.getMessage());
			}
		}
		errors.toModel(model);//���������Ϣ
		return "Admin/Config/viewConfig";
	}
	
	
	
	/**
	 * �г�����Ա������־
	 * @param log
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/System/listLog.do" )
	public String showLog(Log log,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		if(pageNo==null||pageNo<0){pageNo=1;}
		WebSuccess successes = WebSuccess.create(request);
		List logList=logService.listLogByCondition(log,pageNo,10);
		int logSize=logService.getLogCountByCondition(log);
		String condition="";
		model.addAttribute("logList",logList);
		model.addAttribute("logSize",logSize);
		model.addAttribute("currentPage",pageNo);
		successes.addSuccess(condition+"����"+logSize+"������");
		successes.toModel(model);
		return "Admin/System/listLog";
	}
	
	/**
	 * ��վ���ݷ���
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/System/analysisData.do" )
	public String analysisData (HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		//ͳ��ע������
		User user=new User();
		user.setStatus((byte)0);
		int unActiveUserCount=userService.getUserCountByCondition(user);//δ����û�����
		user.setStatus((byte)1);
		int activeUserCount=userService.getUserCountByCondition(user);//������û�����
		Message message=new Message();
		message.setType((byte)0);
		int messCount=messageService.getMessageCountByCondition(message);//��ͨ��Ϣ����
		message.setType((byte)1);
		int atMessCount=messageService.getMessageCountByCondition(message);//@��Ϣ����
		model.addAttribute("unActiveUserCount",unActiveUserCount);
		model.addAttribute("activeUserCount",activeUserCount);
		model.addAttribute("messCount",messCount);
		model.addAttribute("atMessCount",atMessCount);
		return "Admin/System/analysisData";
	}
	
}
