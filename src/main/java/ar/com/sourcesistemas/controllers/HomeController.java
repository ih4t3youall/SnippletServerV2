package ar.com.sourcesistemas.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.com.sourcesistemas.dao.UserDAO;
import ar.com.sourcesistemas.dto.CategoriaDTO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.Snipplet;
import ar.com.sourcesistemas.model.User;
import ar.com.sourcesistemas.utilities.ConvertToDTOUtility;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/admin/home")
	public ModelAndView home() {
		List<User> listUsers = userDao.list();
		ModelAndView model = new ModelAndView("home");
		listUsers.get(0).getCategory();
		model.addObject("user", listUsers.get(0));
		return model;
	}
	
	@RequestMapping("/admin/")
	@Transactional
	public ModelAndView index() {
		
		List<User> listUsers = userDao.list();
		ModelAndView model = new ModelAndView("home");
		listUsers.get(0).getCategory();
		
		List<CategoriaDTO> cat =ConvertToDTOUtility.convertToUserDTO(listUsers.get(0));
		model.addObject("category", cat);
		return model;
		
		
	}
	
	@RequestMapping(value="/admin/prueba")
	public ModelAndView prueba() {
		ModelAndView mav = new ModelAndView("exito");
		
		User user = new User();
		user.setName("nombre");
		user.setPassword("password");
		
		Category cat = new Category();
		cat.setNombreCategoria("nombre categoria");
		
		List<Category> cats = new ArrayList<Category>();
		
		
		cats.add(cat);
		user.setCategory(cats);
		
		Snipplet snip = new Snipplet();
		snip.setCategoria(cat);
		snip.setTitulo("titulo");
		snip.setContenido("contenido");
		
		Snipplet snip2 = new Snipplet();
		snip2.setCategoria(cat);
		snip2.setTitulo("titulo");
		snip2.setContenido("contenido");
		
		List<Snipplet> snipplets = new ArrayList<Snipplet>();
		snipplets.add(snip2);
		snipplets.add(snip);
		
		cat.setSnipplets(snipplets);
		cat.setUser(user);
		
		userDao.saveUser(user);
		
		
		return mav;
	}
	
}
