package com.adweb.adweb.service;

import com.adweb.adweb.entity.Section;

import java.util.List;

public interface SectionService {
    List<Section> getSectionByChapter(Integer chapterId);
    int insertSection(Section section);
    int getLargestSectionOrderNumber(Integer chapter);
    Section isSectionBelongToTeacher(Integer sectionId,String teacherId);
    int modifySection(Section section,Integer oldNumber,Integer chapter);
    int deleteSection(Section section);
    Section getSectionBySectionId(Integer sectionId);
}
