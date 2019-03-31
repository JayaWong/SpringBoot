package com.jaya.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jaya.dao.UserDao;
import com.jaya.model.User;
@Service
public class UserService {
	@Resource
	private UserDao  userDao;
	public User register(User user) {
		User save = this.userDao.save(user);
		return save;
	}
	public User login(User user) {
		Example<User> example = Example.of(user);
		User findOne = this.userDao.findOne(example);
		return findOne;
	}
}
