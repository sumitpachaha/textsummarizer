package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.User;
import repository.UserRepository;
import services.ForgotPasswordService;

@RestController
@RequestMapping(value="ats")
public class ForgotPassController{

	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@RequestMapping(value="/forgotpassword/{toemail}", method=RequestMethod.GET)
	public String forgotPassword(@PathVariable String toemail) {
		User user = userRepository.findByUserEmail(toemail);
		if(user != null) {
			String pass=bcryptPasswordEncoder.encode(user.getUserPass());
			return forgotPasswordService.sendMail(toemail,pass);
			}
		
		else {
			return "Invalid Email!!";
			
			}
		}
}

