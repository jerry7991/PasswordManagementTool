package com.epam.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.epam.api.UserActivities;
import com.epam.exceptions.GroupAlreadyExistException;
import com.epam.factory.User;
import com.epam.model.AccountDetail;
import com.epam.model.FilterCredentials;
import com.epam.model.GroupDetails;
import com.epam.model.Response;
import com.epam.model.UserDetails;
import com.epam.singleton.Loggers;

@Component
public class UserActivityDao extends Master implements UserActivities {

	@Value(value = "#{entityFactory.getEntityManagerFactory()}")
	private EntityManagerFactory entityManagerFactory;
	private Loggers LOGGER;

	@Override
	public Response createPasswordAccount(AccountDetail accountDetail) {
		EntityManager entityManager = null;
		Response response;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			FilterCredentials filter = new FilterCredentials();
			entityManager.getTransaction().begin();
			filter.setUserName(User.userName);
			filter.setMasterPassword(User.password);
			filter.setGroupName(accountDetail.getGroupName());

			response = getGroup(entityManager, filter);

			if (response.getStatus()) {
				GroupDetails groupDetails = (GroupDetails) response.getMsg();
				entityManager.persist(groupDetails);
				List<AccountDetail> accounts = groupDetails.getAccounts();
				if (accounts == null) {
					accounts = new ArrayList<>();
				}
				accounts.add(accountDetail);
				groupDetails.setAccounts(accounts);
				entityManager.getTransaction().commit();
			} else {
				GroupDetails groupDetails = new GroupDetails();
				groupDetails.setGroupName(accountDetail.getGroupName());
				groupDetails.setAccounts(Arrays.asList(accountDetail));
				response = getUser(entityManager, filter);
				UserDetails userDetails = (UserDetails) response.getMsg();
				entityManager.persist(userDetails);

				List<GroupDetails> prevGroup = userDetails.getGroupDetails();
				prevGroup.add(groupDetails);

				userDetails.setGroupDetails(prevGroup);
				entityManager.getTransaction().commit();
			}

			response = new Response(true, "Inserted successfully.");
		} catch (Exception ex) {
			LOGGER.printError(UserActivityDao.class, "Error in inserting");
			ex.printStackTrace();
			response = new Response(false, "Account addition faild.");
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return response;
	}

	@Override
	public Response addUser(UserDetails userDetails) {
		EntityManager entityManager = null;
		Response response;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(userDetails);
			entityManager.getTransaction().commit();
			response = new Response(true, "Inserted successfully.");
		} catch (Exception ex) {
			LOGGER.printError(UserActivityDao.class, ex.getMessage());
			ex.printStackTrace();
			response = new Response(false, "Account addition faild.");
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return response;
	}

	@Override
	public Response modifyPasswordAccount(FilterCredentials filter, AccountDetail newDetails) {

		Response response;
		EntityManager entityManager = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();

			response = getAccountDetails(entityManager, filter);
			if (response.getStatus()) {
				int id = ((AccountDetail) response.getMsg()).getAccountId();
				AccountDetail accountDetail = (AccountDetail) response.getMsg();
				entityManager.persist(accountDetail);
				accountDetail.setAccountName(newDetails.getAccountName().length() != 0 ? newDetails.getAccountName()
						: accountDetail.getAccountName());
				accountDetail.setPassword(newDetails.getPassword().length() != 0 ? newDetails.getPassword()
						: accountDetail.getPassword());
				accountDetail.setUrl(newDetails.getUrl().length() != 0 ? newDetails.getUrl() : accountDetail.getUrl());
				entityManager.getTransaction().commit();
				response.setMsg("Account Updated Successfully.");
			}

		} catch (Exception ex) {
			LOGGER.printError(UserActivityDao.class, ex.getMessage());
			ex.printStackTrace();
			response = new Response(false, "Account addition faild.");
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		return response;
	}

	@Override
	public Response modifyGroupName(FilterCredentials filter, String newName) {
		Response response = null;
		EntityManager entityManager = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			BigInteger alreadExist = (BigInteger) entityManager
					.createNativeQuery("select count(*) from group_details where group_name=?").setParameter(1, newName)
					.getSingleResult();
			if (alreadExist.compareTo(new BigInteger("0")) > 0) {
				throw new GroupAlreadyExistException(newName + " already exist in same group");
			}

			response = getGroup(entityManager, filter);
			if (response.getStatus()) {
				GroupDetails groupDetails = (GroupDetails) response.getMsg();
				entityManager.persist(groupDetails);
				groupDetails.setGroupName(newName);
				entityManager.getTransaction().commit();
				response = new Response(true, "Group Name Updated");
			}
		} catch (Exception ex) {
			LOGGER.printError(UserActivityDao.class, ex.getMessage());
			ex.printStackTrace();
			response = new Response(false, "Account addition faild." + ex.getMessage());
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return response;
	}

	@Override
	public Response deleteGroup(FilterCredentials filter) {
		Response response = null;
		EntityManager entityManager = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			response = getGroup(entityManager, filter);
			if (response.getStatus()) {
				GroupDetails groupDetails = (GroupDetails) response.getMsg();
				entityManager.remove(groupDetails);
				entityManager.getTransaction().commit();
				response = new Response(true, "Group deleted.");
			}
		} catch (Exception ex) {
			LOGGER.printError(UserActivityDao.class, ex.getMessage());
			ex.printStackTrace();
			response = new Response(false, "Deletion faild.");
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		return response;
	}

	@Override
	public Response getAllDetails(FilterCredentials filter) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Response response = getAllUser(entityManager, filter);
		if (entityManager != null) {
			entityManager.close();
		}
		return response;
	}

	@Override
	public Response viewAccountFilter(FilterCredentials filter) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Response response = filter.getAccountName().equals("*") ? getAllAccount(entityManager, filter)
				: getAccountDetails(entityManager, filter);
		if (entityManager != null) {
			entityManager.close();
		}
		return response;
	}

	@Override
	public Response deletePasswordAccount(FilterCredentials filter) {
		Response response;
		EntityManager entityManager = null;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();

			response = getAccountDetails(entityManager, filter);
			if (response.getStatus()) {
				AccountDetail accontDetail = (AccountDetail) response.getMsg();
				entityManager.persist(accontDetail);
				entityManager.remove(accontDetail);
				entityManager.getTransaction().commit();
				response.setMsg("Account Deleted Successfully.");
			} else {
				response.setMsg("Account Doesn't exist.");
			}

		} catch (Exception ex) {
			LOGGER.printError(UserActivityDao.class, ex.getMessage());
			ex.printStackTrace();
			response = new Response(false, "Account Deletion faild.");
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}

		return response;
	}

	@Override
	public void close() {
		entityManagerFactory.close();
	}

}
