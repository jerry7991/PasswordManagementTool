package com.epam.api;

import java.util.List;

import com.epam.dto.AccountDetailDto;
import com.epam.entities.AccountDetail;

public interface AccountService {
	List<AccountDetailDto> addAccount(AccountDetailDto accountDetailDto);

	List<AccountDetailDto> findAccountByGroupId(int groupFk);

	List<AccountDetail> findAllAccount();

	List<AccountDetailDto> updateAccount(AccountDetailDto accountDetailDto);

	boolean isAccountExists(AccountDetail accountDetail);

	List<AccountDetailDto> deleteAccountById(AccountDetailDto accountDetailDto);

	AccountDetailDto findAccountByAccountId(int accountId);

	boolean isAlreadyMappedAccountWithGroup(String accountName, int groupFk);

}
