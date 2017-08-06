package ar.com.sourcesistemas.dao;

import java.util.List;

import ar.com.sourcesistemas.model.Category;

public interface CategoryDAO {

	List<Category> getCategoryByUserId(long userID);
	
	
}
