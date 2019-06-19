package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.Course;
import com.adweb.adweb.service.CourseService;
import com.adweb.adweb.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @RequestMapping({"","index","/"})
    public String index(Model model, HttpServletRequest httpServletRequest){
        String teacherId= SessionUtil.getTeacherId(httpServletRequest);
        model.addAttribute("list",courseService.getCourseByTeacher(teacherId));
        return "course/index";
    }
}
