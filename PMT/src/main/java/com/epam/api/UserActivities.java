package com.epam.api;

import javax.persistence.EntityManager;

import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.Response;
import com.epam.model.UserDetails;

public interface UserActivities {

	public Response createPasswordAccount(AccountDetail accountDetail);

	public Response addUser(UserDetails userDetails);

	public Response modifyPasswordAccount(FilterCredentials filter, AccountDetail newDetails);

	public Response modifyGroupName(FilterCredentials filter, String newName);

	public Response viewAccountFilter(FilterCredentials filter);

	public Response deletePasswordAccount(FilterCredentials filter);

	public Response deleteGroup(FilterCredentials filter);

	public Response getUser(EntityManager entityManager, String userName, String passWord);

	public Response getAllDetails(FilterCredentials filter);

	public void close();

}
