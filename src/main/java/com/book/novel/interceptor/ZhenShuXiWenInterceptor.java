package com.book.novel.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.book.novel.common.anno.NeedAdmin;
import com.book.novel.common.anno.NeedAuthor;
import com.book.novel.common.anno.NoNeedLogin;
import com.book.novel.common.constant.RequestMethodConstant;
import com.book.novel.common.domain.ResponseDTO;
import com.book.novel.module.login.LoginTokenService;
import com.book.novel.module.login.bo.RequestTokenBO;
import com.book.novel.module.role.constant.RoleEnum;
import com.book.novel.module.user.constant.UserResponseCodeConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: liu
 * @Date: 2020/8/7
 * @Description: 拦截器
 */

@Slf4j
@Component
public class ZhenShuXiWenInterceptor extends HandlerInterceptorAdapter {

    private static final String TOKEN_NAME = "x-access-token";

    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * controller执行前前执行 配置跨域问题
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.crossDomainConfig(response);

        // 如果不是访问controller，直接通过
        if (! (handler instanceof HandlerMethod)) {
            return true;
        }

        // 放行options请求
        if (RequestMethodConstant.OPTIONS.equals(request.getMethod())) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 放行不需要登陆的请求
        if (handlerMethod.getMethodAnnotation(NoNeedLogin.class) != null) {
            return true;
        }

        // 获取token
        String headerToken = request.getHeader(TOKEN_NAME);
        System.out.println(headerToken);
        String requestToken = request.getParameter(TOKEN_NAME);
        System.out.println(requestToken);
        String xAccessToken = headerToken != null ? headerToken : requestToken;

        // 解析token
        RequestTokenBO requestTokenBO = loginTokenService.getUserTokenInfo(xAccessToken);
        if (requestTokenBO == null) {
            outputResult(response, UserResponseCodeConst.TOKEN_INVALID);
            return false;
        }

        Integer requestRoleId = requestTokenBO.getUserBO().getRoleId();

        // 管理员用户拥有所有权限
        if (RoleEnum.ADMIN.getValue().equals(requestRoleId)) {
            return true;
        }

        // 管理员已被放行,需要admin权限的直接拒绝
        if (handlerMethod.getMethodAnnotation(NeedAdmin.class) != null) {
            outputResult(response, UserResponseCodeConst.UNAUTHENTICATED);
            return false;
        }

        if (handlerMethod.getMethodAnnotation(NeedAuthor.class) != null) {
            // 当前访问者只能是作者或者普通用户
            if (RoleEnum.AUTHOR.getValue().equals(requestRoleId)) {
                return true;
            } else {
                outputResult(response, UserResponseCodeConst.UNAUTHENTICATED);
                return false;
            }
        }

        return true;
    }

    /**
     * 配置跨域
     *
     * @param response
     */
    private void crossDomainConfig(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, " + "Accept, x-access-token");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires ", "-1");
    }

    /**
     * 错误输出
     *
     * @param response
     * @param responseCodeConst
     * @throws IOException
     */
    private void outputResult(HttpServletResponse response, UserResponseCodeConst responseCodeConst) throws IOException {
        ResponseDTO<Object> wrap = ResponseDTO.wrap(responseCodeConst);
        String msg = JSONObject.toJSONString(wrap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(msg);
        response.flushBuffer();
    }
}
