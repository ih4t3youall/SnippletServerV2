package ar.com.sourcesistemas.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.sourcesistemas.dao.UserDAO;
import ar.com.sourcesistemas.dto.SendDTO;
import ar.com.sourcesistemas.model.Category;
import ar.com.sourcesistemas.model.User;
import ar.com.sourcesistemas.utilities.GsonUtility;
import ar.com.sourcesistemas.utilities.ProgramHelpClass;

@RestController
public class ApiController {
//	@Autowired
//	private Persistencia persistencia;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProgramHelpClass progHelp;
	
	@Autowired
	private GsonUtility gson;

//	private ObjectMapper objMapper = new ObjectMapper();
	@Transactional
	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String guardarCategoria(@RequestBody String requestSendDTO){
		
		SendDTO sendDTO = gson.getGson().fromJson(requestSendDTO, SendDTO.class);
		
		User recoveredUser = userDAO.getUsernameByName(sendDTO.getUsername());
		
		Category newCat = progHelp.getNewCategory();
		
		List<Category> categorys = recoveredUser.getCategory();
		
		for (Category category : categorys) {
			System.out.println(category.getNombreCategoria());
			
			
		}
		
		recoveredUser.getCategory().add(newCat);
		
		userDAO.saveUser(recoveredUser);
		
		return "esito";
	}
	
}
