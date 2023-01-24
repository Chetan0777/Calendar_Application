package com.masai.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dto.CurrentUserSession;
import com.masai.exception.CalenderException;
import com.masai.exception.UserException;
import com.masai.model.Calender;
import com.masai.model.User;
import com.masai.repository.CalenderRepo;
import com.masai.repository.CurrentUserSessionRepo;
import com.masai.repository.UserRepo;
import com.masai.service.CalenderService;

@Service
public class CalenderServiceImpl implements CalenderService{
	
	@Autowired
	private UserRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;
	
	@Autowired
	private CalenderRepo crepo;


	@Override
	public Calender createEvent(Calender cal, String key) throws UserException, CalenderException {
		CurrentUserSession validCustomerSession = csdao.findByUuid(key);

		if (validCustomerSession!=null) {
			
			Optional<User> user =  uRepo.findById(validCustomerSession.getUserId());
			   if(user.isPresent()) {
				   cal.setUser(user.get());
				  return crepo.save(cal);
			   }
				
			   else {
				   throw new UserException("No user name found with this id "+  validCustomerSession.getUserId());
			   }

		}
		else {
			throw new UserException("User Not Logged In with this username");
		}
	}


	@Override
	public Calender updateEvent(Calender cal, String key, Integer eid) throws UserException, CalenderException {
		CurrentUserSession validCustomerSession = csdao.findByUuid(key);
	  if (validCustomerSession!=null) {
			
			Optional<User> user =  uRepo.findById(validCustomerSession.getUserId());
			   if(user.isPresent()) {
				Optional<Calender> c =    crepo.findById(eid);
				if(c.isPresent()) {
					if(cal.getEventId()==eid) {
						
						  cal.setUser(user.get());
						  return crepo.save(cal);
					}
					else {
						throw new CalenderException("Event id not matched "+ c.get().getEventId() +" "+" with "+ eid);
					}
				}
				else {
					throw new CalenderException("calnder not present with this id " + eid);
				}
				
			   }
				
			   else {
				   throw new UserException("No user name found with this id "+  validCustomerSession.getUserId());
			   }
		}
		else {
			throw new UserException("User Not Logged In with this username");
		}
	}


	@Override
	public Calender deleteEvent(Integer eid, String key) throws UserException, CalenderException {
		
		CurrentUserSession validCustomerSession = csdao.findByUuid(key);
         
		if (validCustomerSession!=null) {
			
			Optional<User> user =  uRepo.findById(validCustomerSession.getUserId());
			   if(user.isPresent()) {
                  Optional<Calender> c =  crepo.findById(eid);
                  if(c.isEmpty()) {
                	  throw new CalenderException("No calender found with this id " + eid);
                  }
                  
                  if(c.get().getUser().getUserLoginId()==user.get().getUserLoginId()) {
                	  c.get().setUser(null);
                	  crepo.delete(c.get());
                	  return c.get();
                  }
                  else {
                	  throw new CalenderException("No calender found with this user id "+ user.get().getUserLoginId());
                  }
				  
			   }
				
			   else {
				   throw new UserException("No user name found with this id "+  validCustomerSession.getUserId());
			   }

		}
		else {
			throw new UserException("User Not Logged In with this username");
		}
	}

}
