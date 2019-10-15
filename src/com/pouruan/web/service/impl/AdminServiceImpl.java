package com.pouruan.web.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.AdminDao;
import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.Role;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.AdminService;
import com.pouruan.web.service.RoleService;
import com.pouruan.web.service.UserService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//开启事务
@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Override
	public boolean addRole(User user, Permission  permission) throws Exception {
		// TODO Auto-generated method stub
		Admin admins =new Admin();
		admins.setUser(user);
		List<Admin> list=adminDao.getAdmin(admins);
		if(list.size()==0){
			Admin admin=new Admin(user,(byte)0);
			adminDao.addAdmin(admin);//先得增加管理员
			Role role=new Role(admin,permission);//再增加角色
			return roleService.addRole(role);
		}else{//此用户已经是管理了
			//维护关系在多的一端了，这里无法直接saveAdmin
			Role role=new Role(list.get(0),permission);
			return roleService.addRole(role);
		}
	}
	@Override
	public boolean delAdmin(User user) throws Exception {
		Admin admin =new Admin();
		admin.setUser(user);
		return adminDao.delAdmin(admin);
	}

	@Override
	public boolean delAdmin(Admin admin) throws Exception {
		User user=userService.getUserByUserId(admin.getUser().getUserId());
		userService.kickoutUser(user.getPhone());
		return adminDao.delAdmin(admin);
	}

	@Override
	public List<Admin> getAllAdmin() throws Exception {
		return adminDao.getAdmin(null);
	}

}
