package com.epam.repositories;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.entities.AccountDetail;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<AccountDetail, Integer> {
	@Query(value = "select * from account_details where group_fk=?1", nativeQuery = true)
	public List<AccountDetail> findAccountByGroupId(int groupFk);

	@Query(value = "select count(*) from account_details where account_name=?1 and group_fk=?2", nativeQuery = true)
	public BigInteger countAccountByGroupIdAndAccountName(String accountName, int groupFk);
}
