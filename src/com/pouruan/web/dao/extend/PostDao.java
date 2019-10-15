package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Post;

public interface PostDao {
	/**
	 * ��������
	 * @param post
	 * @return boolean
	 */
	public boolean addPost(Post post) throws Exception ;
	
	
	/**
	 * �༭����
	 * @param post
	 * @return boolean
	 */
	public boolean editPost(Post post) throws Exception ;
	
	
	/**
	 * ɾ������
	 * @param post
	 * @return boolean
	 */
	public boolean delPost(Post post) throws Exception ;
	
	/**
	 * ���������г�����
	 * @param post
	 * @param pageNo
	 * @param pageSize
	 * @param orderType
	 * @return List<Post>
	 */
	public List<Post> listPostByCondition(Post post, int pageNo,int pageSize,int orderType) throws Exception ;
	
	
	/**
	 * ����������ȡ��������
	 * @param post
	 * @return int
	 */
	public int getCountByCondition(Post post) throws Exception ;
}
