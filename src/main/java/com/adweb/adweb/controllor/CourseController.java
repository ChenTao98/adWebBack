package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.Course;
import com.adweb.adweb.service.CourseService;
import com.adweb.adweb.service.ThemeService;
import com.adweb.adweb.util.Message;
import com.adweb.adweb.util.PathUtil;
import com.adweb.adweb.util.SessionUtil;
import com.adweb.adweb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ThemeService themeService;
    private static final String courseImageUp = PathUtil.COURSE_IMAGE_UP;

    @RequestMapping({"", "index", "/"})
    public String index(Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        setModel(model, teacherId);
        return "course/index";
    }

    @PostMapping("add")
    public String addCourse(@RequestParam() MultipartFile image, @RequestParam() String name,
                            @RequestParam() String summary, @RequestParam() String startTime,
                            @RequestParam() String endTime, @RequestParam() Integer credit,
                            @RequestParam() String type, @RequestParam() Integer theme,
                            Model model, HttpServletRequest httpServletRequest) {
        String teacherId = SessionUtil.getTeacherId(httpServletRequest);
        Date startTimeDate;
        Date endTimeDate;
        try {
            startTimeDate = StringUtil.simpleDateFormat.parse(startTime);
            endTimeDate = StringUtil.simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            Message.writeMessage(model, Message.DATE_ERROR);
            setModel(model, teacherId);
            return "course/index";
        }

        if (startTimeDate.after(endTimeDate) || credit <= 0) {
            Message.writeMessage(model, Message.DATA_ERROR);
            setModel(model, teacherId);
            return "course/index";
        }
        String fileName = image.getOriginalFilename();
        String imageSrc = UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
        File file = new File(courseImageUp + imageSrc);
        try {
            image.transferTo(file);
        } catch (IOException e) {
            if (file.exists()) {
                file.delete();
            }
            Message.writeMessage(model, Message.SYSTEM_ERROR);
            setModel(model, teacherId);
            return "course/index";
        }
        Course course = new Course(name, summary, teacherId, startTimeDate, endTimeDate, imageSrc, credit, type, theme, 0);
        if (courseService.insertCourse(course) != 1) {
            file.delete();
            Message.writeMessage(model, Message.DATABASE_ERROR);
            setModel(model, teacherId);
            return "course/index";
        }
        Message.writeMessage(model, Message.SUCCESS);
        setModel(model, teacherId);
        return "course/index";
    }

    private void setModel(Model model, String teacherId) {
        model.addAttribute("list", courseService.getCourseByTeacher(teacherId));
        model.addAttribute("themeList", themeService.getAllTheme());
    }
}
