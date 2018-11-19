package ar.com.sourcesistemas.dao;


import ar.com.commons.send.domain.User;

import java.util.List;

public interface UserDAO {
	public List<User> list();

	void saveUser(User user);

	public User getUsernameByName(String username);

	public void update(User user);


	void cambiarPassword(String username, String passwd);
}
