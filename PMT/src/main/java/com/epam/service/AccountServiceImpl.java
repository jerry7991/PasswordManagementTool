package com.epam.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.api.AccountService;
import com.epam.dto.AccountDetailDto;
import com.epam.entities.AccountDetail;
import com.epam.entities.GroupDetails;
import com.epam.exceptions.AccountMappingWithGroupException;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.repositories.AccountRepository;
import com.epam.repositories.GroupRepository;
import com.epam.util.Constants;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private GroupRepository groupRepository;

	private final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean addAccount(AccountDetailDto accountDetailDto)
			throws GroupNotFoundException, AccountMappingWithGroupException {
		if (isAlreadyMappedAccountWithGroup(accountDetailDto.getAccountName(), accountDetailDto.getGroupId())) {
			throw new AccountMappingWithGroupException("Account Allready mapped with given group.");
		}
		logger.info("User Validated for account");
		Optional<GroupDetails> option = groupRepository.findById(accountDetailDto.getGroupId());
		GroupDetails groupDetails = option.orElseThrow(() -> new GroupNotFoundException("Group Not Exist."));
		AccountDetail accountDetail = modelMapper.map(accountDetailDto, AccountDetail.class);
		accountDetail.setGroupDetails(groupDetails);
		groupDetails.getAccounts().add(accountDetail);
		return groupRepository.save(groupDetails) != null;
	}

	@Override
	public List<AccountDetailDto> findAccountByGroupId(int groupId) {
		List<AccountDetail> accountDetails = accountRepository.findAccountByGroupId(groupId);
		return accountDetails.stream().map(account -> modelMapper.map(account, AccountDetailDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public boolean updateAccount(AccountDetailDto accountDetailDto) throws AccountNotFoundException {
		Optional<AccountDetail> option = accountRepository.findById(accountDetailDto.getAccountId());
		AccountDetail originalAccount = option
				.orElseThrow(() -> new AccountNotFoundException(Constants.ACCOUNT_NOT_EXIST));
		originalAccount.setAccountName(accountDetailDto.getAccountName());
		originalAccount.setUrl(accountDetailDto.getUrl());
		originalAccount.setPassword(accountDetailDto.getPassword());
		return accountRepository.save(originalAccount) != null;
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
	public boolean deleteAccountById(AccountDetailDto accountDetailDto) throws AccountNotFoundException {
		if (!accountRepository.existsById(accountDetailDto.getAccountId())) {
			throw new AccountNotFoundException("Account not exist.");
		}
		accountRepository.deleteById(accountDetailDto.getAccountId());
		return !accountRepository.existsById(accountDetailDto.getGroupId());
	}

	@Override
	public AccountDetailDto findAccountByAccountId(int accountId) throws AccountNotFoundException {
		AccountDetailDto accountDetailDto = null;
		Optional<AccountDetail> option = accountRepository.findById(accountId);
		accountDetailDto = modelMapper.map(
				option.orElseThrow(() -> new AccountNotFoundException(Constants.ACCOUNT_NOT_EXIST)),
				AccountDetailDto.class);
		return accountDetailDto;
	}

	@Override
	public boolean isAlreadyMappedAccountWithGroup(String accountName, int groupId) {
		return accountRepository.countAccountByGroupIdAndAccountName(accountName, groupId)
				.compareTo(BigInteger.valueOf(0)) > 0;
	}
}
