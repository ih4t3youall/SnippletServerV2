package ar.com.sourcesistemas.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.User;


public class CategoryDAOImpl implements CategoryDAO {

	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Category> getCategoryByUserId(long userId) {

		String sql = "from Category where user_id = :userId";
		
		Query hibernateQuery = sessionFactory.getCurrentSession().createQuery(sql);
		hibernateQuery.setParameter("userId", userId);
		List<Category> category = hibernateQuery.list();
		return category;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
