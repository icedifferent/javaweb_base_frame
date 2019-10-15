package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Music;

public interface MusicService {
	/**
	 * ��������
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
	 * �༭����
	 * @param music
	 * @return boolean
	 */
	public boolean editMusic(Music music) throws Exception;
	/**
	 * �������г�����
	 * @param music
	 * @param pageNo
	 * @param pageSize
	 * @return List<Music>
	 */
	public List<Music> listMusicByCondition(Music music,int pageNo,int pageSize) throws Exception;
	/**
	 * ��ȡ��������
	 * @param music
	 * @return int
	 */
	public int getMusicCountByCondition(Music music) throws Exception;
	/**
	 * ��ȡĳһ������
	 * @param music
	 * @return Music
	 */
	public Music getMusic(Music music) throws Exception;
	
	/**
	 * ɾ������
	 * @param music
	 * @return
	 */
	public boolean delMusic(Music music) throws Exception;
}
