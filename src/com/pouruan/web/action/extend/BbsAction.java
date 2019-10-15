package com.pouruan.web.action.extend;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




















import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.tips.WebSuccess;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.extend.ModuleService;
import com.pouruan.web.service.extend.PostService;
import com.pouruan.web.service.extend.ReplyService;
import com.pouruan.web.service.security.CommonService;

@Controller
public class BbsAction {
	private Logger logger = LogManager.getRootLogger();
	@Autowired 
	private CommonService commonService;
	@Autowired
	private PostService postService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	
	/**
	 * 管理员按条件列出帖子
	 * @param post
	 * @param moduleId
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Bbs/postList.do")
	public String postAdminList(Post post,Integer userId,Integer orderType,Integer pageNo,Integer pageSize, HttpServletRequest request,HttpServletResponse response, ModelMap model,String errors,String message) throws Exception{
		WebSuccess successes = WebSuccess.create(request);
		if(userId!=null){
			User user=userService.getUserByUserId(userId);
			if(user!=null){
				post.setUser(user);
			}
		}
		if(pageNo==null||pageNo<=0)pageNo=1;
		if(orderType==null) orderType=0;
		List<Post> list=postService.listPostByCondition(post, pageNo, 10,orderType);
		model.addAttribute("postList", list);
		Map RequestParameters=new HashMap();

	
		if(post.getTitle()!=null){
			RequestParameters.put("title", post.getTitle());
		}
		if(post.getContent()!=null){
			RequestParameters.put("content", post.getContent());
		}
		RequestParameters.put("orderType", orderType);
		if(errors!=null){
			model.addAttribute("errors",errors);
		}
		if(message!=null){
			successes.addSuccess(message);
			successes.toModel(model);
		}
		model.addAttribute("RequestParameters",RequestParameters);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("postSize",postService.getCountByCondition(post));
		return "Admin/Extend/Bbs/postList";
	}
	
	/**
	 * 管理员删除帖子
	 * @param postId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Bbs/delPost.do", method = RequestMethod.POST)
	public String delAdminPost(Integer postId,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		errors.ifNotNumber(postId.toString(), "postId", 1, 10);
		if(!errors.hasErrors()){
			Post post=postService.getPostById(postId);
			if(post!=null){
				try {
					postService.delPost(post);
					model.addAttribute("message", "delete success");
					return "redirect:/Admin/Extend/Bbs/postList.do";
				} catch (Exception e) {
					errors.addError("delete faile"+e.getMessage());
				}
			}else{
				errors.addError("post is not found");
			}
		}
		errors.toModel(model);//输出错误信息
		return "redirect:/Admin/Extend/Bbs/postList.do";
	}
	
	
	/**
	 * 新增论坛版块视图
	 * @param moduleId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Bbs/editModule.do", method = RequestMethod.GET)
	public String editModuleView(Integer moduleId ,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		
		if(moduleId!=null){
			Module oldModule=moduleService.getMoudle(moduleId);
			if(oldModule!=null)
				model.addAttribute("oldModule",oldModule);
		}
		return "Admin/Extend/Bbs/editModule";
	}
	
	/**
	 * 增加论坛版块
	 * @param name
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Bbs/editModule.do", method = RequestMethod.POST)
	public String editModule(String name,Integer moduleId ,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		name=StringEscapeUtils.escapeHtml4(name);
		errors.ifOutOfLength(name, "name", 1, 10);
		if((!errors.hasErrors())&&(moduleId!=null)){//修改
			Module oldModule=moduleService.getMoudle(moduleId);
			if(oldModule!=null){
				oldModule.setName(name);
				try {
					if(!errors.hasErrors()){
						moduleService.editModule(oldModule);
						successes.addSuccess("修改成功");
						successes.toModel(model);
						return "Admin/Extend/Bbs/editModule";
					}else{
						errors.addError("修改失败");
					}
				} catch (Exception e) {
					errors.addError("未知错误"+e.getMessage());
				}
			}
		}
		//新增
		Module module=new Module();
		module.setName(name);
		if(!errors.hasErrors()){
			try {
				moduleService.addModule(module);
				successes.addSuccess("增加成功");
				successes.toModel(model);
				return "Admin/Extend/Bbs/editModule";	
			} catch (Exception e) {
				errors.addError("未知错误"+e.getMessage());
			}
		}
		errors.toModel(model);//输出错误信息
		return "Admin/Extend/Bbs/editModule";
	}
	
	
	/**
	 * 删除版块
	 * @param moduleId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Bbs/delModule.do", method = RequestMethod.POST)
	public String delModule(Integer moduleId,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		Module module=	moduleService.getMoudle(moduleId);
		if(module==null){
			errors.addError("版块不存在");
		}
		if(!errors.hasErrors()){
			try {
				moduleService.delModule(module);
				successes.addSuccess("删除成功");
			}catch (Exception e) {
				errors.addError("删除失败"+e.getMessage());
			}
		}
		successes.toModel(model);
		errors.toModel(model);//输出错误信息
		List <Module> list=moduleService.listMoudleByCondition(null, 1, 30);
		model.addAttribute("list", list);
		return "Admin/Extend/Bbs/moduleList";
	}
	
	/**
	 * 列出论坛版块
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Bbs/moduleList.do")
	public String moduleList(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		List <Module> list=moduleService.listMoudleByCondition(null, 1, 30);
		model.addAttribute("list", list);
		return "Admin/Extend/Bbs/moduleList";
	}
	/**
	 * 按条件列出帖子
	 * @param post
	 * @param moduleId
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Bbs/postList.do")
	public String postList(Post post,Integer userId,Integer moduleId,Integer orderType,Integer pageNo,Integer pageSize, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		if(userId!=null){
			User user=userService.getUserByUserId(userId);
			if(user!=null){
				post.setUser(user);
			}
		}
		if(moduleId!=null){
			Module module= moduleService.getMoudle(moduleId);
			if(module!=null){
				post.setModule(module);
			}
		}
		if(pageNo==null||pageNo<=0)pageNo=1;
		if(orderType==null) orderType=0;
		List<Post> list=postService.listPostByCondition(post, pageNo, 10,orderType);
		model.addAttribute("postList", list);
		Map RequestParameters=new HashMap();
		if(moduleId!=null){
			RequestParameters.put("moduleId", moduleId);
		}
		if(post.getTitle()!=null){
			RequestParameters.put("title", post.getTitle());
		}
		if(post.getContent()!=null){
			RequestParameters.put("content", post.getContent());
		}
		RequestParameters.put("orderType", orderType);
		List <Module> moduleList=moduleService.listMoudleByCondition(null, 1, 20);
		model.addAttribute("moduleList", moduleList);
		model.addAttribute("RequestParameters",RequestParameters);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("moduleId",moduleId);
		model.addAttribute("postSize",postService.getCountByCondition(post));
		return "Bbs/postList";
	}
	
	
	/**
	 * 查看帖子
	 * @param postId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Bbs/readPost.do")
	public String readPost(Integer postId,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		Post post=new Post();
		post.setId(postId);
		post=postService.getPost(post);
		if(post!=null){
			Reply reply=new Reply();
			reply.setPost(post);
			int count=replyService.getCountByCondition(reply);
			if((pageNo==null)||(pageNo<=0)) pageNo=1;
			model.addAttribute("post", post);
			model.addAttribute("replyPageNo", pageNo);
			model.addAttribute("count", count);
			model.addAttribute("postId", postId);
			Map RequestParameters=new HashMap();
			//阅读了+1
			post.setReadTimes(post.getReadTimes()+1);
			postService.editPost(post);
			if(postId!=null){
				RequestParameters.put("postId", postId);
			}
			model.addAttribute("parameters",RequestParameters);
		}else{
			errors.addError("帖子不存在");
			errors.toModel(model);//输出错误信息
		}
		return "Bbs/readPost";
	}
	
	
	/**
	 * 增加帖子
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Bbs/addPost.do", method = RequestMethod.GET)
	public String addPost(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		List <Module> list=moduleService.listMoudleByCondition(null, 1, 20);
		model.addAttribute("moduleList", list);
		return "Bbs/addPost";
	}
	/**
	 * 增加帖子
	 * @param title
	 * @param content
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Bbs/addPost.do", method = RequestMethod.POST)
	public String addPostDone(String title,String content,Integer moduleId,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		title=StringEscapeUtils.escapeHtml4(title);
		errors.ifOutOfLength(title, "title", 1, 30);
		errors.ifOutOfLength(content, "content", 1, 50000);
		errors.ifBlank(moduleId.toString(), "moduleId", 5);
		List <Module> list=moduleService.listMoudleByCondition(null, 1, 20);
		model.addAttribute("moduleList", list);
		Module module=moduleService.getMoudle(moduleId);
		if(module==null){
			errors.addError("版块不存在");
		}
		if(!errors.hasErrors()){
			try {
				if(!postService.addPost(title, content, module)){
					errors.addError("增加失败！发帖时间间隔较短");
				}else{
					successes.addSuccess("发表成功");
					successes.toModel(model);
					return "redirect:/Bbs/postList.do";
				}
			} catch (Exception e) {
				errors.addError("未知错误"+e.getMessage());
			}
		}
		errors.toModel(model);//输出错误信息
		return "Bbs/addPost";
	}
	
	
	/**
	 * 添加回复
	 * @param content
	 * @param postId
	 * @param replyPageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Bbs/addReply.do", method = RequestMethod.POST)
	public String addReply(String content,Integer postId,Integer replyPageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		errors.ifOutOfLength(content, "content", 1, 10000);
		errors.ifBlank(postId.toString(), "postId", 10);
		Post post=postService.getPostById(postId);
		if(post==null){
			errors.addError("帖子不存在");
		}
		if(!errors.hasErrors()){
			try {
				if(!replyService.addReply(post, content, commonService.getCustomUser())){
					errors.addError("增加失败！发帖时间间隔较短");
				}else{
					successes.addSuccess("回复成功");
					successes.toModel(model);
				}
			} catch (Exception e) {
				errors.addError("未知错误"+e.getMessage());
			}
		}
		model.addAttribute("postId",postId);
		model.addAttribute("replyPageNo",replyPageNo);
		errors.toModel(model);//输出错误信息
		return "redirect:./readPost.do";
	}
	
}