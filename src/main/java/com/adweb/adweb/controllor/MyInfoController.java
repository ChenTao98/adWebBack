package com.adweb.adweb.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("myinfo")
public class MyInfoController {
    @RequestMapping({"","index","/"})
    public String index(){
        return "myInfo/index";
    }
}
