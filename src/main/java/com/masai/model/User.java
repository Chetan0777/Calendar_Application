package com.masai.model;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userLoginId;
	
	@Size(min = 3, max = 20, message = "{user.invalid.userName}")
	@Column(unique = true)
	private String userName;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "{user.invalid.password}")
	private String password;
	
	@Size(min = 2, max = 20, message = "{user.invalid.firstName}")
	private String firstName;
	
	@Size(min = 2, max = 20, message = "{user.invalid.lastName}")
	private String lastName;
	
	private LocalDate dateOfBirth;
	
	@javax.validation.constraints.Email(message =  "Email is not in 'example@email.com' format")
	@Column(unique = true)
	private String email;
	
	@Pattern(regexp = "^[0-9]{10}", message = "Mobile number must have 10 digits")
	private String mobile;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Calender> calender;
}
