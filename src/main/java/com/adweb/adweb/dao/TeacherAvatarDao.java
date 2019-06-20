package com.adweb.adweb.dao;

import com.adweb.adweb.entity.TeacherAvatar;
import com.adweb.adweb.entity.TeacherAvatarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherAvatarDao {
    long countByExample(TeacherAvatarExample example);

    int deleteByExample(TeacherAvatarExample example);

    int insert(TeacherAvatar record);

    int insertSelective(TeacherAvatar record);

    List<TeacherAvatar> selectByExample(TeacherAvatarExample example);

    int updateByExampleSelective(@Param("record") TeacherAvatar record, @Param("example") TeacherAvatarExample example);

    int updateByExample(@Param("record") TeacherAvatar record, @Param("example") TeacherAvatarExample example);
}