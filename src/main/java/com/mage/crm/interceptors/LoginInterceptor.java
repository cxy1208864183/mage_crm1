package com.mage.crm.interceptors;

import com.mage.crm.base.CrmConstant;
import com.mage.crm.service.UserService;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String id = CookieUtil.getCookieValue(request, "id");
        System.out.println(id);
        //判断id是否为空
        AssertUtil.isTrue(StringUtils.isBlank(id), CrmConstant.LOGIN_NO_CODE,CrmConstant.LOGIN_NO_MSG);
        //需要对id解密进行查询
        User user = userService.queryUserById(Base64Util.deCode(id));
        AssertUtil.isTrue(null==user,CrmConstant.LOGIN_NO_CODE,CrmConstant.LOGIN_NO_MSG);
        //判断该id账户是否失效
        AssertUtil.isTrue("0".equals(user.getIsValid()),CrmConstant.LOGIN_NO_CODE,CrmConstant.LOGIN_NO_MSG);
        return true;
    }
}
