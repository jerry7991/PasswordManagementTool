package com.epam.service_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.api.Validation;
import com.epam.dto.AccountDetailDto;
import com.epam.dto.GroupDetailsDto;
import com.epam.entities.AccountDetail;
import com.epam.entities.GroupDetails;
import com.epam.exceptions.AccountMappingWithGroupException;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.repositories.AccountRepository;
import com.epam.repositories.GroupRepository;
import com.epam.service.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

	@Mock
	private AccountRepository accountRepository;
	@Mock
	private GroupRepository groupRepository;

	@Mock
	private Validation validation;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AccountServiceImpl accountServiceImpl;

	public static GroupDetailsDto groupDetails;
	public static AccountDetailDto accountDetailDto;
	private ModelMapper modelMappers;

	@BeforeEach
	public void init() {
		modelMappers = new ModelMapper();
		groupDetails = new GroupDetailsDto();
		groupDetails.setGroupId(1);
		groupDetails.setGroupName("google");
		accountDetailDto = new AccountDetailDto();
		accountDetailDto.setAccountId(2);
		accountDetailDto.setAccountName("gmail");
		accountDetailDto.setUrl("https://gmail.com");
		accountDetailDto.setPassword("Gmail@1234");
		List<AccountDetailDto> accounts = new ArrayList<>();
		accounts.add(accountDetailDto);
		groupDetails.setAccountsDto(accounts);
	}

	@Test
	void testAddAccount() throws GroupNotFoundException, AccountMappingWithGroupException {
		when(accountRepository.countAccountByGroupIdAndAccountName(accountDetailDto.getAccountName(),
				accountDetailDto.getGroupId())).thenReturn(BigInteger.valueOf(0));
		Optional<GroupDetailsDto> optional = Optional.of(groupDetails);
		Optional<GroupDetails> optionalGroup = Optional.of(modelMappers.map(optional.get(), GroupDetails.class));
		when(groupRepository.findById(accountDetailDto.getGroupId())).thenReturn(optionalGroup);
		when(modelMapper.map(accountDetailDto, AccountDetail.class))
				.thenReturn(modelMappers.map(accountDetailDto, AccountDetail.class));
		when(groupRepository.save(any(GroupDetails.class))).thenReturn(new GroupDetails());
		assertTrue(accountServiceImpl.addAccount(accountDetailDto));
	}

	@Test
	void testAddAccountDuplicateAccount() {
		when(accountRepository.countAccountByGroupIdAndAccountName(accountDetailDto.getAccountName(),
				accountDetailDto.getGroupId())).thenReturn(BigInteger.valueOf(1));
		AccountMappingWithGroupException exception = assertThrows(AccountMappingWithGroupException.class, () -> {
			accountServiceImpl.addAccount(accountDetailDto);
		});
		assertEquals("Account Allready mapped with given group.", exception.getMessage());
	}

	@Test
	void testFindAccountByGroupId() {
		when(accountRepository.findAccountByGroupId(1)).thenReturn(groupDetails.getAccountsDto().stream()
				.map(accounts -> modelMappers.map(accounts, AccountDetail.class)).collect(Collectors.toList()));
		assertNotNull(accountServiceImpl.findAccountByGroupId(1));
	}

	@Test
	void testUpdateAccount() throws AccountNotFoundException {
		AccountDetailDto newAccount = new AccountDetailDto();
		newAccount.setAccountId(1);
		newAccount.setAccountName("updated");
		newAccount.setPassword("12345");
		newAccount.setUrl("https://gma.com");
		newAccount.setGroupId(2);

		AccountDetail accountDetailPrev = modelMappers.map(groupDetails.getAccountsDto().get(0), AccountDetail.class);

		when(accountRepository.findById(1)).thenReturn(Optional.of(accountDetailPrev));
		accountServiceImpl.updateAccount(newAccount);
		assertEquals(accountDetailPrev.getAccountName(), newAccount.getAccountName());
	}

	@Test
	void testUpdateAccountEx() throws AccountNotFoundException {
		when(accountRepository.findById(2)).thenReturn(Optional.empty());
		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
			accountServiceImpl.updateAccount(accountDetailDto);
		});
		assertEquals("Account not exist.", exception.getMessage());
	}

	@Test
	void testIsAccountExists() {
		when(accountRepository.existsById(2)).thenReturn(true);
		assertTrue(accountServiceImpl.isAccountExists(modelMappers.map(accountDetailDto, AccountDetail.class)));
	}

	@Test
	void testFindAllAccount() {
		when(accountRepository.findAll()).thenReturn(groupDetails.getAccountsDto().stream()
				.map(accounts -> modelMappers.map(accounts, AccountDetail.class)).collect(Collectors.toList()));
		assertNotNull(accountServiceImpl.findAllAccount());
	}

	@Test
	void testDeleteAccountById() throws AccountNotFoundException {
		when(accountRepository.existsById(accountDetailDto.getAccountId())).thenReturn(true);
		assertTrue(accountServiceImpl.deleteAccountById(accountDetailDto));
	}

	@Test
	void testDeleteAccountByIdExc() throws AccountNotFoundException {

		when(accountRepository.existsById(2)).thenReturn(false);

		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
			accountServiceImpl.deleteAccountById(accountDetailDto);
		});

		assertEquals("Account not exist.", exception.getMessage());
	}

	@Test
	void testFindAccountByAccountId() throws AccountNotFoundException {
		when(accountRepository.findById(2))
				.thenReturn(Optional.of(modelMappers.map(accountDetailDto, AccountDetail.class)));
		assertNull(accountServiceImpl.findAccountByAccountId(accountDetailDto.getAccountId()));
	}

	@Test
	void testFindAccountByAccountIdEx() {
		when(accountRepository.findById(2)).thenReturn(Optional.empty());
		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
			accountServiceImpl.findAccountByAccountId(accountDetailDto.getAccountId());
		});
		assertEquals("Account not exist.", exception.getMessage());
	}
}
