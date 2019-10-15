package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Module;

public interface ModuleService {
	/**
	 * 增加版块
	 * @param module
	 * @return boolean
	 */
	public boolean addModule(Module module) throws Exception;
	/**
	 * 删除版块
	 * @param module
	 * @return
	 */
	public boolean delModule(Module module) throws Exception;
	/**
	 * 查找版块
	 * @param moudleId
	 * @return Module
	 */
	public Module getMoudle(int moduleId) throws Exception;
	/**
	 * 编辑版块
	 * @param module
	 * @return
	 */
	public boolean editModule(Module module) throws Exception;
	/**
	 * 根据条件列出版块
	 * @param moudle
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Module> listMoudleByCondition(Module module,int pageNo ,int pageSize) throws Exception;
}
