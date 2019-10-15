package com.pouruan.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.email.EmailSendTool;
import com.pouruan.common.encode.aes.AesEncode;
import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.dao.CheckCodeDao;
import com.pouruan.web.entity.CheckCode;
import com.pouruan.web.entity.Config;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.CheckCodeService;
import com.pouruan.web.service.ConfigService;
import com.pouruan.web.service.UserService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//��������
@Service
public class CheckCodeServiceImpl implements CheckCodeService{
	@Autowired
	private CheckCodeDao checkCodeDao;
	@Autowired
	private ConfigService configService;
	@Autowired
	private EmailSendTool emailSendTool;
	@Autowired
	private UserService userService;
	@Autowired
	private AesEncode aesEncode;
	private Logger logger = LogManager.getRootLogger();
	@Override
	public boolean addCheckCode(CheckCode checkCode) throws Exception {
		List<CheckCode> list=checkCodeDao.getCheckCodeByObject(checkCode);
		if(list.size()!=0){//email�Ѿ����ڣ����޸ļ�¼����
			CheckCode r=	list.get(0);
			long millTime=r.getCodeDate().getTime();
			r.setCheckCode(checkCode.getCheckCode());
			r.setCodeDate(checkCode.getCodeDate());
			if(System.currentTimeMillis()-millTime<60*1000){
				return false;//��֤��Ҫ��ʱ�����
			}else{
				return checkCodeDao.editCheckCode(r);
			}
		}else{
			return	checkCodeDao.addCheckCode(checkCode);
		}
	}
	@Override
	public boolean toCheckCode(String phone, String code) throws Exception {
		CheckCode checkCode=new  CheckCode(phone,code,TimeUtils.getCurrentDay(),(byte)0);
		List<CheckCode> list=checkCodeDao.getCheckCodeByObject(checkCode);
		if(list.size()==0){
			logger.debug("��֤�벻���ڻ����Ѿ�ʹ��");
			return false;
		}else{
			CheckCode r=list.get(0);
			//������֤����ȷ��������Ч������
			if(r.getCheckCode().equals(code)&&
					(System.currentTimeMillis()-r.getCodeDate().getTime()<1000*60*5)){
				r.setStatus((byte)1);
				//�޸���֤��Ϊ�Ѿ�ʹ��
				checkCodeDao.editCheckCode(r);
				return true;
			}else{
				logger.debug("��֤�벻��ȷ���߲�����Ч����");
				return false;
			}
		}
	}
	
	

	@Override
	public boolean toSendEmailCode(String email, String code)  throws Exception{
		//�����ʼ�
		Config config=configService.getConfig();
		String str = MessageFormat.format(config.getEmailPwdContent(),email, config.getWebTitle(), code);
		return this.sendEmail(config,  str,email);
	}
	

	@Override
	public boolean sendEmail(Config config,String content,String email) throws Exception{
		//�����ʼ�
		if(emailSendTool.sendEmail(config.getEmailHost(),
				config.getEmailUserName(),config.getEmailPwd(),email,
				config.getEmailTitle(), content,config.getWebTitle())){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean sendActEmail(User user,String email,HttpServletRequest request) throws Exception {
		Random random = new Random();
        int r = random.nextInt(100000);
		user.setEmailActCode( user.getUserId()+aesEncode.encrypt(System.currentTimeMillis()+"-"+Integer.toString(r)));
		//�����ʼ�
		Config config=configService.getConfig();
		String url;
		try {
			url=config.getDomain()+request.getContextPath()+"/User/actEmail.do?actCode="+java.net.URLEncoder.encode(user.getEmailActCode(),"UTF-8")+"&userId="+user.getUserId();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		String str = MessageFormat.format(config.getEmailActContent(), user.getUserName(), config.getWebTitle(), url);
		if(this.sendEmail(config,str,email)){
			return userService.editUser(user);	
		}else{
			return false;
		}
	}
}
