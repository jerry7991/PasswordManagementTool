package com.epam.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.api.AccountService;
import com.epam.api.Validation;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.Response;
import com.epam.entities.AccountDetail;
import com.epam.entities.GroupDetails;
import com.epam.exceptions.AccountMappingWithGroupException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.exceptions.ValidationFailedException;
import com.epam.repositories.AccountRepository;
import com.epam.repositories.GroupRepository;
import com.epam.util.Loggers;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private Validation validation;

	@Autowired
	private Loggers logger;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<AccountDetailDto> addAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> accounts = null;
		try {
			Response response = validation.isAccountValid(accountDetailDto);
			if (!response.isStatus()) {
				throw new ValidationFailedException((String) response.getMsg());
			}
			if (isAlreadyMappedAccountWithGroup(accountDetailDto.getAccountName(), accountDetailDto.getGroupId())) {
				throw new AccountMappingWithGroupException("Account Allready mapped with given group");
			}
			logger.printInfo(AccountServiceImpl.class, "User Validated for account");
			Optional<GroupDetails> option = groupRepository.findById(accountDetailDto.getGroupId());
			if (option.isPresent()) {
				GroupDetails groupDetails = option.get();
				AccountDetail accountDetail = modelMapper.map(accountDetailDto, AccountDetail.class);

				accountDetail.setGroupDetails(groupDetails);
				groupDetails.getAccounts().add(accountDetail);
				groupRepository.save(groupDetails);
			} else {
				throw new GroupNotFoundException("Group Is not exist.");
			}
			accounts = findAccountByGroupId(accountDetailDto.getGroupId());
		} catch (ValidationFailedException | AccountMappingWithGroupException | GroupNotFoundException ex) {
			logger.printError(AccountServiceImpl.class, ex.getMessage());
		} finally {
			accounts = findAccountByGroupId(accountDetailDto.getGroupId());
		}
		return accounts;
	}

	@Override
	public List<AccountDetailDto> findAccountByGroupId(int groupFk) {
		List<AccountDetail> accountDetails = accountRepository.findAccountByGroupId(groupFk);
		return accountDetails.stream().map(account -> modelMapper.map(account, AccountDetailDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<AccountDetailDto> updateAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> updatedAccount = null;
		try {
			Optional<AccountDetail> option = accountRepository.findById(accountDetailDto.getAccountId());
			AccountDetail originalAccount = option
					.orElseThrow(() -> new AccountNotFoundException("Account doesn't exist."));
			originalAccount.setAccountName(accountDetailDto.getAccountName());
			originalAccount.setUrl(accountDetailDto.getUrl());
			originalAccount.setPassword(accountDetailDto.getPassword());
			accountRepository.save(originalAccount);
			updatedAccount = findAccountByGroupId(accountDetailDto.getGroupId());

		} catch (AccountNotFoundException ex) {
			logger.printError(AccountServiceImpl.class, ex.getMessage());
		}
		return updatedAccount;
	}

	@Override
	public boolean isAccountExists(AccountDetail accountDetail) {
		return accountRepository.existsById(accountDetail.getAccountId());
	}

	@Override
	public List<AccountDetail> findAllAccount() {
		return accountRepository.findAll();
	}

	@Override
	public List<AccountDetailDto> deleteAccountById(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> updatedAccount = null;
		try {
			if (!accountRepository.existsById(accountDetailDto.getAccountId())) {
				throw new AccountNotFoundException("Account not exist. Account Details =====>>" + accountDetailDto);
			}
			accountRepository.deleteById(accountDetailDto.getAccountId());
			updatedAccount = findAccountByGroupId(accountDetailDto.getGroupId());
		} catch (AccountNotFoundException ex) {
			logger.printError(AccountServiceImpl.class, ex.getMessage());
		}
		return updatedAccount;
	}

	@Override
	public AccountDetailDto findAccountByAccountId(int accountId) {
		AccountDetailDto accountDetailDto = null;
		try {
			Optional<AccountDetail> option = accountRepository.findById(accountId);
			accountDetailDto = modelMapper.map(option.orElseThrow(AccountNotFoundException::new),
					AccountDetailDto.class);
		} catch (AccountNotFoundException ex) {
			logger.printError(AccountServiceImpl.class, ex.getMessage());
		}
		return accountDetailDto;
	}

	@Override
	public boolean isAlreadyMappedAccountWithGroup(String accountName, int groupFk) {
		return accountRepository.countAccountByGroupIdAndAccountName(accountName, groupFk) != null;
	}

}
