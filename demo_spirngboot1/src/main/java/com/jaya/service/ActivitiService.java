package com.jaya.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.jaya.model.Leave;
import com.jaya.model.User;

@Service
public class ActivitiService{
	@Resource
	private ProcessEngine processEngine;
	
	public void startProcess(Leave leave) {
		
		HashMap<String, Object> var = new HashMap<String,Object>();
		var.put("userId", leave.getUser().getId());
		ProcessInstance process = this.processEngine.getRuntimeService().startProcessInstanceByKey("Leave", leave.getId()+"", var);
		Task singleResult = this.processEngine.getTaskService().createTaskQuery().processInstanceId(process.getId()).singleResult();
		var.put("parentId", leave.getUser().getLeader().getId());
		var.remove("userId");
		this.processEngine.getTaskService().complete(singleResult.getId(), var);
	}
	
	public List<Task> getRunList(User user) {
		List<Task> list = this.processEngine
									.getTaskService()
									.createTaskQuery()
									.taskAssignee(user.getId()+"")
									.list();
		return list;							
	}
}
