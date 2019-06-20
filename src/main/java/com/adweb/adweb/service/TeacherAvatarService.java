package com.adweb.adweb.service;

import com.adweb.adweb.entity.TeacherAvatar;

public interface TeacherAvatarService {
    /**
     * 通过teacher的userId获取avatar
     * */
    String getAvatarSrcByUserId(String userId);

    /**
     * 通过userId进行删除操作
     * */
    void deleteRecordByUserId(String userId);

    /**
     * 不管是否有原来的数据，直接插入新数据
     * */
    boolean insertRecord(TeacherAvatar record);
}
