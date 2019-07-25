package com.linkedaim.admin.project.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_project")
public class Project implements Serializable {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="project_name")
    private String projectName;

    @Column(name="invest_unit")
    private String investUnit;

    @Column(name="position")
    private String position;

    @Column(name="invest_money")
    private String investMoney;

    @Column(name="start_time")
    private String startTime;

    @Column(name="end_time")
    private String endTime;

    /**
     * 0:正常
     */
    @Column(name="status")
    private Integer status;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="create_user_id")
    private String createUserId;

    @Column(name="update_time")
    private Date updateTime;

    @Column(name="update_user_id")
    private String updateUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInvestUnit() {
        return investUnit;
    }

    public void setInvestUnit(String investUnit) {
        this.investUnit = investUnit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(String investMoney) {
        this.investMoney = investMoney;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
