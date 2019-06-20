package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.Teacher;
import com.adweb.adweb.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class LoginRegisterController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = {"", "/", "/index", "/index.html", "/index.php"})
    public String index(Model model) {
        model.addAttribute("login_hint", "");
        model.addAttribute("register_hint", "");

        return "login/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addUser(@RequestParam(name="username", required = true)String username,
                          @RequestParam(name="password", required = true)String password, Model model) {
        Teacher teacher = new Teacher();
        teacher.setEmail(username);
        // TODO: 2019/6/20 BcriptPasswordEncorder 
        teacher.setPassword(password);
        teacher.setName("");
        teacher.setOpenId(UUID.randomUUID().toString());
        model.addAttribute("login_hint", "");
        model.addAttribute("register_hint", "");

        try {
            if (teacherService.addTeacher(teacher)) {
                // 成功，返回登陆页面
                model.addAttribute("login_hint", "注册成功！");
                return "login/index";
            }

        } catch (DuplicateKeyException e) {
            model.addAttribute("register_hint", "注册失败！重复的邮箱地址！");
            return "login/index";
        }
        model.addAttribute("register_hint", "注册失败！");
        return "login/index";
        // 失败，返回注册页面
    }
}
