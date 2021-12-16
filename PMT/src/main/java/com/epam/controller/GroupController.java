package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.api.GroupService;
import com.epam.entities.GroupDetails;

@Controller
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping(value = "viewGroupBy")
	public ModelAndView getGroupDetails() {
		List<GroupDetails> groupDetails = groupService.getAllGroup();

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("viewGroupBy");
		modelAndView.addObject("GroupDetails", groupDetails);
		return modelAndView;
	}

	@PostMapping("addGroup")
	public ModelAndView addGroup(String groupName) {
		boolean isAdded = groupService.addGroup(groupName);
		List<GroupDetails> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewGroupBy");
		modelAndView.addObject("GroupDetails", groupDetails);
		modelAndView.addObject("response", isAdded ? "Group Added" : "Group Addition failed");
		return modelAndView;
	}

	@PostMapping("UpdateGroupName")
	public ModelAndView updateGroupName(String groupName, int groupId) {
		groupService.modifyGroupName(groupId, groupName);
		List<GroupDetails> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewGroupBy");
		modelAndView.addObject("GroupDetails", groupDetails);
		return modelAndView;
	}

	@GetMapping("deleteGroup")
	public ModelAndView deleteGroup(String groupName, int groupId) {
		boolean isDeleted = groupService.deleteGroup(groupId, groupName);
		List<GroupDetails> groupDetails = groupService.getAllGroup();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewGroupBy");
		modelAndView.addObject("GroupDetails", groupDetails);
		modelAndView.addObject("response", isDeleted ? "Deleted Successfully" : "Deletion Failed");
		return modelAndView;
	}
}
