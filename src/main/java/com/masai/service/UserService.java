package com.masai.service;

import java.util.List;

import com.masai.dto.UserDto;
import com.masai.exception.CalenderException;
import com.masai.exception.LoginException;
import com.masai.exception.UserException;
import com.masai.model.Calender;
import com.masai.model.User;


public interface UserService {
	
	public User saveUser(User user) throws UserException;

	public User updateUser(User user, String key) throws UserException, LoginException;

	public String loginUser(UserDto user) throws LoginException;

	public String logoutUser(String key) throws LoginException;
	
	public List<Calender> getEventWithType(String eventType) throws CalenderException;
}
