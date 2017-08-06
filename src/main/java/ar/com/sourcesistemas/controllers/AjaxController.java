package ar.com.sourcesistemas.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ar.com.sourcesistemas.dao.SnippletDAO;
import ar.com.sourcesistemas.model.Snipplet;
import ar.com.sourcesistemas.utilities.GsonUtility;

@RestController
public class AjaxController {
	
	@Autowired
	private SnippletDAO snippletDAO;
	
	@Autowired
	private GsonUtility gsonUtility;

	@RequestMapping("getSnipplets")
	@Transactional
	public ModelAndView getSnipplets(String categoryId) {
		
		List<Snipplet> snipplets = snippletDAO.getSnippletsByCategory(Integer.parseInt(categoryId));
		ModelAndView mav = new ModelAndView("components/snippletComponent");
		mav.addObject("snipplets",snipplets);
		
		return mav;
		
	}
	
	
	@RequestMapping("getModal")
	@Transactional
	public ModelAndView getModal(String snippletId) {
		
		ModelAndView mav = new ModelAndView("modal/modalEditarSnipplet");
		Snipplet snipplet = snippletDAO.getSnippletById(Long.parseLong(snippletId));
		mav.addObject("snipplet",snipplet);
		
		return mav;
		
	}
	@Transactional
	@RequestMapping(value ="saveEditedSnipplet" , method = RequestMethod.POST)
	public String saveEditedSnipplet(String jsonSnipplet) {
		
		 Snipplet snippletAux = gsonUtility.getGsonWithExclusion().fromJson(jsonSnipplet, Snipplet.class);
		 Snipplet snipplet = snippletDAO.getSnippletById(snippletAux.getId());
		 
		 snipplet.setContenido(snippletAux.getContenido());
		 snipplet.setTitulo(snippletAux.getTitulo());
		 
		snippletDAO.saveSnipplet(snipplet);
		return "200ok";
		 
		 
		
	}
	
	
	
}
