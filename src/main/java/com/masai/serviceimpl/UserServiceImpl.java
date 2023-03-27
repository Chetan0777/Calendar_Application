package com.masai.serviceimpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.dto.CurrentUserSession;
import com.masai.dto.UserDto;
import com.masai.exception.CalenderException;
import com.masai.exception.LoginException;
import com.masai.exception.UserException;
import com.masai.model.Calender;
import com.masai.model.User;
import com.masai.repository.CalenderRepo;
import com.masai.repository.CurrentUserSessionRepo;
import com.masai.repository.UserRepo;
import com.masai.service.UserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private UserRepo uRepo;

	@Autowired
	private CurrentUserSessionRepo csdao;
	
	@Autowired
	private CalenderRepo crepo;


	@Override
	public User saveUser(User user) throws UserException {

		User existingUserName = uRepo.findByUserName(user.getUserName());
		User existingUserEmail = uRepo.findByEmail(user.getEmail());

		if (existingUserName != null)
			throw new UserException("Username already exists " + user.getUserName());
		
		if (existingUserEmail != null)
			throw new UserException("User email exists " + user.getEmail());
       
		return uRepo.save(user);
	}

	@Override
	public User updateUser(User user, String key) throws UserException, LoginException {

		CurrentUserSession loggedInUser = csdao.findByUuid(key);

		if (loggedInUser == null) {
			throw new UserException("Please provide a valid key to update a customer");
		}

		if (user.getUserLoginId() == loggedInUser.getUserId()) {
			return uRepo.save(user);
		} else
			throw new LoginException("Invalid User Details, please login first");

	}

	

	@Override
	public String loginUser(UserDto user) throws LoginException {

		User existingUser = uRepo.findByUserName(user.getUserName());

		if (existingUser == null)
			throw new LoginException("Invalid credentials. Username does not exist " + user.getUserName());

		Optional<CurrentUserSession> validCustomerSessionOpt = csdao.findById(existingUser.getUserLoginId());

		if (validCustomerSessionOpt.isPresent()) {

			throw new LoginException("User already Logged In with this username");

		}

		if (existingUser.getPassword().equals(user.getPassword())) {

			String key = RandomString.make(6);
			
			Boolean isAdmin = false;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getUserLoginId(), key, isAdmin,
					LocalDateTime.now());

			csdao.save(currentUserSession);

			return currentUserSession.toString();
		} else
			throw new LoginException("Please Enter a valid password");
	}

	@Override
	public String logoutUser(String key) throws LoginException {

		CurrentUserSession validCustomerSession = csdao.findByUuid(key);

		if (validCustomerSession == null) {
			throw new LoginException("User Not Logged In with this username");

		}

		csdao.delete(validCustomerSession);

		return "Logged Out !";
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<Calender> getEventWithType(String eventType) throws CalenderException {
		
		List<Calender> calList =  crepo.findAll();
		
		List<Calender> list = new ArrayList<>();
		if(eventType.equals("month")) {
			for(Calender k : calList) {
				SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
				if(k.getFromDate().getMonthValue() ==LocalDate.now().getMonthValue() &&k.getToDate().getMonthValue()==LocalDate.now().getMonthValue()){
					{
						
						
						list.add(k);
				}
					
				}
			}
			return list;
		}
		if(eventType.equals("week")) {
			for(Calender k : calList) {
				SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
				LocalDate today = LocalDate.now();
				LocalDate weekNext = today.plusWeeks(1);
				if(k.getFromDate().getDayOfMonth()>=today.getDayOfMonth() && k.getToDate().getDayOfMonth()<weekNext.getDayOfMonth() && k.getFromDate().getMonthValue()==today.getMonthValue()&& k.getToDate().getMonthValue()==today.getMonthValue()){
					{
						
						list.add(k);
				}
				}
			}
			return list;
		}
		if(eventType.equals("day")) {
			for(Calender k : calList) {
				SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
				if(k.getFromDate().equals(LocalDate.now())&& k.getToDate().equals(LocalDate.now())){
					{
						
						list.add(k);
				}
					
				}
			}
			return list;
		}
	
		else {
			List<Calender> allEvents =  crepo.findByEventType(eventType);
			return allEvents;
		} 
		 
	}

}
