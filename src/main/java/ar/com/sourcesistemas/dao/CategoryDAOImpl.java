package ar.com.sourcesistemas.dao;

import ar.com.commons.send.domain.Category;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.List;



public class CategoryDAOImpl implements CategoryDAO {

	private SessionFactory sessionFactory;

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Category> getCategoryByUserId(long userId) {

		String sql = "from Category where user_id = :userId";
		Query hibernateQuery = sessionFactory.getCurrentSession().createQuery(sql);
		hibernateQuery.setParameter("userId", userId);
		List<Category> category = hibernateQuery.list();
		return category;
	}

	@Override
	@Transactional
	public void saveCategory(Category category){
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(category);

	}
	@Override
	@Transactional
	public void updateCategory(Category category){
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(category);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
