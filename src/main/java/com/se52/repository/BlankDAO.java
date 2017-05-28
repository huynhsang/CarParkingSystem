package com.se52.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.Blank;

@Transactional
public interface BlankDAO extends JpaRepository<Blank, Integer>{
	
	@Query("select b from Blank b where b.floor.id = :id")
	List<Blank> findByFloorId(@Param("id") int floorId);
}
