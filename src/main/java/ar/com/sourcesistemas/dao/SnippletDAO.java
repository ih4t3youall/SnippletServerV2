package ar.com.sourcesistemas.dao;

import ar.com.commons.send.domain.Snipplet;

import java.util.List;

public interface SnippletDAO {

	
	List<Snipplet> getSnippletsByCategory(long categoryId);
	Snipplet getSnippletById(long snippletId);
	void saveSnipplet(Snipplet snipplet);
	void removeSnipplet(Long id);
	
}
