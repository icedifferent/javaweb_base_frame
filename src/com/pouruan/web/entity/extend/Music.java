package com.pouruan.web.entity.extend;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.extend.parent.MusicParent;

public class Music extends MusicParent{
	public Music(){}
	public Music(String name,String player,String lyric,String cover,String url,String album){
		this.setCover(cover);
		this.setLastPlayTime(TimeUtils.getCurrentDay());
		this.setLyric(lyric);
		this.setName(name);
		this.setUrl(url);
		this.setPlayer(player);
		this.setPlayCount(0);
		this.setAlbum(album);
	}
}
