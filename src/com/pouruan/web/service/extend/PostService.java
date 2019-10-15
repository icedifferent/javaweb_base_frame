package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.entity.extend.Post;

public interface PostService {
	/**
	 * 增加帖子
	 * @param title
	 * @param content
	 * @param module
	 * @return boolean
	 */
	public boolean addPost(String title,String content,Module module) throws Exception;
	
	
	/**
	 * 删除帖子
	 * @param post
	 * @return boolean
	 */
	public boolean delPost(Post post) throws Exception;
	
	
	/**
	 * 查找符合条件的帖子数目
	 * @param post
	 * @return int
	 */
	public int getCountByCondition(Post post) throws Exception;
	
	/**
	 * 修改帖子
	 * @param post
	 * @return boolean
	 */
	public boolean editPost(Post post) throws Exception;
	
	/**
	 * 根据条件查找帖子
	 * @param post
	 * @param postNo
	 * @param postSize
	 * @param orderType
	 * @return List<Post>
	 */
	public List<Post> listPostByCondition(Post post,int pageNo,int pageSize,int orderType) throws Exception;
	
	/**
	 * 
	 * @param post
	 * @return Post
	 */
	public Post getPost(Post post) throws Exception;
	
	
	/**
	 * 根据id查找帖子
	 * @param id
	 * @return
	 */
	public Post getPostById(int id) throws Exception;
}
