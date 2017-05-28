package com.se52.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.Ticket;


@Transactional
public interface TicketDAO extends JpaRepository<Ticket, Integer>{
	
	@Query("select t from Ticket t where t.parkingId = :id and t.status = \'using\'")
	List<Ticket> findByParkingId(@Param("id") int parkingId);
	
	@Query("select t from Ticket t where t.parkingId = :id and t.status = \'completed\'")
	List<Ticket> findCompletedByParkingId(@Param("id") int parkingId);
	
	@Query("select t from Ticket t where t.staffName = :name")
	List<Ticket> findByStaffName(@Param("name") String staffName);
}