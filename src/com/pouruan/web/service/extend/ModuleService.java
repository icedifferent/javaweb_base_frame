package com.pouruan.web.service.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Module;

public interface ModuleService {
	/**
	 * ���Ӱ��
	 * @param module
	 * @return boolean
	 */
	public boolean addModule(Module module) throws Exception;
	/**
	 * ɾ�����
	 * @param module
	 * @return
	 */
	public boolean delModule(Module module) throws Exception;
	/**
	 * ���Ұ��
	 * @param moudleId
	 * @return Module
	 */
	public Module getMoudle(int moduleId) throws Exception;
	/**
	 * �༭���
	 * @param module
	 * @return
	 */
	public boolean editModule(Module module) throws Exception;
	/**
	 * ���������г����
	 * @param moudle
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Module> listMoudleByCondition(Module module,int pageNo ,int pageSize) throws Exception;
}
