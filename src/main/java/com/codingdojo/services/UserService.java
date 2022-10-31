package com.codingdojo.services;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.model.LoginUser;
import com.codingdojo.model.User;
import com.codingdojo.repositories.UserRepository;
    
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // TO-DO: Write register and login methods!
    public User registerUser(User newUser, BindingResult result) {
    	Optional <User> checkUser = userRepository.findByEmail(newUser.getEmail());
    	if(checkUser.isPresent()) {
    		result.rejectValue("email","Matches", "This Email is in use. Please select another.");
    	}
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm","Matches","Passwords do not match.");
    	}
    	if(result.hasErrors()) {
        return null;
    	}
    	String hashPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashPassword);
    	return userRepository.save(newUser);
    }
    public User login(LoginUser newLogin, BindingResult result) { 
    Optional <User> checkUser = userRepository.findByEmail(newLogin.getEmail());
        if(!checkUser.isPresent()) {
    		result.rejectValue("email","Matches", "invalid email.");
    		return null;
        }
        
        if (! BCrypt.checkpw(newLogin.getPassword(), checkUser.get().getPassword())) {
        	result.rejectValue("password","Matches", "invalid password.");
        	return null;
        }
        return checkUser.get();
    }
    
    public boolean validateSession(HttpSession session) {
    	if(session.getAttribute("userId")== null) {
    		session.invalidate();
    		return false;
    	}
    	return true;
    }
    
    public User findUser(String email) {
    	Optional<User> user = userRepository.findByEmail(email);
    	if(user.isPresent()) {
    		return user.get();    	
    	}else {
    		return null;
    	}
    }
}
