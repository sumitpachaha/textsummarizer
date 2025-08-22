package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import models.User;
import models.UserHistory;
import repository.UserHistoryRepository;
import services.UserHistoryService;

@RestController
@RequestMapping(value="/ats")
public class HistoryController {
	
	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	UserHistoryRepository userHistoryRepository;
	
	
	@RequestMapping(value="/history",method=RequestMethod.GET)
	 public List<UserHistory> getUserHistory(@RequestParam(value="id") int id) {
		System.out.println(id);		 
		User user= new User();
		user.setId(id);
		return userHistoryService.showAllHistory(user);
	 }
	
	@RequestMapping(value="/history/{id}",method=RequestMethod.POST)
	 public boolean deleteUserHistory(@PathVariable("id") int id) {
		userHistoryRepository.deleteById(id);
		System.out.print("his: "+id+" is deleted");
		return true;
	 }
	
	

}
