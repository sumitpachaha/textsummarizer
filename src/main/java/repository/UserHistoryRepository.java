package repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.User;
import models.UserHistory;



public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
	public UserHistory findById(int id);
	public List<UserHistory> findAllByUser(User user);
}

