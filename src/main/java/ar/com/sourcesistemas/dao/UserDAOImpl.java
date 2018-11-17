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
	
	@Override
	@Transactional
	public void saveUser(User user) {
		
		sessionFactory.getCurrentSession().save(user);
		
	}

	@Override
	@Transactional(readOnly =true)
	public User getUsernameByName(String username) {
		
		String query = "from User where name = :username";
		Query hibernateQuery = null; 
		try {
		hibernateQuery = sessionFactory.getCurrentSession().createQuery(query);
//		hibernateQuery = sessionFactory.openSession().createQuery(query);
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		hibernateQuery.setParameter("username", username);
		List<User> users = hibernateQuery.list();
		//FIXME always get () ?? wtf
		return users.get(0);
		
		
	}

	@Override
	@Transactional
	public void update(User user) {
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	@Transactional
	public void cambiarPassword(String username, String passwd) {

		User user =getUsernameByName(username);
		user.setPassword(passwd);
		sessionFactory.getCurrentSession().save(user);
	}

}
