package ar.com.sourcesistemas.controllers;

import java.security.Principal;
import java.util.Iterator;
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

	@Transactional
	@RequestMapping(value = "/admin/getSnipplets", method = RequestMethod.GET)
	public ModelAndView getSnipplets(String categoryId) {
		
		List<Snipplet> snipplets = snippletDAO.getSnippletsByCategory(Integer.parseInt(categoryId));
		ModelAndView mav = new ModelAndView("components/snippletComponent");
		mav.addObject("snipplets",snipplets);
		
		return mav;
		
	}
	
	@Transactional
	@RequestMapping(value = "/admin/getModal" , method = RequestMethod.GET)
	public ModelAndView getModal(String snippletId) {
		
		ModelAndView mav = new ModelAndView("modal/modalEditarSnipplet");
		Snipplet snipplet = snippletDAO.getSnippletById(Long.parseLong(snippletId));
		mav.addObject("snipplet",snipplet);
		
		return mav;
		
	}

	@Transactional
	@RequestMapping(value ="/admin/saveEditedSnipplet" , method = RequestMethod.POST)
	public String saveEditedSnipplet(String jsonSnipplet) {
		
		 Snipplet snippletAux = gsonUtility.getGsonWithExclusion().fromJson(jsonSnipplet, Snipplet.class);
		 Snipplet snipplet = snippletDAO.getSnippletById(snippletAux.getId());
		 
		 snipplet.setContenido(snippletAux.getContenido());
		 snipplet.setTitulo(snippletAux.getTitulo());
		 
		snippletDAO.saveSnipplet(snipplet);
		return "200ok";
		 
		 
		
	}
	
	@Transactional
	@RequestMapping(value ="/admin/createNewSnipplet", method =RequestMethod.POST)
	public String createNewSnipplet(String jsonSnipplet,String categoryId ,Principal principal) {
		
		Snipplet snippletAux = gsonUtility.getGsonWithExclusion().fromJson(jsonSnipplet, Snipplet.class);
		//FIXME when i have spring security
		User user = userDAO.getUsernameByName(principal.getName());
		
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
	@RequestMapping(value = "/admin/getModalAddSnipplet", method = RequestMethod.GET)
	public ModelAndView getModalAddSnipplet() {
		
		ModelAndView mav = new ModelAndView("modal/addSnipplet");
		return mav;
		
	}

	@Transactional
	@RequestMapping(value= "/admin/getModalCambioPasswd", method = RequestMethod.GET)
	public ModelAndView getModalPassword() {
		ModelAndView mav = new ModelAndView("modal/cambiarPasswd");
		return mav;
	}
	
	@Transactional
	@RequestMapping(value = "/admin/getModalAddCategory", method = RequestMethod.GET)
	public ModelAndView getModalAddCategory() {
		
		ModelAndView mav = new ModelAndView("modal/addCategory");
		return mav;
		
	}
	
	@Transactional
	@RequestMapping(value="/admin/createNewCategory", method =RequestMethod.GET)
	public String createNewCategory(String categoryTitle, Principal principal) {
		
		User user = userDAO.getUsernameByName(principal.getName());

		Category category = new Category();
		category.setNombreCategoria(categoryTitle);
		category.setUser(user);
		user.getCategory().add(category);
		userDAO.update(user);
		
		return "200ok";
	}

	@Transactional
	@RequestMapping(value ="/admin/deleteCategory", method =RequestMethod.POST)
	public String deleteCategory(String categoryId,Principal principal) {
		
		User user =userDAO.getUsernameByName(principal.getName());
		Iterator<Category> iterator = user.getCategory().iterator();
		long fixedId = Long.parseLong(categoryId);
		
		while(iterator.hasNext()) {
			Category category = iterator.next();
			if(category.getId() == fixedId) {
				iterator.remove();
				break;
			}
			
		}
		userDAO.saveUser(user);
		return "200ok";
		
	}
	
	@Transactional
	@RequestMapping(value = "/admin/eliminarSnipplet", method = RequestMethod.POST)
	public String eliminarSnipplet(String jsonSnipplet,String categoryId,Principal principal) {
		Snipplet snippletAux = gsonUtility.getGsonWithExclusion().fromJson(jsonSnipplet, Snipplet.class);
		
		long fixedId = Long.parseLong(categoryId);
		
		User user = userDAO.getUsernameByName(principal.getName());
		
		for(Category category :user.getCategory()) {
			
			if(category.getId() == fixedId) {
				
				Iterator<Snipplet>i = category.getSnipplets().iterator();
				while(i.hasNext()) {
					Snipplet snipplet = i.next();
					if (snipplet.getId().equals(snippletAux.getId())) {
						i.remove();
						break;
					}
				}
			}
		}

		userDAO.saveUser(user);
		return "200ok";
		
	}
	
	
}
