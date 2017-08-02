package ar.com.sourcesistemas.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.internal.util.SerializationHelper;
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

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProgramHelpClass progHelp;
	
	@Autowired
	private GsonUtility gson;


	@Transactional
	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String guardarCategoria(@RequestBody String requestSendDTO){
		gson.getGson().fromJson(requestSendDTO,SendDTO.class);
		SendDTO sendDTO = gson.getGson().fromJson(requestSendDTO, SendDTO.class);
		User recoveredUser = userDAO.getUsernameByName(sendDTO.getUsername());
		
		List<Category> recoveredCategory =  recoveredUser.getCategory();
		
		
		
		
		userDAO.saveUser(recoveredUser);
		String something = gson.getGsonWithExclusion().toJson(recoveredUser);
		return something;
	}
	
}
