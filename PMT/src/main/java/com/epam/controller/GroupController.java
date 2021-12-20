package com.epam.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.api.GroupService;
import com.epam.dto.GroupDetailsDto;
import com.epam.util.Constants;

@Controller
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping(value = "viewGroupBy")
	public ModelAndView getGroupDetails() {
		List<GroupDetailsDto> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.GROUP_CONTROLER);
		modelAndView.addObject(Constants.GROUP_RESPONSE, groupDetails);
		return modelAndView;
	}

	@PostMapping("addGroup")
	public ModelAndView addGroup(String groupName) {
		boolean isAdded = groupService.addGroup(groupName);
		List<GroupDetailsDto> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.GROUP_CONTROLER);
		modelAndView.addObject(Constants.GROUP_RESPONSE, groupDetails);
		modelAndView.addObject("response", isAdded ? "Group Added" : "Group Addition failed");
		return modelAndView;
	}

	@PostMapping("UpdateGroupName")
	public ModelAndView updateGroupName(String groupName, int groupId) {
		groupService.modifyGroupName(groupId, groupName);
		List<GroupDetailsDto> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.GROUP_CONTROLER);
		modelAndView.addObject(Constants.GROUP_RESPONSE, groupDetails);
		return modelAndView;
	}

	@GetMapping("deleteGroup")
	public ModelAndView deleteGroup(String groupName, int groupId) {
		boolean isDeleted = groupService.deleteGroup(groupId, groupName);
		List<GroupDetailsDto> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.GROUP_CONTROLER);
		modelAndView.addObject(Constants.GROUP_RESPONSE, groupDetails);
		modelAndView.addObject("response", isDeleted ? "Deleted Successfully" : "Deletion Failed");
		return modelAndView;
	}

	@PostMapping("searchGroup")
	public ModelAndView searchGroup(String groupName) {
		List<GroupDetailsDto> groupDetailsDto = Arrays.asList(groupService.getGroupByName(groupName));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.GROUP_CONTROLER);
		modelAndView.addObject(Constants.GROUP_RESPONSE, groupDetailsDto);
		return modelAndView;
	}
}
