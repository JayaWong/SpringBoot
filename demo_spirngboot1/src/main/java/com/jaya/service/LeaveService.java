package com.jaya.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jaya.dao.LeaveDao;
import com.jaya.model.Leave;
import com.jaya.model.User;

@Service
public class LeaveService {
	@Resource
	private LeaveDao leaveDao;
	
	public List<Leave> getList(User user){
		Leave leave = new Leave();
		leave.setUser(user);
		Example<Leave> example = Example.of(leave);
		List<Leave> findAll = this.leaveDao.findAll(example);
		return findAll;
	}
	
	public Leave save(User user,Leave leave) {
		leave.setUser(user);
		Leave save = this.leaveDao.save(leave);
		return save;
	}
}
