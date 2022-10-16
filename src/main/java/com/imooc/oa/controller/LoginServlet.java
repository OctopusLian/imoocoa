package com.imooc.oa.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.UserService;
import com.imooc.oa.utils.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        // 接收用户收入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 调用业务逻辑
        ResponseUtils resp = null;
        try {
            User user = userService.checkLogin(username, password);
            // 防止黑客软件抓包，因此过滤敏感信息
            user.setPassword(null);
            user.setSalt(null);
            resp = new ResponseUtils().put("user",user);
        }catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseUtils().put(e.getClass().getName(),e.getMessage());
        }
        // 返回JSON结果
        response.getWriter().println(resp);
    }
}