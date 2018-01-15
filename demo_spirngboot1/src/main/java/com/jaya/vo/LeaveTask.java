package com.jaya.vo;

import java.util.List;

public class LeaveTask {
	private Integer id;
	private String typeName;
	private String content;
	private List<String> operation;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getOperation() {
		return operation;
	}
	public void setOperation(List<String> operation) {
		this.operation = operation;
	}
}
