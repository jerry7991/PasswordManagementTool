package com.epam.service_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
import com.epam.dto.Response;
import com.epam.entities.AccountDetail;
import com.epam.entities.GroupDetails;
import com.epam.repositories.AccountRepository;
import com.epam.repositories.GroupRepository;
import com.epam.service.AccountServiceImpl;
import com.epam.util.Loggers;

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

	@Mock
	private Loggers LOGGER;

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
	void testAddAccount() {
		when(validation.isAccountValid(accountDetailDto)).thenReturn(new Response(true, "valid"));
		Optional<GroupDetailsDto> optional = Optional.of(groupDetails);
		Optional<GroupDetails> optionalGroup = Optional.of(modelMappers.map(optional.get(), GroupDetails.class));
		when(groupRepository.findById(accountDetailDto.getGroupId())).thenReturn(optionalGroup);
		when(modelMapper.map(accountDetailDto, AccountDetail.class))
				.thenReturn(modelMappers.map(accountDetailDto, AccountDetail.class));
		assertNotNull(accountServiceImpl.addAccount(accountDetailDto));
	}

	@Test
	void testAddAccountEx() {
		when(validation.isAccountValid(accountDetailDto)).thenReturn(new Response(false, "Invalid"));
		assertEquals(0, accountServiceImpl.addAccount(accountDetailDto).size());
	}

	@Test
	void testFindAccountByGroupId() {
		when(accountRepository.findAccountByGroupId(1)).thenReturn(groupDetails.getAccountsDto().stream()
				.map(accounts -> modelMappers.map(accounts, AccountDetail.class)).collect(Collectors.toList()));
		assertNotNull(accountServiceImpl.findAccountByGroupId(1));
	}

	@Test
	void testUpdateAccount() {
		AccountDetailDto newAccount = new AccountDetailDto();
		newAccount.setAccountId(1);
		newAccount.setAccountName("updated");
		newAccount.setPassword("12345");
		newAccount.setUrl("https://gma.com");
		newAccount.setGroupId(2);

		AccountDetail accountDetailPrev = modelMappers.map(groupDetails.getAccountsDto().get(0), AccountDetail.class);

		when(accountRepository.findById(1)).thenReturn(Optional.of(accountDetailPrev));
		when(accountRepository.findAccountByGroupId(2)).thenReturn(groupDetails.getAccountsDto().stream()
				.map(accounts -> modelMappers.map(accounts, AccountDetail.class)).collect(Collectors.toList()));
		accountServiceImpl.updateAccount(newAccount);
		assertEquals(accountDetailPrev.getAccountName(), newAccount.getAccountName());
	}

	@Test
	void testUpdateAccountEx() {
		when(accountRepository.findById(2)).thenReturn(Optional.empty());

		assertNull(accountServiceImpl.updateAccount(accountDetailDto));
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
	void testDeleteAccountById() {
		when(accountRepository.existsById(accountDetailDto.getAccountId())).thenReturn(true);
		assertNotNull(accountServiceImpl.deleteAccountById(accountDetailDto));
	}

	@Test
	void testDeleteAccountByIdExc() {

		when(accountRepository.existsById(2)).thenReturn(false);

		assertNull(accountServiceImpl.deleteAccountById(accountDetailDto));
	}

	@Test
	void testFindAccountByAccountId() {
		when(accountRepository.findById(2))
				.thenReturn(Optional.of(modelMappers.map(accountDetailDto, AccountDetail.class)));
		assertNull(accountServiceImpl.findAccountByAccountId(accountDetailDto.getAccountId()));
	}

	@Test
	void testFindAccountByAccountIdEx() {
		when(accountRepository.findById(2)).thenReturn(Optional.empty());
		assertNull(accountServiceImpl.findAccountByAccountId(accountDetailDto.getAccountId()));
	}
}
