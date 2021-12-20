package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.api.AccountService;
import com.epam.dto.AccountDetailDto;
import com.epam.util.Constants;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "getAccount")
	public ModelAndView getAccounts(int groupId) {
		List<AccountDetailDto> accounts = accountService.findAccountByGroupId(groupId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.ACCOUNT_CONTROLER);
		modelAndView.addObject(Constants.ACCOUNT_RESPONSE, accounts);
		modelAndView.addObject(Constants.GROUP_ID, groupId);
		return modelAndView;
	}

	@PostMapping("addAccount")
	public ModelAndView addAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> accounts = accountService.addAccount(accountDetailDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.ACCOUNT_CONTROLER);
		modelAndView.addObject(Constants.ACCOUNT_RESPONSE, accounts);
		return modelAndView;
	}

	@PostMapping("updateAccount")
	public ModelAndView updateAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> updateAccounts = accountService.updateAccount(accountDetailDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.ACCOUNT_CONTROLER);
		modelAndView.addObject(Constants.ACCOUNT_RESPONSE, updateAccounts);
		modelAndView.addObject(Constants.GROUP_ID, accountDetailDto.getGroupId());
		return modelAndView;
	}

	@PostMapping("deleteAccount")
	public ModelAndView deleteAccount(AccountDetailDto accountDetailDto) {
		List<AccountDetailDto> updateAccounts = accountService.deleteAccountById(accountDetailDto);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.ACCOUNT_CONTROLER);
		modelAndView.addObject(Constants.ACCOUNT_RESPONSE, updateAccounts);
		modelAndView.addObject(Constants.GROUP_ID, accountDetailDto.getGroupId());
		return modelAndView;
	}

	@GetMapping("showAccountByAccountID")
	public ModelAndView showAccount(int accountId, int groupId) {
		AccountDetailDto accountDetailDto = accountService.findAccountByAccountId(accountId);
		accountDetailDto.setGroupId(groupId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ShowUpdateAccount");
		modelAndView.addObject(Constants.ACCOUNT_RESPONSE, accountDetailDto);
		return modelAndView;
	}
}
