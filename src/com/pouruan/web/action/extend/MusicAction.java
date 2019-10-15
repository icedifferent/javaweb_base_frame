package com.pouruan.web.action.extend;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.pouruan.common.upload.FileRepository;
import com.pouruan.common.web.tips.WebErrors;
import com.pouruan.common.web.tips.WebSuccess;
import com.pouruan.web.entity.extend.Music;
import com.pouruan.web.service.extend.MusicService;
import com.pouruan.web.service.security.CommonService;

@Controller
public class MusicAction {
	private Logger logger = LogManager.getRootLogger();
	@Autowired 
	private CommonService commonService;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private MusicService musicService;
	/**
	 * 增加音乐页面
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/Admin/Extend/Music/addMusic.do", method = RequestMethod.GET)
	public String addMusic(HttpServletRequest request,HttpServletResponse response, ModelMap model){
		return "Admin/Extend/Music/addMusic";
	}
	/**
	 * 增加音乐
	 * @param music
	 * @param music_file
	 * @param cover_file
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/Admin/Extend/Music/addMusic.do", method = RequestMethod.POST)
	public String addMusicDone(Music music,MultipartFile music_file,MultipartFile cover_file,HttpServletRequest request,HttpServletResponse response, ModelMap model){
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		String musicUrl=null;
		String coverUrl=null;
		errors.ifOutOfLength(music.getName(), "musicName",1,30);
		errors.ifOutOfLength(music.getPlayer(), "musicSinger",1,20);
		errors.ifOutOfLength(music.getLyric(), "musicLyric",1,512);
		errors.ifOutOfLength(music.getAlbum(), "musicAlbum",1,30);
		if((cover_file!=null)&&(music_file!=null)&&(!music_file.isEmpty())&&(!cover_file.isEmpty())&&(!errors.hasErrors())){
			String musicOrigName = music_file.getOriginalFilename();
			String musicExt = FilenameUtils.getExtension(musicOrigName).toLowerCase(
					Locale.ENGLISH);
			String coverOrigName = cover_file.getOriginalFilename();
			String coverExt = FilenameUtils.getExtension(coverOrigName).toLowerCase(
					Locale.ENGLISH);
			if(musicExt.equals("")){
				musicExt="mp3";
			}
			if(coverExt.equals("")){
				coverExt="png";
			}
			if (!"MP3".contains(musicExt.toUpperCase())) {
				errors.addError("音乐只支持MP3格式");
			}
			if (!"GIF,JPG,JPEG,PNG,BMP".contains(coverExt.toUpperCase())) {
				errors.addError("封面只支持(gif,jpg,jpeg,png,bmp)格式");
			}
			if(!errors.hasErrors()){
				try {
					musicUrl=fileRepository.storeByExt("/res/upload/music", musicExt, music_file);
					coverUrl=fileRepository.storeByExt("/res/upload/music/cover", coverExt, cover_file);
					if(!musicService.addMusic(music.getName(),music.getPlayer(),musicUrl , coverUrl, music.getLyric(),music.getAlbum())){
						errors.addError("增加音乐失败");
						logger.error("增加音乐失败 "+music.getName());
					}else{
						successes.addSuccess("添加成功");
						successes.toModel(model);
						return "Admin/Extend/Music/addMusic";
					}
				} catch (IOException e) {
					//增加失败则全部删除
					ServletContext  context=request.getSession().getServletContext();
					File fi = new File(context.getRealPath(musicUrl)); 
					fi.delete();
					File fis = new File(context.getRealPath(coverUrl)); 
					fis.delete();
					errors.addError("存储失败");
					logger.error("音乐存储失败 "+music.getName()+e);
				} catch (Exception e) {
					errors.addError("addMusic未知错误"+e.getMessage());
				}
			}
		}
		errors.toModel(model);//输出错误信息
		return "Admin/Extend/Music/addMusic";
	}
	
	/**
	 * 列出符合条件的音乐
	 * @param music
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Music/listMusic.do")
	public String listMusic(Music music,Integer pageNo,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		if(pageNo==null||pageNo<0){pageNo=1;}
		List list =musicService.listMusicByCondition(music, pageNo, 10);
		int musicSize=musicService.getMusicCountByCondition(music);
		model.addAttribute("musicList",list);
		model.addAttribute("musicSize",musicSize);
		model.addAttribute("currentPage",pageNo);
		return "Admin/Extend/Music/listMusic";
	}
	
	/**
	 * 编辑音乐页面
	 * @param music
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Music/editMusic.do", method = RequestMethod.GET)
	public String editMusic(Integer id,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		Music music=new Music();
		music.setId(id);
		music=musicService.getMusic(music);
		if(music==null){
			errors.addError("音乐不存在");
			errors.toModel(model);//输出错误信息
		}else{
			model.addAttribute("music",music);
		}
		
		return "Admin/Extend/Music/editMusic";
	}		
	
	
	/**
	 * 修改音乐
	 * @param music
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Music/editMusic.do", method = RequestMethod.POST)
	public String editMusic(MultipartFile music_file,MultipartFile cover_file,Music music,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		WebSuccess successes = WebSuccess.create(request);
		errors.ifOutOfLength(music.getName(), "musicName",1,30);
		errors.ifOutOfLength(music.getPlayer(), "musicSinger",1,20);
		errors.ifOutOfLength(music.getLyric(), "musicLyric",1,512);
		errors.ifOutOfLength(music.getAlbum(), "musicAlbum",1,30);
		String musicUrl=null;
		String coverUrl=null;
		String musicExt=null;
		String coverExt = null;
		Music m=new Music();
		m.setId(music.getId());
		Music oldMusic=musicService.getMusic(m);
		if((!errors.hasErrors())&&(oldMusic!=null)){
			//检验文件
			if((music_file!=null)&&(!music_file.isEmpty())){
				String musicOrigName = music_file.getOriginalFilename();
					musicExt = FilenameUtils.getExtension(musicOrigName).toLowerCase(
							Locale.ENGLISH);
					if(musicExt.equals("")){
					musicExt="mp3";
				}
				if (!"MP3".contains(musicExt.toUpperCase())) {
					errors.addError("音乐只支持MP3格式");
				}
			}
			if((cover_file!=null)&&(!cover_file.isEmpty())&&(!errors.hasErrors())){
				String coverOrigName = cover_file.getOriginalFilename();
				coverExt = FilenameUtils.getExtension(coverOrigName).toLowerCase(
							Locale.ENGLISH);
				if(coverExt.equals("")){
					coverExt="png";
				}
				if (!"GIF,JPG,JPEG,PNG,BMP".contains(coverExt.toUpperCase())) {
					errors.addError("封面只支持(gif,jpg,jpeg,png,bmp)格式");
				}
			}
			//保存文件到本地
			if(!errors.hasErrors()){
				try {
					if(musicExt!=null){
						musicUrl=fileRepository.storeByExt("/res/upload/music", musicExt, music_file);
					}
					if(coverExt!=null){
						coverUrl=fileRepository.storeByExt("/res/upload/music/cover", coverExt, cover_file);
					}
				} catch (IOException e) {
					logger.error("音乐存储失败 "+music.getName()+e);
					//增加失败则全部删除
					ServletContext  context=request.getSession().getServletContext();
					if(musicExt!=null){
						File fi = new File(context.getRealPath(musicUrl)); 
						fi.delete();
					}
					if(coverExt!=null){
						File fis = new File(context.getRealPath(coverUrl)); 
						fis.delete();
					}
					errors.addError("存储失败"+e.getMessage());
				} 
			}
			if(!errors.hasErrors()){
				//删除旧文件
				try{
					ServletContext  context=request.getSession().getServletContext();
					if(musicUrl!=null){
						music.setUrl(musicUrl);
						File fi = new File(context.getRealPath(oldMusic.getUrl())); 
						fi.delete();
					}
					if(coverUrl!=null){
						music.setCover(coverUrl);
						File fi = new File(context.getRealPath(oldMusic.getCover())); 
						fi.delete();
					}
				}catch (Exception e) {
					logger.error("音乐存储删除失败 musicId"+oldMusic.getId()+e);
				}
			}
			//保存到数据库
			if(!errors.hasErrors()){
				this.copyMusic(oldMusic, music);
				try {
					musicService.editMusic(oldMusic);
					model.addAttribute("music",oldMusic);
					successes.addSuccess("修改成功");
					successes.toModel(model);
					return "Admin/Extend/Music/editMusic";
					
				} catch (Exception e) {
					errors.addError("未知错误"+e.getMessage());	
				}
			}
		}else{
			errors.addError("音乐不存在");
		}
		errors.toModel(model);//输出错误信息
		return "Admin/Extend/Music/editMusic";
	}		
	
	
	/**
	 * 删除音乐
	 * @param musicId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value = "/Admin/Extend/Music/delMusic.do", method = RequestMethod.POST)
	public String delMusic(Integer musicId,HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception{
		WebErrors errors = WebErrors.create(request);
		Music music=new Music();
		music.setId(musicId);
		music=musicService.getMusic(music);
		if(music==null){
			errors.addError("音乐不存在");
		}else{
			//从数据库中删除
			try {
				if(!musicService.delMusic(music)){
					errors.addError("删除音乐失败 musicId"+music.getId());
					logger.error("删除音乐失败 musicId"+music.getId());
				}else{
					//从硬盘中删除
					ServletContext  context=request.getSession().getServletContext();
					try{
						if(music.getUrl()!=null){
							File fi = new File(context.getRealPath(music.getUrl())); 
							fi.delete();
						}
						if(music.getCover()!=null){
							File fi = new File(context.getRealPath(music.getCover())); 
							fi.delete();
						}
					}catch(Exception e){
						logger.error("删除音乐失败 musicId"+music.getId()+e);
					}
					return "redirect:/Admin/Extend/Music/listMusic.do";
				}
			} catch (Exception e) {
				errors.addError(e.getMessage());
			}
		
		}
		errors.toModel(model);//输出错误信息
		return "redirect:/Admin/Extend/Music/listMusic.do";
	}
	
	/**
	 * 音乐对象的复制
	 * @param music
	 * @param wasCopyedMusic
	 * @return
	 */
	private boolean copyMusic(Music music,Music wasCopyedMusic){
		if(wasCopyedMusic.getCover()!=null)
		music.setCover(wasCopyedMusic.getCover());
		music.setLyric(wasCopyedMusic.getLyric());
		music.setName(wasCopyedMusic.getName());
		if(wasCopyedMusic.getUrl()!=null)
		music.setUrl(wasCopyedMusic.getUrl());
		music.setPlayer(wasCopyedMusic.getPlayer());
		music.setAlbum(wasCopyedMusic.getAlbum());
		return true;
	}

}
