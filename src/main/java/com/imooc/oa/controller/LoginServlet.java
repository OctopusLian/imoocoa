package com.imooc.oa.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.UserService;

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
        Map result = new LinkedHashMap();
        try {
            User user = userService.checkLogin(username, password);
            //0表示成功，非0表示失败
            result.put("code","0");
            result.put("result","success");
            Map data = new LinkedHashMap();
            data.put("user",user);
            data.put("data",data);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("code",e.getClass().getSimpleName());
            result.put("message",e.getMessage());
        }
        // 返回JSON结果
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().println(json);
    }
}
