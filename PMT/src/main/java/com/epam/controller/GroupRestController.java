package com.epam.controller;

import java.util.Arrays;
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

import com.epam.api.GroupService;
import com.epam.dto.GroupDetailsDto;
import com.epam.dto.UserData;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.exceptions.GroupNotFoundException;

@RestController
@RequestMapping("/pmt/groups")
public class GroupRestController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserData userData;

	@GetMapping("/{userId}")
	public ResponseEntity<List<GroupDetailsDto>> getGroupDetails(@PathVariable int userId) {
		userData.setId(userId);
		List<GroupDetailsDto> groupDetails = groupService.getAllGroup();
		return new ResponseEntity<>(groupDetails, HttpStatus.OK);
	}

	@PostMapping("/addGroup")
	public ResponseEntity<Boolean> addGroup(String groupName, int userId) throws GroupAlreadyExistException {
		userData.setId(userId);
		boolean isAdded = groupService.addGroup(groupName);
		return new ResponseEntity<>(isAdded, isAdded ? HttpStatus.OK : HttpStatus.CONFLICT);
	}

	@PutMapping("/updateGroup")
	public ResponseEntity<Boolean> updateGroupName(@RequestBody GroupDetailsDto groupDetailsDto)
			throws GroupAlreadyExistException {
		userData.setId(groupDetailsDto.getUserId());
		boolean response = groupService.modifyGroupName(groupDetailsDto.getGroupId(), groupDetailsDto.getGroupName());
		return new ResponseEntity<>(response, response ? HttpStatus.OK : HttpStatus.CONFLICT);
	}

	@DeleteMapping("/deleteGroup/{groupId}")
	public ResponseEntity<Boolean> deleteGroup(@PathVariable int groupId) throws GroupNotFoundException {
		boolean isDeleted = groupService.deleteGroup(groupId);
		return new ResponseEntity<>(isDeleted, isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping("/search")
	public ResponseEntity<List<GroupDetailsDto>> searchGroup(int userId, String groupName)
			throws GroupNotFoundException {
		userData.setId(userId);
		List<GroupDetailsDto> groupDetailsDto = Arrays.asList(groupService.getGroupByName(groupName));
		return new ResponseEntity<>(groupDetailsDto, HttpStatus.OK);
	}
}
