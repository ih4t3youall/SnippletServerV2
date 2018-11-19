package ar.com.sourcesistemas.controllers;

import ar.com.commons.send.domain.Category;
import ar.com.commons.send.domain.Snipplet;
import ar.com.commons.send.domain.User;
import ar.com.sourcesistemas.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private UserDAO userDao;

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		List<User> listUsers = userDao.list();
		ModelAndView model = new ModelAndView("home");
		listUsers.get(0).getCategory();
		model.addObject("user", listUsers.get(0));
		return model;
	}

	@RequestMapping(value = "/admin/", method = RequestMethod.GET)
	@Transactional
	public ModelAndView index(Principal principal) {

		List<User> listUsers = userDao.list();
		User viewUser = null;

		for (User user : listUsers) {

			if (user.getName().equals(principal.getName())) {
				viewUser = user;
				break;

			}
		}

		ModelAndView model = new ModelAndView("home");
		List<Category> cat = viewUser.getCategory();
		model.addObject("category", cat);
		return model;

	}

	// @RequestMapping(value="/admin/prueba" , method = RequestMethod.GET)
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

	@Transactional
	@RequestMapping(value = "/admin/cambiarPasswd")
	public @ResponseBody String cambiarPasswd(String passwd1, String passwd2,Principal principal) {

		if (passwd1.equals(passwd2)) {
			try {
			userDao.cambiarPassword(principal.getName(),passwd1);
			}catch(Exception e) {
				
				return "error 500";
			}
			return "contrasenia cambiada con exito";
		} else {

			return "las contrasenias no coinciden";

		}
	}
}
