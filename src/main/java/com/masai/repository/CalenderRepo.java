package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.Calender;

@Repository
public interface CalenderRepo extends JpaRepository<Calender, Integer>{
 
	
	
	public List<Calender> findByEventType(String type);
}
