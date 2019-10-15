package com.pouruan.app.api.extend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pouruan.web.entity.extend.Music;
import com.pouruan.web.service.extend.MusicService;

@Controller
public class MusicAPi {
	@Autowired
	private MusicService musicService;
	
	
	@RequestMapping(value = "/Api/Extend/Music/listMusic.do")
	public void listMusic(String types,Music music,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		if(pageNo==null||pageNo<0){pageNo=1;}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		String json="";
		if(types.equals("search")){
			List list =musicService.listMusicByCondition(music, pageNo, 10);
			int musicSize=musicService.getMusicCountByCondition(music);
			json="mkPlayerCallBack({\"result\":{\"songs\":"+JSONArray.fromObject(list).toString()+"},\"code\":200})";
		}	
		
		if(types.equals("musicInfo")){
			Music m=musicService.getMusic(music);
			json="mkPlayerCallBack({\"songs\":"+JSONArray.fromObject(m).toString()+",\"code\":200})";
		}
		out.write(json);
		out.flush();
		out.close();
	
	}
}
