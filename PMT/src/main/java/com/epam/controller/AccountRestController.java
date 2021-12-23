package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.api.AccountService;
import com.epam.dto.AccountDetailDto;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;

@RestController
@RequestMapping("pmt/groups/accounts")
public class AccountRestController {
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "/{groupId}")
	public ResponseEntity<List<AccountDetailDto>> getAccounts(@PathVariable int groupId) {
		List<AccountDetailDto> accounts = accountService.findAccountByGroupId(groupId);
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@PostMapping("addAccount")
	public ResponseEntity<Boolean> addAccount(@RequestBody AccountDetailDto accountDetailDto)
			throws GroupNotFoundException {
		boolean isAdded = accountService.addAccount(accountDetailDto);
		return new ResponseEntity<>(isAdded, isAdded ? HttpStatus.OK : HttpStatus.CONFLICT);
	}

	@PutMapping("updateAccount")
	public ResponseEntity<Boolean> updateAccount(@RequestBody AccountDetailDto accountDetailDto)
			throws AccountNotFoundException {
		boolean updated = accountService.updateAccount(accountDetailDto);
		return new ResponseEntity<>(updated, updated ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("deleteAccount")
	public ResponseEntity<Boolean> deleteAccount(@RequestBody AccountDetailDto accountDetailDto)
			throws AccountNotFoundException {
		boolean deleted = accountService.deleteAccountById(accountDetailDto);
		return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}

	@GetMapping("showAccountByAccountID")
	public ResponseEntity<AccountDetailDto> showAccount(int accountId, int groupId) throws AccountNotFoundException {
		AccountDetailDto accountDetailDto = accountService.findAccountByAccountId(accountId);
		return new ResponseEntity<>(accountDetailDto, HttpStatus.OK);
	}
}
