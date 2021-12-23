package com.epam.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.entities.UserDetails;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
	boolean existsByUserNameAndMasterPassword(String userName, String masterPassword);

	boolean existsByUserName(String userName);

	UserDetails findByUserName(String userName);

	UserDetails findByUserNameAndMasterPassword(String userName, String masterPassword);
}
