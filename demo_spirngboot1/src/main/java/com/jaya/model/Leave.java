package com.jaya.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="jaya_leave")
public class Leave {
	@Id
	@Column(length=11)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition="int(11) not null comment '请假天数'")
	private Integer days;
	@Column(columnDefinition="date not null comment '请假日期'")
	private Date leaveDate;
	@Column(columnDefinition="varchar(512) not null comment '请假内容'")
	private String content;
	@Column(columnDefinition="int(11) not null comment '申请状态'")
	private Integer status;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",columnDefinition="int(11) not null comment '用户id'")
	private User user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
