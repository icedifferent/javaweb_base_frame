package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Music;

public interface MusicDao {
	/**
	 * ��������
	 * @param music
	 * @return boolean
	 */
	public boolean addMusic(Music music)  throws Exception ;
	/**
	 * �༭����
	 * @param music
	 * @return boolean
	 */
	public boolean editMusic(Music music)  throws Exception ;
	/**
	 * �������г�����
	 * @param music
	 * @param pageNo
	 * @param pageSize
	 * @return List<Music>
	 */
	public List<Music> listMusicByCondition(Music music,int pageNo,int pageSize)  throws Exception ;
	/**
	 * ��ȡ��������
	 * @param music
	 * @return int
	 */
	public int getMusicCountByCondition(Music music) throws Exception ;
	/**
	 * ɾ������
	 * @param music
	 * @return
	 */
	public boolean delMusic(Music music) throws Exception ;
}
