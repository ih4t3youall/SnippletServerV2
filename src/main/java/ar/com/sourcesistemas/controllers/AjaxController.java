package ar.com.sourcesistemas.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ar.com.sourcesistemas.dao.SnippletDAO;
import ar.com.sourcesistemas.model.Snipplet;

@RestController
public class AjaxController {
	
	@Autowired
	private SnippletDAO snippletDAO;

	@RequestMapping("getSnipplets")
	@Transactional
	public ModelAndView getSnipplets(String categoryId) {
		
		List<Snipplet> snipplets = snippletDAO.getSnippletsByCategory(Integer.parseInt(categoryId));
		ModelAndView mav = new ModelAndView("components/snippletComponent");
		mav.addObject("snipplets",snipplets);
		
		return mav;
		
	}
	
	
}
