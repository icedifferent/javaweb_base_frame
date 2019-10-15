package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Module;

public interface ModuleDao {
	/**
	 * 增加论坛版块
	 * @param module
	 * @return boolean
	 */
	public boolean addMoudle(Module module) throws Exception ;
	
	/**
	 * 删除论坛版块
	 * @param module
	 * @return boolean
	 */
	public boolean delModule(Module module) throws Exception ;
	/**
	 * 编辑论坛版块
	 * @param module
	 * @return boolean
	 */
	public boolean editModule(Module module) throws Exception ;
	
	/**
	 * 根据条件列出论坛版块
	 * @param module
	 * @param pageNo
	 * @param pageSize
	 * @return List <Module> 
	 */
	public List <Module> listModuleByCondition(Module module,int pageNo,int pageSize) throws Exception ;
	
	/**
	 * 查询符合条件版块的个数
	 * @param module
	 * @return int
	 */
	public int getCountByCondition(Module module)  throws Exception ;
}
