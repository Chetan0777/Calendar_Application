package com.masai.service;

import com.masai.exception.CalenderException;
import com.masai.exception.UserException;
import com.masai.model.Calender;

public interface CalenderService {
	
	public Calender createEvent(Calender cal, String key) throws UserException, CalenderException;
	public Calender updateEvent(Calender cal, String key, Integer eid) throws UserException, CalenderException;
	public Calender deleteEvent(Integer eid, String key) throws UserException,CalenderException;
}
