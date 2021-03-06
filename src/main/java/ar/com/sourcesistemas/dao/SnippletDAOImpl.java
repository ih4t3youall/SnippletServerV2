package ar.com.sourcesistemas.dao;

import ar.com.commons.send.domain.Snipplet;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

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
	
	@Override
	public Snipplet getSnippletById(long snippletId) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Snipplet.class).add(Restrictions.eq("id", snippletId));
		return (Snipplet)crit.list().get(0);
	}
	
	@Override
	public void saveSnipplet(Snipplet snipplet) {
		sessionFactory.getCurrentSession().save(snipplet);
		
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void removeSnipplet(Long id) {
		Snipplet snippletById = getSnippletById(id);
		this.sessionFactory.getCurrentSession().delete(snippletById);
		
	}

	


}
