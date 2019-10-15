package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Music;

public interface MusicService {
	/**
	 * 增加音乐
	 * @param name
	 * @param url
	 * @param cover
	 * @param lyric
	 * @param player
	 * @param album
	 * @return boolean
	 */
	public boolean addMusic(String name,String player,String url,String cover,String lyric,String album) throws Exception;
	/**
	 * 编辑音乐
	 * @param music
	 * @return boolean
	 */
	public boolean editMusic(Music music) throws Exception;
	/**
	 * 按条件列出音乐
	 * @param music
	 * @param pageNo
	 * @param pageSize
	 * @return List<Music>
	 */
	public List<Music> listMusicByCondition(Music music,int pageNo,int pageSize) throws Exception;
	/**
	 * 获取音乐条数
	 * @param music
	 * @return int
	 */
	public int getMusicCountByCondition(Music music) throws Exception;
	/**
	 * 获取某一条音乐
	 * @param music
	 * @return Music
	 */
	public Music getMusic(Music music) throws Exception;
	
	/**
	 * 删除音乐
	 * @param music
	 * @return
	 */
	public boolean delMusic(Music music) throws Exception;
}
