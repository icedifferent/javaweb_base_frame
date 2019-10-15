package com.pouruan.web.entity.parent;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringEscapeUtils;

import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Log;
import com.pouruan.web.entity.Message;
import com.pouruan.web.entity.extend.Answer;
import com.pouruan.web.entity.extend.Mark;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Question;
import com.pouruan.web.entity.extend.Reply;

public class UserParent {
	private Integer userId;
	private String userName;
	private String userPwd;
	private String email;
	private Byte isCheckEmail;
	private String emailActCode;
	private String phone;
	private String register_ip;
	private Timestamp register_date;
	private String portrait_img;
	private Byte status;
	private String character;
	private Set<Admin> admins;
	private Set<Message> messageSended;
	private Set<Message> messageReceived;
	private Set<Log> logs;
	private Admin admin;
	//extend
	private Set<Post> posts;
	private Set<Reply> replys;
	private Set<Mark> marks;
	private Set<Answer> answers;
	private Set<Question> questions;
	
	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<Mark> getMarks() {
		return marks;
	}

	public void setMarks(Set<Mark> marks) {
		this.marks = marks;
	}

	public Admin getAdmin() {
		Set<Admin> set = this.getAdmins();  
		Iterator<Admin> it = set.iterator();  
		while (it.hasNext()) {  
			 Admin admin = it.next();  
			 this.admin=admin;
		}  
		return admin;
	}
	
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Set<Admin> getAdmins() {
		return admins;
	}
	public void setAdmins(Set<Admin> admins) {
		this.admins = admins;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		userName=StringEscapeUtils.escapeHtml4(userName);
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegister_ip() {
		return register_ip;
	}
	public void setRegister_ip(String register_ip) {
		this.register_ip = register_ip;
	}
	public String getPortrait_img() {
		return portrait_img;
	}
	public void setPortrait_img(String portrait_img) {
		this.portrait_img = portrait_img;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public Timestamp getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Timestamp register_date) {
		this.register_date = register_date;
	}

	public Byte getIsCheckEmail() {
		return isCheckEmail;
	}

	public void setIsCheckEmail(Byte isCheckEmail) {
		this.isCheckEmail = isCheckEmail;
	}

	public String getEmailActCode() {
		return emailActCode;
	}

	public void setEmailActCode(String emailActCode) {
		this.emailActCode = emailActCode;
	}

	public Set<Message> getMessageSended() {
		return messageSended;
	}

	public void setMessageSended(Set<Message> messageSended) {
		this.messageSended = messageSended;
	}

	public Set<Message> getMessageReceived() {
		return messageReceived;
	}

	public void setMessageReceived(Set<Message> messageReceived) {
		this.messageReceived = messageReceived;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Reply> getReplys() {
		return replys;
	}

	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}
	
}
