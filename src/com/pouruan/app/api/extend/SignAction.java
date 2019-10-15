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
		
		// ��Ϊjson���ظ�ǰ̨�������ж��Ƿ��ں�̨ǩ���ɹ�,status=0û��ǩ��,statue=1ǩ���ɹ�
		Map<String,Object> map = new HashMap<String,Object>();
		response.setCharacterEncoding("UTF-8");
				
		try {
			PrintWriter out=response.getWriter();
			Timestamp currenttime = TimeUtils.getCurrentDay();		// ���������
			User user = commonService.getCustomUser();				// ��ȡ��ǰ�û�
			Mark mark = markService.getMarkById(user.getUserId());	// ��ȡ��ǰ�û���Ӧ��mark
			Integer score = 2;// д�������ˡ�����
			
			if(mark == null) {
				// ���Ϊ�գ��û�֮ǰû����mark���Ͻ��й�ǩ������ô��Ҫ�½�һ����¼��mark�����
					mark = new Mark(user,0,currenttime);	//����˵�����û�����ʼ��������ʼʱ��
				if(markService.addMark(mark,score,currenttime))
					map.put("status", 1);
					String json="";
					json=JSONObject.fromObject(map).toString();
					out.write(json);
					out.flush();
					out.close();
					return ;
			} else {
				// �����Ϊ�գ��û��Ѿ�����ʷ��ǩ����¼�ˣ�����Ҫ�ж���ʷ��¼�Ƿ�������غϣ���ֹ�ظ�ǩ����
				if(TimeUtils.isToday(mark.getSign_time())) {
					// �����ʷǩ��ʱ����ǽ��죬��ô���ﲻ�����ظ�ǩ��
					map.put("status", 0);
					String json="";
					json=JSONObject.fromObject(map).toString();
					out.write(json);
					out.flush();
					out.close();
				}	else {
					// ǩ��
					if(markService.addMark(mark,score,currenttime)) {
						// ǩ���ɹ�
						map.put("status", 1);
						String json="";
						json=JSONObject.fromObject(map).toString();
						out.write(json);
						out.flush();
						out.close();
					}
					else {
						// ��֪��ʲô�������ǩ��ʧ��
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
			// δ����
			
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
