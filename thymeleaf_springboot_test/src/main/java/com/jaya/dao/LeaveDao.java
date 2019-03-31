package com.jaya.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaya.model.Leave;
/** <p>Description: [描述该类概要功能介绍]</p>
 * Created on 2018年1月18日
 * @author  <a href="mailto: wangjianying@camelotchina.com">王剑英</a>
 * @version 1.0 
 * Copyright (c) 2018 北京柯莱特科技有限公司 交付部 
 */ 
@Repository
public interface LeaveDao extends JpaRepository<Leave, Integer>{

}
