package ar.com.sourcesistemas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import ar.com.sourcesistemas.model.Snipplet;

public class SnippletDAOImpl implements SnippletDAO {

	private SessionFactory sessionFactory;

	@Override
	public List<Snipplet> getSnippletsByCategory(long categoryId) {
		String sql = "from Snipplet where categoria_id = :categoryId";
		Query hibernateQuery = sessionFactory.getCurrentSession().createQuery(sql);
		hibernateQuery.setParameter("categoryId", categoryId);
		List<Snipplet> snipplets =  hibernateQuery.list();
		
		return snipplets;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
