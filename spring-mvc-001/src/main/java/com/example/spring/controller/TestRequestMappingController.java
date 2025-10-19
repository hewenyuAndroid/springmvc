package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test")
public class TestRequestMappingController {

    /**
     * 由于 @RequestMapping 注解在类上有配置，因此，资源的 URI 为 /springmvc/test/testSuccess.html
     */
    @RequestMapping("/testSuccess")
    public String testSuccess() {
        return "testSuccess";
    }

    /**
     * @RequestMapping.value 属性能够配置多个 uri，表示多个uri访问的是同一个资源
     */
    @RequestMapping(
            value = {"/test", "/test2"}
    )
    public String testMappingValue() {
        return "testSuccess";
    }

    @RequestMapping(value = "/method", method = RequestMethod.GET)
    public String testMethod() {
        return "testSuccess";
    }

    @RequestMapping("/save/{userId}/{username}")
    public String testPathVariable(@PathVariable("userId") String userId, @PathVariable("username") String username) {
        System.out.println("userId = " + userId + ", username = " + username);
        return "testSuccess";
    }

}
