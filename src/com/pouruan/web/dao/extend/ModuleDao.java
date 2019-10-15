package com.pouruan.web.dao.extend;

import java.util.List;

import com.pouruan.web.entity.extend.Module;

public interface ModuleDao {
	/**
	 * ������̳���
	 * @param module
	 * @return boolean
	 */
	public boolean addMoudle(Module module) throws Exception ;
	
	/**
	 * ɾ����̳���
	 * @param module
	 * @return boolean
	 */
	public boolean delModule(Module module) throws Exception ;
	/**
	 * �༭��̳���
	 * @param module
	 * @return boolean
	 */
	public boolean editModule(Module module) throws Exception ;
	
	/**
	 * ���������г���̳���
	 * @param module
	 * @param pageNo
	 * @param pageSize
	 * @return List <Module> 
	 */
	public List <Module> listModuleByCondition(Module module,int pageNo,int pageSize) throws Exception ;
	
	/**
	 * ��ѯ�����������ĸ���
	 * @param module
	 * @return int
	 */
	public int getCountByCondition(Module module)  throws Exception ;
}
