package com.adweb.adweb.entity;

import java.io.Serializable;

/**
 * student
 * @author 
 */
public class Student implements Serializable {
    /**
     * 用户id 即微信openid
     */
    private String openId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 登录邮箱
     */
    private String email;

    /**
     * 性别 male或female
     */
    private String sex;

    /**
     * 已修学分
     */
    private Integer revisedCredits;

    private static final long serialVersionUID = 1L;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getRevisedCredits() {
        return revisedCredits;
    }

    public void setRevisedCredits(Integer revisedCredits) {
        this.revisedCredits = revisedCredits;
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
        Student other = (Student) that;
        return (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getRevisedCredits() == null ? other.getRevisedCredits() == null : this.getRevisedCredits().equals(other.getRevisedCredits()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getRevisedCredits() == null) ? 0 : getRevisedCredits().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", openId=").append(openId);
        sb.append(", name=").append(name);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", revisedCredits=").append(revisedCredits);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}