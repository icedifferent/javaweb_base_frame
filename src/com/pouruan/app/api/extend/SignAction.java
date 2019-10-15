package com.pouruan.app.api.extend;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Mark;
import com.pouruan.web.entity.extend.Question;
import com.pouruan.web.service.extend.MarkService;
import com.pouruan.web.service.extend.QuestionService;
import com.pouruan.web.service.security.CommonService;

import net.sf.json.JSONObject;

@Controller
public class SignAction {
	
	@Autowired
	private MarkService markService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value = "/User/Extend/SignAction/Sign.do",method = RequestMethod.POST)
	public void signMark(HttpServletRequest request,HttpServletResponse response) {
		
		// 作为json返回给前台，用于判断是否在后台签到成功,status=0没有签到,statue=1签到成功
		Map<String,Object> map = new HashMap<String,Object>();
		response.setCharacterEncoding("UTF-8");
				
		try {
			PrintWriter out=response.getWriter();
			Timestamp currenttime = TimeUtils.getCurrentDay();		// 今天的日期
			User user = commonService.getCustomUser();				// 获取当前用户
			Mark mark = markService.getMarkById(user.getUserId());	// 获取当前用户对应的mark
			Integer score = 2;// 写死积分了。。。
			
			if(mark == null) {
				// 如果为空，用户之前没有在mark表上进行过签到，那么需要新建一个记录在mark表给它
					mark = new Mark(user,0,currenttime);	//参数说明：用户，初始分数，初始时间
				if(markService.addMark(mark,score,currenttime))
					map.put("status", 1);
					String json="";
					json=JSONObject.fromObject(map).toString();
					out.write(json);
					out.flush();
					out.close();
					return ;
			} else {
				// 如果不为空，用户已经有历史的签到记录了，则需要判断历史记录是否与今天重合（防止重复签到）
				if(TimeUtils.isToday(mark.getSign_time())) {
					// 如果历史签到时间就是今天，那么这里不允许重复签到
					map.put("status", 0);
					String json="";
					json=JSONObject.fromObject(map).toString();
					out.write(json);
					out.flush();
					out.close();
				}	else {
					// 签到
					if(markService.addMark(mark,score,currenttime)) {
						// 签到成功
						map.put("status", 1);
						String json="";
						json=JSONObject.fromObject(map).toString();
						out.write(json);
						out.flush();
						out.close();
					}
					else {
						// 不知道什么情况导致签到失败
						map.put("status", 0);
						String json="";
						json=JSONObject.fromObject(map).toString();
						out.write(json);
						out.flush();
						out.close();
					}
				}
			}
			
		} catch (Exception e) {
			// 未处理
			
			return ;
		}

	}
	
	@RequestMapping(value = "/Ask/takeQuestion.do")
	public void takeQuestion(Integer questionId, Integer answerId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		PrintWriter out=response.getWriter();
		Question question=new Question();
		question.setId(questionId);
		question=questionService.getQuestion(question);
		User user = commonService.getCustomUser();
		Mark mark = markService.getMarkById(user.getUserId());
		if(question!=null && question.getAnswer_token()==null) {
			question.setAnswer_token(answerId);
			markService.addMark(mark, question.getMark());
			questionService.updateQuestion(question);
			
			map.put("status", 1);
			String json="";
			json=JSONObject.fromObject(map).toString();
			out.write(json);
			out.flush();
			out.close();
		} else {
			map.put("status", 0);
			String json="";
			json=JSONObject.fromObject(map).toString();
			out.write(json);
			out.flush();
			out.close();
		}
	}
}
