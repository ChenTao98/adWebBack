package com.adweb.adweb.service;

import com.adweb.adweb.entity.Chapter;

import java.util.List;

public interface ChapterService {
    List<Chapter> getChapterByCourse(Integer course);
    int insertChapter(Chapter chapter);
    int getLargestChapterOrderName(Integer courseId);
    Chapter isChapterBelongToTeacher(Integer chapterId,String teacherId);
    Chapter getChapterByChapterId(Integer chapterId);
    int modifyChapter(Chapter chapter,Integer oldNumber,Integer courseId);
    int deleteChapter(Chapter chapter);
}
