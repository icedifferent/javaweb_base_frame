package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Post;

public interface PostDao {
	/**
	 * 增加帖子
	 * @param post
	 * @return boolean
	 */
	public boolean addPost(Post post) throws Exception ;
	
	
	/**
	 * 编辑帖子
	 * @param post
	 * @return boolean
	 */
	public boolean editPost(Post post) throws Exception ;
	
	
	/**
	 * 删除帖子
	 * @param post
	 * @return boolean
	 */
	public boolean delPost(Post post) throws Exception ;
	
	/**
	 * 根据条件列出帖子
	 * @param post
	 * @param pageNo
	 * @param pageSize
	 * @param orderType
	 * @return List<Post>
	 */
	public List<Post> listPostByCondition(Post post, int pageNo,int pageSize,int orderType) throws Exception ;
	
	
	/**
	 * 根据条件获取帖子总数
	 * @param post
	 * @return int
	 */
	public int getCountByCondition(Post post) throws Exception ;
}
