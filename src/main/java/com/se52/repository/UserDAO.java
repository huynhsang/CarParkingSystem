package com.se52.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.User;

@Transactional
public interface UserDAO extends JpaRepository<User, Integer>{

	User findByUsername(String username);
	
	@Query( " select u from User u where u.id in :ids " )
	List<User> findByListUserId(@Param("ids")List<Integer> ids);
	
	@Query( " select u from User u inner join u.authorities a where a.name= \'ROLE_USER\' " )
	List<User> findAllUser();
}
