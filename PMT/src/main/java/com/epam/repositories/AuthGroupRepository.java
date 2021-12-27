package com.epam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.entities.AuthGroup;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, Long> {
	List<AuthGroup> findByUsername(String username);
}
