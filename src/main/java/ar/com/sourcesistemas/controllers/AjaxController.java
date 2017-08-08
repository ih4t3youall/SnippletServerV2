package ar.com.sourcesistemas.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ar.com.sourcesistemas.dao.SnippletDAO;
import ar.com.sourcesistemas.dao.UserDAO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.Snipplet;
import ar.com.sourcesistemas.model.User;
import ar.com.sourcesistemas.utilities.GsonUtility;

@RestController
public class AjaxController {
	
	@Autowired
	private SnippletDAO snippletDAO;
	
	@Autowired
	private UserDAO userDAO;
	
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
	
	@Transactional
	@RequestMapping(value ="createNewSnipplet", method =RequestMethod.POST)
	public String createNewSnipplet(String jsonSnipplet,String categoryId ) {
		
		Snipplet snippletAux = gsonUtility.getGsonWithExclusion().fromJson(jsonSnipplet, Snipplet.class);
		//FIXME when i have spring security
		User user = userDAO.getUsernameByName("martin");
		
		long fixedId = Long.parseLong(categoryId);
		
		for( Category category : user.getCategory()) {
			
			if(category.getId() == fixedId) {
				snippletAux.setCategoria(category);
				category.getSnipplets().add(snippletAux);
				break;
			}
			
		}
		
		userDAO.saveUser(user);
		return "200ok";
		
		
	}
	
	@Transactional
	@RequestMapping(value = "getModalAddSnipplet", method = RequestMethod.GET)
	public ModelAndView getModalAddSnipplet() {
		
		ModelAndView mav = new ModelAndView("modal/addSnipplet");
		return mav;
		
	}
	
	@Transactional
	@RequestMapping(value = "eliminarSnipplet", method = RequestMethod.POST)
	public String eliminarSnipplet(String jsonSnipplet,String categoryId) {
		Snipplet snippletAux = gsonUtility.getGsonWithExclusion().fromJson(jsonSnipplet, Snipplet.class);
		
		long fixedId = Long.parseLong(categoryId);
		
		//FIXME spring security
		User user = userDAO.getUsernameByName("martin");
		
		boolean isUser = false;
		
		for (Category category :user.getCategory()) {
			
			if(category.getId().equals(fixedId)) {
				
				for (Snipplet snipplet : category.getSnipplets()) {
					
					if(snipplet.getId().equals(snippletAux.getId())) {
						
						isUser =true;
						break;
						
					}
					
				}
				
				if(isUser)
					break;
				
			}
			
		}
		
		if(isUser)
			snippletDAO.removeSnipplet(snippletAux.getId());
		
		
		return null;
		
		
		
	}
	
	
	
}
