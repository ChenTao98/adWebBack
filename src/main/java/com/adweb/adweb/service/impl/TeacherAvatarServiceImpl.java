package com.adweb.adweb.service.impl;

import com.adweb.adweb.dao.TeacherAvatarDao;
import com.adweb.adweb.entity.TeacherAvatar;
import com.adweb.adweb.entity.TeacherAvatarExample;
import com.adweb.adweb.service.TeacherAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherAvatarServiceImpl implements TeacherAvatarService {
    @Autowired
    private TeacherAvatarDao teacherAvatarDao;

    @Override
    public String getAvatarSrcByUserId(String userId) {
        TeacherAvatarExample example = new TeacherAvatarExample();
        example.createCriteria().andTeacherIdEqualTo(userId);
        List<TeacherAvatar> teacherAvatars = teacherAvatarDao.selectByExample(example);

        if (teacherAvatars.size() == 0) {
            return null;
        }
        return teacherAvatars.get(0).getImageSrc();
    }

    @Override
    public void deleteRecordByUserId(String userId) {
        TeacherAvatarExample example = new TeacherAvatarExample();
        example.createCriteria().andTeacherIdEqualTo(userId);
        teacherAvatarDao.deleteByExample(example);
    }

    @Override
    public boolean insertRecord(TeacherAvatar record) {
        deleteRecordByUserId(record.getTeacherId());

        return teacherAvatarDao.insert(record) == 1;
    }


}
