package com.adweb.adweb.util;

import org.springframework.ui.Model;

public enum Message {
    SUCCESS("成功"),
    DATE_ERROR("时间错误"),
    DATA_ERROR("输入数据错误"),
    DATABASE_ERROR("数据库错误"),
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
