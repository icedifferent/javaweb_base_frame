package com.pouruan.web.service.extend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.extend.PostDao;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.entity.extend.Post;
import com.pouruan.web.entity.extend.Reply;
import com.pouruan.web.service.extend.PostService;
import com.pouruan.web.service.extend.ReplyService;
import com.pouruan.web.service.security.CommonService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostDao postDao;
	@Autowired 
	private CommonService commonService;
	@Autowired
	private ReplyService replyService;
	@Override
	public boolean addPost(String title, String content,Module module) throws Exception {
		User user=commonService.getCustomUser();
		Post oldPost=new Post();
		oldPost.setUser(user);
		oldPost=this.getPost(oldPost);
		if(oldPost!=null){
			if(System.currentTimeMillis()-oldPost.getAddDate().getTime()<1000*120){
				return false;//发帖时间较短
			}
		}
		Post post=new Post(title,content,module,user);
		return postDao.addPost(post);
	}

	@Override
	public boolean delPost(Post post) throws Exception {
		return postDao.delPost(post);
	}

	@Override
	public int getCountByCondition(Post post) throws Exception {
		return postDao.getCountByCondition(post);
	}

	@Override
	public boolean editPost(Post post) throws Exception {
		return postDao.editPost(post);
	}

	@Override
	public List<Post> listPostByCondition(Post post, int pageNo, int pageSize,int orderType) throws Exception {
		List<Post> list=postDao.listPostByCondition(post, pageNo, pageSize,orderType);
		Reply reply=new Reply();
		for(int i=0;i<list.size();i++){
			reply.setPost(list.get(i));
			list.get(i).setReplyCount(replyService.getCountByCondition(reply));
		}
		return list;
	}

	@Override
	public Post getPost(Post post) throws Exception {
		List<Post> list=listPostByCondition(post, 1,1,0);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Post getPostById(int id) throws Exception {
		Post post=new Post();
		post.setId(id);
		return this.getPost(post);
	}

}
