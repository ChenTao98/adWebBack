////package com.adweb.adweb.controllor;
////
////import com.adweb.adweb.dao.ChapterDao;
////import com.adweb.adweb.entity.Chapter;
////import com.alibaba.fastjson.JSONObject;
////import com.fasterxml.jackson.databind.util.JSONPObject;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
//
//
////import com.adweb.adweb.service.Tl.TlRobotService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestParam;
////import org.springframework.web.bind.annotation.RestController;
////
//@RestController
//@RequestMapping("test")
//public class TestCon {
////    @Autowired
////    private ChapterDao chapterDao;
////    @GetMapping("test")
////    public String a(){
////        JSONObject jsonObject=new JSONObject();
////        jsonObject.put("1",chapterDao.a());
////        return jsonObject.toJSONString();
////    }
////    @Autowired
////    private TlRobotService tlRobotService;
////    @GetMapping(value = {"","/"},produces = "application/json;UTF-8")
////    public String test(@RequestParam("m")String m){
////        return tlRobotService.sentMassage(m,"1234");
////    }
////    @GetMapping("testTl")
////    public String testTl(){
////        return tlRobotService.sentMassage("计算机网络是什么","1234");
////    }
//}
