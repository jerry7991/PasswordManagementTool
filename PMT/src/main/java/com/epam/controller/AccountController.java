package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.api.AccountService;
import com.epam.dto.AccountDetailDto;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "getAccount")
	public ModelAndView getAccounts(int groupId) {
		List<AccountDetailDto> accounts = accountService.findAccountByGroupId(groupId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("getAccount");
		modelAndView.addObject("accounts", accounts);
		modelAndView.addObject("groupId", groupId);
		return modelAndView;
	}

	@PostMapping("addAccount")
	public ModelAndView addAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> accounts = accountService.addAccount(accountDetailDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("getAccount");
		modelAndView.addObject("accounts", accounts);
		return modelAndView;
	}

	@PostMapping("updateAccount")
	public ModelAndView updateAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> updateAccounts = accountService.updateAccount(accountDetailDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("getAccount");
		modelAndView.addObject("accounts", updateAccounts);
		modelAndView.addObject("groupId", accountDetailDto.getGroupId());
		return modelAndView;
	}

	@PostMapping("deleteAccount")
	public ModelAndView deleteAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> updateAccounts = accountService.deleteAccountById(accountDetailDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("getAccount");
		modelAndView.addObject("accounts", updateAccounts);
		modelAndView.addObject("groupId", accountDetailDto.getGroupId());
		return modelAndView;
	}

	@GetMapping("showAccountByAccountID")
	public ModelAndView showAccount(int accountId, int groupId) {
		AccountDetailDto accountDetailDto = accountService.findAccountByAccountId(accountId);
		accountDetailDto.setGroupId(groupId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ShowUpdateAccount");
		modelAndView.addObject("account", accountDetailDto);
		return modelAndView;
	}
}
