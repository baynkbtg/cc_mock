package com.mock.web;

import com.mock.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by qilong.chen on 2018/3/20.
 */
@Controller
@RequestMapping(value = "/")
public class PageController {
    @Autowired
    private MockService mockService;

    @RequestMapping(value = "")
    public ModelAndView mockPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mock");
        return modelAndView;
    }

    @RequestMapping(value="test", method = {RequestMethod.GET})
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Hello World!");
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
