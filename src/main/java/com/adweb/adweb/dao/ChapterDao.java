package com.adweb.adweb.dao;

import com.adweb.adweb.entity.Chapter;
import com.adweb.adweb.entity.ChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChapterDao {
    long countByExample(ChapterExample example);

    int deleteByExample(ChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    List<Chapter> selectByExample(ChapterExample example);

    Chapter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Chapter record, @Param("example") ChapterExample example);

    int updateByExample(@Param("record") Chapter record, @Param("example") ChapterExample example);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);
    Chapter isChapterBelongToTeacher(@Param("chapterId") Integer chapterId,@Param("teacherId") String teacherId);
    int updateChapterSmaller(@Param("small") Integer small, @Param("large") Integer large, @Param("course") Integer course);
    int updateChapterLarger(@Param("small") Integer small, @Param("large") Integer large, @Param("course") Integer course);
    int deleteOption(Integer chapterId);
    int deleteChoice(Integer chapterId);
    int deleteKnowledge(Integer chapterId);
}