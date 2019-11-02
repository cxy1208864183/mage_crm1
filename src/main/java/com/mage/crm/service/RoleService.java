package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.dao.RoleDao;
import com.mage.crm.dao.UserRoleDao;
import com.mage.crm.query.RoleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;
    public List<Role> queryAllRoles() {
        return roleDao.queryAllRoles();
    }

    public Map<String,Object> queryRolesByParama(RoleQuery roleQuery) {
        PageHelper.startPage(roleQuery.getPage(), roleQuery.getRows());
        List<Role> roles= roleDao.queryRolesByParams(roleQuery.getRoleName());
        PageInfo<Role> pageInfo=new PageInfo<Role>(roles);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }

    public void insert(Role role) {
        /*
		 * 1.参数校验
		 *    角色名非空
		 * 2.角色名重复性校验
		 * 3.额外字段值设置
		 * 4.执行添加
		 */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名非空!");
        AssertUtil.isTrue(null!=roleDao.queryRoleByRoleName(role.getRoleName()),"角色已存在!");
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        AssertUtil.isTrue(roleDao.insert(role)<1, CrmConstant.OPS_FAILED_MSG);
    }

    public void update(Role role) {
        /*
		 * 1.参数校验
		 *    角色名非空
		 *    id 记录必须存在
		 * 2.角色名重复性校验
		 * 3.额外字段值设置
		 *    updateDate
		 * 4.执行更新
		 */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名非空!");
        AssertUtil.isTrue(null==role.getId()||null==roleDao.queryRoleById(role.getId()),"待更新的角色记录不存在!");
        Role temp = roleDao.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null!=temp&&temp.getId().equals(role.getId()),"角色名已存在!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleDao.update(role)<1,CrmConstant.OPS_FAILED_MSG);
    }

    public void delete(Integer id) {
        AssertUtil.isTrue(null==id||null==roleDao.queryRoleById(id+""),"待删除的角色记录不存在!");
        /**
         *    级联删除用户角色记录
         * 删除角色记录
         */
        int count = userRoleDao.queryUserRoleCountsByRoleId(id);
        if (count>0){
            AssertUtil.isTrue(userRoleDao.deleteUserRolesByRoleId(id)<count,CrmConstant.OPS_FAILED_MSG);
        }

    }
}
