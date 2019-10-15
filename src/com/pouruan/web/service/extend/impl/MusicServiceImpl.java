package com.pouruan.web.service.extend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.extend.MusicDao;
import com.pouruan.web.entity.extend.Music;
import com.pouruan.web.service.extend.MusicService;

@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class MusicServiceImpl implements MusicService{
	@Autowired
	private MusicDao musicDao;
	@Override
	public boolean addMusic(String name, String player,String url, String cover, String lyric,String album)  throws Exception{
		Music music =new Music(name,player,lyric,cover,url,album);
		return musicDao.addMusic(music);
	}

	@Override
	public boolean editMusic(Music music)  throws Exception{
		return musicDao.editMusic(music);
	}

	@Override
	public List<Music> listMusicByCondition(Music music, int pageNo,
			int pageSize)  throws Exception{
		
		return musicDao.listMusicByCondition(music, pageNo, pageSize);
	}

	@Override
	public int getMusicCountByCondition(Music music) throws Exception {
		return musicDao.getMusicCountByCondition(music);
	}

	@Override
	public Music getMusic(Music music) throws Exception {
		List list=musicDao.listMusicByCondition(music, 1, 1);
		if(list.size()!=0){
			return (Music) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean delMusic(Music music) throws Exception {
		return musicDao.delMusic(music);
	}

}
