package ar.com.sourcesistemas.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import ar.com.sourcesistemas.model.User;

public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<User> list() {
		@SuppressWarnings("unchecked")
		List<User> listUser = (List<User>) sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		return listUser;
	}
	
	@Transactional
	@Override
	public void saveUser(User user) {
		
		
		sessionFactory.getCurrentSession().save(user);
		
	}

	@Override
	@Transactional
	public User getUsernameByName(String username) {
		
		String query = "from User where name = :username";
		Query hibernateQuery = sessionFactory.getCurrentSession().createQuery(query);
		hibernateQuery.setParameter("username", username);
		List<User> users = hibernateQuery.list();
		return users.get(0);
		
		
	}

}
