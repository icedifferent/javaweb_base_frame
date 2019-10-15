package com.pouruan.web.service.impl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.common.exception.BadCredentialsException;
import com.pouruan.common.exception.UserPhoneNotFoundException;
import com.pouruan.web.entity.Config;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.AuthService;
import com.pouruan.web.service.CheckCodeService;
import com.pouruan.web.service.ConfigService;
import com.pouruan.web.service.UserService;
import com.pouruan.web.service.security.CommonService;
import com.pouruan.web.dao.UserDao;
import com.pouruan.common.email.EmailSendTool;
import com.pouruan.common.encode.PwdEncoder;
import com.pouruan.common.encode.aes.AesEncode;
import com.pouruan.common.web.CookieUtils;
import com.pouruan.common.web.RequestUtils;
import com.pouruan.common.web.TimeUtils;

import static com.pouruan.common.web.Constants.AUTH_KEY;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private PwdEncoder md5PwdEncoder;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthService authService;
	@Autowired
	private AesEncode aesEncode;
	@Autowired
	private CheckCodeService checkCodeService;
	@Autowired
	private ConfigService configService;
	@Autowired 
	private CommonService commonService;
	@Autowired
	private SessionRegistry  sessionRegistry;
	
	@Override
	public User login(String username, String password)
			throws UserPhoneNotFoundException ,BadCredentialsException,Exception{
		
		User user =this.getByUserPhone(username);
		if(user==null){//手机号码不存在则抛出异常
			throw new UserPhoneNotFoundException("UsePhone not found: "
					+ username);
		}
		//System.exit(0);
		if (!md5PwdEncoder.isPasswordValid(user.getUserPwd(), password)) {
			throw new BadCredentialsException("password invalid");
		}
		return user;
	}
	
	
	@Override
	public boolean loginSuccess(HttpServletRequest request, HttpServletResponse response,User user,String sessionId) throws Exception {
		//String encode=user.getUserId()+md5PwdEncoder.encodePassword(user.getUserId().toString(), user.getUserPwd()+"pouruan");
		//CookieUtils.addCookie(request, response, AUTH_KEY, encode, 86400*30, null);//有效期为一个月
		authService.addAuthentication(user.getUserId(), sessionId, RequestUtils.getIpAddr(request));
		return true;
	}
	
	@Override
	public User getByUserPhone(String username) throws Exception{
		User user =new User();
		user.setPhone(username);
		List<User> list=userDao.getUserByCondition(user,1,1);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public boolean addUser(String phone,String password,HttpServletRequest request) throws Exception  {
		User user=new User ("用户"+phone,phone,null,md5PwdEncoder.encodePassword(password)
				,null,(byte)0 ,RequestUtils.getIpAddr(request),
				null, TimeUtils.getCurrentDay(),(byte)0,"");
			userDao.addUser(user);
			return true;
	}


	@Override
	public boolean editUser(User user) throws Exception{
		//如果修改用户 的状态为0，那就把他踢下线
		if(user.getStatus()==0)
			this.kickoutUser(user.getPhone());
		return userDao.editUser(user);
	}


	@Override
	public User getUserByUserId(int userId) throws Exception {
		User user =new User();
		user.setUserId(userId);
		List<User> list=userDao.getUserByCondition(user,1,1);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public boolean editUserInfo(User user,String userName, String email) throws Exception {
		user.setUserName(userName);
		if(user.getIsCheckEmail()==0){//已经激活的不再让用户修改邮箱
			//邮箱是否被别人绑定了
			User otherUser=this.getUserByEmail(email);
			if((otherUser!=null)&&(user.getUserId()!=otherUser.getUserId())){
				if(otherUser.getIsCheckEmail()==1){
					return false;//已经被他人绑定且已经激活
				}else{//绑定尚未激活
					otherUser.setEmail(null);
					otherUser.setEmailActCode("");
					userDao.editUser(otherUser);
				}
			}
			user.setEmail(email);
			user.setIsCheckEmail((byte)0);
		}
		return userDao.editUser(user);
	}


	@Override
	public User getUserByEmail(String email) throws Exception {
		User user =new User();
		user.setEmail(email);
		List<User> list=userDao.getUserByCondition(user,1,1);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<User> getUserByCondition(User user,int pageNo,int pageSize) throws Exception {
		return userDao.getUserByCondition(user,pageNo,pageSize);
	}


	@Override
	public int getUserCountByCondition(User user) throws Exception {
		return userDao.getUserCountByCondition(user);
	}


	@Override
	public void kickoutUser( String   phone) throws Exception{   
        List<Object> objects = sessionRegistry.getAllPrincipals();
       // System.out.println(objects.size());
        for (Object o : objects) {  
        	UserDetails userDetails= (UserDetails) o;  
        	//System.out.println(userDetails.getUsername());
            if (userDetails.getUsername().equals(phone)) {  
                List<SessionInformation> sis =sessionRegistry
                        .getAllSessions(o, false);  
               // System.out.println(sis.size());
                if (sis != null) {  
                    for (SessionInformation si : sis) { 
                    	//System.out.println(si.getSessionId());
                        si.expireNow();  
                        System.out  
                                .println(si.isExpired() ? "yes,  session be expired"  
                                        : "no yet,session still active");  
                      //彻底删除,删除之后就不能通过(si.expireNow();  )判断是否过期了，而在拦截器里面就是通过这个判断是否过期的，而不是si==null
                      // sessionRegistry.removeSessionInformation(si.getSessionId());
   
                    }  
                }  
            }  
        }  
    }
	@Override
	public void loginOut() throws Exception{   
		UserDetails userDetails= commonService.getUserDetails();
		 List<SessionInformation> sis =sessionRegistry
         .getAllSessions(userDetails, false);  
		 if (sis != null) {  
             for (SessionInformation si : sis) { 
                 si.expireNow();  
             }  
         }  
    }
}
