package cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于跳转到jsp页面
 */
@Controller
public class JspController {

    //用于转到update.jsp
    @RequestMapping("toupdate")
    public String toUpdate(){
        return "update";
    }

    //跳转到add.jsp
    @RequestMapping("toadd")
    public String toAdd(){
        return "add";
    }

    //跳转到login.jsp
    @RequestMapping("tologin")
    public String toLogin(){
        return "login";
    }
}
