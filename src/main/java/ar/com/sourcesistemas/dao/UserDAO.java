package ar.com.sourcesistemas.dao;

import java.util.List;

import ar.com.sourcesistemas.model.User;

public interface UserDAO {
	public List<User> list();

	void saveUser(User user);

	public User getUsernameByName(String username);
}
