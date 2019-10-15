package com.pouruan.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pouruan.web.dao.RoleDao;
import com.pouruan.web.entity.Admin;
import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.Role;
import com.pouruan.web.entity.User;
import com.pouruan.web.service.RoleService;
import com.pouruan.web.service.UserService;
@Transactional(rollbackFor={Exception.class, RuntimeException.class})//¿ªÆôÊÂÎñ
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserService userService;
	@Override
	public boolean addRole(Admin admin, Permission permission) throws Exception {
		Role role=new Role(admin,permission);
		return roleDao.addRole(role);
	}

	@Override
	public Role findRoleByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addRole(Role role) throws Exception {
		boolean flag=roleDao.addRole(role);
		User user=userService.getUserByUserId(role.getAdmin().getUser().getUserId());
		userService.kickoutUser(user.getPhone());
		return flag;
	}

	@Override
	public boolean delRole(Role role) throws Exception {
		boolean flag=roleDao.delRole(role);
		User user=userService.getUserByUserId(role.getAdmin().getUser().getUserId());
		userService.kickoutUser(user.getPhone());
		return flag;
	}
	@Override
	public boolean delRole(Admin admin, Permission permission) throws Exception {
		Role role=new Role(admin,permission);
		return this.delRole(role);
	}
}
