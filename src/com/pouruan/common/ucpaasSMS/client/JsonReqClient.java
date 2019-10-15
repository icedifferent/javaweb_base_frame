/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package com.pouruan.common.ucpaasSMS.client;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.pouruan.common.ucpaasSMS.models.TemplateSMS;
import com.google.gson.Gson;
import com.pouruan.common.ucpaasSMS.DateUtil;
import com.pouruan.common.ucpaasSMS.EncryptUtil;

public class JsonReqClient extends AbsRestClient {
	private static Logger logger=Logger.getLogger(JsonReqClient.class);


	@Override
	public String templateSMS(String accountSid, String authToken,
			String appId, String templateId, String to, String param) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(),
					DateUtil.DATE_TIME_NO_SLASH);// 时间戳
			String signature =getSignature(accountSid,authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/Messages/templateSMS")
					.append("?sig=").append(signature).toString();
			TemplateSMS templateSMS=new TemplateSMS();
			templateSMS.setAppId(appId);
			templateSMS.setTemplateId(templateId);
			templateSMS.setTo(to);
			templateSMS.setParam(param);
			Gson gson = new Gson();
			String body = gson.toJson(templateSMS);
			body="{\"templateSMS\":"+body+"}";
			logger.info(body);
			HttpResponse response=post("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public String dispalyNumber(String accountSid, String authToken,
			String appId, String clientNumber, String display) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient=getDefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			//构造请求URL内容
			String timestamp = DateUtil.dateToStr(new Date(), DateUtil.DATE_TIME_NO_SLASH);
			String signature=getSignature(accountSid, authToken,timestamp,encryptUtil);
			String url = getStringBuffer().append("/").append(version)
					.append("/Accounts/").append(accountSid)
					.append("/dispalyNumber")
					.append("?sig=").append(signature)
					.append("&appId=").append(appId)
					.append("&clientNumber=").append(clientNumber)
					.append("&display=").append(display)
					.toString();
			HttpResponse response=get("application/json",accountSid, authToken, timestamp, url, httpclient, encryptUtil);
			//获取响应实体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		return result;
	}
}
