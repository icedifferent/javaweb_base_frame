package com.pouruan.web.dao.extend;

import java.sql.Timestamp;

import com.pouruan.web.entity.extend.Mark;

public interface MarkDao {

	/**
	 * 增加分数，但是不对签到日期进行修改
	 * @param mark {id,userid,mark,sign_time}
	 * @param score 分数，整形
	 * @return
	 * @throws Exception
	 */
	public boolean addMark(Mark mark,Integer score) throws Exception ;
	
	/**
	 * 增加分数，并且对签到日期进行修改（可以说这个是签到修改分数的专用入口）
	 * @param mark
	 * @param score 分数，整形
	 * @param sign_time 最后签到日期
	 * @return
	 * @throws Exception
	 */
	public boolean addMark(Mark mark,Integer score,Timestamp sign_time) throws Exception ;
	
	
	/**
	 * 减少分数，但是不对签到日期进行修改
	 * @param mark 
	 * @param score 分数，整形
	 * @return
	 * @throws Exception
	 */
	public boolean dropMark(Mark mark,Integer score) throws Exception ;
	
	
	/**
	 * 根据条件获取分数情况————————注意，此处传进去的参数是用户的id，所以应该先想办法获取用户的id然后再调用
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	public Mark getMarkById(Integer userId) throws Exception ;
	
	
	
}
