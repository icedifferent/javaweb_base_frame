package com.pouruan.web.entity.parent;
/**
 * 网站配置
 * @author Ice
 *
 */
public class ConfigParent {
	private Integer Id;
	private String webTitle;
	private String webDescription;
	private String webKeyWord;
	private String domain;
	private Integer maxSendEmailTimes; 
	private Integer maxSendMesTimes; 
	private String emailHost;
	private String emailUserName;
	private String emailPwd;
	private String emailTitle;
	private String emailPwdContent;
	private String emailActContent;
	private Integer authExpiryDate;//认证的有效期
	private Integer maxErrorTimes;//超过登录次数则显示验证码
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getWebTitle() {
		return webTitle;
	}
	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}
	public String getWebDescription() {
		return webDescription;
	}
	public void setWebDescription(String webDescription) {
		this.webDescription = webDescription;
	}
	public String getWebKeyWord() {
		return webKeyWord;
	}
	public void setWebKeyWord(String webKeyWord) {
		this.webKeyWord = webKeyWord;
	}
	public Integer getAuthExpiryDate() {
		return authExpiryDate;
	}
	public void setAuthExpiryDate(Integer authExpiryDate) {
		this.authExpiryDate = authExpiryDate;
	}
	public Integer getMaxErrorTimes() {
		return maxErrorTimes;
	}
	public void setMaxErrorTimes(Integer maxErrorTimes) {
		this.maxErrorTimes = maxErrorTimes;
	}
	public Integer getMaxSendEmailTimes() {
		return maxSendEmailTimes;
	}
	public void setMaxSendEmailTimes(Integer maxSendEmailTimes) {
		this.maxSendEmailTimes = maxSendEmailTimes;
	}
	public Integer getMaxSendMesTimes() {
		return maxSendMesTimes;
	}
	public void setMaxSendMesTimes(Integer maxSendMesTimes) {
		this.maxSendMesTimes = maxSendMesTimes;
	}
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailUserName() {
		return emailUserName;
	}
	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}
	public String getEmailPwd() {
		return emailPwd;
	}
	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}
	public String getEmailTitle() {
		return emailTitle;
	}
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}
	public String getEmailPwdContent() {
		return emailPwdContent;
	}
	public void setEmailPwdContent(String emailPwdContent) {
		this.emailPwdContent = emailPwdContent;
	}
	public String getEmailActContent() {
		return emailActContent;
	}
	public void setEmailActContent(String emailActContent) {
		this.emailActContent = emailActContent;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public void editConfig(String domain,String emailActContent,String emailHost,String emailPwd,
			String emailPwdContent,String emailTitle,String emailUserName,int maxErrorTimes,
			int maxSendEmailTimes,int maxSendMesTimes, String webDescription,String webKeyWord,String webTitle){
		//this.setAuthExpiryDate(authExpiryDate);
		this.setDomain(domain);
		this.setEmailActContent(emailActContent);
		this.setEmailHost(emailHost);
		this.setEmailPwd(emailPwd);
		this.setEmailPwdContent(emailPwdContent);
		this.setEmailTitle(emailTitle);
		this.setEmailUserName(emailUserName);
	//	this.setId(id);
		this.setMaxErrorTimes(maxErrorTimes);
		this.setMaxSendEmailTimes(maxSendEmailTimes);
		this.setMaxSendMesTimes(maxSendMesTimes);
		this.setWebDescription(webDescription);
		this.setWebKeyWord(webKeyWord);
		this.setWebTitle(webTitle);
	}
}
