package com.pouruan.web.entity.parent;




import com.pouruan.web.entity.Permission;
import com.pouruan.web.entity.Admin;

public class RoleParent {
	//private Integer id;
	//private Integer adminId;
	//private Integer permissionId;
	private Admin admin;
	private Permission permission; 
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Admin getAdmin() {
		return admin;
	}

	/*public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	public Integer getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
*/
}
