package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Music;

public interface MusicDao {
	/**
	 * 增加音乐
	 * @param music
	 * @return boolean
	 */
	public boolean addMusic(Music music)  throws Exception ;
	/**
	 * 编辑音乐
	 * @param music
	 * @return boolean
	 */
	public boolean editMusic(Music music)  throws Exception ;
	/**
	 * 按条件列出音乐
	 * @param music
	 * @param pageNo
	 * @param pageSize
	 * @return List<Music>
	 */
	public List<Music> listMusicByCondition(Music music,int pageNo,int pageSize)  throws Exception ;
	/**
	 * 获取音乐条数
	 * @param music
	 * @return int
	 */
	public int getMusicCountByCondition(Music music) throws Exception ;
	/**
	 * 删除音乐
	 * @param music
	 * @return
	 */
	public boolean delMusic(Music music) throws Exception ;
}
