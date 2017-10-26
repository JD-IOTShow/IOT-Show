package com.jdnw.iotshow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * AUTHOR: zxy
 * com.jdnw.iotshow.controller
 * DATE: 2017/10/24
 * TIME: 14:36
 **/
@Controller
public class ShowController {

    @Autowired
    @RequestMapping("")
    public String show(){
        return "index";
    }

    @RequestMapping("/send")
    public void sendMessage(){
    }
}