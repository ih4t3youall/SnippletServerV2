package ar.com.sourcesistemas.dao;

import java.util.List;

import ar.com.sourcesistemas.model.Snipplet;

public interface SnippletDAO {

	
	List<Snipplet> getSnippletsByCategory(long categoryId);
	
}
