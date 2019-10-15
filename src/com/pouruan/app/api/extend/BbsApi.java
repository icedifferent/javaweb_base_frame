package com.pouruan.app.api.extend;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.pouruan.common.image.ImageScaleImpl;
import com.pouruan.common.upload.FileRepository;
import com.pouruan.common.util.DateJsonValueProcessor;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;
import com.pouruan.web.service.extend.PostService;
import com.pouruan.web.service.extend.ReplyService;


@Controller
public class BbsApi {
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private PostService postService;
	@Autowired
	private ImageScaleImpl imageScale;
	@Autowired
	private ReplyService replyService;
	private Logger logger = LogManager.getRootLogger();
	/**
	 * 富文本图片上传
	 * @param imgFile
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = "/Bbs/uploadImg.do", method = RequestMethod.POST)
	public void upLoadImg(MultipartFile imgFile,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws IOException{
		String photoUrl=null;
		PrintWriter out=response.getWriter();
		Map<String,String> map = new HashMap<String,String>();
		if(imgFile!=null){
			if(!imgFile.isEmpty()){
				String origName = imgFile.getOriginalFilename();
				String ext = FilenameUtils.getExtension(origName).toLowerCase(
						Locale.ENGLISH);
				if(ext.equals("")){
					ext="png";
				}
				if (!"GIF,JPG,JPEG,PNG,BMP".contains(ext.toUpperCase())) {

				}else{
					try {
						photoUrl=fileRepository.storeByExt("/res/upload/Bbs/img", ext, imgFile);
						out.write("/pouruan"+photoUrl);
					} catch (IOException e) {
						logger.error("bbs图片上传IOException"+e );
					} catch (Exception e) {
						logger.error("bbs图片上传Exception"+e );
					}
				}
			}else{

			}
		}else{

		}
		out.flush();
		out.close();
	}
	
	
	/**
	 * 查看帖子 API
	 * @param postId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping(value = "/Bbs/getPost.do", method = RequestMethod.POST)
	public void readPost(Integer postId,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws IOException{
		JsonConfig config = new JsonConfig();
		//防止防止自包导致“There is a cycle in the hierarchy”
		config.setExcludes(new String[]{"user","module","replys","hashCode","handler","hibernateLazyInitializer"}); 
		
		Map<String,Object> map = new HashMap<String,Object>();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		Post post=new Post();
		post.setId(postId);
		String json="";
		try {
			post=postService.getPost(post);
			if(post!=null){
				map.put("status", 1);
				map.put("post", post);
			
			}else{
				map.put("status", 0);
				map.put("post", "");
			}
		} catch (Exception e) {
			map.put("status", -1);
			map.put("post", e.getMessage());
		}
		
		json=JSONObject.fromObject(map,config).toString();
		out.write(json);
		out.flush();
		out.close();
	}
	
	
	/**
	 * 获取帖子的回复
	 * @param model
	 * @param pageNo
	 * @param pageSize
	 * @throws IOException 
	 */
	@RequestMapping(value = "/Bbs/getReply.do", method = RequestMethod.POST)
	private void getReply(Integer postId,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws IOException{
		JsonConfig config = new JsonConfig();
		//防止防止自包导致“There is a cycle in the hierarchy”
		config.setExcludes(new String[]{"hashCode","handler","hibernateLazyInitializer"}); 
		//处理时间格式
		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
	    config.setJsonPropertyFilter(new PropertyFilter() {  
            @Override  
            public boolean apply(Object source, String name, Object value) {  
                if (name.equals("replyList")  
                        ||name.equals("status")  
                        || name.equals("id")  
                        || name.equals("content")  
                        || name.equals("replyUser")  
                        || name.equals("date") || name.equals("userName")  
                        || name.equals("userId")|| name.equals("portrait_img")) {  
                		//这些字段会被转换  
                    return false;  
                }  
                return true;  
            }  
        });  
		Map<String,Object> map = new HashMap<String,Object>();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		Post post=new Post();
		post.setId(postId);
		try {
			post=postService.getPost(post);
			if(post!=null){
				Reply reply=new Reply();
				reply.setPost(post);
				if(pageNo==null||pageNo<=0) pageNo=1;
				List<Reply> list=replyService.listReplyByCondition(reply, pageNo, 10);
				map.put("status", 1);
				//map.put("count", replyService.getCountByCondition(reply));
				map.put("replyList", list);
			}else{
				map.put("status", 0);
				map.put("replyList", "");
			}
		} catch (Exception e) {
			map.put("status", -1);
			map.put("replyList", "未知错误"+e.getMessage());
		}
		String json="";
		json=JSONObject.fromObject(map,config).toString();
		out.write(json);
		out.flush();
		out.close();
	}
}
