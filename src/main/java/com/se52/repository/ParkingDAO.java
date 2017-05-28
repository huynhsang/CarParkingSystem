package com.se52.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.Parking;

@Transactional
public interface ParkingDAO extends JpaRepository<Parking, Integer>{
	
	@Query("select p from Parking p where p.user.id = :id")
	List<Parking> findByUserId(@Param("id") int userId);

	@Query("select p from Parking p where p.staff_id = :id")
	Parking findByStaffId(@Param("id") int staffId);
}
