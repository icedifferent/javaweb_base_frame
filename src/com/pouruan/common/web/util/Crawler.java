package com.pouruan.common.web.util;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Crawler {
	public static String createhttpClient(String url, NameValuePair[] data) {
		  HttpClient client = new HttpClient();
		  String response = null;
		  String keyword = null;
		  PostMethod postMethod = new PostMethod(url);
		  if (data != null)
		// 将表单的值放入postMethod中
		   postMethod.setRequestBody(data);
		  postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
		  // 以上部分是带参数抓取,我自己把它注销了．大家可以把注销消掉研究下
		  try {
		   int statusCode = client.executeMethod(postMethod);
		   response= postMethod.getResponseBodyAsString();
		  // response= new String(response.getBytes("UTF-8"), "UTF-8");
		  // String p = response.replaceAll("//&[a-zA-Z]{1,10};", "") .replaceAll("<[^>]*>", "");//去掉网页中带有html语言的标签
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  return response;

		}
	
	
	  /**
     * Method: saveHtml 
     * Description: save String to file
     * @param filepath
     * file path which need to be saved
     * @param str
     * string saved
     */
    public static void saveHtml(String filepath, String str){
        try {
            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), "utf-8");
            outs.write(str);
            outs.close();
        } catch (IOException e) {
           // System.out.println("Error at save html...");
            e.printStackTrace();
        }
    }
	public static void main(String args[]){
		NameValuePair[] param = { 
				new NameValuePair("s", "真的爱你"),
				new NameValuePair("total","true"),
				new NameValuePair("offset", "1"),//页数
				new NameValuePair("hlpretag", "<span class=\"s-fc7\">"),
				new NameValuePair("type", "1"),
				new NameValuePair("limit", "1"),//数量
		};
		String url="http://music.163.com/api/search/get/web?csrf_token=";
		String j=createhttpClient(url, param);
		JsonParser parse =new JsonParser();  //创建json解析器
		JsonObject  json=(JsonObject ) parse.parse(j);
		//System.out.println(((JsonObject) json.get("result")).get("songs"));
		JsonArray jsonArray=(JsonArray) ((JsonObject) json.get("result")).get("songs");
		System.out.println(((JsonObject)jsonArray.get(0)).get("id"));//id;
	//	System.out.println(((JsonObject)jsonArray.get(0)).get("name"));//singer;
		//getMusicInfo(((JsonObject)jsonArray.get(0)).get("id").toString());
		getLyric(((JsonObject)jsonArray.get(0)).get("id").toString());
	}
	
	
	private static void getMusicInfo(String id){
		//http://music.163.com/api/song/detail/?id={$id}&ids=%5B{$id}%5D&csrf_token=
		String url="http://music.163.com/api/song/detail/?id="+id+"&ids=%5B"+id+"%5D&csrf_token=";
		String j=createhttpClient(url, null);
		//System.out.println(j);
		JsonParser parse =new JsonParser();  //创建json解析器
		JsonObject  json=(JsonObject ) parse.parse(j);
		JsonArray songsArray=(JsonArray) json.get("songs");
		System.out.println(((JsonObject)songsArray.get(0)).get("mp3Url"));//url
		System.out.println(((JsonObject)songsArray.get(0)).get("name"));//singer;
		JsonArray artists=(JsonArray)(((JsonObject)songsArray.get(0)).get("artists"));
		System.out.println(((JsonObject)artists.get(0)).get("img1v1Url"));//img url
		System.out.println(((JsonObject)artists.get(0)).get("name"));//artists
		System.out.println(((JsonObject)((JsonObject)songsArray.get(0)).get("album")).get("name"));
	}
	
	private static void getLyric(String id){
		String url="http://music.163.com/api/song/lyric?os=pc&id=" +id+"&lv=-1&kv=-1&tv=-1";
		String j=createhttpClient(url, null);
		//System.out.println(j);
		JsonParser parse =new JsonParser();  //创建json解析器
		JsonObject  json=(JsonObject ) parse.parse(j);
		JsonObject lrcObject=(JsonObject) json.get("lrc");
		System.out.println(lrcObject.get("lyric"));
	}
}
