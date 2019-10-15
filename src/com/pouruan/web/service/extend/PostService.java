package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.entity.extend.Post;

public interface PostService {
	/**
	 * ��������
	 * @param title
	 * @param content
	 * @param module
	 * @return boolean
	 */
	public boolean addPost(String title,String content,Module module) throws Exception;
	
	
	/**
	 * ɾ������
	 * @param post
	 * @return boolean
	 */
	public boolean delPost(Post post) throws Exception;
	
	
	/**
	 * ���ҷ���������������Ŀ
	 * @param post
	 * @return int
	 */
	public int getCountByCondition(Post post) throws Exception;
	
	/**
	 * �޸�����
	 * @param post
	 * @return boolean
	 */
	public boolean editPost(Post post) throws Exception;
	
	/**
	 * ����������������
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
	 * ����id��������
	 * @param id
	 * @return
	 */
	public Post getPostById(int id) throws Exception;
}
