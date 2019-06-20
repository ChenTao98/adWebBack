package com.adweb.adweb.util;

import org.springframework.ui.Model;

public enum Message {
    SUCCESS("成功"),
    DATE_ERROR("时间错误"),
    DATA_ERROR("输入数据错误"),
    DATABASE_ERROR("数据库错误"),
    COURSE_NOT_YOURS_ERROR("该课程不是你所有"),
    COURSE_GET_ERROR("课程获取失败"),
    CHAPTER_NOT_YOURS_ERROR("该章节不是你所有"),
    COURSE_HAVE_START_ERROR("该课程已经开课，不允许修改"),
    PLEASE_INPUT_ERROR("请输入数据"),
    SECTION_NOT_YOURS_ERROR("该小节不是你所有"),
    SYSTEM_ERROR("系统内部错误");
    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public static void writeMessage(Model model,Message message){
        model.addAttribute("message",message.getMessage());
    }
}
