package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import models.User;
import repository.UserRepository;
import services.UserService;


@RestController
@RequestMapping("/ats")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	//Select all from table
	@GetMapping("/users")
	public List<User> getUsers(User users){
		return userService.getUsers(users);
		
	}
	
	//Create new User
	@PostMapping("/register")
	public boolean createUsers(@RequestBody User user){
		return userService.createUser(user);
		
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public User login(@RequestBody User user) {
		System.out.println(user.getUserEmail());
		User u = userService.findByUserEmail(user.getUserEmail(),user.getUserPass());
		if(u==null)
		{
			System.out.println("Sorry not logged in");
			return null;
		}
		
		else {
			System.out.println("Successfull");
			return u;
		}
	    
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public User updateUser(@RequestBody User user,@PathVariable Integer id) {
		System.out.println(user.getUserEmail());
		if((userRepository.findById(id))!=null) {
			boolean b=userService.updateUser(user);
			return user;
		}		
		else {
			return null;
		}
	    
	}
	
	//Select particular user with id
	@GetMapping("/user/{userId}")
	  public Optional<User>getUser(@PathVariable Integer userId) {
	        return userService.getUser(userId);
	    }

}
