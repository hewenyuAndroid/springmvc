package com.example.spring.controller;

import com.example.spring.controller.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 请求参数
 */
@Controller
@RequestMapping("/reqParam")
public class TestRequestParamController {

    @RequestMapping("/")
    public String reqParam() {
        return "reqParam";
    }

    @RequestMapping("/use_servlet_api")
    public String useServletApi(HttpServletRequest request) {
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        System.out.println("username: " + username + " password: " + password);
        return "testSuccess";
    }

    @RequestMapping("/use_method_param")
    public String useMethodParam(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("username: " + username + " password: " + password);
        return "testSuccess";
    }

    @RequestMapping("/use_pojo")
    public String usePojo(User user) {
        System.out.println("user: " + user);
        return "testSuccess";
    }

}
