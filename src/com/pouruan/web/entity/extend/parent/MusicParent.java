package com.pouruan.web.entity.extend.parent;

import java.sql.Timestamp;

public class MusicParent {
	private Integer id;
	private String name;
	private String url;
	private String lyric;
	private String cover;
	private Integer playCount;
	private String player;
	private Timestamp lastPlayTime;
	private String album;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Integer getPlayCount() {
		return playCount;
	}
	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}
	public Timestamp getLastPlayTime() {
		return lastPlayTime;
	}
	public void setLastPlayTime(Timestamp lastPlayTime) {
		this.lastPlayTime = lastPlayTime;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
}
