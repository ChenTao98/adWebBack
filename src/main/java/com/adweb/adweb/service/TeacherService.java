package com.adweb.adweb.service;

import com.adweb.adweb.entity.Teacher;

public interface TeacherService {
    /**
     * 添加teacher
     * */
    boolean addTeacher(Teacher teacher);

    /**
     * 通过email查找用户信息
     * */
    Teacher getTeacherByEmail(String email);

    /**
     * 通过userId查找老师信息
     * */
    Teacher getTeacherByUserId(String userId);

    /**
     * 更新老师信息
     * */
    boolean updateTeacherInfo(Teacher teacher);

}
