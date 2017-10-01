package ar.com.sourcesistemas.authenticationmanager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.sourcesistemas.dao.UserDAO;
import ar.com.sourcesistemas.model.User;

public class CustomUserDetailsService implements UserDetailsService {

	private UserDAO userDao;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		if (userDao == null) {

			user = new User();
			user.setActive(1);
			user.setName("martin");
			user.setPassword("artemisa21");
			System.out.println("userDAO = null");

		} else {
			user = userDao.getUsernameByName(username);
			System.out.println("user loaded from database");
		}

		System.out.println("User : " + user);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true,
				true, true, getGrantedAuthorities(user));
	}

	// private List<GrantedAuthority> getGrantedAuthorities(User user){
	// List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	//
	// for(UserProfile userProfile : user.getUserProfiles()){
	// System.out.println("UserProfile : "+userProfile);
	// authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
	// }
	// System.out.print("authorities :"+authorities);
	// return authorities;
	// }

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		System.out.print("authorities :" + authorities);
		return authorities;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}