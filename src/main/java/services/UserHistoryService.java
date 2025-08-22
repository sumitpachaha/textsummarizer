package services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.User;
import models.UserHistory;
import repository.UserHistoryRepository;
import repository.UserRepository;

@Service
public class UserHistoryService {
	
	private UserHistoryRepository userHistoryRepository;
	
	@Autowired
	UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
        
    }
	
	//Save history
	public void saveHistory(UserHistory userHistory) {
		userHistoryRepository.save(userHistory);
		System.out.println("History is saved successfully");
	}
		
	//show summary history
	public List<UserHistory> showAllHistory(User user){
		return userHistoryRepository.findAllByUser(user);
	}
    

}
