package com.pouruan.web.entity.extend;

import com.pouruan.common.web.TimeUtils;
import com.pouruan.web.entity.User;
import com.pouruan.web.entity.extend.parent.ReplyParent;

public class Reply extends ReplyParent{
	public Reply(){}
	public Reply(String content,Post post,User replyUser){
		this.setContent(content);
		this.setPost(post);
		this.setReplyUser(replyUser);
		this.setDate(TimeUtils.getCurrentDay());
	}
}
