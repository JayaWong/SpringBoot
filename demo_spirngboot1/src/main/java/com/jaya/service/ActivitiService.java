package com.jaya.service;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.jaya.model.User;

@Service
public class ActivitiService{
	@Resource
	private ProcessEngine processEngine;
	
	public List<Task> getRunList(User user) {
		List<Task> list = this.processEngine
									.getTaskService()
									.createTaskQuery()
									.taskAssignee(user.getId()+"")
									.list();
		return list;							
	}
}
