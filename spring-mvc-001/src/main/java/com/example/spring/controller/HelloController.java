package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    // "/"  -> /WEB-INF/templates/index.html

    /**
     * /: 表示当前工程的上下文路径 localhost:8080/springmvc/
     *
     * @return
     */
    @RequestMapping(value = "/")
    public String index() {
        // 返回视图名称，
        // 完整的资源路径为 /WEB-INF/templates/ + 视图名称 + .html
        return "index";
    }

    @RequestMapping("/target")
    public String toTarget() {
        return "target";
    }

}
