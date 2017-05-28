package com.se52.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.StaffRelationship;

@Transactional
public interface StaffRelationshipDAO extends JpaRepository<StaffRelationship, Integer>{

	@Query("select s from StaffRelationship s where s.user.id = :id")
	List<StaffRelationship> findByUserId(@Param("id") int userId);
}
