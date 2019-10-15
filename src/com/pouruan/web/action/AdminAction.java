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
	 * 增加管理员页面
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Role/addRole.do", method = RequestMethod.GET)
	public String addRole(
			HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		//列出所有权限
		model.addAttribute("permissionList",permissionService.showAllPermission());
		logger.debug("所有权限:"+permissionService.showAllPermission());
		return "Admin/Role/addRole";
	}
	
	/**
	 * 增加管理某项权限（非管理员则自动添加成为管理员）
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
				//判断用户Id是否存在
				User user = userService.getUserByUserId(userId);
				
				if(user==null){
					errors.addError("用户id不存在");
				}
				
				//判断权限Id是否存在
				Permission permission =permissionService.getPermissionById(permissionId);
				if(permission==null){
					errors.addError("权限不存在");
				}
				//判断此用户是否已经拥有此权限
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
							errors.addError("此用户已经拥有此权限，无需重新添加");
						}
					}
				}
				if(!errors.hasErrors()){
					adminService.addRole(user, permission);
					successes.addSuccess("添加成功");
				}
			}
		} catch (Exception e) {
			errors.addError("addRoleDone"+e.getMessage());
		}
		//列出所有权限
		model.addAttribute("permissionList",permissionService.showAllPermission());
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		return "Admin/Role/addRole";
	}
	
	/**
	 * 删除某个管理员
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
			errors.addError("不能操作自己");	
		}
		try{
			if(!errors.hasErrors()){
				//判断用户Id是否存在
				User user=userService.getUserByUserId(userId);
				if(user==null){
					errors.addError("此用户不存在");	
				}else{
					if(user.getAdmin()==null){
						errors.addError("此用户不是管理员");	
					}
				}
				if(!errors.hasErrors()){
					adminService.delAdmin(user.getAdmin());//级关联删除所有对应的权限
					model.addAttribute("adminList",adminService.getAllAdmin());
					successes.addSuccess("成功删除此管理员");
				}
			}	
		}catch (Exception e) {
			errors.addError(e.getMessage());
		}
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		model.addAttribute("adminList",adminService.getAllAdmin());
		return "Admin/Role/listAdmin";
	}
	
	/**
	 * 删除用户的某项权限
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
			errors.addError("不能操作自己");	
		}
		try{
			if(!errors.hasErrors()){
				//判断用户Id是否存在
				User user=userService.getUserByUserId(userId);
				if(user==null){
					errors.addError("此用户不存在");	
				}else{
					if(user.getAdmin()==null){
						errors.addError("此用户不是管理员");	
					}
				}
				//判断权限Id是否存在
				Permission permission =permissionService.getPermissionById(permissionId);
				if(permission==null){
					errors.addError("权限不存在");
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
						errors.addError("此用户不拥有此权限");
					}
				}
			
				if(!errors.hasErrors()){
					successes.addSuccess("成功删除权限");
					logger.info("删除成功");
					model.addAttribute("adminList",adminService.getAllAdmin());
				}
			}
		}catch (Exception e) {
			errors.addError(e.getMessage());
		}
		logger.info("删除失败");
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		model.addAttribute("adminList",adminService.getAllAdmin());
		return "Admin/Role/listAdmin";
	}
	/**
	 * 列出所有管理员以及权限
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
	 * 按条件列出用户
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
		successes.addSuccess(condition+"共有"+userSize+"条数据");
		successes.toModel(model);
		model.addAttribute("RequestParameters",RequestParameters);
		return "Admin/User/listUser";
	}
	
	
	
	/**
	 * 审核用户  JSON
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
		//判断用户Id是否存在
		User user=null;
		if(!errors.hasErrors())
			user=userService.getUserByUserId(userId);
		if(errors.hasErrors()){
			map.put("status", "-4");
			map.put("message", "userId不能为空");
		}else if(status!=0&&status!=1){
			map.put("status", "0");
			map.put("message", "状态不存在");
		}else if(users.getUserId()==userId){
			map.put("status", "-1");
			map.put("message", "不能对自己进行操作");
		}else if(user==null){
			map.put("status", "-2");
			map.put("message", "用户不存在");
		}else{
			if(user.getStatus()!=(byte)status.intValue()){
				user.setStatus((byte)status.intValue());
				try {
					userService.editUser(user);
					map.put("status", "1");
					map.put("message", "成功");		
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
	 * 浏览系统属性页面
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
	 * 修改系统属性
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
				successes.addSuccess("修改成功");
				successes.toModel(model);
				
			} catch (Exception e) {
				errors.addError("editConfig修改失败"+e.getMessage());
			}
		}
		errors.toModel(model);//输出错误信息
		return "Admin/Config/viewConfig";
	}
	
	
	
	/**
	 * 列出管理员操作日志
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
		successes.addSuccess(condition+"共有"+logSize+"条数据");
		successes.toModel(model);
		return "Admin/System/listLog";
	}
	
	/**
	 * 网站数据分析
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/System/analysisData.do" )
	public String analysisData (HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		//统计注册人数
		User user=new User();
		user.setStatus((byte)0);
		int unActiveUserCount=userService.getUserCountByCondition(user);//未审核用户总数
		user.setStatus((byte)1);
		int activeUserCount=userService.getUserCountByCondition(user);//已审核用户总数
		Message message=new Message();
		message.setType((byte)0);
		int messCount=messageService.getMessageCountByCondition(message);//普通消息条数
		message.setType((byte)1);
		int atMessCount=messageService.getMessageCountByCondition(message);//@消息条数
		model.addAttribute("unActiveUserCount",unActiveUserCount);
		model.addAttribute("activeUserCount",activeUserCount);
		model.addAttribute("messCount",messCount);
		model.addAttribute("atMessCount",atMessCount);
		return "Admin/System/analysisData";
	}
	
}
