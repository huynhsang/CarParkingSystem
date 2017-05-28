package com.se52.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.Floor;

@Transactional
public interface FloorDAO extends JpaRepository<Floor, Integer>{
	@Query("select f from Floor f where f.parking.id = :id")
	List<Floor> findByParkingId(@Param("id") int parkingId);
}

