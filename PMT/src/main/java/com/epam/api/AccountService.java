package com.epam.api;

import java.util.List;

import com.epam.dto.AccountDetailDto;
import com.epam.entities.AccountDetail;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;

public interface AccountService {
	boolean addAccount(AccountDetailDto accountDetailDto) throws GroupNotFoundException;

	List<AccountDetailDto> findAccountByGroupId(int groupFk);

	List<AccountDetail> findAllAccount();

	boolean updateAccount(AccountDetailDto accountDetailDto) throws AccountNotFoundException;

	boolean isAccountExists(AccountDetail accountDetail);

	boolean deleteAccountById(AccountDetailDto accountDetailDto) throws AccountNotFoundException;

	AccountDetailDto findAccountByAccountId(int accountId) throws AccountNotFoundException;

	boolean isAlreadyMappedAccountWithGroup(String accountName, int groupFk);

}
