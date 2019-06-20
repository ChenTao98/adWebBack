package com.adweb.adweb.service;

import com.adweb.adweb.entity.Section;

import java.util.List;

public interface SectionService {
    List<Section> getSectionByChapter(Integer chapterId);
    int insertSection(Section section);
    int getLargestSectionOrderNumber(Integer chapter);
}
