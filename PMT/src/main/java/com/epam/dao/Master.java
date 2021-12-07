package com.epam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.GroupDetails;
import com.epam.model.Response;
import com.epam.model.UserDetails;
import com.epam.singleton.Loggers;

public class Master {
	private Loggers LOGGER;

	public Master() {
		LOGGER = Loggers.getLogger();
	}

	@SuppressWarnings("unchecked")
	public Response getUser(EntityManager entityManager, FilterCredentials filter) {
		Response response;
		List<UserDetails> userDetails;
		try {
			Query query = entityManager.createNamedQuery("GetUser");
			query.setParameter("userName", filter.getUserName());
			query.setParameter("password", filter.getMasterPassword());
			userDetails = query.getResultList();

			if (userDetails != null && !userDetails.isEmpty()) {
				response = new Response(true, userDetails.get(0));
			} else {
				response = new Response(false, "User doesn't exist");
			}

		} catch (Exception e) {
			response = new Response(false, "Exception in while executing named query.");
			LOGGER.printError(Master.class, e.getMessage());
		}

		return response;
	}

	public Response getGroup(EntityManager entityManager, FilterCredentials filter) {
		Response response = getUser(entityManager, filter);
		List<GroupDetails> groupDetails;
		if (response.getStatus()) {
			entityManager.clear();
			UserDetails userDetails = (UserDetails) response.getMsg();
			Query query = entityManager.createNamedQuery("FetchGroup");
			query.setParameter("groupName", filter.getGroupName());
			groupDetails = query.getResultList();

			if (groupDetails != null && !groupDetails.isEmpty()) {
				response = new Response(true, groupDetails.get(0));
			} else {
				response = new Response(false, "Group doesn't exist");
			}
		}

		return response;
	}

	public Response getAccountDetails(EntityManager entityManager, FilterCredentials filter) {
		Response response = getGroup(entityManager, filter);
		if (response.getStatus()) {
			GroupDetails groupDetail = (GroupDetails) response.getMsg();

			int groupId = groupDetail.getGroupId();

			entityManager.clear();

			Query query = entityManager.createNativeQuery(
					"select * from account_details where account_name = ? and group_fk =?", AccountDetail.class);
			query.setParameter(1, filter.getAccountName());
			query.setParameter(2, groupId);
			AccountDetail accountDetail = (AccountDetail) query.getSingleResult();

			if (accountDetail != null) {
				response = new Response(true, accountDetail);
			} else {
				response = new Response(false, "Account doesn't exist");
			}
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public Response getUser(EntityManager entityManager, String userName, String passWord) {
		Response response;
		List<UserDetails> userDetails;
		try {
			Query query = entityManager.createNamedQuery("GetUser");
			query.setParameter("userName", userName);
			query.setParameter("password", passWord);
			userDetails = query.getResultList();

			System.out.println("UserDetails " + userDetails);

			if (userDetails != null && !userDetails.isEmpty()) {
				response = new Response(true, userDetails.get(0));
			} else {
				response = new Response(false, "User doesn't exist");
			}

		} catch (Exception e) {
			response = new Response(false, "Exception in while executing named query.");
			LOGGER.printError(Master.class, e.getMessage());
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	public Response getAllUser(EntityManager entityManager, FilterCredentials filter) {
		Response response;
		List<UserDetails> userDetails;
		try {
			Query query = entityManager.createNamedQuery("GetUser");
			query.setParameter("userName", filter.getUserName());
			query.setParameter("password", filter.getMasterPassword());
			userDetails = query.getResultList();

			System.out.println("UserDetails " + userDetails);

			if (userDetails != null && !userDetails.isEmpty()) {
				response = new Response(true, userDetails);
			} else {
				response = new Response(false, "User doesn't exist");
			}

		} catch (Exception e) {
			response = new Response(false, "Exception in while executing named query.");
			LOGGER.printError(Master.class, e.getMessage());
		}

		return response;
	}

	public Response getAllAccount(EntityManager entityManager, FilterCredentials filter) {
		Response response = getGroup(entityManager, filter);
		if (response.getStatus()) {
			GroupDetails groupDetail = (GroupDetails) response.getMsg();

			int groupId = groupDetail.getGroupId();

			entityManager.clear();

			Query query = entityManager.createNativeQuery("select * from account_details where group_fk =?",
					AccountDetail.class);
			query.setParameter(1, groupId);
			@SuppressWarnings("unchecked")
			List<AccountDetail> accountDetails = query.getResultList();

			if (accountDetails != null) {
				response = new Response(true, accountDetails);
			} else {
				response = new Response(false, "Account doesn't exist");
			}
		}
		return response;
	}

}
