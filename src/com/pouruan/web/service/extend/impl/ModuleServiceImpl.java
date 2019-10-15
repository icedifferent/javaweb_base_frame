package com.pouruan.web.service.extend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.extend.ModuleDao;
import com.pouruan.web.entity.extend.Module;
import com.pouruan.web.service.extend.ModuleService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class ModuleServiceImpl implements ModuleService{
	@Autowired
	private ModuleDao moduleDao;
	@Override
	public boolean addModule(Module module) throws Exception {
		return moduleDao.addMoudle(module);
	}

	@Override
	public boolean delModule(Module module)  throws Exception{
		return moduleDao.delModule(module);
	}

	@Override
	public Module getMoudle(int moduleId)  throws Exception{
		Module module=new Module();
		module.setId(moduleId);
		List <Module > list=this.listMoudleByCondition(module, 1, 1);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean editModule(Module module)  throws Exception{
		return moduleDao.editModule(module);
	}

	@Override
	public List<Module> listMoudleByCondition(Module module, int pageNo,
			int pageSize) throws Exception {
		return moduleDao.listModuleByCondition(module, pageNo, pageSize);
	}

}
