package com.adweb.adweb.entity.courseStudent;

import com.adweb.adweb.entity.ChoiceQuestion;

import java.util.List;

public class StudentChoice {
    private String studentId;
    private String studentName;
    private String sectionName;
    private List<ChoiceQuestion> choiceQuestionList;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<ChoiceQuestion> getChoiceQuestionList() {
        return choiceQuestionList;
    }

    public void setChoiceQuestionList(List<ChoiceQuestion> choiceQuestionList) {
        this.choiceQuestionList = choiceQuestionList;
    }
}
