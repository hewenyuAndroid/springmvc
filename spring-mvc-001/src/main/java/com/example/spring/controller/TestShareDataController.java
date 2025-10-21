package com.example.spring.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 域对象共享数据
 */
@Controller
@RequestMapping("/share_data")
public class TestShareDataController {

    /**
     * 请求域共享数据
     *
     * @param request request
     * @return result
     */
    @GetMapping("/use_http_servlet_request")
    public String useHttpServletRequest(HttpServletRequest request) {
        // 向请求域红写入数据
        request.setAttribute("username", "zhangsan");
        return "testSuccess";
    }

    /**
     * 使用 ModelAndView
     * ModelAndView有Model和View的功能
     * Model主要用于向请求域共享数据
     * View主要用于设置视图，实现页面跳转
     *
     * @return result
     */
    @GetMapping("/use_model_and_view")
    public ModelAndView useModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        // 设置请求域的共享数据
        modelAndView.addObject("username", "zhangsan");
        // 设置视图，实现页面跳转
        modelAndView.setViewName("testSuccess");
        return modelAndView;
    }

    /**
     * 使用 model 向request域对象共享数据
     *
     * @param model model
     * @return result
     */
    @GetMapping("/use_model")
    public String useModel(Model model) {
        model.addAttribute("username", "zhangsan");
        return "testSuccess";
    }

    /**
     * 使用map向request域对象共享数据
     *
     * @param map map
     * @return result
     */
    @RequestMapping("/use_map")
    public String useMap(Map<String, Object> map) {
        map.put("username", "zhangsan");
        return "testSuccess";
    }

    /**
     * 使用ModelMap向request域对象共享数据
     * Model、ModelMap、Map类型的参数其实本质上都是 BindingAwareModelMap 类型的
     *
     * @param modelMap modelMap
     * @return result
     */
    @GetMapping("/use_model_map")
    public String useModelMap(ModelMap modelMap) {
        modelMap.addAttribute("username", "zhangsan");
        return "testSuccess";
    }

    /**
     * 设置 session域共享数据
     *
     * @param session session
     * @return result
     */
    @GetMapping("/use_http_session")
    public String useHttpSession(HttpSession session) {
        session.setAttribute("username", "zhangsan");
        return "testSuccess";
    }

    /**
     * 设置 application域共享数据
     *
     * @param session session session
     * @return result result
     */
    @GetMapping("/use_application_context")
    public String useApplicationContext(HttpSession session) {
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("username", "zhangsan");
        return "testSuccess";
    }

}
