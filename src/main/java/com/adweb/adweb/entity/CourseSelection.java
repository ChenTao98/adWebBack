package com.adweb.adweb.entity;

import java.io.Serializable;

/**
 * course_selection
 * @author 
 */
public class CourseSelection extends CourseSelectionKey implements Serializable {
    /**
     * 课程完成状态 1表示已完成 0表示未完成
     */
    private Integer status;

    /**
     * 当前学到哪个章节的id
     */
    private Integer chapter;

    /**
     * 当前学到哪个小节
     */
    private Integer sectionId;

    private static final long serialVersionUID = 1L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CourseSelection other = (CourseSelection) that;
        return (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getChapter() == null ? other.getChapter() == null : this.getChapter().equals(other.getChapter()))
            && (this.getSectionId() == null ? other.getSectionId() == null : this.getSectionId().equals(other.getSectionId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getChapter() == null) ? 0 : getChapter().hashCode());
        result = prime * result + ((getSectionId() == null) ? 0 : getSectionId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", status=").append(status);
        sb.append(", chapter=").append(chapter);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}