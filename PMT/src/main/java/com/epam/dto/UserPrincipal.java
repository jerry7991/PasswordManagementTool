package com.epam.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.epam.entities.AuthGroup;
import com.epam.entities.UserDetails;

public class UserPrincipal implements org.springframework.security.core.userdetails.UserDetails {

	private static final long serialVersionUID = 1L;
	private UserDetails userDetails;

	private List<AuthGroup> authGroups;

	public UserPrincipal(UserDetails userDetails2, List<AuthGroup> authGroups) {
		super();
		this.userDetails = userDetails2;
		this.authGroups = authGroups;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public List<AuthGroup> getAuthGroups() {
		return authGroups;
	}

	public void setAuthGroups(List<AuthGroup> authGroups) {
		this.authGroups = authGroups;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Optional<List<AuthGroup>> optionalAuthGroups = Optional.of(authGroups);

		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

		if (optionalAuthGroups.isPresent()) {
			authGroups.forEach(group -> {
				grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthGroup()));
			});
		}

		System.out.println(grantedAuthorities);

		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.userDetails.getMasterPassword();
	}

	@Override
	public String getUsername() {
		return this.userDetails.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
