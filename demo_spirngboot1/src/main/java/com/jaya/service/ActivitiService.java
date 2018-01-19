package com.jaya.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.jaya.dao.LeaveDao;
import com.jaya.model.Leave;
import com.jaya.model.User;
import com.jaya.vo.LeaveTask;

@Service
public class ActivitiService{
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private LeaveDao leaveDao;
	
	public void startProcess(Leave leave) {
		HashMap<String, Object> var = new HashMap<String,Object>();
		var.put("userId", leave.getUser().getId());
		this.processEngine.getRuntimeService().startProcessInstanceByKey("Leave", leave.getId()+"", var);
		Leave findOne = this.leaveDao.findOne(leave.getId());
		findOne.setStatus(1);
		this.leaveDao.save(findOne);
	}
	public void completeTask(Leave leave,String message) {
		Task singleResult = this.processEngine.getTaskService()
												.createTaskQuery()
												.taskId(leave.getId()+"")
												.singleResult();
		HashMap<String, Object> var = new HashMap<String,Object>();
		if(leave.getUser().getLeader()!=null) {
			var.put("parentId", leave.getUser().getLeader().getId());
		}
		if(message != null && !message.equals("")) {
			var.put("message", message);
		}
		this.processEngine.getTaskService().complete(leave.getId()+"", var);
		ProcessInstance instance = this.processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(singleResult.getProcessInstanceId()).singleResult();
		HistoricProcessInstance historicProcessInstance = this.processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(singleResult.getProcessInstanceId()).singleResult();
		Leave findOne = this.leaveDao.findOne(Integer.parseInt(historicProcessInstance.getBusinessKey()));
		if(instance == null ) {
			findOne.setStatus(3);
		}else if(message != null && message.equals("驳回")){
			findOne.setStatus(4);
		}else {
			findOne.setStatus(2);
		}
		this.leaveDao.save(findOne);
	}
	public List<LeaveTask> getRunList(User user) {
		List<Task> list = this.processEngine
									.getTaskService()
									.createTaskQuery()
									.taskAssignee(user.getId()+"")
									.list();
		ArrayList<LeaveTask> dataList = new ArrayList<LeaveTask>();
		if(list != null && list.size() > 0) {
			for (Task task : list) {
				LeaveTask leaveTask = new LeaveTask();
				leaveTask.setId(Integer.parseInt(task.getId()));
				String processInstanceId = task.getProcessInstanceId();
				ProcessInstance processInstance = this.processEngine.getRuntimeService()
						.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				Leave leave = this.leaveDao.findOne(Integer.parseInt(processInstance.getBusinessKey()));
				if(leave == null) {
					continue;
				}
				leaveTask.setContent(leave.getContent());
				leaveTask.setTypeName("请假");
 				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) this.processEngine
						.getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
				String activityId = processInstance.getActivityId();
				ActivityImpl activity = processDefinition.findActivity(activityId);
				List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
				ArrayList<String> oprationList = new ArrayList<String>();
				if (outgoingTransitions != null && outgoingTransitions.size() > 0) {
					for (PvmTransition pvmTransition : outgoingTransitions) {
						String property = (String) pvmTransition.getProperty("name");
						oprationList.add(property);
					} 
				}else {
					oprationList.add("保存");
				}
				leaveTask.setOperation(oprationList);
				dataList.add(leaveTask);
			}
		}
		return dataList;							
	}
	public List<LeaveTask> historyTask(User user) {
		List<HistoricTaskInstance> list = this.processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery().taskAssignee(user.getId()+"").finished().list();
		ArrayList<LeaveTask> result = new ArrayList<LeaveTask>();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance historicTaskInstance : list) {
				String processInstanceId = historicTaskInstance.getProcessInstanceId();
				ProcessInstanceHistoryLog singleResult = this.processEngine.getHistoryService().createProcessInstanceHistoryLogQuery(processInstanceId).singleResult();
				String id = singleResult.getBusinessKey();
				Leave leave = this.leaveDao.findOne(Integer.parseInt(id));
				LeaveTask leaveTask = new LeaveTask();
				leaveTask.setId(Integer.parseInt(historicTaskInstance.getId()));
				leaveTask.setContent(leave.getContent());
				leaveTask.setUserName(leave.getUser().getUserName());
				leaveTask.setTypeName("请假");
				result.add(leaveTask);
			}
		}
		return result;
	}
}
