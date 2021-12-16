package com.epam.repositories;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.entities.GroupDetails;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<GroupDetails, Integer> {
	@Query(value = "select * from group_details where user_fk=?1", nativeQuery = true)
	public List<GroupDetails> findByUserId(int userFk);

	public boolean existsByGroupName(String groupName);

	@Query(value = "select count(*) from group_details where user_fk=?1 and group_name=?2", nativeQuery = true)
	public BigInteger existsByGroupNameAndUserId(int userId, String newGroupName);
}
