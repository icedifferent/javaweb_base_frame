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
import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Mark;
import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;
import com.pouruan.web.entity.extend.Reply;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.extend.AnswerService;
import com.pouruan.web.service.extend.MarkService;
import com.pouruan.web.service.extend.QuestionService;
import com.pouruan.web.service.security.CommonService;

@Controller
public class QuestionAction {
	private Logger logger = LogManager.getRootLogger();
	@Autowired 
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private MarkService markService; 
	@Autowired
	private AnswerService answerService; 
	
	/**
	 * 管理员列出问题列表
	 * @param question
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @param errors
	 * @param message
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/Admin/Extend/Ask/questionList.do")
	public String adminPostQuestion(Question question, Integer userId, Integer mark, Integer pageNo, Integer pageSize, HttpServletRequest request,HttpServletResponse response, ModelMap model,String errors,String message) throws Exception{
		WebSuccess successes = WebSuccess.create(request);
		if(userId!=null){
			User user=userService.getUserByUserId(userId);
			if(user!=null){
				question.setUser(user);
			}
		}
		if(pageNo==null||pageNo<=0)pageNo=1;
		List<Question> list=questionService.listQuestionByCondition(question, pageNo, 10);
		model.addAttribute("questionList", list);
		
		Map RequestParameters=new HashMap();
		
		
		if(question.getTitle()!=null){
			RequestParameters.put("title", question.getTitle());
		}
		if(question.getDiscribe()!=null){
			RequestParameters.put("content", question.getDiscribe());
		}
		if(question.getMark()!=null) {
			RequestParameters.put("mark", question.getMark());
		}
		
		if(errors!=null){
			model.addAttribute("errors",errors);
		}
		if(message!=null){
			successes.addSuccess(message);
			successes.toModel(model);
		}
		
		model.addAttribute("RequestParameters",RequestParameters);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("questionSize",questionService.getCountByCondition(question));
		return "Admin/Extend/Ask/questionList";
	}
	
	/**
	 * 管理员删除问题
	 * @param questionId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Admin/Extend/Ask/delQuestion.do", method = RequestMethod.POST)
	public String adminDeleteQuestion(Integer questionId,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		errors.ifNotNumber(questionId.toString(), "questionId", 1, 10);
		if(!errors.hasErrors()){
			Question question=questionService.getQuestionById(questionId);
			if(question!=null){
				try {
					questionService.deleteQuestion(question);
					model.addAttribute("message", "delete success");
					return "redirect:/Admin/Extend/Ask/questionList.do";
				} catch (Exception e) {
					errors.addError("delete fail"+e.getMessage());
				}
			}else{
				errors.addError("question is not found");
			}
		}
		errors.toModel(model);//输出错误信息
		return "redirect:/Admin/Extend/Ask/questionList.do";
	}
	
	/**
	 * 普通用户浏览问题
	 * @param question
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Ask/questionList.do")
	public String questionList(Question question,Integer userId,Integer orderType, Integer mark,Integer pageNo,Integer pageSize, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		if(userId!=null){
			User user=userService.getUserByUserId(userId);
			if(user!=null){
				question.setUser(user);
			}
		}
		if(pageNo==null||pageNo<=0)pageNo=1;
		
		List<Question> list=questionService.listQuestionByCondition(question, pageNo, 10);
		model.addAttribute("questionList", list);
		Map RequestParameters=new HashMap();
		
		if(question.getTitle()!=null){
			RequestParameters.put("title", question.getTitle());
		}
		if(question.getDiscribe()!=null){
			RequestParameters.put("content", question.getDiscribe());
		}
		if(question.getMark()!=null) {
			RequestParameters.put("mark", question.getMark());
		}
		if(orderType==null) orderType=0;
		RequestParameters.put("orderType", orderType);
		model.addAttribute("RequestParameters",RequestParameters);
		model.addAttribute("currentPage",pageNo);
		model.addAttribute("questionSize",questionService.getCountByCondition(question));
		return "Ask/questionList";
	}
	
	/**
	 * 用户发布问题-过程
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Ask/addQuestion.do", method = RequestMethod.GET)
	public String addQuestion(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		return "Ask/addQuestion";
	}
	
	/**
	 * 用户发布问题-结果
	 * @param title
	 * @param content
	 * @param mark
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Ask/addQuestion.do", method = RequestMethod.POST)
	public String addQuestionDone(String title,String content,Integer mark,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		title=StringEscapeUtils.escapeHtml4(title);
		content=StringEscapeUtils.escapeHtml4(content);
		errors.ifOutOfLength(title, "title", 1, 30);
		errors.ifOutOfLength(content, "content", 1, 50000);
		
		User user=commonService.getCustomUser();
		Integer score = markService.getMarkById(user.getUserId()).getMark();
		
		if(!errors.hasErrors()){
			try {
				if(!questionService.addQuestion(title, content, mark)){
					errors.addError("提问失败！提问时间间隔较短");
				}
				else if(mark < 0) {
					errors.addError("提问失败！积分不能为负");
				}
				else if(mark > score){
					errors.addError("提问失败！积分不足");
				}
				else{
					successes.addSuccess("提问成功");
					successes.toModel(model);
					markService.dropMark(markService.getMarkById(user.getUserId()), mark);
					return "redirect:/Ask/questionList.do";
				}
			} catch (Exception e) {
				errors.addError("未知错误"+e.getMessage());
			}
		}
		errors.toModel(model);//输出错误信息
		return "Ask/addQuestion";
	}
	
	/**
	 * 添加回答
	 * @param describe
	 * @param questionId
	 * @param answerPageNo
	 * @param is_token
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Ask/addAnswer.do", method = RequestMethod.POST)
	public String addAnswer(String discribe,Integer questionId,Integer answerPageNo,
			HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		errors.ifOutOfLength(discribe, "discribe", 1, 10000);
		errors.ifBlank(questionId.toString(), "questionId", 10);
		Question question=questionService.getQuestionById(questionId);
		if(question==null){
			errors.addError("问题不存在");
		}
		if(!errors.hasErrors()){
			try {
				if(!answerService.addAnswer(commonService.getCustomUser(), question, discribe, 0)){
					errors.addError("增加失败！提问时间间隔较短");
				}else{
					successes.addSuccess("回复成功");
					successes.toModel(model);
				}
			} catch (Exception e) {
				errors.addError("未知错误"+e.getMessage());
			}
		}
		model.addAttribute("questionId",questionId);
		model.addAttribute("answerPageNo",answerPageNo);
		errors.toModel(model);//输出错误信息
		return "redirect:./readQuestion.do";
	}
	
	/**
	 * 查看问题
	 * @param questionId
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Ask/readQuestion.do")
	public String readQuestion(Integer questionId,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		Question question=new Question();
		question.setId(questionId);
		question=questionService.getQuestion(question);
		
		User user = commonService.getCustomUser();
		Mark mark = markService.getMarkById(user.getUserId());
		if((pageNo==null)||(pageNo<=0)) pageNo=1;
		if(question.getAnswer_token()!=null) {
			Answer answerTake = answerService.listTakeAnswerByCondition(question, 1, 1).get(0);
			model.addAttribute("answerTake", answerTake);
		}
		
		if(question!=null){
			//Answer answer=new Answer();
			//answer.setQuestion(question);
			int count=answerService.getCountByCondition(question);
			System.out.println(count+"fdhfhfhhhhh");
			model.addAttribute("question", question);
			model.addAttribute("anwser", answerService.listAnswerByCondition(question, pageNo, 10));
			model.addAttribute("answerPageNo", pageNo);
			model.addAttribute("count", count);
			model.addAttribute("questionId", questionId);
			model.addAttribute("mark", mark.getMark());
			Map RequestParameters=new HashMap();

			if(questionId!=null){
				RequestParameters.put("questionId", questionId);
			}
			model.addAttribute("parameters",RequestParameters);
		}else{
			errors.addError("问题不存在");
			errors.toModel(model);//输出错误信息
		}
		return "Ask/readQuestion";
	}
	

}
