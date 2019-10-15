/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package com.pouruan.common.ucpaasSMS;

import java.io.FileInputStream;
import java.io.IOException;

import com.pouruan.common.ucpaasSMS.client.AbsRestClient;
import com.pouruan.common.ucpaasSMS.client.JsonReqClient;


public class Rest {
	private String accountSid;
	private String authToken;
	
	public String getAccountSid() {
		return accountSid;
	}
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	static AbsRestClient InstantiationRestAPI(boolean enable) {
		return new JsonReqClient();
	}
	public static void testTemplateSMS(boolean json,String accountSid,String authToken,String appId,String templateId,String to,String param){
		try {
			String result=InstantiationRestAPI(json).templateSMS(accountSid, authToken, appId, templateId, to, param);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送短信验证码
	 * @param phone
	 * @param code
	 * @return boolean
	 */
	public  static boolean sendMessage(String phone,String code){
		//System.out.println(phone);
		//System.out.println(code);
		//System.exit(0);
		boolean json=true;
		String accountSid="7d1cef102d4308295159e30e1f048693";
		String token="b09966c0f5e3c0e781c8755a42c02195";
		String appId="d2f556d0354e40cbbe4ca7594969913c";
		String templateId="36974";
		String para="pouruan,"+code+",30";
		try{
			testTemplateSMS(json, accountSid,token,appId, templateId,phone,para);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * 测试说明 参数顺序，请参照各具体方法参数名称
	 * 参数名称含义，请参考rest api 文档
	 * @author Glan.duanyj
	 * @date 2014-06-30
	 * @param params[]
	 * @return void
	 * @throws IOException 
	 * @method main
	 */
	public static void main(String[] args) throws IOException {
			boolean json=true;
			String accountSid="7d1cef102d4308295159e30e1f048693";
			String token="b09966c0f5e3c0e781c8755a42c02195";
			String appId="d2f556d0354e40cbbe4ca7594969913c";
			String templateId="36974";
			String to="13250479087";
			String para="5,1256,30";
			sendMessage("13631782672","1234");
			//testTemplateSMS(json, accountSid,token,appId, templateId,to,para);
	}
}
