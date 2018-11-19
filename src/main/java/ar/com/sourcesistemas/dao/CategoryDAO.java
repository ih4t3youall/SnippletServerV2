package ar.com.sourcesistemas.dao;

import ar.com.commons.send.domain.Category;

import java.util.List;

public interface CategoryDAO {

	List<Category> getCategoryByUserId(long userID);
	void saveCategory(Category category);
	void updateCategory(Category category);
	
}
