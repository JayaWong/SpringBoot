package com.jaya.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaya.model.Leave;
@Repository
public interface LeaveDao extends JpaRepository<Leave, Integer>{

}
