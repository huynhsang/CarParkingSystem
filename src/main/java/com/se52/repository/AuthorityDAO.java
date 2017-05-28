package com.se52.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.se52.entity.Authority;

@Transactional
public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
}
