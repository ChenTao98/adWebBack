package com.adweb.adweb.controllor;

import com.adweb.adweb.entity.Teacher;
import com.adweb.adweb.entity.TeacherAvatar;
import com.adweb.adweb.service.TeacherAvatarService;
import com.adweb.adweb.service.TeacherService;
import com.adweb.adweb.util.PathUtil;
import com.adweb.adweb.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("myinfo")
public class MyInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyInfoController.class);

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherAvatarService teacherAvatarService;

    @RequestMapping({"","index","/"})
    public String index(HttpServletRequest request, Model model){
        model.addAttribute("hint", "");
        HttpSession session = request.getSession();
        String openId = (String)session.getAttribute("openId");
        if (openId != null ) {
            // 查找信息
            Teacher teacher = teacherService.getTeacherByUserId(openId);
            if (teacher == null) {
                return "myInfo/index";
            }
            // image src信息
            String imageSrc = teacherAvatarService.getAvatarSrcByUserId(openId);
            if (StringUtil.isEmpty(imageSrc)) {
                imageSrc = "default.jpg";
            }
            model.addAttribute("imageSrc", PathUtil.TEACHER_IMAGE_HTML + imageSrc);

            model.addAttribute("name", teacher.getName());
            model.addAttribute("email", teacher.getEmail());
        }


        model.addAttribute("openId", openId);


        return "myInfo/index";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(Model model,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email, HttpServletRequest request) {
        model.addAttribute("name", name);
        model.addAttribute("email", email);

        model.addAttribute("hint", "");
        // 如果成功了，hint将会改为 修改成功！

        // 处理数据
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("openId");

        // 上传图片
        if (!imageFile.isEmpty()) {
            if (!uploadImage(imageFile, model, userId)) {
                //上传失败
                return "myInfo/index";
            }
        }
        else {
            // 还是要添加一下原有的图片
            String imageSrc = teacherAvatarService.getAvatarSrcByUserId(userId);
            model.addAttribute("imageSrc", PathUtil.TEACHER_IMAGE_HTML + imageSrc);
        }

        // 修改用户信息
        Teacher teacher = new Teacher();
        teacher.setOpenId(userId);
        teacher.setName(name);
        teacher.setEmail(email);
        if (!teacherService.updateTeacherInfo(teacher)) {
            model.addAttribute("hint", "数据更新失败！");
            return "myInfo/index";
        }
        model.addAttribute("hint", "修改成功！");
        return "myInfo/index";
    }

    /**
     * 检测上传文件类型是否为图片
     * */
    public boolean isValidFileType(String contentType) {
        String fileType = contentType.split("/")[0];
        return fileType.equals("image");
    }

    /**
     * 处理图片
     * */
    public boolean uploadImage(MultipartFile imageFile, Model model, String userId) {
        // 上传图片
        // 检查文件类型
        String imageOriginName = imageFile.getOriginalFilename();
        String fileType = imageOriginName.substring(imageOriginName.lastIndexOf(".")+1);
        LOGGER.info("upload file type: {}", fileType);
        if (!isValidFileType(imageFile.getContentType())) {
            model.addAttribute("hint", "文件类型不合法！");
            return false;
        }
        // 删除原有文件
        String imageSrc = teacherAvatarService.getAvatarSrcByUserId(userId);
        model.addAttribute("imageSrc", PathUtil.TEACHER_IMAGE_HTML + imageSrc);
        if (!(StringUtil.isEmpty(imageSrc) || imageSrc.equals("default.jpg"))) {
            // 需要删除原有的文件
            File file = new File(PathUtil.TEACHER_IMAGE_UP + imageSrc);
            if (file.exists()) {
                if (!file.delete()) {
                    model.addAttribute("hint", "删除原有的头像失败！");
                    return false;
                }
            }
        }

        // 上传新文件
        StringBuilder tmp = new StringBuilder();
        tmp.append(UUID.randomUUID().toString().replaceAll("-",""));
        tmp.append(".");
        tmp.append(fileType);
        try {
            imageFile.transferTo(new File(PathUtil.TEACHER_IMAGE_UP + tmp));
        } catch (IOException e) {
            model.addAttribute("hint", "头像上传失败！");
            return false;
        }
        model.addAttribute("imageSrc", PathUtil.TEACHER_IMAGE_HTML + tmp.toString());

        // 修改TeacherAvatarService
        TeacherAvatar teacherAvatar = new TeacherAvatar();
        teacherAvatar.setImageSrc(tmp.toString());
        teacherAvatar.setTeacherId(userId);
        if (!teacherAvatarService.insertRecord(teacherAvatar) ) {
            model.addAttribute("hint", "数据更新失败！");
            return false;
        }
        return true;
    }
}
