package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUserEmail(String userEmail);
	
}
